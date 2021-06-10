package com.example.myproject22.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myproject22.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

import io.alterac.blurkit.BlurKit;
import io.alterac.blurkit.BlurLayout;

public class DashboardActivity extends AppCompatActivity {

    BlurLayout blurLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);


        BlurKit.init(this);
        ImageButton btnAddRecord = findViewById(R.id.btnNewRecord);
        btnAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AddingActivity.class);
                startActivity(intent);
            }
        });


        MotionLayout a= findViewById(R.id.motion1);
        a.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                if(i == R.id.end1){
                   Intent intent = new Intent(DashboardActivity.this, AddingActivity.class);
                    startActivity(intent);
                }
                if(i == R.id.end){
                    Intent intent = new Intent(DashboardActivity.this, SavingActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });
    }





    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}