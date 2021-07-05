package com.example.myproject22.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class GoalRecord {
    // i dont need description here
    private String goalName;
    private Float goalMoney;
    private String date_start;
    private String goalImage;

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public Float getGoalMoney() {
        return goalMoney;
    }

    public void setGoalMoney(Float goalMoney) {
        this.goalMoney = goalMoney;
    }

    public String getDate_start() {
        String [] dayStr = date_start.split(" ");
        return dayStr[3];
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getGoalImage() {
        return goalImage;
    }

    public void setGoalImage(String goalImage) {
        this.goalImage = goalImage;
    }

    public GoalRecord(String goalName, Float goalMoney, String date_start, String goalImage) {
        this.goalName = goalName;
        this.goalMoney = goalMoney;
        this.date_start = date_start;
        this.goalImage = goalImage;
    }

    public  static long getDayDiffByStr(String date)  {
        long diff_in_days = 0;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            long diff_in_times = new Date().getTime() - formatter.parse(date).getTime();
            diff_in_days= TimeUnit
                    .MILLISECONDS
                    .toDays(diff_in_times)
                    % 365;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diff_in_days;
    }
}

