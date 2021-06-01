package com.example.myproject22.Presenter;

import android.content.Context;

public interface AddingMoneyInterface {
    public void AddingCategoryFail();
    public void GetBuddleSuccessful();
    public void GetNoMoneyData();
    public void GetNoCategoryData();
    public void GetAddSuccessful();
    public  void GetSpendSuccessful();
    public void GetDataFail();
    public void ButtonCategoryClickWithBoth(Context context,String sMoney, String sDescription);
}
