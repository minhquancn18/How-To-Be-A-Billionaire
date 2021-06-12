package com.example.myproject22.Presenter;

import android.app.Activity;
import android.graphics.Bitmap;

public interface AddingMoneyInterface {
    public void AddingCategoryFail();
    public void GetBuddleSuccessful();

    public void GetNoCategoryData();
    public void GetAddSuccessful();
    public  void GetSpendSuccessful();
    public void GetDataFail();

    //Set component
    public void SetInit();

    //Load income and spending category
    public void LoadCategory();

    //Check Permission
    public Boolean CheckPermissionRecord();
    public Boolean CheckPermissionImage();
    public Boolean CheckPermissionRead();

    //Load Image from Camera of Gallery
    public void ChooseImage();

    //Capture record and play audio
    public void CaptureRecord();
    public void CaptureAudio();

    //Check valid number
    public void IsValidNumber(CharSequence s);

    //Find id category by its name
    public int FindIdByName(String name);

    //Check image null
    public Boolean IsNullImage(Bitmap bitmap);
    public String GetStringImage();

    //Check audio null
    public Boolean IsNullAudio();
    public byte[] Convert3gbToByte();
    public String GetStringAudio();

    //Saving
    public void SavingMoneyData(String money, String description, int category_id, String image, String audio);

    //If money not numeric, toast user
    public void GetNoMoneyData();

}
