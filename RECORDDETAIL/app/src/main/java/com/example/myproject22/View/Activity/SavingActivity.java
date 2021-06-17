package com.example.myproject22.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
import com.example.myproject22.Model.SavingDatabaseHelper;
import com.example.myproject22.R;
import com.example.myproject22.Presenter.SavingInterface;
import com.example.myproject22.Presenter.SavingPresenter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.myproject22.Util.FormatImage.ByteToBitmap;

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




/*      View overlay = findViewById(R.id.mylayout);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        CircleImageView profile_image = findViewById(R.id.profile_image);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ASavingDatabaseHelper.closeAll();
    }

    @Override
    public void LoadChiTietTietKiem() {
    }

    @Override
    public void LoadTietKiem() {

    }

    @Override
    public void LoadMucTieu() {

    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void AddRecords() {
/*

        ASavingDatabaseHelper.insertChitietTienChi(20, "me cho", 1, null, "2020-06-03");
        ASavingDatabaseHelper.insertChitietTienChi(20, "me cho", 1, null, "2020-06-04");
        ASavingDatabaseHelper.insertChitietTienChi(20, "me cho", 1, null, "2020-06-05");
        ASavingDatabaseHelper.insertChitietTienChi(20, "me cho", 1, null, "2020-06-06");

        ASavingDatabaseHelper.insertChiTietTienThu(10, "ssa", 1, "2020-06-07");
        ASavingDatabaseHelper.insertChitietTienChi(10, "ssa", 1, null,"2020-06-07");


        ASavingDatabaseHelper.insertChitietTienChi(10, "ssa", 1, null,"2020-06-08");
        ASavingDatabaseHelper.insertChiTietTienThu(10, "ssa", 1, "2020-06-09");
*/


    }

    // async stask
    public void onModifyGoalClicked(View view) {
        Intent intent = new Intent(this, GoalActivity.class);
        startActivity(intent);
    }
}

