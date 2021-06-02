package com.example.myproject22.Presenter;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.myproject22.Model.AddingCategoryClass;
import com.example.myproject22.Model.MoneyCategoryClass;
import com.example.myproject22.Model.SavingDatabaseHelper;
import com.example.myproject22.Model.SpendingCategoryClass;
import com.example.myproject22.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AddingCategoryPresenter {
    private CategoryMoneyInterface categoryMoneyInterface;
    private Context context;

    public AddingCategoryPresenter(CategoryMoneyInterface categoryMoneyInterface, Context context){
        this.categoryMoneyInterface = categoryMoneyInterface;
        this.context = context;
    }

    //Lưu category mới vào database
    public void SaveCategory(String name, String parent_name,byte[] image, Boolean type , SavingDatabaseHelper dbHelper){

        try{
            if(type == true){
                int isChild = 1;
                if(parent_name.equals("0"))
                    isChild = 0;
                dbHelper.insertDanhMucThu(name,image,isChild,parent_name);
            }
            else{
                int isChild = 1;
                if(parent_name.equals("0"))
                    isChild = 0;
                dbHelper.insertDanhMucChi(name,image,isChild,parent_name);
            }
        }
        catch (SQLiteException sqLiteException){
            categoryMoneyInterface.GetDataFail();
            return;
        }
    }

    public MoneyCategoryClass getMoneyCategoryClass(Bundle bundle)
    {
        SavingDatabaseHelper dbHelper = new SavingDatabaseHelper(context);

        if(bundle != null) {
            int bType = bundle.getInt("Type");
            if(bType == 1) {
                ArrayList<AddingCategoryClass> list = dbHelper.getAddingCategoryList();
                int moneyID = bundle.getInt("IDType");
                int ID = list.get(moneyID).getID();
                String type = list.get(moneyID).getNameType();
                byte[] iconType = list.get(moneyID).getImageResource();
                int isType = list.get(moneyID).isBoolType();
                ArrayList<MoneyCategoryClass> listChild = list.get(moneyID).getListChild();

                MoneyCategoryClass moneyCategoryClass = new MoneyCategoryClass(ID, type, isType, iconType, listChild);
                return  moneyCategoryClass;
            }
            else {
                ArrayList<SpendingCategoryClass> list = dbHelper.getSpendingCategoryList();
                int moneyID = bundle.getInt("IDType");
                int ID = list.get(moneyID).getID();
                String type = list.get(moneyID).getNameType();
                byte[] iconType = list.get(moneyID).getImageResource();
                int isType = list.get(moneyID).isBoolType();
                ArrayList<MoneyCategoryClass> listChild = list.get(moneyID).getListChild();

                MoneyCategoryClass moneyCategoryClass = new MoneyCategoryClass(ID, type, isType, iconType, listChild);
                return  moneyCategoryClass;
            }
        }
        else{
            MoneyCategoryClass moneyCategoryClass = new MoneyCategoryClass(-1,"Nhóm cha", 0,ConvertToByte(R.drawable.question));
            return moneyCategoryClass;
        }
    }

    private byte[] ConvertToByte(int ID) {
        Resources res = context.getResources();
        Drawable drawable = res.getDrawable(ID);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();

        return bitMapData;
    }
}
