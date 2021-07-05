package com.example.myproject22.Presenter.Presenter;

import com.example.myproject22.Presenter.Interface.ReportCategoryDetailInterface;

public class ReportCategoryDetailPresenter {
    ReportCategoryDetailInterface anInterface;

    public ReportCategoryDetailPresenter(ReportCategoryDetailInterface anInterface) {
        this.anInterface = anInterface;
    }

    //region Set init, get bundle

    public void setInit(){
        anInterface.SetInit();
    }

    public void getBundleData(){
        anInterface.GetBundleData();
    }

    //endregion

    //region Loading

    public void loadTotalArray(){
        anInterface.LoadTotalArray();
    }

    public void loadData(){
        anInterface.LoadData();
    }

    //endregion

    //region Fetch income, outcome

    public void fetchIncomeFromServer(){
        anInterface.FetchIncomeDetailInServer();
    }

    public void fetchOutcomeFromServer(){
        anInterface.FetchOutcomeDetailInServer();
    }

    //endregion

}
