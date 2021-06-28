package com.example.myproject22.Presenter;

import com.example.myproject22.Model.UserClass;

import java.io.File;
import java.io.IOException;

public interface SavingInterface {

    //region Set init, get bundle
    public void InitViews();
    public void GetBundleData();
    //endregion

    //region Create Data bar chart
    public void CreateDataBarChart();
    //endregion

    //region Loading to layout
    public void LoadDataFromServerToBarChart();
    public void LoadUser(UserClass userClass);
    public void LoadDataFromServer();
    //endregion

    //region Fetch data from server
    public void FetchSavingDetailFromServer(String date_start, String date_end);
    public void FetchArrayDateFromServer();
    public void FetchUserFromServer();
    //endregion

    //region Handle Button Click
    public void BtnUserClick();
    //endregion


}

