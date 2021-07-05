package com.example.myproject22.Util;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class FormatterForChart {

    public static  ValueFormatter valueFormatter = new ValueFormatter() {
        @Override
        public String getAxisLabel(float value, AxisBase axis) {

            DecimalFormat df = new DecimalFormat("#.#");
            df.setRoundingMode(RoundingMode.CEILING);

            if(Math.abs(value ) >= 1000000){
                value = value /1000000;
                return df.format(value)  + " M";
            }
            if(Math.abs(value ) >= 1000){
                value = value /1000;
                df = new DecimalFormat("#");
                return df.format(value)  + " K";
            }
            return df.format(value) + " VND";
        }

        @Override
        public String getBarLabel(BarEntry barEntry) {
            return "SS";
        }
    };

}
