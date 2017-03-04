package com.udacity.stockhawk.ui;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class YAxisLabelFormatter implements IAxisValueFormatter{

    DecimalFormat mDecimalFormat;

    public YAxisLabelFormatter() {
        mDecimalFormat = new DecimalFormat("#.##");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mDecimalFormat.format(value) + "$";
    }
}
