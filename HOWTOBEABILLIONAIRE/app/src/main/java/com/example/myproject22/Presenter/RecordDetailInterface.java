package com.example.myproject22.Presenter;

import android.view.View;

public interface RecordDetailInterface {

    //region Set init, get bundle
    //Set init
    public void SetInit();
    //Get Bundle Data
    public void GetBundleData();
    //endregion

    //region Load Data to Layout
    public void LoadDataToLayout();
    public void LoadDataToLayoutNoAudio();
    //LoadFromServer
    public void LoadFromServer();
    //endregion

    //region Fetch Data from server
    public  void FetchIncomeDataFromServer();
    public void FetchOutcomeDataFromServer();
    //endregion

    //region Handle Media
    //Prepare Media Player
    public void PrepareMedia(String url);
    //Get time media
    public String GetTimeMedia(long millionsecond);
    public void UpdateSeekbar();
    public void GetPauseClick();
    public boolean GetSeekbarTouch(View view);
    public void SetCompleteMedia();
    public void SetNext5Second();
    public void SetBack5Second();
    public void SetRealseMedia();
    //endregion

}
