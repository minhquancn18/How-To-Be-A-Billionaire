package com.example.myproject22.Presenter;

public interface ReportCategoryDetailInterface {

    //region Set init, get bundle
    //Set init
    public void SetInit();
    //Get Bundle Data
    public void GetBundleData();
    //endregion

    //region Fetch data from server
    public void FetchIncomeDetailInServer();
    public void FetchOutcomeDetailInServer();
    //endregion

    //region Loading
    //Load Array include income and outcome
    public void LoadTotalArray();
    //Load Layout
    public void LoadData();
    //endregion

}
