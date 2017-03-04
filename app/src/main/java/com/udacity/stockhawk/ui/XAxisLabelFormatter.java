package com.udacity.stockhawk.ui;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


public class XAxisLabelFormatter implements IAxisValueFormatter {
    private SimpleDateFormat mSimpleDateFormat;
    private Long referenceTime;
    private DecimalFormat mDecimalFormat;

    public XAxisLabelFormatter(Long referenceTime) {
        mSimpleDateFormat = new SimpleDateFormat("dd/MM");
        this.referenceTime = referenceTime;
        mDecimalFormat = new DecimalFormat("#.##");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        value = Float.parseFloat(mDecimalFormat.format(value*1000*60*60));
        return mSimpleDateFormat.format(
                value + referenceTime
        );
    }
}
