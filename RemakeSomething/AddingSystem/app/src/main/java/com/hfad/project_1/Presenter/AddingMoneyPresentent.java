package com.hfad.project_1.Presenter;

import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.hfad.project_1.Model.AddingCategoryClass;
import com.hfad.project_1.Model.MoneyCategoryClass;
import com.hfad.project_1.R;
import com.hfad.project_1.Model.SavingDatabaseHelper;
import com.hfad.project_1.Model.SpendingCategoryClass;

import java.io.ByteArrayOutputStream;

public class AddingMoneyPresentent {

    private AddingMoneyInterface anInterface;

    public AddingMoneyPresentent(AddingMoneyInterface anInterface) {
        this.anInterface = anInterface;
    }

    //Lấy thông tin từ thông qua AddingTypeActivity
    public MoneyCategoryClass GetIntentData(Bundle bundle) {
        String type = "";
        int iconType = 0;
        int isType = 0;
        if (bundle != null) {
            //Kiểm tra dữ liệu lấy ra từ bunle có bi null không
            try {
                //Kiểm tra dữ liệu bắt được là của thu hay của chi, nếu là của thu thì thực hiện lệnh if còn của chi thucowj hiện lệnh else
                int moneyID = bundle.getInt("addingID");
                int moneyChildID = bundle.getInt("addingChildID");
                if (moneyID != 0) {
                    //Kiểm tra xem có chọn dữ liệu không nếu không thì thực hiện lệnh if còn nếu có thực hiện lệnh else
                    if (moneyChildID == -1) {
                        type = AddingCategoryClass.addingtypes.get(moneyID).getNameType();
                        iconType = AddingCategoryClass.addingtypes.get(moneyID).getImageResourceID();
                        isType = AddingCategoryClass.addingtypes.get(moneyID).isBoolType();
                    } else {
                        type = AddingCategoryClass.getData().get(AddingCategoryClass.addingtypes.get(moneyID).getNameType()).get(moneyChildID).getNameType();
                        iconType = AddingCategoryClass.getData().get(AddingCategoryClass.addingtypes.get(moneyID).getNameType()).get(moneyChildID).getImageResourceID();
                        isType = AddingCategoryClass.getData().get(AddingCategoryClass.addingtypes.get(moneyID).getNameType()).get(moneyChildID).isBoolType();
                    }
                } else {
                    moneyID = bundle.getInt("spendingID");
                    moneyChildID = bundle.getInt("spendingChildID");
                    //Kiểm tra xem có chọn dữ liệu không nếu không thì thực hiện lệnh if còn nếu có thực hiện lệnh else
                    if (moneyChildID == -1) {
                        type = SpendingCategoryClass.spendingTypes.get(moneyID).getNameType();
                        iconType = SpendingCategoryClass.spendingTypes.get(moneyID).getImageResourceID();
                        isType = SpendingCategoryClass.spendingTypes.get(moneyID).isBoolType();
                    } else {
                        type = SpendingCategoryClass.getData().get(SpendingCategoryClass.spendingTypes.get(moneyID).getNameType()).get(moneyChildID).getNameType();
                        iconType = SpendingCategoryClass.getData().get(SpendingCategoryClass.spendingTypes.get(moneyID).getNameType()).get(moneyChildID).getImageResourceID();
                        isType = SpendingCategoryClass.getData().get(SpendingCategoryClass.spendingTypes.get(moneyID).getNameType()).get(moneyChildID).isBoolType();
                    }
                }
            } catch (NullPointerException e) {
                anInterface.AddingCategoryFail();
            }

            //Đưa dữ liệu bắt được vào button chứa dữ liệu thu chi
            MoneyCategoryClass moneyCategoryClass = new MoneyCategoryClass(type, isType, iconType);
            anInterface.GetBuddleSuccessful();
            return moneyCategoryClass;
        } else {
            MoneyCategoryClass moneyCategoryClass = new MoneyCategoryClass("Chọn loại", 0, R.drawable.question);
            return moneyCategoryClass;
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
                db.insertChitietTienChi(money, sDescription, db.getID(sType));
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

    //Chuyển đổi image từ int sang byte[]
    public byte[] getImageByID(MaterialButton btn) {
        Bitmap bitmap = ((BitmapDrawable) btn.getIcon()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        return imageInByte;
    }
}
