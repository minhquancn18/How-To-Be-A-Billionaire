package com.example.myproject22.Presenter.Interface;

import android.graphics.Bitmap;
import android.view.View;

import java.io.File;
import java.io.IOException;

public interface SignUpInterface {

    //region Create and Set
    //Set init
    public void SetInit();
    //Hide keyboard
    public void HideKeyboard(View view);
    //endregion

    //region Condition
    public Boolean GetNoUserName(String username);
    public Boolean GetNoPassword(String password);
    public Boolean GetNoSalary(String salary, String money_string);
    public Boolean GetNoFullName(String fullname);
    public Boolean GetNoEmail(String email);
    //endregion

    //region Image
    public void ChooseImage();
    public void TakeImageFromGallery();
    public void TakeImageFromCamera();
    public File CreateImageFile() throws IOException;
    public Boolean IsNullImage(Bitmap bitmap);
    public String GetStringImage();
    public void DenyPermission();
    public void DeleteImage();
    //endregion

    //region Handle Click Event
    public void BtnSignUp();
    public void TextViewClick();
    //endregion

    //region Upload data from server
    public void UploadUserToServer(String username, String password, String email,
                                   String fullname, String salary, String image);
    public void UploadUserNoImageToServer(String username, String password, String email,
                                          String fullname, String salary);
    //endregion

}
