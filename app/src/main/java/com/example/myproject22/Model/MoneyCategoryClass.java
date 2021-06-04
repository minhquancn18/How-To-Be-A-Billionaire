package com.example.myproject22.Model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.example.myproject22.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MoneyCategoryClass {
    protected int id;
    protected String nameType; //Tên loại thu chi
    protected int boolType; //1 là thu, -1 là chi, 0 là chưa chọn
    protected int imageResourceID; //Hình ảnh loại thu chi
    protected byte[] imageResource;
    protected ArrayList<MoneyCategoryClass> listChild;
    protected Boolean boolChild;

    public int getID() {return id;}
    public void setId(int id){this.id = id;}

    public String getNameType() {
        return nameType;
    }
    public void setNameType(String nameType) {this.nameType = nameType;}

    public int isBoolType() {
        return boolType;
    }
    public void setBoolType(int boolType){this.boolType = boolType;}

    public Boolean isBoolChild() {return boolChild;}
    public void setBoolChild(Boolean boolChild) {this.boolChild = boolChild;}

    public int getImageResourceID() {
        return imageResourceID;
    }
    public void setImageResourceID(int imageResourceID) {this.imageResourceID = imageResourceID;}

    public byte[] getImageResource() {return imageResource;}
    public void setImageResource(byte[] imageResource){this.imageResource = imageResource;}

    public ArrayList<MoneyCategoryClass> getListChild() {
        if(listChild != null)
            return listChild;
        else
            return null;
    }
    public void setListChild(ArrayList<MoneyCategoryClass> listChild){this.listChild = listChild;}

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

    public MoneyCategoryClass(int id, String nameType, int boolType, byte[] imageResource, ArrayList<MoneyCategoryClass> listChild )
    {
        this.id = id;
        this.nameType = nameType;
        this.boolType = boolType;
        this.boolChild = true;
        this.imageResource = imageResource;
        this.listChild = listChild;
    }

    public MoneyCategoryClass(int id, String nameType, int boolType, byte[] imageResource)
    {
        this.id =id;
        this.nameType = nameType;
        this.boolType = boolType;
        this.imageResource = imageResource;
        this.boolChild = false;
        this.listChild = null;
    }

    static public MoneyCategoryClass Default = new MoneyCategoryClass("Nhóm cha", 0,R.drawable.question);

    public Boolean IsDefaultCategory(){
        if(this.nameType == Default.nameType)
            return true;
        else
            return false;
    }

}

