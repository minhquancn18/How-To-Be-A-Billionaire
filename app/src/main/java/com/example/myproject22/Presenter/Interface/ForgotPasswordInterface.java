package com.example.myproject22.Presenter.Interface;

import android.view.View;

public interface ForgotPasswordInterface {

    //region Create and Set
    public void SetInIt();
    public void HideKeyboard(View view);
    //endregion

    ////region Condition
    public Boolean GetNoUserName(String username);
    public Boolean GetNoPassword(String password);
    public Boolean GetNoEmail(String email);
    public Boolean GetNoConfirmPassword(String password, String password_confirm);
    //endregion

    //region Handle Click
    public void BtnForgetClick();
    public void TextViewClick();
    //endregion

    //region Upload password to server
    public void UploadNewPassword(String username, String email, String password);
    //endregion

}
