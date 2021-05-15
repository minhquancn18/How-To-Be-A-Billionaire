package com.example.myproject22.Presenter;

public class SavingPresenter {
    SavingInterface mSavingInterface;

    public SavingPresenter(SavingInterface mSavingInterface) {
        this.mSavingInterface = mSavingInterface;
    }


    public void LoadGetTietKiemData() {
        mSavingInterface.LoadChiTietTietKiem();
    }
    public void LoadTietKiem(){
        mSavingInterface.LoadTietKiem();
    }
    public void LoadMucTieu(){
        mSavingInterface.LoadMucTieu();
    }
}
