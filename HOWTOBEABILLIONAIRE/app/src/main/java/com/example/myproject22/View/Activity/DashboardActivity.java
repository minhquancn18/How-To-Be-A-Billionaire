package com.example.myproject22.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.myproject22.R;

import io.alterac.blurkit.BlurKit;
import io.alterac.blurkit.BlurLayout;

public class DashboardActivity extends AppCompatActivity {

    private int id_user = 1;
    private int id_income = 1;
    private int id_outcome = 1;
    private int id_saving = 2;
    BlurLayout blurLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        GetBundleData();

        BlurKit.init(this);
        ImageButton btnAddRecord = findViewById(R.id.btnNewRecord);
        btnAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddingClick();
            }
        });

        ImageButton btnGraph = findViewById(R.id.btnGraph);
        btnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnReportCategoryClick();
            }
        });

        ImageButton btnSaving = findViewById(R.id.btnMaybe);
        btnSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSavingClick();
            }
        });

        ImageButton btnGraphTest = findViewById(R.id.btnGraphTest);
        btnGraphTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGraphTestClick();
            }
        });


        MotionLayout a = findViewById(R.id.motion1);
        a.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                if (i == R.id.end) {
                    btnAddingClick();
                }
                if (i == R.id.end1) {
                    btnReportCategoryClick();
                }
                if (i == R.id.end2) {
                    btnSavingClick();
                }
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    public void GetBundleData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id_user = bundle.getInt("ID_USER");
        id_income = bundle.getInt("ID_INCOME");
        id_outcome = bundle.getInt("ID_OUTCOME");
        id_saving = bundle.getInt("ID_SAVING");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void btnAddingClick() {
        Intent intent = new Intent(DashboardActivity.this, AddingActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("ID_USER", id_user);
        bundle.putInt("ID_INCOME", id_income);
        bundle.putInt("ID_OUTCOME", id_outcome);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void btnReportCategoryClick() {
        Intent intent = new Intent(DashboardActivity.this, ReportCategoryActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("ID_INCOME", id_income);
        bundle.putInt("ID_OUTCOME", id_outcome);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void btnSavingClick(){
        Intent intent = new Intent(DashboardActivity.this, SavingActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("ID_USER", id_user);
        bundle.putInt("ID_SAVING", id_saving);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void btnGraphTestClick(){
        Intent intent = new Intent(DashboardActivity.this, ReportCategoryGraphActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("ID_USER", id_user);
        bundle.putInt("ID_INCOME", id_income);
        bundle.putInt("ID_OUTCOME", id_outcome);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}