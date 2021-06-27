package com.example.myproject22.Presenter.Interface;

import android.view.View;

public interface NewGoalInterface {



    // database
    public void AddNewGoalToServer();





    // init
    public void GetBundle();

    public void SetUpBeforeInit();

    public void InitView();

    public void DisableViews();

    public void EnableViews();

    //image
    public void ChooseImage();

    public void PressBack();

    public void getMessage();
}
