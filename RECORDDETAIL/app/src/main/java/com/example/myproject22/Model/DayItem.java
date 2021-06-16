package com.example.myproject22.Model;

import java.util.Date;

public class DayItem {
    public Date date;
    public String DateString;
    public int numberOfRecord;

    public DayItem(Date date, int numberOfRecord) {
        this.date = date;
        this.numberOfRecord = numberOfRecord;
    }

    public DayItem(String dateString, int numberOfRecord) {
        DateString = dateString;
        this.numberOfRecord = numberOfRecord;
    }

    public DayItem(Date date, String dateString, int numberOfRecord) {
        this.date = date;
        DateString = dateString;
        this.numberOfRecord = numberOfRecord;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumberOfRecord() {
        return numberOfRecord;
    }

    public void setNumberOfRecord(int numberOfRecord) {
        this.numberOfRecord = numberOfRecord;
    }

    public String getDateString() {
        return DateString;
    }

    public void setDateString(String dateString) {
        DateString = dateString;
    }
}
