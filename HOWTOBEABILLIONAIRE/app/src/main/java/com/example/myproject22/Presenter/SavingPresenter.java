package com.example.myproject22.Presenter;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.example.myproject22.Model.UserClass;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SavingPresenter {
    SavingInterface mSavingInterface;

    public SavingPresenter(SavingInterface mSavingInterface) {
        this.mSavingInterface = mSavingInterface;
    }

    //region Set init, get bundle

    public void initView(){
        mSavingInterface.InitViews();
    }

    public void getBundleData(){
        mSavingInterface.GetBundleData();
    }

    //endregion

    //region Create Barchart

    public void createDataBarChart(){
        mSavingInterface.CreateDataBarChart();
    }

    //endregion

    //region Fetch Data from server

    public void fetchSavingDetailFromServer(String date_start, String date_end){
        mSavingInterface.FetchSavingDetailFromServer(date_start,date_end);
    }

    public void fetchArrayDateFromServer(){
        mSavingInterface.FetchArrayDateFromServer();
    }

    public void fetchUserFromServer(){
        mSavingInterface.FetchUserFromServer();
    }

    //endregion

    //region Load data from server to layout

    public void loadDataFromServerToBarChart(){
        mSavingInterface.LoadDataFromServerToBarChart();
    }

    public void loadDataFromServer(){
        mSavingInterface.LoadDataFromServer();
    }

    public void loadUser(UserClass userClass){
        mSavingInterface.LoadUser(userClass);
    }
    //endregion

    //region Handle Button Click

    public void btnUserClick(){
        mSavingInterface.BtnUserClick();
    }

    //endregion

    //region Các static function sử dụng

    //Tìm thứ hai từ 1 ngày bất kỳ
    public static Date FindMondayFromDate(Date date_from){
        Calendar cal = Calendar.getInstance();
        Date date_monday = new Date();

        cal.setTime(date_from);
        int dow = cal.get(Calendar.DAY_OF_WEEK);
        switch (dow){
            case 1: {
                cal.add(Calendar.DATE, -6);
                date_monday = cal.getTime();
                return date_monday;
            }
            case 2: {
                date_monday = cal.getTime();
                return date_monday;
            }
            case 3: {
                cal.add(Calendar.DATE, -1);
                date_monday = cal.getTime();
                return date_monday;
            }
            case 4: {
                cal.add(Calendar.DATE, -2);
                date_monday = cal.getTime();
                return date_monday;
            }
            case 5: {
                cal.add(Calendar.DATE, -3);
                date_monday = cal.getTime();
                return date_monday;
            }
            case 6: {
                cal.add(Calendar.DATE, -4);
                date_monday = cal.getTime();
                return date_monday;
            }
            default: {
                cal.add(Calendar.DATE, -5);
                date_monday = cal.getTime();
                return date_monday;
            }
        }
    }

    //Tìm ngày cuối cùng từ ngày thứ đã tìm ở trên
    public static Date FindEndOfWeek(Date date_from){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date_from);
        cal.add(Calendar.DATE, 7);

        Date date_end = cal.getTime();
        return date_end;
    }

    //Chuyển đổi từ Date sang String
    public static String StringFromDate(Date date){
        SimpleDateFormat format_string = new SimpleDateFormat("yyyy-MM-dd");
        String string_date = format_string.format(date);
        return string_date;
    }

    //Tìm khoảng ngày từ 2 ngày đã biết
    public static long CalculateDateUse(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null)
            return 0;
        return (long) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    //Chuyển đổi từ String sang Date
    public static Date DateFromString(String date_string){
        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = curFormater.parse(date_string);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    //Chuyển đổi tiền thành String
    public static String GetStringMoney(double money){
        String money_string = "";
        Log.i("MONEY", String.valueOf(money));
        if(money > 1000000000.0){
            double money_bil = money / 1000000000.0;
            double money_round = (int)(Math.round(money_bil * 100))/100.0;
            money_string = money_round + "tỷ";
        }
        else if (money > 1000000.0){
            double money_mil = money / 1000000.0;
            double money_round = (int)(Math.round(money_mil * 100))/100.0;
            money_string = money_round + "tr";
        }else{
            double money_thousand = money / 1000.0;
            int money_round = (int)Math.round(money_thousand);
            money_string = money_round + "k";
        }

        return money_string;
    }

    //endregion


}
