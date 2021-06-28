package com.example.myproject22.Presenter;

import android.view.View;

public interface UpdatePasswordInterface {

    //region SetInit, Get Bundle, Keyboard
    public void SetInit();
    public void GetBundleData();
    public void HideKeyboard(View view);
    //endregion

    //region Handle Button CLick
    public void BtnSaveClick();
    public void BtnCancelClick();
    //endregion

    //region Condition
    public Boolean GetNoPassword(String password, String oldpassword);
    public Boolean GetNoOldPassword(String password);
    public Boolean GetNoConfirmPassword(String password, String password_confirm);
    //endregion

    //region Upload Password to Server
    public void UploadPasswordToServer(String oldpassword, String newpassword);
    //endregion

}
