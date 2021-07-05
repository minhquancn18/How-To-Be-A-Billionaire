package com.example.myproject22.Model;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceClass {

    private SharedPreferences mSharedPref;

    public SharePreferenceClass(Context context){
        mSharedPref = context.getSharedPreferences("HowToBeABillionaire", Context.MODE_PRIVATE);
    }

    //region Lần đầu sử dụng

    public void setFirstTime(Boolean isFirstTime){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putBoolean("IsFirstTime", isFirstTime);
        editor.commit();
    }

    public  Boolean isFirstTime(){
        return  mSharedPref.getBoolean("IsFirstTime", true);
    }

    //endregion

    //region Nhớ tài khoản và mật khẩu

    //region Remember is Check

    public void setRemember(Boolean isRemember){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putBoolean("IsRemember", isRemember);
        editor.commit();
    }

    public  Boolean isRemember(){
        return  mSharedPref.getBoolean("IsRemember", false);
    }

    //endregion

    //region Username

    public void setUsername(String username){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString("Username", username);
        editor.commit();
    }

    public String getUsername(){
        return mSharedPref.getString("Username", "");
    }

    //endregion

    //region Password

    public void setPassword(String password){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString("Password", password);
        editor.commit();
    }

    public String getPassword(){
        return mSharedPref.getString("Password", "");
    }

    //endregion

    //endregion

    //region Tự động đăng nhập nếu chưa LogOut

    //region IsLogOut

    public void setIsLogOut(Boolean isLogout){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putBoolean("IsLogOut", isLogout);
        editor.commit();
    }

    public Boolean isLogOut(){
        return  mSharedPref.getBoolean("IsLogOut", true);
    }

    //endregion

    //region ID_USER

    public void setIdUser(int id_user){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putInt("ID_USER", id_user);
        editor.commit();
    }

    public int getIdUser(){
        return  mSharedPref.getInt("ID_USER", 0);
    }

    //endregion

    //region ID_INCOME

    public void setIdIncome(int id_income){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putInt("ID_INCOME", id_income);
        editor.commit();
    }

    public int getIdIncome(){
        return  mSharedPref.getInt("ID_INCOME", 0);
    }

    //endregion

    //region ID_OUTCOME

    public void setIdOutcome(int id_outcome){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putInt("ID_OUTCOME", id_outcome);
        editor.commit();
    }

    public int getIdOutcome(){
        return  mSharedPref.getInt("ID_OUTCOME", 0);
    }

    //endregion

    //region ID_SAVING

    public void setIdSaving(int id_saving){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putInt("ID_SAVING", id_saving);
        editor.commit();
    }

    public int getIdSaving(){
        return  mSharedPref.getInt("ID_SAVING", 0);
    }

    //endregion

    //endregion

    //region Is Update User
    public void setIsUpdateUser(Boolean isUpdateUser){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putBoolean("IS_UPDATE_USER", isUpdateUser);
        editor.commit();
    }

    public Boolean getIsUpdateUser() {
        return mSharedPref.getBoolean("IS_UPDATE_USER", false);
    }
    //endregion

    //region Is Update Category
    public void setIsUpdateCategory(Boolean isUpdateCategory){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putBoolean("IS_UPDATE_CATEGORY", isUpdateCategory);
        editor.commit();
    }

    public  Boolean getIsUpdateCategory(){
        return mSharedPref.getBoolean("IS_UPDATE_CATEGORY", false);
    }
    //endregion
}
