package com.example.myproject22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.PrimitiveIterator;

public class MainActivity extends AppCompatActivity implements SavingInterface {

    private BarChart weekchart;

    // tiet kiem
    private TextView tvDayStreak;
    private TextView tvTotalSaving;

    // muc tieu
    private TextView tvGoalName;
    private TextView tvGoalDescription;
    private TextView tvMoneyGoal;
    private TextRoundCornerProgressBar ProgressSaving;


    private static final int DAYSOFWEEK = 7;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SavingPresenter mSavingPresenter;

    SQLiteDatabase db;
    Cursor cursor;
    ArrayList<String> ngayTrongTuan = new ArrayList<String>();
    ArrayList<BarEntry> recordTietKiem = new ArrayList<>();
    SavingDatabaseHelper savingDatabaseHelper = new SavingDatabaseHelper(this, null, null, 0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ngayTrongTuan.add("Chủ Nhật");
        ngayTrongTuan.add("Thứ 2");
        ngayTrongTuan.add("Thứ 3");
        ngayTrongTuan.add("Thứ 4");
        ngayTrongTuan.add("Thứ 5");
        ngayTrongTuan.add("Thứ 6");
        ngayTrongTuan.add("Thứ 7");

        InitViews();

        AddRecords();

        mSavingPresenter = new SavingPresenter(this);
        mSavingPresenter.LoadGetTietKiemData();
        mSavingPresenter.LoadTietKiem();
        mSavingPresenter.LoadMucTieu();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        savingDatabaseHelper.closeAll();
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
        new LoadTietKiem().execute();
    }


    public void AddRecords() {
/*        savingDatabaseHelper.insertChiTietTietKiem(200, 501100, "2020-05-10");
        savingDatabaseHelper.insertChiTietTietKiem(200, 601100, "2020-05-11");
        savingDatabaseHelper.insertChiTietTietKiem(200, 301100, "2020-05-12");
        savingDatabaseHelper.insertChiTietTietKiem(200, 401100, "2020-05-13");
        savingDatabaseHelper.insertChiTietTietKiem(200, 301100, "2020-05-14");
        savingDatabaseHelper.insertChiTietTietKiem(200, 401100, "2020-05-15");
        savingDatabaseHelper.insertChiTietTietKiem(200, 501100, "2020-05-16");*/

        savingDatabaseHelper.insertChitietTienChi(20, "me cho", 1, null, "2020-05-9");
        savingDatabaseHelper.insertChitietTienChi(30, "me cho", 1, null, "2020-05-10");
        savingDatabaseHelper.insertChitietTienChi(10, "me cho", 1, null, "2020-05-11");
        savingDatabaseHelper.insertChitietTienChi(20, "me cho", 1, null, "2020-05-12");
        savingDatabaseHelper.insertChitietTienChi(30, "me cho", 1, null, "2020-05-13");
        savingDatabaseHelper.insertChitietTienChi(10, "me cho", 1, null, "2020-05-14");
        savingDatabaseHelper.insertChitietTienChi(12, "me cho", 1, null, "2020-05-15");

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
    }




    // async stask
    class LoadChiTietTietKiem extends AsyncTask<Void, Process, Void> {

        @Override
        protected void onPreExecute() {
            db = savingDatabaseHelper.getWritableDatabase();
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

            weekchart.animateY(2000);
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
            cursor = savingDatabaseHelper.getTietKiem();
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

    class LoadMucTieu extends AsyncTask<Void, Process, Void> {

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
        protected Void doInBackground(Void... voids) {
            cursor = savingDatabaseHelper.getMucTieu();

            if (cursor.moveToFirst()) {
                goalName = cursor.getString(1);
                goalDescription = cursor.getString(2);
                goalMoney = cursor.getDouble(3);
                SavingMoney = cursor.getDouble(4);
                goal_image = cursor.getBlob(5);
                strGoal = goalMoney + "/" + SavingMoney;
                progress = SavingMoney / goalMoney;


            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            tvGoalName.setText(goalName);
            tvGoalDescription.setText(goalDescription);
            tvMoneyGoal.setText(strGoal);
            ProgressSaving.setProgressText(String.valueOf(progress));
            ProgressSaving.setProgress((float) progress);

            if (progress <= 90) {
                ProgressSaving.setSecondaryProgress((float) progress + 10);
            }
        }
    }


    public void onModifyGoalClicked(View view) {
        Intent intent  = new Intent(this ,GoalActivity.class);
        startActivity(intent);
    }


}

