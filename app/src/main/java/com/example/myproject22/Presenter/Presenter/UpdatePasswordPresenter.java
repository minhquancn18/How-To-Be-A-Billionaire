package com.example.myproject22.Presenter.Presenter;

import android.view.View;

import com.example.myproject22.Presenter.Interface.UpdatePasswordInterface;

public class UpdatePasswordPresenter {
    private UpdatePasswordInterface anInterface;

    public UpdatePasswordPresenter(UpdatePasswordInterface anInterface) {
        this.anInterface = anInterface;
    }

    //region Set Init, get bundle, keyboard

    public void setInit() {
        anInterface.SetInit();
    }

    public void getBundleData(){
        anInterface.GetBundleData();
    }

    public void hideKeyboard(View view){
        anInterface.HideKeyboard(view);
    }

    //endregion

    //region Handle Button Click

    public void btnSaveClick(){
        anInterface.BtnSaveClick();
    }

    public void btnCancelClick(){
        anInterface.BtnCancelClick();
    }

    //endregion

    //region Condition

    public Boolean getNoPassword(String password, String oldpassword){
        return anInterface.GetNoPassword(password,oldpassword);
    }

    public Boolean getNoConfirmPassword(String password, String password_confirm) {
        return anInterface.GetNoConfirmPassword(password,password_confirm);
    }

    public Boolean getNoOldPassword(String password){
        return anInterface.GetNoOldPassword(password);
    }

    //endregion

    //region Upload password to server

    public void uploadPasswordToServer(String oldpassword, String newpassword){
        anInterface.UploadPasswordToServer(oldpassword,newpassword);
    }

    //endregion

}
