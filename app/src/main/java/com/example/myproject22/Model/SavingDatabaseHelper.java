//Chỉnh lại package
package com.example.myproject22.Model;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myproject22.R;


import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SavingDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "BILLIONAIRE";
    private static final int DB_VERSION = 1;

    private Context context;
    SQLiteDatabase db;
    Cursor cursor;


    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public SavingDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public SavingDatabaseHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null,DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //lưu thông tin người dùng
        db.execSQL("CREATE TABLE USER (_id_user INTEGER PRIMARY KEY AUTOINCREMENT," +
                "USERNAME TEXT," +
                "PASSWORD TEXT, " +
                "THUNHAP DOUBLE, " +
                "NGAY_KICHHOAT TEXT," +
                "USER_IMAGE BLOB," +
                "HOTEN TEXT" +
                ")");

        //lưu thông tin danh muc chi
        db.execSQL("CREATE TABLE DANHMUCCHI(_id_danhMucChi INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TEN_DANH_MUC_CHI TEXT," +
                "IMAGE_TIENCHI BLOG," +
                "LOAI_THUOC_TINH INTEGER," +
                "TEN_DANH_MUC_CHA TEXT)");

        //lưu thông tin danh mục thu
        db.execSQL("CREATE TABLE DANHMUCTHU(_id_danhMucThu INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TEN_DANH_MUC_THU TEXT," +
                "IMAGE_TIENTHU BLOG," +
                "LOAI_THUOC_TINH INTEGER," +
                "TEN_DANH_MUC_CHA TEXT)");

        //lưu thông tin tiền thu
        db.execSQL("CREATE TABLE TIENTHU(_id_tienThu INTEGER PRIMARY KEY AUTOINCREMENT," +
                "_id_user INTGER," +
                "TONGTIENTHU DOUBLE," +
                "FOREIGN KEY(_id_user) REFERENCES USER(_id_user))");

        //lưu thông tin chi tiết thu
        db.execSQL("CREATE TABLE CHITIETTIENTHU (_id_chiTietTienThu INTEGER PRIMARY KEY AUTOINCREMENT," +
                "_id_tienThu INTEGER ," +
                "SOTIENTHU DOUBLE," +
                "CHITIET_TIENTHU TEXT, " +
                "_id_danhMucThu INTEGER, " +
                "NGAY_TIENTHU TEXT, " +
                "GIO_TIENTHU TEXT, " +
                "FOREIGN KEY (_id_danhMucThu) REFERENCES DANHMUCTHU(_id_danhMucThu), " +
                "FOREIGN KEY(_id_tienThu) REFERENCES TIENTHU(_id_tienThu))");

        //lưu thông tin tiền chi
        db.execSQL("CREATE TABLE TIENCHI(_id_tienChi INTEGER PRIMARY KEY AUTOINCREMENT," +
                "_id_user INTGER," +
                "TONGTIENCHI DOUBLE," +
                "FOREIGN KEY(_id_user) REFERENCES USER(_id_user))");

        //lưu thông tin chi tiết tiền chi
        db.execSQL("CREATE TABLE CHITIETTIENCHI (_id_chiTietTienChi INTEGER PRIMARY KEY AUTOINCREMENT," +
                "_id_tienChi INTEGER ," +
                "SOTIENCHI DOUBLE," +
                "CHITIET_TIENCHI TEXT, " +
                "_id_danhMucChi INTEGER, " +
                "NGAY_TIENCHI TEXT, " +
                "GIO_TIENCHI TEXT, " +
                "FOREIGN KEY (_id_danhMucChi) REFERENCES DANHMUCHI(_id_danhMucCHI), " +
                "FOREIGN KEY(_id_tienChi) REFERENCES TIENCHI(_id_tienChi))");


        db.execSQL("CREATE TABLE TIETKIEM(_id_tietKiem INTEGER PRIMARY KEY AUTOINCREMENT," +
                "_id_user INTGER," +
                "TONGTIENTIETKIEM DOUBLE," +
                "TONGTIENCHI DOUBLE," +
                "TONGTIENTHU DOUBLE," +
                "SONGAY INT," +
                "FOREIGN KEY(_id_user) REFERENCES USER(_id_user))");


        db.execSQL("CREATE TABLE CHITIETTIETKIEM (_id_chiTietTietKiem INTEGER PRIMARY KEY AUTOINCREMENT," +
                "_id_tietKiem INTEGER ," +
                "TONGSOTIENCHITRONGNGAY DOUBLE," +
                "TONGSOTIENTHUTRONGNGAY DOUBLE," +
                "SOTIENTIETKIEMTRONGNGAY DOUBLE," +
                "NGAY_TIETKIEM TEXT, " +
                "FOREIGN KEY(_id_tietkiem) REFERENCES TIETKIEM(_id_tietKiem))");


        db.execSQL("CREATE TABLE MUCTIEU (_id_mucTieu INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "_id_user INTEGER , " +
                "TENMUCTIEU TEXT, " +
                "MOTAMUCTIEU TEXT , " +
                "SOTIENMUCTIEU DOUBLE , " +
                "IMAGE_MUCTIEU BLOB, " +
                "HOANTHANH INT, " +
                "SOTIENTIETKIEM DOUBLE, " +
                "FOREIGN KEY (_id_user) REFERENCES USER(_id_user))");

        db.execSQL("CREATE TABLE IMAGE_CATEGORY (_idImage INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "IMAGE_BLOB BLOB)");

        Log.d("SAVINGHELPER","LAY DATABASE");
        // insert new user
        addSomeBeginDatabase(db);
        addCategoryMoney();
        AddImageCategory();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void queryData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    // danh muc
    public void insertDanhMucChi(String tenDanhMucChi, byte[] imageDanhMucChi, int IsChild, String MucCha) {
        ContentValues danhMuc = new ContentValues();
        danhMuc.put("TEN_DANH_MUC_CHI", tenDanhMucChi);
        danhMuc.put("LOAI_THUOC_TINH", IsChild);
        danhMuc.put("TEN_DANH_MUC_CHA", MucCha);
        danhMuc.put("IMAGE_TIENCHI", imageDanhMucChi);

        db.insert("DANHMUCCHI", null, danhMuc);
    }

    public void insertDanhMucThu(String tenDanhMucThu, byte[] imageDanhMucThu, int IsChild, String MucCha) {
        ContentValues danhMuc = new ContentValues();
        danhMuc.put("TEN_DANH_MUC_THU", tenDanhMucThu);
        danhMuc.put("LOAI_THUOC_TINH", IsChild);
        danhMuc.put("TEN_DANH_MUC_CHA", MucCha);
        danhMuc.put("IMAGE_TIENTHU", imageDanhMucThu);

        db.insert("DANHMUCTHU", null, danhMuc);
    }


    ////////////////////////////////////////////////////
    // tien thu
    public void insertChiTietTienThu(double sotienthu, String chiTietTienThu, int _id_danhMucThu) {
       insertChiTietTienThu(sotienthu, chiTietTienThu, _id_danhMucThu, dateFormat.format(new Date()));
    }

    public Cursor getTienThu() {
        db = getReadableDatabase();
        Cursor cursor = db.query("CHITIETTIENTHU", new String[]{"_id_chiTietTienThu", "SOTIENTHU", "NGAY_TIENTHU"}, null, null, null, null, "_id_chiTietTienThu DESC");
        return cursor;
    }


    public int getThuID(String name) {
        int ID = 0;
        db = getWritableDatabase();
        Cursor cursor = db.query("DANHMUCTHU", new String[]{"_id_danhMucThu"}, "TEN_DANH_MUC_Thu=?", new String[]{name}, null, null, null);
        if (cursor.moveToFirst()) {
            ID = cursor.getInt(0);
        }
        return ID;
    }

    //my overload
    public void insertChiTietTienThu(double sotienthu, String chiTietTienThu, int _id_danhMucThu, String dateAdd) {
        try {
            db = getWritableDatabase();

            // get old data
            Date dateBefore = new Date();
            Double tienThu = 0d;
            String strDate = dateFormat.format(new Date());


            // get data before
            cursor = getTienThu();
            if (cursor.moveToFirst()) {

                tienThu = cursor.getDouble(1);
                strDate = cursor.getString(2);

                // get date
                dateBefore = dateFormat.parse(strDate);
            }
            updateTietKiemDoThu(sotienthu, strDate, dateAdd);

            // insert part
            ContentValues record = new ContentValues();
            record.put("SOTIENTHU", sotienthu);
            record.put("CHITIET_TIENTHU", chiTietTienThu);
            record.put("_id_danhMucThu", _id_danhMucThu);
            record.put("_id_tienThu", 1);


            // insert date and time
            record.put("NGAY_TIENTHU", dateAdd);
            db.insert("CHITIETTIENTHU", null, record);

        } catch (ParseException e) {

        }
    }

    public void updateTietKiemDoThu(double soTienThu, String dateBefore, String dateAdd) {

        try {
            // update soNgay
            int soNgay = 1;

            // update table TIETKIEM
            cursor = getTietKiem();
            if (cursor.moveToFirst()) {
                double TongTienTietKiem = cursor.getDouble(1);
                double TongTienThu = cursor.getDouble(2);
                double TongTienChi = cursor.getDouble(3);
                soNgay = cursor.getInt(4);
                updateTietKiem(TongTienTietKiem + soTienThu, TongTienThu + soTienThu, TongTienChi);
            }


            // same day
            if (dateBefore.equals(dateAdd)) {

                // update table CHITIETTIETKIEM`
                cursor = getChiTietTietKiem();
                if (cursor.moveToFirst()) {
                    int _id_Chitiettietkiem = cursor.getInt(0);
                    double tongSoTienChiTrongNgay = cursor.getDouble(1);
                    double tongSoTienThuTrongNgay = cursor.getDouble(2);
                    double soTienTietKiemTrongNgay = cursor.getDouble(3);


                    updateChiTietTietKiem(_id_Chitiettietkiem, tongSoTienChiTrongNgay,
                            tongSoTienThuTrongNgay + soTienThu, soTienTietKiemTrongNgay + soTienThu);
                    tangSongayLen1(soNgay);
                } else {
                    insertChiTietTietKiem(soTienThu, 0);
                }

            } else {
                insertChiTietTietKiem(soTienThu, 0);
            }

            updateSoNgayTietKiem(dateBefore, dateAdd, soNgay);

        } catch (Exception e) {

        }
    }

    public int findDiffInDay(Date dateStart, Date dateEnd) {
        Long diff_in_time = dateStart.getTime() - dateEnd.getTime();

        long difference_In_Days
                = TimeUnit
                .MILLISECONDS
                .toDays(diff_in_time)
                % 365;

        return (int) difference_In_Days;
    }


    ////////////////////////////////////////////////////
    // tien chi
    public int getChiID(String name) {
        int ID = 0;
        db = getWritableDatabase();
        Cursor cursor = db.query("DANHMUCCHI", new String[]{"_id_danhMucChi"}, "TEN_DANH_MUC_CHI=?", new String[]{name}, null, null, null);
        if (cursor.moveToFirst()) {
            ID = cursor.getInt(0);
        }
        return ID;
    }

    public void insertChitietTienChi(double soTienChi, String chiTietTienChi, int _id_danhMucChi) {
        insertChitietTienChi(soTienChi, chiTietTienChi, _id_danhMucChi, null, dateFormat.format(new Date()));
    }

    public Cursor getTienChi() {
        db = getReadableDatabase();
        cursor = db.query("CHITIETTIENCHI", new String[]{"_id_chiTietTienChi", "SOTIENCHI", "NGAY_TIENCHI"}, null, null, null, null, "_id_chiTietTienChi DESC");
        return cursor;
    }


    public void insertChitietTienChi(double soTienChi, String chiTietTienChi, int _id_danhMucChi, byte[] image_chi) {
        insertChitietTienChi(soTienChi, chiTietTienChi, _id_danhMucChi, image_chi, dateFormat.format(new Date()));
    }

    public void insertChitietTienChi(double soTienChi, String chiTietTienChi, int _id_danhMucChi, byte[] image_chi, String dateAdd) {

        // get old data
        db = getWritableDatabase();
        Date dateBefore = new Date();
        String strDate = dateFormat.format(dateBefore);


        // get data before
        cursor = getTienChi();
        if (cursor.moveToFirst()) {
            strDate = cursor.getString(2);
        }
        updateTietKiemDoChi(soTienChi, strDate, dateAdd);

        ContentValues record = new ContentValues();
        record.put("SOTIENCHI", soTienChi);
        record.put("CHITIET_TIENCHI", chiTietTienChi);
        record.put("_id_danhMucChi", _id_danhMucChi);
        record.put("_id_tienChi", 1);


        // insert date and time
        record.put("NGAY_TIENCHI", dateAdd);

        // insert image
        if (image_chi != null)
            record.put("IMAGE_TIENCHI", image_chi);

        db.insert("CHITIETTIENCHI", null, record);


    }

    public void updateTietKiemDoChi(double soTienChi, String dateBefore, String dateAdd) {

        try {
            // update soNgay
            int soNgay = 1;

            // update table TIETKIEM
            cursor = getTietKiem();
            if (cursor.moveToFirst()) {
                double TongTienTietKiem = cursor.getDouble(1);
                double TongTienThu = cursor.getDouble(2);
                double TongTienChi = cursor.getDouble(3);

                // update so ngay
                soNgay = cursor.getInt(4);
                updateTietKiem(TongTienTietKiem - soTienChi, TongTienThu, TongTienChi + soTienChi);
            }

            // same day
            if (dateBefore.equals(dateAdd)) {
                // update table CHITIETTIETKIEM
                cursor = getChiTietTietKiem();
                if (cursor.moveToFirst()) {

                    int _id_Chitiettietkiem = cursor.getInt(0);
                    double tongSoTienChiTrongNgay = cursor.getDouble(1);
                    double tongSoTienThuTrongNgay = cursor.getDouble(2);
                    double soTienTietKiemTrongNgay = cursor.getDouble(3);

                    updateChiTietTietKiem(_id_Chitiettietkiem, tongSoTienChiTrongNgay + soTienChi,
                            tongSoTienThuTrongNgay, soTienTietKiemTrongNgay - soTienChi);
                } else {
                    insertChiTietTietKiem(0, soTienChi, dateAdd);
                }
            } else { // different day
                insertChiTietTietKiem(0, soTienChi, dateAdd);
            }
            updateSoNgayTietKiem(dateBefore, dateAdd, soNgay);

        } catch (Exception e) {

        }


    }


    ////////////////////////////////////////////////////
    // chi tiet tiet kiem
    public void insertChiTietTietKiem(double tienThu, double tienChi) {
        ContentValues record = new ContentValues();
        record.put("TONGSOTIENCHITRONGNGAY", tienChi);
        record.put("TONGSOTIENTHUTRONGNGAY", tienThu);
        record.put("SOTIENTIETKIEMTRONGNGAY", tienThu - tienChi);
        record.put("NGAY_TIETKIEM", dateFormat.format(new Date()));
        record.put("_id_tietKiem", 1);// default id of tietKiem = 1

        db.insert("CHITIETTIETKIEM", null, record);
    }

    public void insertChiTietTietKiem(double tienThu, double tienChi, String ngay) {

        db = getWritableDatabase();

        ContentValues record = new ContentValues();
        record.put("TONGSOTIENCHITRONGNGAY", tienChi);
        record.put("TONGSOTIENTHUTRONGNGAY", tienThu);
        record.put("NGAY_TIETKIEM", ngay);
        record.put("SOTIENTIETKIEMTRONGNGAY", tienThu - tienChi);
        record.put("_id_tietKiem", 1);// default id of tietKiem = 1
        db.insert("CHITIETTIETKIEM", null, record);

        //updateTietKiem(tienThu - tienChi, tienThu, tienChi);
    }

    public Cursor getChiTietTietKiem() {
        db = getReadableDatabase();
        cursor = db.query("CHITIETTIETKIEM", new String[]{"_id_chiTietTietKiem", "TONGSOTIENCHITRONGNGAY", "TONGSOTIENTHUTRONGNGAY", "SOTIENTIETKIEMTRONGNGAY"}
                , null, null, null, null, "_id_chiTietTietKiem DESC");
        return cursor;
    }

    public void updateChiTietTietKiem(int _id_chiTietTietKiem, double tongSoTienChiTrongNgay,
                                      double tongSoTienThuTrongNgay, double soTienTietKiemTrongNgay) {

        ContentValues record = new ContentValues();

        record.put("TONGSOTIENCHITRONGNGAY", tongSoTienChiTrongNgay);
        record.put("TONGSOTIENTHUTRONGNGAY", tongSoTienThuTrongNgay);
        record.put("SOTIENTIETKIEMTRONGNGAY", soTienTietKiemTrongNgay);

        db.update("CHITIETTIETKIEM",
                record, "_id_chiTietTietKiem = ?",
                new String[]{String.valueOf(_id_chiTietTietKiem)});
    }


    ////////////////////////////////////////////////////
    // tiet kiem
    public Cursor getTietKiem() {
        db = getReadableDatabase();
        cursor = db.query("TIETKIEM", new String[]{"_id_TietKiem", "TONGTIENTIETKIEM", "TONGTIENTHU", "TONGTIENCHI", "SONGAY"}
                , null, null, null, null, "_id_TietKiem DESC");
        return cursor;
    }

    public void updateTietKiem(double tongTienTietKiem, double tongtienThu, double tongtienChi) {


        ContentValues record = new ContentValues();
        record.put("_id_tietKiem", 1); // default id for tietkiem
        record.put("TONGTIENTHU", tongtienThu);
        record.put("TONGTIENCHI", tongtienChi);
        record.put("TONGTIENTIETKIEM", tongTienTietKiem);

        db.update("TIETKIEM", record, null, null);
    }

    public void resetSoNgayTietKiem() {
        ContentValues record = new ContentValues();
        record.put("SONGAY", 1);
        db.update("TIETKIEM", record, null, null);
    }

    public void tangSongayLen1(int soNgay) {
        ContentValues record = new ContentValues();
        record.put("SONGAY", soNgay + 1);
        db.update("TIETKIEM", record, null, null);
    }

    public void updateSoNgayTietKiem(String dateBefore, String dateAdd, int soNgay) {
        try {
            int diff = findDiffInDay(dateFormat.parse(dateAdd), dateFormat.parse(dateBefore));

            if (diff >= 2) {
                resetSoNgayTietKiem();
            }
            if (diff == 1) {
                tangSongayLen1(soNgay);
            }
        } catch (Exception e) {
        }
    }

    ////////////////////////////////////////////////////
    // muc tieu
    public void insertMucTieu(String tenMucTieu, String moTaMucTieu, double SoTienMucTieu, double SoTienTietKiem, byte[] image_mucTieu) {
        db = getWritableDatabase();
        ContentValues record = new ContentValues();
        record.put("TENMUCTIEU", tenMucTieu);
        record.put("MOTAMUCTIEU", moTaMucTieu);
        record.put("SOTIENMUCTIEU", SoTienMucTieu);
        record.put("HOANTHANH", 0);
        record.put("SOTIENTIETKIEM", SoTienTietKiem);

        // image is option
        if (image_mucTieu != null)
            record.put("IMAGE_MUCTIEU", image_mucTieu);

        record.put("_id_user", 1); // default _id user

        db.insert("MUCTIEU", null, record);
    }

    public Cursor getMucTieu() {
        db = getReadableDatabase();
        cursor = db.query("MUCTIEU", new String[]{"_id_MucTieu", "TENMUCTIEU", "MOTAMUCTIEU", "SOTIENMUCTIEU", "SOTIENTIETKIEM", "IMAGE_MUCTIEU", "HOANTHANH"},
                null, null, null, null, "_id_MucTieu DESC");
        return cursor;
    }

    public void updateMucTieu(double tenMucTieu, double moTaMucTieu, double SoTienMucTieu, double SoTienTietKiem, byte[] image_mucTieu) {
        db = getWritableDatabase();
        ContentValues record = new ContentValues();
        record.put("TENMUCTIEU", tenMucTieu);
        record.put("MOTAMUCTIEU", moTaMucTieu);
        record.put("SOTIENMUCTIEU", SoTienMucTieu);
        record.put("SOTIENTIETKIEM", SoTienTietKiem);
        record.put("IMAGE_MUCTIEU", image_mucTieu);
        record.put("_id_user", 1); // default _id user

        db.update("MUCTIEU", record, null, null);
    }

    public void updateMucTieu(double soTienTietKiem) {
        cursor = getMucTieu();
        if (cursor.moveToFirst()) {

            if (cursor.getInt(6) == 1) {// chua hoan thanh
                int highestID = cursor.getInt(0);
                double oldTienTietKiem = cursor.getDouble(4);
                ContentValues contentValues = new ContentValues();
                contentValues.put("SOTIENMUCTIEU", oldTienTietKiem + soTienTietKiem);
                db.update("MUCTIEU", contentValues, "_id_MucTieu = ? ", new String[]{String.valueOf(highestID)});
            }
        }

    }


    ////////////////////////////////////////////////////
    // order stuff
    public void addSomeBeginDatabase(SQLiteDatabase db) {


        ContentValues beginUser = new ContentValues();
        beginUser.put("USERNAME", "AAA");
        beginUser.put("PASSWORD", "AAA");
        db.insert("USER", null, beginUser);


        // insert new tienthu
        ContentValues beginTienThu = new ContentValues();
        beginTienThu.put("_id_user", 1);
        db.insert("TIENTHU", null, beginTienThu);


        // insert new tienNhap
        ContentValues beginTienChi = new ContentValues();
        beginTienChi.put("_id_user", 1);
        db.insert("TIENCHI", null, beginTienChi);


        // insert tiet kiem
        ContentValues beginTietKiem = new ContentValues();
        beginTietKiem.put("_id_user", 1);
        beginTietKiem.put("TONGTIENCHI", 0);
        beginTietKiem.put("TONGTIENTHU", 0);
        beginTietKiem.put("TONGTIENTIETKIEM", 0);
        beginTietKiem.put("SONGAY", 1);
        db.insert("TIETKIEM", null, beginTietKiem);
    }

    public void addCategoryMoney() {
        Log.d("INSERTCATEGORY","LAY DATABSE");
        // insert danh muc cha
        insertDanhMucThu("Tiền thưởng", ConvertToByte(R.drawable.bonus), 0, "0");
        insertDanhMucThu("Lương", ConvertToByte(R.drawable.salary), 0, "0");
        insertDanhMucThu("Bán hàng",ConvertToByte(R.drawable.sale), 0, "0");
        insertDanhMucThu("Thu nhập khác", ConvertToByte(R.drawable.other), 0, "0");

        insertDanhMucChi("Ăn uống", ConvertToByte(R.drawable.eating), 0, "0");
        insertDanhMucChi("Hóa đơn", ConvertToByte(R.drawable.bill), 0, "0");
        insertDanhMucChi("Di chuyển", ConvertToByte(R.drawable.transport), 0, "0");
        insertDanhMucChi("Mua sắm", ConvertToByte(R.drawable.shopping), 0, "0");
        insertDanhMucChi("Bạn bè", ConvertToByte(R.drawable.friends), 0, "0");
        insertDanhMucChi("Giải trí", ConvertToByte(R.drawable.entertainment), 0, "0");
        insertDanhMucChi("Sức khỏe", ConvertToByte(R.drawable.health), 0, "0");
        insertDanhMucChi("Gia đình", ConvertToByte(R.drawable.home), 0, "0");
        insertDanhMucChi("Giáo dục", ConvertToByte(R.drawable.education), 0, "0");
        insertDanhMucChi("Chi tiêu khác", ConvertToByte(R.drawable.other), 0, "0");

        //insert danh muc con
        insertDanhMucChi("Nhà hàng", ConvertToByte(R.drawable.restaurant), 1, "Ăn uống");
        insertDanhMucChi("Cà phê", ConvertToByte(R.drawable.coffee), 1, "Ăn uống");
        insertDanhMucChi("Thức ăn", ConvertToByte(R.drawable.food), 1, "Ăn uống");

        insertDanhMucChi("Hóa đơn điện", ConvertToByte(R.drawable.electricity_bill), 1, "Hóa đơn");
        insertDanhMucChi("Hóa đơn nước", ConvertToByte(R.drawable.water_bill), 1, "Hóa đơn");
        insertDanhMucChi("Hóa đơn mạng", ConvertToByte(R.drawable.network_bill), 1, "Hóa đơn");
        insertDanhMucChi("Hóa đơn gas", ConvertToByte(R.drawable.gas_bill), 1, "Hóa đơn");

        insertDanhMucChi("Taxi", ConvertToByte(R.drawable.taxi), 1, "Di chuyển");
        insertDanhMucChi("Gửi xe", ConvertToByte(R.drawable.parking), 1, "Di chuyển");
        insertDanhMucChi("Xăng dầu", ConvertToByte(R.drawable.gas), 1, "Di chuyển");
        insertDanhMucChi("Bảo dưỡng", ConvertToByte(R.drawable.maintenance), 1, "Di chuyển");

        insertDanhMucChi("Quần áo", ConvertToByte(R.drawable.clothes), 1, "Mua sắm");
        insertDanhMucChi("Giày dép", ConvertToByte(R.drawable.shoes), 1, "Mua sắm");
        insertDanhMucChi("Phụ kiện", ConvertToByte(R.drawable.tools), 1, "Mua sắm");
        insertDanhMucChi("Thiết bị điện tử", ConvertToByte(R.drawable.e_device), 1, "Mua sắm");

        insertDanhMucChi("Cưới hỏi", ConvertToByte(R.drawable.wedding), 1, "Bạn bè");
        insertDanhMucChi("Tang lễ", ConvertToByte(R.drawable.funeral), 1, "Bạn bè");
        insertDanhMucChi("Từ thiện", ConvertToByte(R.drawable.charity), 1, "Bạn bè");
        insertDanhMucChi("Người yêu", ConvertToByte(R.drawable.lover), 1, "Bạn bè");
        insertDanhMucChi("Quà cáp", ConvertToByte(R.drawable.gift), 1, "Bạn bè");

        insertDanhMucChi("Phim ảnh", ConvertToByte(R.drawable.film), 1, "Bạn bè");
        insertDanhMucChi("Trò chơi", ConvertToByte(R.drawable.game), 1, "Bạn bè");
        insertDanhMucChi("Du lịch", ConvertToByte(R.drawable.travel), 1, "Bạn bè");
        insertDanhMucChi("Thể thao", ConvertToByte(R.drawable.sports), 1, "Bạn bè");

        insertDanhMucChi("Khám chữa bệnh", ConvertToByte(R.drawable.healthcare), 1, "Sức khỏe");
        insertDanhMucChi("Thuốc", ConvertToByte(R.drawable.medicine), 1, "Sức khỏe");
        insertDanhMucChi("Chăm sóc cá nhân", ConvertToByte(R.drawable.personal_care), 1, "Sức khỏe");
        insertDanhMucChi("Bảo hiểm", ConvertToByte(R.drawable.insurance), 1, "Sức khỏe");

        insertDanhMucChi("Con cái", ConvertToByte(R.drawable.children), 0,"Gia đình");
        insertDanhMucChi("Sữa chửa nhà cửa", ConvertToByte(R.drawable.home_repair), 0, "Gia đình");
        insertDanhMucChi("Dịch vụ gia đình", ConvertToByte(R.drawable.family_service), 0, "Gia đình");
        insertDanhMucChi("Thú cưng", ConvertToByte(R.drawable.pet), 0, "Gia đình");

        insertDanhMucChi("Sách", ConvertToByte(R.drawable.books), 1, "Giáo dục");
        insertDanhMucChi("Phần mềm giáo dục", ConvertToByte(R.drawable.education_software), 1, "Giáo dục");
        insertDanhMucChi("Khóa học", ConvertToByte(R.drawable.course), 1, "Giáo dục");
    }

    public void closeAll() {
        db.close();
    }

    //Convert ID from drawable to byte[]
    private byte[] ConvertToByte(int ID)
    {
        Resources res = context.getResources();
        Drawable drawable = res.getDrawable(ID);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();

        return bitMapData;
    }

    //Get list Adding Category from database to ArrayList<MoneyCategoryClass>
    public ArrayList<AddingCategoryClass> getAddingCategoryList()
    {
        ArrayList<AddingCategoryClass> addingList =new ArrayList<>();
        String queryString = "SELECT * from DANHMUCTHU WHERE TEN_DANH_MUC_CHA = " + "0";

        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst())
        {
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] image = cursor.getBlob(2);

                String queryChildString = "SELECT * from DANHMUCTHU WHERE TEN_DANH_MUC_CHA = '" + name + "'";
                SQLiteDatabase dbChild =this.getReadableDatabase();
                Cursor cursorChild = dbChild.rawQuery(queryChildString, null);
                ArrayList<MoneyCategoryClass> childList = new ArrayList<>();
                if(cursorChild.moveToFirst())
                {
                    do{
                        int idChild = cursorChild.getInt(0);
                        String nameChild = cursorChild.getString(1);
                        byte[] imageChild = cursorChild.getBlob(2);
                        AddingCategoryClass child =new AddingCategoryClass(idChild, nameChild,1,imageChild);
                        childList.add(child);
                    }while (cursorChild.moveToNext());
                }
                cursorChild.close();
                dbChild.close();
                AddingCategoryClass parent = new AddingCategoryClass(id, name, 1, image,childList);
                addingList.add(parent);
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return addingList;
    }

    //Get list Spending Category from database to ArrayList<MoneyCategoryClass>
    public ArrayList<SpendingCategoryClass> getSpendingCategoryList()
    {
        ArrayList<SpendingCategoryClass> spendingList =new ArrayList<>();
        String queryString = "SELECT * from DANHMUCCHI WHERE TEN_DANH_MUC_CHA = " + "0";

        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst())
        {
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] image = cursor.getBlob(2);

                String queryChildString = "SELECT * from DANHMUCCHI WHERE TEN_DANH_MUC_CHA = '" + name + "'";
                SQLiteDatabase dbChild =this.getReadableDatabase();
                Cursor cursorChild = dbChild.rawQuery(queryChildString, null);
                ArrayList<MoneyCategoryClass> childList = new ArrayList<>();
                if(cursorChild.moveToFirst())
                {
                    do{
                        int idChild = cursorChild.getInt(0);
                        String nameChild = cursorChild.getString(1);
                        byte[] imageChild = cursorChild.getBlob(2);
                        SpendingCategoryClass child =new SpendingCategoryClass(idChild, nameChild,-1,imageChild);
                        childList.add(child);
                    }while (cursorChild.moveToNext());
                }
                cursorChild.close();
                dbChild.close();
                SpendingCategoryClass parent = new SpendingCategoryClass(id, name, -1, image,childList);
                spendingList.add(parent);
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return spendingList;
    }

    //Xử lí table Image
    public void insertImageCategory(byte[] image){
        ContentValues category = new ContentValues();
        category.put("IMAGE_BLOB",image);
        db.insert("IMAGE_CATEGORY", null, category);
    }

    public void AddImageCategory(){
        insertImageCategory(ConvertToByte(R.drawable.bill));
        insertImageCategory(ConvertToByte(R.drawable.bonus));
        insertImageCategory(ConvertToByte(R.drawable.books));
        insertImageCategory(ConvertToByte(R.drawable.charity));
        insertImageCategory(ConvertToByte(R.drawable.children));
        insertImageCategory(ConvertToByte(R.drawable.clothes));
        insertImageCategory(ConvertToByte(R.drawable.coffee));
        insertImageCategory(ConvertToByte(R.drawable.course));
        insertImageCategory(ConvertToByte(R.drawable.e_device));
        insertImageCategory(ConvertToByte(R.drawable.eating));
        insertImageCategory(ConvertToByte(R.drawable.education));
        insertImageCategory(ConvertToByte(R.drawable.education_software));
        insertImageCategory(ConvertToByte(R.drawable.electricity_bill));
        insertImageCategory(ConvertToByte(R.drawable.entertainment));
        insertImageCategory(ConvertToByte(R.drawable.family_service));
        insertImageCategory(ConvertToByte(R.drawable.film));
        insertImageCategory(ConvertToByte(R.drawable.food));
        insertImageCategory(ConvertToByte(R.drawable.friends));
        insertImageCategory(ConvertToByte(R.drawable.funeral));
        insertImageCategory(ConvertToByte(R.drawable.game));
        insertImageCategory(ConvertToByte(R.drawable.gas));
        insertImageCategory(ConvertToByte(R.drawable.gas_bill));
        insertImageCategory(ConvertToByte(R.drawable.gift));
        insertImageCategory(ConvertToByte(R.drawable.health));
        insertImageCategory(ConvertToByte(R.drawable.lover));
        insertImageCategory(ConvertToByte(R.drawable.maintenance));
        insertImageCategory(ConvertToByte(R.drawable.network_bill));
        insertImageCategory(ConvertToByte(R.drawable.parking));
        insertImageCategory(ConvertToByte(R.drawable.personal_care));
        insertImageCategory(ConvertToByte(R.drawable.pet));
        insertImageCategory(ConvertToByte(R.drawable.restaurant));
        insertImageCategory(ConvertToByte(R.drawable.salary));
        insertImageCategory(ConvertToByte(R.drawable.sale));
        insertImageCategory(ConvertToByte(R.drawable.shoes));
        insertImageCategory(ConvertToByte(R.drawable.shopping));
        insertImageCategory(ConvertToByte(R.drawable.sports));
        insertImageCategory(ConvertToByte(R.drawable.taxi));
        insertImageCategory(ConvertToByte(R.drawable.tools));
        insertImageCategory(ConvertToByte(R.drawable.travel));
        insertImageCategory(ConvertToByte(R.drawable.water_bill));
        insertImageCategory(ConvertToByte(R.drawable.wedding));
        insertImageCategory(ConvertToByte(R.drawable.other));
    }

    public ArrayList<byte[]> getImageCategory(){
        ArrayList<byte[]> imageList = new ArrayList<>();
        String queryString = "SELECT * FROM IMAGE_CATEGORY";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToFirst()){
            do{
                byte[] image = cursor.getBlob(1);

                imageList.add(image);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return imageList;
    }
}
