package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.udacity.stockhawk.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class StockDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Stock> {

    private final static int LOADER_ID = 2;
    private static final String TAG = StockDetails.class.getSimpleName();
    private List<Entry> mEntries;

    @BindView(R.id.chart)
    LineChart mLineChart;

    @BindView(R.id.chart_progress_bar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Uri uri = intent.getData();
        String symbol = uri.getPathSegments().get(1);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(symbol +" Graph");
        }
        mEntries = new ArrayList<>();

        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.stock_key), symbol);
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Stock> loader = loaderManager.getLoader(LOADER_ID);
        if (loader == null) {
            loaderManager.initLoader(LOADER_ID, bundle, this).forceLoad();
        } else {
            loaderManager.restartLoader(LOADER_ID, bundle, this).forceLoad();
        }
    }

    @Override
    public Loader<Stock> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Stock>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public Stock loadInBackground() {
                if (args != null) {
                    Calendar from = Calendar.getInstance();
                    from.add(Calendar.DATE, -10);
                    Calendar to = Calendar.getInstance();
                    to.add(Calendar.DATE, 0);
                    try {
                        return YahooFinance.get(args.getString(getString(R.string.stock_key), "YHOO"),
                                from, to, Interval.DAILY);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onStopLoading() {
                super.onStopLoading();

            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Stock> loader, Stock data) {
        mProgressBar.setVisibility(View.GONE);
        mLineChart.setVisibility(View.VISIBLE);
        if (data != null) {
            try {
                List<HistoricalQuote> historicalQuoteList = data.getHistory();
                Long referenceTime = historicalQuoteList.get(historicalQuoteList.size()-1).getDate().getTime().getTime();
                for (HistoricalQuote historicalQuote: historicalQuoteList) {
                    mEntries.add(new Entry(
                            (historicalQuote.getDate().getTime().getTime()-referenceTime)/(1000*60*60),
                            historicalQuote.getHigh().floatValue()
                    ));
                }

                Collections.sort(mEntries, new EntryXComparator());

                Legend l = mLineChart.getLegend();
                l.setFormSize(10f);
                l.setForm(Legend.LegendForm.CIRCLE);
                l.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
                l.setTextSize(12f);
                l.setTextColor(Color.RED);


                XAxis xAxis = mLineChart.getXAxis();
                xAxis.setValueFormatter(new XAxisLabelFormatter(referenceTime));
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextColor(Color.WHITE);
                xAxis.setDrawGridLines(false);
                xAxis.setAxisLineWidth(2);

                YAxis left = mLineChart.getAxisLeft();
                left.setTextColor(Color.WHITE);
                left.setDrawGridLines(false);
                left.setValueFormatter(new YAxisLabelFormatter());
                left.setAxisLineWidth(2);
                mLineChart.getAxisRight().setEnabled(false);

                mLineChart.setScaleEnabled(false);
                mLineChart.setPinchZoom(false);
                mLineChart.setBorderColor(Color.WHITE);

                LineDataSet dataSet = new LineDataSet(mEntries, "Stock");
                LineData lineData = new LineData(dataSet);
                lineData.setValueTextColor(Color.YELLOW);
                mLineChart.setData(lineData);
                Description description = new Description();
                description.setTextColor(Color.RED);
                description.setTextSize(12);
                description.setText(getString(R.string.stock_Description));
                mLineChart.setDescription(description);
                mLineChart.invalidate();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Log.d(TAG, "onLoadFinished: " + "No data to show!");
        }
    }

    @Override
    public void onLoaderReset(Loader<Stock> loader) {

    }
}