package com.example.myproject22.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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



        blurLayout = findViewById(R.id.blurLayout);
        blurLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }



    public void AddOnClicked(View view) {
        Intent intent = new Intent(this, AddingActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        blurLayout.startBlur();
        super.onStart();
    }

    @Override
    protected void onStop() {
        blurLayout.pauseBlur();
        super.onStop();
    }
}