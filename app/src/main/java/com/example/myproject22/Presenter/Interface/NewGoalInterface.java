package com.example.myproject22.Presenter.Interface;

import java.io.File;
import java.io.IOException;

public interface NewGoalInterface {



    // database
    public void AddNewGoalToServer();





    // init
    public void GetBundle();

    public void SetUpBeforeInit();

    public void InitView();

    void TakeImageFromGallery();

    public void DisableViews();

    public void EnableViews();

    void TakeImageFromCamera();

    File createImageFile() throws IOException;

    //image
    public void ChooseImage();

    public void PressBack();

    public void getMessage();
}
