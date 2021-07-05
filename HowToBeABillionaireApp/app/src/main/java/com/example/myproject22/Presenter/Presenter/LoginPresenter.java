package com.example.myproject22.Presenter.Presenter;

import android.view.View;

import com.example.myproject22.Presenter.Interface.LoginInterface;
import com.example.myproject22.View.Activity.LoginActivity;

public class LoginPresenter {
    private LoginInterface anInterface;

    public LoginPresenter(LoginInterface anInterface) {
        this.anInterface = anInterface;
    }

    //region Create and Set

    public void setInit(){
        anInterface.SetInIt();
    }

    public void hideKeyboard(View view){
        anInterface.HideKeyboard(view);
    }

    //endregion

    //region Condition

    public Boolean getNoUserName(String username){
        return anInterface.GetNoUserName(username);
    }

    public Boolean getNoPassword(String password){
        return anInterface.GetNoPassword(password);
    }

    //endregion

    //region Handle Click

    public void btnSignIn(){
        anInterface.BtnSignIn();
    }

    public void textViewClick(){
        anInterface.TextViewClick();
    }

    public void textViewForgetClick(){
        anInterface.TextViewForgetClick();
    }

    //endregion

    //region Login to Server

    public void loginFromServer(String username, String password){
        anInterface.LoginFromServer(username,password);
    }

    //endregion

}
