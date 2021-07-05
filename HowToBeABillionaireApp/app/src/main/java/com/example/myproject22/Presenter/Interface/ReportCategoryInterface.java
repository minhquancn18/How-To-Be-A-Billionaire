package com.example.myproject22.Presenter.Interface;

public interface ReportCategoryInterface {

    //region Get and Set
    //Set init
    public void SetInit();
    //Get Bundle data
    public void GetBundleData();
    //endregion

    //region Loading
    //Load RecycleView
    public void LoadRecycleView();
    //Load layout
    public void LoadData();
    //endregion

    //region Fetch data income, outcome from server
    public void FetchIncomeInServer();
    public void FetchOutcomeInServer();
    //endregion

}
