package com.example.myproject22.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.myproject22.R;
import com.example.myproject22.Util.RecordItemAdapter;

public class ReportCategoryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_category_detail);

        RecyclerView recyclerView = findViewById(R.id.record_recycler);

        RecordItemAdapter recordItemAdapter = new RecordItemAdapter();
        recyclerView.setAdapter(recordItemAdapter);

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("  Tiền thu 1.1.2020");

    }
}