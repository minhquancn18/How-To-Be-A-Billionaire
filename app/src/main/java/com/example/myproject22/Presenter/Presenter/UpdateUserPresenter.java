package com.example.myproject22.Presenter.Presenter;

import android.graphics.Bitmap;
import android.view.View;

import com.example.myproject22.Model.UserClass;
import com.example.myproject22.Presenter.Interface.UpdateUserInterface;

public class UpdateUserPresenter {
    private UpdateUserInterface anInterface;

    public UpdateUserPresenter(UpdateUserInterface anInterface) {
        this.anInterface = anInterface;
    }

    //region SetInit, Get bundle, Keyboard

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

    //region Fetch User

    public void fetchUserFromServer(){
        anInterface.FetchUserFromServer();
    }

    //endregion

    //region Loading

    public void loadUser(UserClass userClass){
        anInterface.LoadUser(userClass);
    }

    public void loadDataToLayout(){
        anInterface.LoadDataToLayout();
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

    //region Image User

    public void chooseImage() {
        anInterface.ChooseImage();
    }

    public void takeImageFromGallery() {
        anInterface.TakeImageFromGallery();
    }

    public void takeImageFromCamera() {
        anInterface.TakeImageFromCamera();
    }

    public Boolean isNullImage(Bitmap bitmap){
        return anInterface.IsNullImage(bitmap);
    }

    public String getStringImage(){
        return anInterface.GetStringImage();
    }

    //endregion

    //region Condition

    public Boolean getNoFullName(String fullname){
        return anInterface.GetNoFullName(fullname);
    }

    public Boolean getNoEmail(String email){
        return anInterface.GetNoEmail(email);
    }

    //endregion

    //region Upload User to Server

    public void uploadUserToServer(String fullname, String email, String image) {
        anInterface.UploadUserToServer(fullname,email,image);
    }

    public void uploadUserToServerNoImage(String fullname, String email) {
        anInterface.UploadUserToServerNoImage(fullname,email);
    }

    //endregion

}
