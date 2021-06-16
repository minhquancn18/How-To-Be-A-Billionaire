package com.example.myproject22.Presenter;

public class DashboardPresenter {
    DashboardInterface anInterface;

    public DashboardPresenter(DashboardInterface anInterface) {
        this.anInterface = anInterface;
    }

    public void setInit(){
        anInterface.SetInit();
    }

    public void setData(){
        anInterface.SetData();
    }
}
