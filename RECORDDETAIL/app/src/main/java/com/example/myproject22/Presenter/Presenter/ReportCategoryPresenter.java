package com.example.myproject22.Presenter.Presenter;

import com.example.myproject22.Presenter.Interface.ReportCategoryInterface;

public class ReportCategoryPresenter {
    ReportCategoryInterface anInterface;


    public ReportCategoryPresenter(ReportCategoryInterface anInterface) {
        this.anInterface = anInterface;
    }

    public void setInit(){
        anInterface.SetInit();
    }

    public void getBundleData(){
        anInterface.GetBundleData();
    }
}
