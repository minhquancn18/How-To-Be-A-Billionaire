package com.example.myproject22.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myproject22.Model.DayItem;
import com.example.myproject22.R;
import com.example.myproject22.Util.DayItemAdapter;

import java.util.ArrayList;
import java.util.Date;

public class ReportCategoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);



        ArrayList<DayItem> days = new ArrayList<>();
        days.add(new DayItem(new Date(121,6,12 ), 5));
        days.add(new DayItem(new Date(), 2));
        days.add(new DayItem(new Date(), 3));
        days.add(new DayItem(new Date(), 4124));
        days.add(new DayItem(new Date(), 5));
        days.add(new DayItem(new Date(), 5));

        days.add(new DayItem(new Date(121,6,12 ), 5));

        days.add(new DayItem(new Date(), 5));
        days.add(new DayItem(new Date(), 5));
        days.add(new DayItem(new Date(), 5));
        days.add(new DayItem(new Date(), 5));
        days.add(new DayItem(new Date(), 5));
        days.add(new DayItem(new Date(), 5));
        days.add(new DayItem(new Date(121,6,12 ), 5));


        DayItemAdapter dayItemAdapter = new DayItemAdapter(days, this, true);
        RecyclerView recyclerView = findViewById(R.id.day_recycler);
        recyclerView.setAdapter(dayItemAdapter);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);


        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("     Nhật ký thu chi");
        getSupportActionBar().setIcon(R.drawable.yoyoyo);

    }
}