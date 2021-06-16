package com.example.myproject22.Presenter;

public class ReportCategoryDetailPresenter {
    ReportCategoryDetailInterface anInterface;

    public ReportCategoryDetailPresenter(ReportCategoryDetailInterface anInterface) {
        this.anInterface = anInterface;
    }

    public void setInit(){
        anInterface.SetInit();
    }

    public void getBundleData(){
        anInterface.GetBundleData();
    }

    public void loadRecycleView(){anInterface.LoadRecycleView();}

}
