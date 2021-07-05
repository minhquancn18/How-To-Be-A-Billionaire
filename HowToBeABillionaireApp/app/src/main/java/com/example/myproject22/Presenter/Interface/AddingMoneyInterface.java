package com.example.myproject22.Presenter.Interface;

import android.graphics.Bitmap;
import android.view.View;

import java.io.File;
import java.io.IOException;

public interface AddingMoneyInterface {

    //region Set component
    public void SetInit();
    public void GetDataBundle();
    //Hide Keyboard
    public void HideKeyboard(View view);
    //endregion

    //region Loading Data to server
    public void LoadDataToServer();
    //endregion

    //region Category
    //Load income and spending category
    public void LoadCategory();
    //Find id category by its name
    public int FindIdByName(String name);
    //Fetch category from server
    public void FetchIncomeCategory();
    public void FetchOutcomeCategory();
    //endregion

    //region Image
    //Load Image from Camera of Gallery
    public void ChooseImage();

    //Take image from camera and gallery
    public void TakeImageFromGallery();
    public void TakeImageFromCamera();

    public void DeleteRecord();
    public void DeleteImage();
    public File createImageFile() throws IOException;

    //Check image null
    public Boolean IsNullImage(Bitmap bitmap);
    public String GetStringImage();
    //endregion

    //region Audio
    //Capture record and play audio
    public void CaptureRecord();
    public void CaptureAudio();

    //Record and Audio
    public void StartRecord();
    public void StopRecord();
    public void StartAudio();
    public void StopAudio();

    //Check audio null
    public Boolean IsNullAudio();
    public byte[] Convert3gbToByte();
    public String GetStringAudio();

    //Reset
    public void ResetSound();
    //endregion

    //region Condition
    //Check valid number
    public void IsValidNumber(CharSequence s);
    //If money not numeric or category, toast user
    public Boolean GetNoMoneyData(String money);
    public void GetNoCategoryData();
    //endregion

    //Saving
    public void SavingMoneyData(String money, String description, int category_id, String image, String audio);

}
