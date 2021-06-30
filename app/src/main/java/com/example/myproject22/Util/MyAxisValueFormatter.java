package com.example.myproject22.Util;

import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class MyAxisValueFormatter extends ValueFormatter  {
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        axis.setLabelCount(2, true);
        Log.d("AAAAAA", "getFormattedValue: " + value);
        return "@ " + value;
    }

}
