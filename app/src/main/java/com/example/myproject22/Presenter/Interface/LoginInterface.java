package com.example.myproject22.Presenter.Interface;

import android.view.View;

import com.example.myproject22.View.Activity.LoginActivity;

public interface LoginInterface {

    //region Create and Set
    //Set init
    public void SetInIt();
    //Hide keyboard
    public void HideKeyboard(View view);
    //endregion

    //region Check Internet
    public Boolean IsConnect(LoginActivity loginActivity);
    public void ShowCustomDialog();
    //endregion

    //region Condition
    public Boolean GetNoUserName(String username);
    public Boolean GetNoPassword(String password);
    //endregion

    //region Handle Click Event
    public void BtnSignIn();
    public void TextViewClick();
    public void TextViewForgetClick();
    //endregion

    //region Login From Server
    public void LoginFromServer(String username, String password);
    //endregion

}
