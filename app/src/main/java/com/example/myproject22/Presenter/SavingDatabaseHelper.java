package com.example.myproject22.Presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;

public class SavingDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "billionaire";
    private static final int DB_VERSION = 1;

    SQLiteDatabase db;
    Cursor cursor;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public SavingDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE USER (_id_user INTEGER PRIMARY KEY AUTOINCREMENT," +
                "USERNAME TEXT," +
                "PASSWORD TEXT, " +
                "THUNHAP DOUBLE, " +
                "NGAY_KICHHOAT TEXT," +
                "USER_IMAGE BLOB," +
                "HOTEN TEXT" +
                ")");


        db.execSQL("CREATE TABLE DANHMUCCHI(_id_danhMucChi INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TEN_DANH_MUC_CHI TEXT)");


        db.execSQL("CREATE TABLE DANHMUCTHU(_id_danhMucThu INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TEN_DANH_MUC_THU TEXT)");


        db.execSQL("CREATE TABLE TIENTHU(_id_tienThu INTEGER PRIMARY KEY AUTOINCREMENT," +
                "_id_user INTGER," +
                "TONGTIENTHU DOUBLE," +
                "FOREIGN KEY(_id_user) REFERENCES USER(_id_user))");


        db.execSQL("CREATE TABLE CHITIETTIENTHU (_id_chiTietTienThu INTEGER PRIMARY KEY AUTOINCREMENT," +
                "_id_tienThu INTEGER ," +
                "SOTIENTHU DOUBLE," +
                "CHITIET_TIENTHU TEXT, " +
                "_id_danhMucThu INTEGER, " +
                "NGAY_TIENTHU TEXT, " +
                "GIO_TIENTHU TEXT, " +
                "FOREIGN KEY (_id_danhMucThu) REFERENCES DANHMUCTHU(_id_danhMucThu), " +
                "FOREIGN KEY(_id_tienThu) REFERENCES TIENTHU(_id_tienThu))");


        db.execSQL("CREATE TABLE TIENCHI(_id_tienChi INTEGER PRIMARY KEY AUTOINCREMENT," +
                "_id_user INTGER," +
                "TONGTIENCHI DOUBLE," +
                "FOREIGN KEY(_id_user) REFERENCES USER(_id_user))");


        db.execSQL("CREATE TABLE CHITIETTIENCHI (_id_chiTietTienChi INTEGER PRIMARY KEY AUTOINCREMENT," +
                "_id_tienChi INTEGER ," +
                "SOTIENCHI DOUBLE," +
                "CHITIET_TIENCHI TEXT, " +
                "IMAGE_TIENCHI BLOB, " +
                "NGAY_TIENCHI TEXT, " +
                "GIO_TIENCHI TEXT, " +
                "_id_danhMucChi INTEGER, " +
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


        // insert new user
        addSomeBeginDatabase(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void queryData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }


    // danh muc
    public void insertDanhMucChi(String tenDanhMucChi, SQLiteDatabase db) {
        ContentValues danhMuc = new ContentValues();
        danhMuc.put("TEN_DANH_MUC_CHI", tenDanhMucChi);
        db.insert("DANHMUCCHI", null, danhMuc);
    }

    public void insertDanhMucThu(String tenDanhMucThu, SQLiteDatabase db) {
        ContentValues danhMuc = new ContentValues();
        danhMuc.put("TEN_DANH_MUC_THU", tenDanhMucThu);
        db.insert("DANHMUCTHU", null, danhMuc);
    }

    public void addSomeBeginDatabase(SQLiteDatabase db) {


        // insert user
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


        // insert tietkiem
        ContentValues beginTietKiem = new ContentValues();
        beginTietKiem.put("_id_user", 1);
        beginTietKiem.put("TONGTIENCHI", 0);
        beginTietKiem.put("TONGTIENTHU", 0);
        beginTietKiem.put("TONGTIENTIETKIEM", 0);
        beginTietKiem.put("SONGAY", 1);
        db.insert("TIETKIEM", null, beginTietKiem);

        // insert danh muc
        insertDanhMucThu("THUC AN", db);
        insertDanhMucThu("NHA O ", db);
        insertDanhMucThu("GIAI TRI", db);
        insertDanhMucThu("DA PHO", db);


        insertDanhMucChi("THUC AN", db);
        insertDanhMucChi("NHA O ", db);
        insertDanhMucChi("GIAI TRI", db);
        insertDanhMucChi("DA PHO", db);

    }


    // tien thu
    public void insertChiTietTienThu(double sotienthu, String chiTietTienThu, int _id_danhMucThu) {
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

            updateTietKiemDoThu(tienThu, strDate, dateFormat.format(new Date()));

            // insert part
            ContentValues record = new ContentValues();
            record.put("SOTIENTHU", sotienthu);
            record.put("CHITIET_TIENTHU", chiTietTienThu);
            record.put("_id_danhMucThu", _id_danhMucThu);
            record.put("_id_tienThu", 1);


            // insert date and time
            Date date = new Date();
            record.put("NGAY_TIENTHU", dateFormat.format(date));
            db.insert("CHITIETTIENTHU", null, record);

        } catch (ParseException e) {

        }
    }

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

    public Cursor getTienThu() {
        db = getReadableDatabase();
        Cursor cursor = db.query("CHITIETTIENTHU", new String[]{"_id_chiTietTienThu", "SOTIENTHU", "NGAY_TIENTHU"}, null, null, null, null, "_id_chiTietTienThu DESC");
        return cursor;
    }


    // tien chi
    public void insertChitietTienChi(double soTienChi, String chiTietTienChi, int _id_danhMucChi, byte[] image_chi) {
        try {
            // get old data
            db = getWritableDatabase();
            Date dateBefore = new Date();
            Double tienChi = 0d;
            String strDate = dateFormat.format(new Date());


            // get data before
            cursor = getTienChi();
            if (cursor.moveToFirst()) {

                tienChi = cursor.getDouble(1);
                strDate = cursor.getString(2);
                // get date
                dateBefore = dateFormat.parse(strDate);
            }
            updateTietKiemDoChi(tienChi, strDate, dateFormat.format(new Date()));

            ContentValues record = new ContentValues();
            record.put("SOTIENCHI", soTienChi);
            record.put("CHITIET_TIENCHI", chiTietTienChi);
            record.put("_id_danhMucChi", _id_danhMucChi);
            record.put("_id_tienChi", 1);

            // insert new data

            // insert date and time
            Date dateAdd = new Date();
            record.put("NGAY_TIENCHI", dateFormat.format(dateAdd));

            // insert image
            if (image_chi != null)
                record.put("IMAGE_TIENCHI", image_chi);

            db.insert("CHITIETTIENCHI", null, record);


        } catch (ParseException e) {
            Log.d("DATABASE", "Cannot parse datetime string !");
        }
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
                }
            } else { // different day
                insertChiTietTietKiem(0, soTienChi, dateAdd);
            }

            updateSoNgayTietKiem(dateBefore, dateAdd, soNgay);

        } catch (Exception e) {

        }


    }

    public Cursor getTienChi() {
        db = getReadableDatabase();
        cursor = db.query("CHITIETTIENCHI", new String[]{"_id_chiTietTienChi", "SOTIENCHI", "NGAY_TIENCHI"}, null, null, null, null, "_id_chiTietTienChi DESC");
        return cursor;
    }


    // chi tiet tiet kiem
    public void insertChiTietTietKiem(double tienThu, double tienChi) {
        ContentValues record = new ContentValues();
        record.put("TONGSOTIENCHITRONGNGAY", tienChi);
        record.put("TONGSOTIENTHUTRONGNGAY", tienThu);
        record.put("SOTIENTIETKIEMTRONGNGAY", 0);
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

    public void closeAll() {
        db.close();
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

}