//Chỉnh lại package
package com.hfad.project_1.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.hfad.project_1.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SavingDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "billionaire";
    private static final int DB_VERSION = 1;


    SQLiteDatabase db;
    Cursor cursor;


    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

        //lưu thông tin tiết kiệm
        db.execSQL("CREATE TABLE TIETKIEM(_id_tietKiem INTEGER PRIMARY KEY AUTOINCREMENT," +
                "_id_user INTGER," +
                "TONGTIENTIETKIEM DOUBLE," +
                "TONGTIENCHI DOUBLE," +
                "TONGTIENTHU DOUBLE," +
                "FOREIGN KEY(_id_user) REFERENCES USER(_id_user))");

        //lưu thông tin chi tiết tiết kiệm
        db.execSQL("CREATE TABLE CHITIETTIETKIEM (_id_chiTietTietKiem INTEGER PRIMARY KEY AUTOINCREMENT," +
                "_id_tietKiem INTEGER ," +
                "TONGSOTIENCHITRONGNGAY DOUBLE," +
                "TONGSOTIENTHUTRONGNGAY DOUBLE," +
                "SOTIENTIETKIEMTRONGNGAY DOUBLE," +
                "NGAY_TIETKIEM TEXT, " +
                "FOREIGN KEY(_id_tietkiem) REFERENCES TIETKIEM(_id_tietKiem))");

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


    public void insertChiTietTienThu(double sotienthu, String chiTietTienThu, int _id_danhMucThu) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues record = new ContentValues();
        record.put("SOTIENTHU", sotienthu);
        record.put("CHITIET_TIENTHU", chiTietTienThu);
        record.put("_id_danhMucThu", _id_danhMucThu);
        record.put("_id_tienThu", 1);

        // insert date and time
        Date date = new Date();
        record.put("NGAY_TIENTHU", dateFormat.format(date));

        db.insert("CHITIETTIENTHU", null, record);
    }


    public void insertChitietTienChi(double soTienChi, String chiTietTienChi, int _id_danhMucChi) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues record = new ContentValues();
        record.put("SOTIENCHI", soTienChi);
        record.put("CHITIET_TIENCHI", chiTietTienChi);
        record.put("_id_danhMucChi", _id_danhMucChi);
        record.put("_id_tienChi", 1);

        // insert date and time
        Date date = new Date();
        record.put("NGAY_TIENCHI", dateFormat.format(date));

        db.insert("CHITIETTIENCHI", null, record);
    }


    public void updateTietKiem() {


    }

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


        ContentValues beginTietKiem = new ContentValues();
        beginTietKiem.put("_id_user", 1);
        beginTietKiem.put("TONGTIENCHI", 0);
        beginTietKiem.put("TONGTIENTHU", 0);
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
        insertDanhMucChi("Nhà hàng", R.drawable.restaurant, 1, getID("Ăn uống"), db);
        insertDanhMucChi("Cà phê", R.drawable.coffee, 1, getID("Ăn uống"), db);
        insertDanhMucChi("Thức ăn", R.drawable.food, 1, getID("Ăn uống"), db);

        insertDanhMucChi("Hóa đơn điện", R.drawable.electricity_bill, 1, getID("Hóa đơn"), db);
        insertDanhMucChi("Hóa đơn nước", R.drawable.water_bill, 1, getID("Hóa đơn"), db);
        insertDanhMucChi("Hóa đơn mạng", R.drawable.network_bill, 1, getID("Hóa đơn"), db);
        insertDanhMucChi("Hóa đơn gas", R.drawable.gas_bill, 1, getID("Hóa đơn"), db);

        insertDanhMucChi("Taxi", R.drawable.taxi, 1, getID("Di chuyển"), db);
        insertDanhMucChi("Gửi xe", R.drawable.parking, 1, getID("Di chuyển"), db);
        insertDanhMucChi("Xăng dầu", R.drawable.gas, 1, getID("Di chuyển"), db);
        insertDanhMucChi("Bảo dưỡng", R.drawable.maintenance, 1, getID("Di chuyển"), db);

        insertDanhMucChi("Quần áo", R.drawable.clothes, 1, getID("Mua sắm"), db);
        insertDanhMucChi("Giày dép", R.drawable.shoes, 1, getID("Mua sắm"), db);
        insertDanhMucChi("Phụ kiện", R.drawable.tools, 1, getID("Mua sắm"), db);
        insertDanhMucChi("Thiết bị điện tử", R.drawable.e_device, 1, getID("Mua sắm"), db);

        insertDanhMucChi("Cưới hỏi", R.drawable.wedding, 1, getID("Bạn bè"), db);
        insertDanhMucChi("Tang lễ", R.drawable.funeral, 1, getID("Bạn bè"), db);
        insertDanhMucChi("Từ thiện", R.drawable.charity, 1, getID("Bạn bè"), db);
        insertDanhMucChi("Người yêu", R.drawable.lover, 1, getID("Bạn bè"), db);
        insertDanhMucChi("Quà cáp", R.drawable.gift, 1, getID("Bạn bè"), db);

        insertDanhMucChi("Phim ảnh", R.drawable.film, 1, getID("Giải trí"), db);
        insertDanhMucChi("Trò chơi", R.drawable.game, 1, getID("Giải trí"), db);
        insertDanhMucChi("Du lịch", R.drawable.travel, 1, getID("Giải trí"), db);
        insertDanhMucChi("Thể thao", R.drawable.sports, 1, getID("Giải trí"), db);

        insertDanhMucChi("Khám chữa bệnh", R.drawable.healthcare, 1, getID("Sức khỏe"), db);
        insertDanhMucChi("Thuốc", R.drawable.medicine, 1, getID("Sức khỏe"), db);
        insertDanhMucChi("Chăm sóc cá nhân", R.drawable.personal_care, 1, getID("Sức khỏe"), db);
        insertDanhMucChi("Bảo hiểm", R.drawable.insurance, 1, getID("Sức khỏe"), db);

        insertDanhMucChi("Con cái", R.drawable.children, 0, getID("Gia đình"), db);
        insertDanhMucChi("Sữa chửa nhà cửa", R.drawable.home_repair, 0, getID("Gia đình"), db);
        insertDanhMucChi("Dịch vụ gia đình", R.drawable.family_service, 0, getID("Gia đình"), db);
        insertDanhMucChi("Thú cưng", R.drawable.pet, 0, getID("Gia đình"), db);

        insertDanhMucChi("Sách", R.drawable.books, 1, getID("Giáo dục"), db);
        insertDanhMucChi("Phần mềm giáo dục", R.drawable.education_software, 1, getID("Giáo dục"), db);
        insertDanhMucChi("Khóa học", R.drawable.course, 1, getID("Giáo dục"), db);
    }

    public Cursor getTienThu() {
        db = getReadableDatabase();
        Cursor cursor = db.query("CHITIETTIENTHU", new String[]{"_id_chiTietTienThu", "SOTIENTHU"}, null, null, null, null, "_id_chiTietTienThu");
        return cursor;
    }

    public int getID(String name) {
        int ID = 0;
        db = getWritableDatabase();
        Cursor cursor = db.query("DANHMUCCHI", new String[]{"_id_danhMucChi"}, "TEN_DANH_MUC_CHI=?", new String[]{name}, null, null, null);
        if (cursor.moveToFirst()) {
            ID = cursor.getInt(0);
        }
        return ID;
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

    public Cursor getTienChi() {
        db = getReadableDatabase();
        cursor = db.query("CHITIETTIENCHI", new String[]{"_id_chiTietTienChi", "SOTIENCHI"}, null, null, null, null, "_id_chiTietTienChi");
        return cursor;
    }

    public void closeAll() {
        db.close();
    }

}
