package com.example.myproject22.Presenter.Presenter;

import android.graphics.Bitmap;
import android.view.View;

import com.example.myproject22.Presenter.Interface.SignUpInterface;

import java.io.File;
import java.io.IOException;

public class SignUpPresenter {
    private SignUpInterface anInterface;

    public SignUpPresenter(SignUpInterface anInterface) {
        this.anInterface = anInterface;
    }

    //region Create and Set

    public void setInit(){
        anInterface.SetInit();
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

    public Boolean getNoSalary(String salary){
        return anInterface.GetNoSalary(salary);
    }

    public Boolean getNoFullName(String fullname){
        return anInterface.GetNoFullName(fullname);
    }

    public Boolean getNoEmail(String email){
        return anInterface.GetNoEmail(email);
    }

    //endregion

    //region Image

    public void chooseImage() {
        anInterface.ChooseImage();
    }

    public void takeImageFromGallery(){
        anInterface.TakeImageFromGallery();
    }

    public void takeImageFromCamera(){
        anInterface.TakeImageFromCamera();
    }

    public File createImageFile() throws IOException{
        return anInterface.CreateImageFile();
    }

    public Boolean isNullImage(Bitmap bitmap) {
        return anInterface.IsNullImage(bitmap);
    }

    public String getStringImage() {
        return anInterface.GetStringImage();
    }

    public void denyPermission(){
        anInterface.DenyPermission();
    }

    //endregion

    //region Handle click

    public void btnSignUp(){
        anInterface.BtnSignUp();
    }

    public void textViewClick() {
        anInterface.TextViewClick();
    }

    //endregion

    //region Upload data to server

    public void uploadUserToServer(String username, String password, String email,
                                   String fullname, String salary, String image){
        anInterface.UploadUserToServer(username, password, email, fullname, salary, image);
    }

    public void uploadUserNoImageToServer(String username, String password, String email,
                                          String fullname, String salary) {
        anInterface.UploadUserNoImageToServer(username, password, email, fullname, salary);
    }

    //endregion
}
