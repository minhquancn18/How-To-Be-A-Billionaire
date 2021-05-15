package com.hfad.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.hfad.project_1.Model.SavingDatabaseHelper;
import com.hfad.project_1.Model.SpendingCategoryClass;
import com.hfad.project_1.View.AddingActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button btn_Adding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Adding = (Button) findViewById(R.id.btn_adding);
        btn_Adding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddingActivity.class);
                v.getContext().startActivity(intent);
                finish();
            }
        });
    }
}