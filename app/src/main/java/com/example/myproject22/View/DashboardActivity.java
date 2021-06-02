package com.example.myproject22.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.myproject22.Model.SavingDatabaseHelper;
import com.example.myproject22.R;
import com.google.android.material.card.MaterialCardView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        SQLiteOpenHelper dpHelper = new SavingDatabaseHelper(this);

        MaterialCardView savingCardView =  findViewById(R.id.CardViewSaving);
        savingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SavingActivity.class);
                startActivity(intent);
            }
        });
    }



    public void AddOnClicked(View view) {
        Intent intent = new Intent(this, AddingActivity.class);
        startActivity(intent);
    }
}