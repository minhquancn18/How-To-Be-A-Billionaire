package com.example.myproject22.Presenter;

import android.graphics.Bitmap;
import android.view.View;

import com.android.volley.toolbox.StringRequest;
import com.example.myproject22.Model.UserClass;

import java.io.File;
import java.io.IOException;

public interface UpdateUserInterface {

    //region SetInit, Get bundle, Keyboard
    public void SetInit();
    public void GetBundleData();
    public void HideKeyboard(View view);
    //endregion

    //region Fetch User
    public void FetchUserFromServer();
    //endregion

    //region Loading
    public void LoadUser(UserClass userClass);
    public void LoadDataToLayout();
    //endregion

    //region Handle Button Click
    public void BtnSaveClick();
    public void BtnCancelClick();
    //endregion

    //region Image User
    public void ChooseImage();
    public void TakeImageFromGallery();
    public void TakeImageFromCamera();
    public File createImageFile() throws IOException;
    public void DeleteImage();
    public Boolean IsNullImage(Bitmap bitmap);
    public String GetStringImage();
    //endregion

    //region Condition
    public Boolean GetNoFullName(String fullname);
    public Boolean GetNoEmail(String email);
    //endregion

    //region Put Data To Server
    public void UploadUserToServer(String fullname, String email, String image);
    public void UploadUserToServerNoImage(String fullname, String email);
    //endregion

}
