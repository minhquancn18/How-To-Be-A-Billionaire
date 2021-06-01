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
import com.example.myproject22.Model.MoneyInformationClass;
import com.example.myproject22.Model.SavingDatabaseHelper;
import com.example.myproject22.Model.SpendingCategoryClass;
import com.example.myproject22.R;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AddingMoneyPresentent {

    private AddingMoneyInterface anInterface;
    private Context context;

    public AddingMoneyPresentent(AddingMoneyInterface anInterface, Context context) {
        this.anInterface = anInterface;
        this.context = context;
    }

    //Lấy thông tin từ thông qua AddingTypeActivity
    public MoneyInformationClass GetIntentData(Bundle bundle) {
        String type = "";
        byte[] iconType = new byte[0];
        int isType = 0;
        int ID = -1;
        Double money = 0.0;
        String sDescription = "";

        ArrayList<MoneyCategoryClass> listChild = null;
        if (bundle != null) {
            //Kiểm tra dữ liệu lấy ra từ bunle có bi null không
            try {
                String sMoney = bundle.getString("MoneyText");
                money = Double.parseDouble(sMoney);

                sDescription = bundle.getString("DescriptionText");

                SavingDatabaseHelper dbHelper = new SavingDatabaseHelper(context);
                //Kiểm tra dữ liệu bắt được là của thu hay của chi, nếu là của thu thì thực hiện lệnh if còn của chi thucowj hiện lệnh else
                int bType = bundle.getInt("IsType");
                if (bType == 1) {
                    int moneyID = bundle.getInt("addingID");
                    int moneyChildID = bundle.getInt("addingChildID");
                    ArrayList<AddingCategoryClass> list = dbHelper.getAddingCategoryList();
                    //Kiểm tra xem có chọn dữ liệu không nếu không thì thực hiện lệnh if còn nếu có thực hiện lệnh else
                    if (moneyChildID == -1) {
                        ID = list.get(moneyID).getID();
                        type = list.get(moneyID).getNameType();
                        iconType = list.get(moneyID).getImageResource();
                        isType = list.get(moneyID).isBoolType();
                        listChild = list.get(moneyID).getListChild();
                    } else {
                        ID = list.get(moneyID).getListChild().get(moneyChildID).getID();
                        type = list.get(moneyID).getListChild().get(moneyChildID).getNameType();
                        iconType = list.get(moneyID).getListChild().get(moneyChildID).getImageResource();
                        isType = list.get(moneyID).getListChild().get(moneyChildID).isBoolType();
                        listChild = null;
                    }
                }
                else if (bType == -1) {
                    int moneyID = bundle.getInt("spendingID");
                    int moneyChildID = bundle.getInt("spendingChildID");
                    ArrayList<SpendingCategoryClass> list = dbHelper.getSpendingCategoryList();
                    //Kiểm tra xem có chọn dữ liệu không nếu không thì thực hiện lệnh if còn nếu có thực hiện lệnh else
                    if (moneyChildID == -1) {
                        ID = list.get(moneyID).getID();
                        type = list.get(moneyID).getNameType();
                        iconType = list.get(moneyID).getImageResource();
                        isType = list.get(moneyID).isBoolType();
                        listChild = list.get(moneyID).getListChild();
                    } else {
                        ID = list.get(moneyID).getListChild().get(moneyChildID).getID();
                        type = list.get(moneyID).getListChild().get(moneyChildID).getNameType();
                        iconType = list.get(moneyID).getListChild().get(moneyChildID).getImageResource();
                        isType = list.get(moneyID).getListChild().get(moneyChildID).isBoolType();
                        listChild = null;
                    }
                }
                else {
                    MoneyCategoryClass moneyCategoryClass = new MoneyCategoryClass(-1,"Chọn loại", 0,ConvertToByte(R.drawable.question));
                    MoneyInformationClass moneyInformationClass = new MoneyInformationClass(money,sDescription,moneyCategoryClass);
                    return moneyInformationClass;
                }
            } catch (NullPointerException e) {
                anInterface.AddingCategoryFail();
            }

            //Đưa dữ liệu bắt được vào button chứa dữ liệu thu chi
            MoneyCategoryClass moneyCategoryClass = new MoneyCategoryClass(ID, type, isType, iconType, listChild);
            MoneyInformationClass moneyInformationClass = new MoneyInformationClass(money,sDescription,moneyCategoryClass);
            anInterface.GetBuddleSuccessful();
            return moneyInformationClass;
        }
        else {
            MoneyCategoryClass moneyCategoryClass = new MoneyCategoryClass(-1,"Chọn loại", 0,ConvertToByte(R.drawable.question));
            MoneyInformationClass moneyInformationClass = new MoneyInformationClass(money,sDescription,moneyCategoryClass);
            return moneyInformationClass;
        }
    }

    //Hàm lưu dữ liệu vào database
    public Boolean AddMoneyIntoDB(String sMoney, String sDescription, MoneyCategoryClass moneyCategoryClass, SavingDatabaseHelper db) {

        //khởi tạo đối tượng xử lý dữ liệu
        String foo = null; //Kiểm tra null
        String sType = moneyCategoryClass.getNameType();
        int isType = moneyCategoryClass.isBoolType();
        Double money = 0.0; //Ép kiểu từ string sMoney


        //Kiểm tra tiền có khác 0 và khác null ko
        if (sMoney.equals(R.string.money)) {
            anInterface.GetNoMoneyData();
            return false;
        } else {
            money = Double.parseDouble(sMoney);
            if(money == 0.0)
            {
                anInterface.GetNoMoneyData();
                return false;
            }
        }

        //Kiểm tra chuỗi truyền vào có khác null không
        if (sType.equals(foo)) {
            anInterface.GetNoCategoryData();
            return false;
        } else if (sType.equals("Chọn loại")) {
            anInterface.GetNoCategoryData();
            return false;
        }

        //Kiểm tra loại có hợp lệ
        if (isType == 0) {
            anInterface.GetNoCategoryData();
            return false;
        }

        //Nếu ko có chuỗi null và tiền nhập vào hợp lệ và loại thu chi hợp lê thì dưa dữ liệu vào database
        try {
            //Nếu loại thu chi là 1 thì đưa vào bảng ChiTietTienThu
            if (isType == 1) {
                db.insertChiTietTienThu(money, sDescription, db.getThuID(sType));
                anInterface.GetAddSuccessful();
            }
            //Nếu loại thu chi là -1 thì đưa vào bảng ChiTietTienChi
            else if (isType == -1) {
                db.insertChitietTienChi(money, sDescription, db.getChiID(sType));
                anInterface.GetSpendSuccessful();
            }
            return true;
        }
        //Kết nối dữ liệu không thành công thì bắt lỗi
        catch (SQLiteException e) {
            anInterface.GetDataFail();
            return false;
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

    public void onButtonCategoryClicked(String sMoney, String sDescription){
        anInterface.ButtonCategoryClickWithBoth(context,sMoney,sDescription);
    }
}
