package com.example.myproject22.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
import com.example.myproject22.Model.SavingDatabaseHelper;
import com.example.myproject22.R;
import com.example.myproject22.Presenter.ASavingDatabaseHelper;
import com.example.myproject22.Presenter.SavingInterface;
import com.example.myproject22.Presenter.SavingPresenter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

public class SavingActivity extends AppCompatActivity implements SavingInterface {

    private BarChart weekchart;

    // for debugg
    double ivSize = 0;


    // tiet kiem
    private TextView tvDayStreak;
    private TextView tvTotalSaving;

    // muc tieu
    private TextView tvGoalName;
    private TextView tvGoalDescription;
    private TextView tvMoneyGoal;
    private ImageView ivGoal;
    private TextRoundCornerProgressBar ProgressSaving;


    private static final int DAYSOFWEEK = 7;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SavingPresenter mSavingPresenter;

    SQLiteDatabase db;
    Cursor cursor;
    ArrayList<String> ngayTrongTuan = new ArrayList<String>();
    ArrayList<BarEntry> recordTietKiem = new ArrayList<>();
    SavingDatabaseHelper ASavingDatabaseHelper = new SavingDatabaseHelper(this, null, null, 0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_saving);

        ngayTrongTuan.add("Chủ Nhật");
        ngayTrongTuan.add("Thứ 2");
        ngayTrongTuan.add("Thứ 3");
        ngayTrongTuan.add("Thứ 4");
        ngayTrongTuan.add("Thứ 5");
        ngayTrongTuan.add("Thứ 6");
        ngayTrongTuan.add("Thứ 7");

        InitViews();

