package com.example.myproject22.Presenter;

public class ReportCategoryPresenter {
    ReportCategoryInterface anInterface;

    public ReportCategoryPresenter(ReportCategoryInterface anInterface) {
        this.anInterface = anInterface;
    }

    //region Set and Get

    public void setInit(){
        anInterface.SetInit();
    }

    public void getBundleData(){
        anInterface.GetBundleData();
    }

    //endregion

    //region Loading

    public void loadRecycleView(){
        anInterface.LoadRecycleView();
    }

    public void loadData(){
        anInterface.LoadData();
    }

    //endregion

    //region Fetch Data

    public void fetchIncomeInServer(){
        anInterface.FetchIncomeInServer();
    }

    public  void fetchOutcomeInServer(){
        anInterface.FetchOutcomeInServer();
    }

    //endregion

}
