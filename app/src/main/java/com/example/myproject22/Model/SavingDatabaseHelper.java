//Chỉnh lại package
package com.example.myproject22.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myproject22.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
                "IMAGE_TIENCHI INTEGER," +
                "LOAI_THUOC_TINH INTEGER," +
                "ID_MUC_CHA INTEGER," +
                "FOREIGN KEY (ID_MUC_CHA) REFERENCES DANHMUCCHI(_id_danhMucChi))");

        //lưu thông tin danh mục thu
        db.execSQL("CREATE TABLE DANHMUCTHU(_id_danhMucThu INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TEN_DANH_MUC_THU TEXT," +
                "IMAGE_TIENTHU INTEGER," +
                "LOAI_THUOC_TINH INTEGER," +
                "ID_MUC_CHA INTEGER," +
                "FOREIGN KEY (ID_MUC_CHA) REFERENCES DANHMUCTHU(_id_danhMucThu))");

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
    public void insertDanhMucChi(String tenDanhMucChi, int imageDanhMucChi, int IsChild, int idMucCha, SQLiteDatabase db) {
        ContentValues danhMuc = new ContentValues();
        danhMuc.put("TEN_DANH_MUC_CHI", tenDanhMucChi);
        danhMuc.put("LOAI_THUOC_TINH", IsChild);
        danhMuc.put("ID_MUC_CHA", idMucCha);
        danhMuc.put("IMAGE_TIENCHI", imageDanhMucChi);

        db.insert("DANHMUCCHI", null, danhMuc);
    }

    public void insertDanhMucThu(String tenDanhMucThu, int imageDanhMucThu, int IsChild, int idMucCha, SQLiteDatabase db) {
        ContentValues danhMuc = new ContentValues();
        danhMuc.put("TEN_DANH_MUC_THU", tenDanhMucThu);
        danhMuc.put("LOAI_THUOC_TINH", IsChild);
        danhMuc.put("ID_MUC_CHA", idMucCha);
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

        db = getWritableDatabase();

        // get old data
        Double tienThu = 0d;
        String strDate = dateFormat.format(new Date());


        // get data before
        cursor = getTienThu();
        if (cursor.moveToFirst()) {

            tienThu = cursor.getDouble(1);
            strDate = cursor.getString(2);
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

        updateMucTieu(sotienthu);

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
        String strDate = dateFormat.format(new Date());


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

        updateMucTieu(-soTienChi);

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

        updateMucTieu(tienThu - tienChi);
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

        updateMucTieu(tienThu - tienChi);
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

            if (cursor.getInt(6) == 0) {// chua hoan thanh
                // lay du lieu cu
                int highestID = cursor.getInt(0);
                double oldTienTietKiem = cursor.getDouble(4);
                double soTienMucTieu = cursor.getDouble(3);

                ContentValues contentValues = new ContentValues();
                // if enough -- > finish
                if (oldTienTietKiem + soTienTietKiem >= soTienMucTieu) {
                    contentValues.put("HOANTHANH", 1);
                }
                if (oldTienTietKiem + soTienTietKiem >= 0)
                    contentValues.put("SOTIENTIETKIEM", oldTienTietKiem + soTienTietKiem);
                else{
                    contentValues.put("SOTIENTIETKIEM", 0);
                }
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

        // insert danh muc
        insertDanhMucThu("Tiền Thưởng", R.drawable.bonus, 0, -1, db);
        insertDanhMucThu("Lương", R.drawable.salary, 0, -1, db);
        insertDanhMucThu("Bán hàng", R.drawable.sale, 0, -1, db);
        insertDanhMucThu("Thu nhập khác", R.drawable.other, 0, -1, db);

        insertDanhMucChi("Ăn uống", R.drawable.eating, 0, -1, db);
        insertDanhMucChi("Hóa đơn", R.drawable.bill, 0, -1, db);
        insertDanhMucChi("Di chuyển", R.drawable.transport, 0, -1, db);
        insertDanhMucChi("Mua sắm", R.drawable.shopping, 0, -1, db);
        insertDanhMucChi("Bạn bè", R.drawable.friends, 0, -1, db);
        insertDanhMucChi("Giải trí", R.drawable.entertainment, 0, -1, db);
        insertDanhMucChi("Sức khỏe", R.drawable.health, 0, -1, db);
        insertDanhMucChi("Gia đình", R.drawable.home, 0, -1, db);
        insertDanhMucChi("Giáo dục", R.drawable.education, 0, -1, db);
        insertDanhMucChi("Chi tiêu khác", R.drawable.other, 0, -1, db);

    }

    public void Addsomething() {
        insertDanhMucChi("Nhà hàng", R.drawable.restaurant, 1, getChiID("Ăn uống"), db);
        insertDanhMucChi("Cà phê", R.drawable.coffee, 1, getChiID("Ăn uống"), db);
        insertDanhMucChi("Thức ăn", R.drawable.food, 1, getChiID("Ăn uống"), db);

        insertDanhMucChi("Hóa đơn điện", R.drawable.electricity_bill, 1, getChiID("Hóa đơn"), db);
        insertDanhMucChi("Hóa đơn nước", R.drawable.water_bill, 1, getChiID("Hóa đơn"), db);
        insertDanhMucChi("Hóa đơn mạng", R.drawable.network_bill, 1, getChiID("Hóa đơn"), db);
        insertDanhMucChi("Hóa đơn gas", R.drawable.gas_bill, 1, getChiID("Hóa đơn"), db);

        insertDanhMucChi("Taxi", R.drawable.taxi, 1, getChiID("Di chuyển"), db);
        insertDanhMucChi("Gửi xe", R.drawable.parking, 1, getChiID("Di chuyển"), db);
        insertDanhMucChi("Xăng dầu", R.drawable.gas, 1, getChiID("Di chuyển"), db);
        insertDanhMucChi("Bảo dưỡng", R.drawable.maintenance, 1, getChiID("Di chuyển"), db);

        insertDanhMucChi("Quần áo", R.drawable.clothes, 1, getChiID("Mua sắm"), db);
        insertDanhMucChi("Giày dép", R.drawable.shoes, 1, getChiID("Mua sắm"), db);
        insertDanhMucChi("Phụ kiện", R.drawable.tools, 1, getChiID("Mua sắm"), db);
        insertDanhMucChi("Thiết bị điện tử", R.drawable.e_device, 1, getChiID("Mua sắm"), db);

        insertDanhMucChi("Cưới hỏi", R.drawable.wedding, 1, getChiID("Bạn bè"), db);
        insertDanhMucChi("Tang lễ", R.drawable.funeral, 1, getChiID("Bạn bè"), db);
        insertDanhMucChi("Từ thiện", R.drawable.charity, 1, getChiID("Bạn bè"), db);
        insertDanhMucChi("Người yêu", R.drawable.lover, 1, getChiID("Bạn bè"), db);
        insertDanhMucChi("Quà cáp", R.drawable.gift, 1, getChiID("Bạn bè"), db);

        insertDanhMucChi("Phim ảnh", R.drawable.film, 1, getChiID("Giải trí"), db);
        insertDanhMucChi("Trò chơi", R.drawable.game, 1, getChiID("Giải trí"), db);
        insertDanhMucChi("Du lịch", R.drawable.travel, 1, getChiID("Giải trí"), db);
        insertDanhMucChi("Thể thao", R.drawable.sports, 1, getChiID("Giải trí"), db);

        insertDanhMucChi("Khám chữa bệnh", R.drawable.healthcare, 1, getChiID("Sức khỏe"), db);
        insertDanhMucChi("Thuốc", R.drawable.medicine, 1, getChiID("Sức khỏe"), db);
        insertDanhMucChi("Chăm sóc cá nhân", R.drawable.personal_care, 1, getChiID("Sức khỏe"), db);
        insertDanhMucChi("Bảo hiểm", R.drawable.insurance, 1, getChiID("Sức khỏe"), db);

        insertDanhMucChi("Con cái", R.drawable.children, 0, getChiID("Gia đình"), db);
        insertDanhMucChi("Sữa chửa nhà cửa", R.drawable.home_repair, 0, getChiID("Gia đình"), db);
        insertDanhMucChi("Dịch vụ gia đình", R.drawable.family_service, 0, getChiID("Gia đình"), db);
        insertDanhMucChi("Thú cưng", R.drawable.pet, 0, getChiID("Gia đình"), db);

        insertDanhMucChi("Sách", R.drawable.books, 1, getChiID("Giáo dục"), db);
        insertDanhMucChi("Phần mềm giáo dục", R.drawable.education_software, 1, getChiID("Giáo dục"), db);
        insertDanhMucChi("Khóa học", R.drawable.course, 1, getChiID("Giáo dục"), db);
    }

    public void closeAll() {
        db.close();
    }

}