        mSavingPresenter = new SavingPresenter(this);
        mSavingPresenter.LoadGetTietKiemData();
        mSavingPresenter.LoadTietKiem();
        mSavingPresenter.LoadMucTieu();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ASavingDatabaseHelper.closeAll();
    }


    @Override
    public void LoadChiTietTietKiem() {
        new LoadChiTietTietKiem().execute();
    }

    @Override
    public void LoadTietKiem() {
        new LoadTietKiem().execute();
    }

    @Override
    public void LoadMucTieu() {
        new LoadMucTieu().execute();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        mSavingPresenter.LoadMucTieu();
    }


    public void AddRecords() {

        ASavingDatabaseHelper.insertChitietTienChi(20, "me cho", 1, null, "2020-05-9");
        ASavingDatabaseHelper.insertChitietTienChi(30, "me cho", 1, null, "2020-05-10");
        ASavingDatabaseHelper.insertChitietTienChi(10, "me cho", 1, null, "2020-05-11");
        ASavingDatabaseHelper.insertChitietTienChi(20, "me cho", 1, null, "2020-05-12");
        ASavingDatabaseHelper.insertChitietTienChi(30, "me cho", 1, null, "2020-05-13");
        ASavingDatabaseHelper.insertChitietTienChi(10, "me cho", 1, null, "2020-05-14");
        ASavingDatabaseHelper.insertChitietTienChi(12, "me cho", 1, null, "2020-05-15");
        ASavingDatabaseHelper.insertChitietTienChi(500, "me cho", 1, null, "2020-05-15");

        ASavingDatabaseHelper.insertChiTietTienThu(500, "me cho", 1, "2020-05-15");
        ASavingDatabaseHelper.insertChiTietTienThu(500, "me cho", 1, "2020-05-15");
    }

    public void InitViews() {
        weekchart = findViewById(R.id.weekChar);
        tvDayStreak = findViewById(R.id.tvDayStreak);
        tvTotalSaving = findViewById(R.id.tvTotalSaving);


        // saving
        tvGoalName = findViewById(R.id.tvGoalName);
        tvGoalDescription = findViewById(R.id.tvGoalDescription);
        tvMoneyGoal = findViewById(R.id.tvMoneyGoal);
        ProgressSaving = findViewById(R.id.Progress_saving);
        ivGoal = findViewById(R.id.ivGoal);
    }


    // async stask
    class LoadChiTietTietKiem extends AsyncTask<Void, Process, Void> {

        @Override
        protected void onPreExecute() {
            db = ASavingDatabaseHelper.getWritableDatabase();
            weekchart.animateY(2000);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                cursor = db.query("CHITIETTIETKIEM", new String[]{"_id_tietkiem", "SOTIENTIETKIEMTRONGNGAY", "NGAY_TIETKIEM"},
                        null, null, null, null, "_id_ChiTietTietKiem DESC");

                if (cursor.moveToFirst()) {
                    int cnt = 0;
                    do {
                        cnt++;
                        double tietKiem = cursor.getDouble(1);
                        String strDate = cursor.getString(2);
                        Date date = dateFormat.parse(strDate);
                        int ngay = date.getDay();
                        recordTietKiem.add(new BarEntry((float) ngay, (float) tietKiem));
                    }
                    while (cnt <= DAYSOFWEEK && cursor.moveToNext());
                }
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // assign value to char1
            weekchart = findViewById(R.id.weekChar);

            //get data from database
            BarDataSet barDataSet = new BarDataSet(recordTietKiem, "Ngày trong tuần");
            barDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
            barDataSet.setValueTextColor(Color.BLACK);
            barDataSet.setValueTextSize(0f);
            barDataSet.setValueTypeface(Typeface.MONOSPACE);
            barDataSet.setBarBorderWidth(1);
            BarData barData = new BarData(barDataSet);


            barData.setBarWidth(0.5f);
            weekchart.setFitBars(true);
            weekchart.setData(barData);
            weekchart.getDescription().setText("");
            weekchart.setHighlightFullBarEnabled(true);


            // set XAxis value formater
            XAxis xAxis = weekchart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(ngayTrongTuan));

            xAxis.setPosition(XAxis.XAxisPosition.TOP);
            xAxis.setDrawAxisLine(false);
            xAxis.setDrawGridLines(true);
            xAxis.setGranularity(1f);
            xAxis.setDrawLabels(true);
            xAxis.setLabelCount(ngayTrongTuan.size());

        }
    }

    class LoadTietKiem extends AsyncTask<Void, Process, Boolean> {

        double totalSaving;
        int dayStreak;


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            cursor = ASavingDatabaseHelper.getTietKiem();
            if (cursor.moveToFirst()) {
                totalSaving = cursor.getDouble(1);
                dayStreak = cursor.getInt(4);
                return true;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            tvTotalSaving.setText(String.valueOf(totalSaving));
            tvDayStreak.setText(String.valueOf(dayStreak));
        }
    }

    class LoadMucTieu extends AsyncTask<Void, Process, Boolean> {


        String goalName;
        String goalDescription;
        double goalMoney;
        double SavingMoney;
        byte[] goal_image;
        String strGoal;
        double progress;

        @Override
        protected void onPreExecute() {

        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            cursor = ASavingDatabaseHelper.getMucTieu();

            try {
                if (cursor.moveToFirst()) {
                    goalName = "[ " + cursor.getString(1) + " ]";
                    goalDescription = "*" + cursor.getString(2) + "*";
                    goalMoney = cursor.getDouble(3);
                    SavingMoney = cursor.getDouble(4);
                    strGoal = SavingMoney + "/" + goalMoney;
                    progress = SavingMoney / goalMoney;
                    goal_image = cursor.getBlob(5);


                    return true;
                }
            } catch (Exception e) {
            }
            return false;
        }


        @Override
        protected void onPostExecute(Boolean havingRecord) {

            if (havingRecord) {

                tvGoalName.setText(goalName);
                tvGoalDescription.setText(goalDescription);
                tvMoneyGoal.setText(strGoal);
                ProgressSaving.setProgressText(String.valueOf(progress));
                ProgressSaving.setProgress((float) progress);


                Bitmap bitmap = ByteToBitmap(goal_image);
                Drawable d = new BitmapDrawable(bitmap);
                ivGoal.setImageDrawable(d);

                if (progress <= 90) {
                    ProgressSaving.setSecondaryProgress((float) progress + 10);
                }

            }
        }
    }


    public void onModifyGoalClicked(View view) {
        Intent intent = new Intent(this, GoalActivity.class);
        startActivity(intent);
    }

    public static Bitmap ByteToBitmap(byte[] images) {
        if (images != null) {
            images = decompress(images);
            Bitmap bitmap = BitmapFactory.decodeByteArray(images, 0, images.length);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            return bitmap;
        }
        return null;
    }

    // decompress
    public static byte[] decompress(byte[] data) {

        try {
            Inflater inflater = new Inflater();
            inflater.setInput(data);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
            byte[] buffer = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
            byte[] output = outputStream.toByteArray();

            return output;
        } catch (Exception e) {

        }
        return null;
    }

}

