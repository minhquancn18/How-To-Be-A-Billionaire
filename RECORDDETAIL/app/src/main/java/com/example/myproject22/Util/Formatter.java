package com.example.myproject22.Util;

import java.text.NumberFormat;
import java.util.Locale;

public class Formatter {
    public static String getCurrencyStr(String str) {
        Locale VN = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(VN);
        String moneyGoal2 = currencyFormatter.format(Double.parseDouble(str));
        moneyGoal2 = moneyGoal2.substring(0, moneyGoal2.length() - 1);
        return moneyGoal2;
    }
}
