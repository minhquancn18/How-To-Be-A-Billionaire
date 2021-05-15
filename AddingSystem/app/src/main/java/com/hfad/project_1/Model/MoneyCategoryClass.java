package com.hfad.project_1.Model;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

public class MoneyCategoryClass {
    protected String nameType; //Tên loại thu chi
    protected int boolType; //1 là thu, -1 là chi, 0 là chưa chọn
    protected int imageResourceID; //Hình ảnh loại thu chi

    public String getNameType() {
        return nameType;
    }

    public int isBoolType() {
        return boolType;
    }

    public int getImageResourceID() {
        return imageResourceID;
    }

    @NonNull
    @Override
    public String toString() {
        return nameType;
    }

    public MoneyCategoryClass(String name, int bool, int ID) {
        nameType = name;
        boolType = bool;
        imageResourceID = ID;
    }

}

