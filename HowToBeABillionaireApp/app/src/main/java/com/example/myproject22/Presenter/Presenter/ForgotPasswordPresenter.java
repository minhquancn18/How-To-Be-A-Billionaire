package com.example.myproject22.Presenter.Presenter;

import android.view.View;

import com.example.myproject22.Presenter.Interface.ForgotPasswordInterface;

public class ForgotPasswordPresenter {
    ForgotPasswordInterface anInterface;

    public ForgotPasswordPresenter(ForgotPasswordInterface anInterface) {
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

    public Boolean getNoUserName(String username) {
        return anInterface.GetNoUserName(username);
    }

    public Boolean getNoPassword(String password) {
        return anInterface.GetNoPassword(password);
    }

    public Boolean getNoEmail(String email){
        return anInterface.GetNoEmail(email);
    }

    public Boolean getNoConfirmPassword(String password, String password_confirm){
        return anInterface.GetNoConfirmPassword(password, password_confirm);
    }

    //endregion

    //region Handle Click

    public void btnForgetClick(){
        anInterface.BtnForgetClick();
    }

    public void textViewClick(){
        anInterface.TextViewClick();
    }

    //endregion

    //region Update Password to server

    public void uploadNewPassword(String username, String email, String password){
        anInterface.UploadNewPassword(username,email,password);
    }

    //endregion

}
