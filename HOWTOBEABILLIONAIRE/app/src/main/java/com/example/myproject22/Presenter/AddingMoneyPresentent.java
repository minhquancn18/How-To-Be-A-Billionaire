package com.example.myproject22.Presenter;

import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;

import com.example.myproject22.R;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;

public class AddingMoneyPresentent {

    private AddingMoneyInterface anInterface;

    public AddingMoneyPresentent(AddingMoneyInterface anInterface) {
        this.anInterface = anInterface;
    }
    //Chuyển đổi image từ int sang byte[]
    public byte[] getImageByID(MaterialButton btn) {
        Bitmap bitmap = ((BitmapDrawable) btn.getIcon()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        return imageInByte;
    }

    public void setInit(){
        anInterface.SetInit();
    }
    public void loadingIncomeCategory(){
        anInterface.LoadCategory();
    }

    public void chooseImage(){
        anInterface.ChooseImage();
    }

    public void CaptureRecord(){
        anInterface.CaptureRecord();
    }

    public void CaptureAudio(){
        anInterface.CaptureAudio();
    }

    public void SetOnTextChange(CharSequence s){
        anInterface.IsValidNumber(s);
    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[0-9]+");
    }

    public int findIdByName(String name){
        return anInterface.FindIdByName(name);
    }

    public Boolean isNullAudio(){
        return anInterface.IsNullAudio();
    }

    public Boolean isNullImage(Bitmap bitmap){
        return anInterface.IsNullImage(bitmap);
    }

    public byte[] convert3gbToByte(){
        return anInterface.Convert3gbToByte();
    }

    public String getStringImage(){
        return anInterface.GetStringImage();
    }

    public String getStringAudio(){
        return anInterface.GetStringAudio();
    }

    public static String convertByteToString(byte[] bytes){
        if(bytes == null){
            String s = "NULL";
            return s;
        }
        else{
            String s = Base64.encodeToString(bytes,Base64.DEFAULT);
            return s;
        }
    }

    public void savingMoneyData(String money, String description, int category_id, String image, String audio){
        //Check valid money
        if(isNumeric(money) == false){
            anInterface.GetNoMoneyData();
            return;
        }

        //Check valid Category
        if(category_id == 0){
            anInterface.GetNoCategoryData();
            return;
        }

        anInterface.SavingMoneyData(money,description,category_id,image,audio);
    }
}
