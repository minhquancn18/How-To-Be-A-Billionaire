package com.example.myproject22.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myproject22.Model.AddingCategoryClass;
import com.example.myproject22.Model.SavingDatabaseHelper;
import com.example.myproject22.Model.SpendingCategoryClass;
import com.example.myproject22.Presenter.ListAddingCategoryAdapter;
import com.example.myproject22.Presenter.ListSpendingCategoryAdapter;
import com.example.myproject22.R;

import java.util.ArrayList;

public class CategoryParentActivity extends AppCompatActivity {

    private ListView lvCategoryAdding;
    private ArrayList<AddingCategoryClass> addType;
    private ArrayList<SpendingCategoryClass> spendType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_parent);

        lvCategoryAdding = findViewById(R.id.lv_category_adding);

        //Connect to database to get addingList
        SavingDatabaseHelper savingDatabaseHelper = new SavingDatabaseHelper(this);

        int type = 1;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            type = bundle.getInt("Type");
        }

        if(type == 1){
            addType = savingDatabaseHelper.getAddingCategoryList();
            ListAddingCategoryAdapter listCategoryAdapter = new ListAddingCategoryAdapter(addType,this);
            lvCategoryAdding.setAdapter(listCategoryAdapter);
        }
        else if(type == -1){
            spendType = savingDatabaseHelper.getSpendingCategoryList();
            ListSpendingCategoryAdapter listCategoryAdapter = new ListSpendingCategoryAdapter(spendType,this);
            lvCategoryAdding.setAdapter(listCategoryAdapter);
        }

        int finalType = type;
        lvCategoryAdding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent new_intent = new Intent(view.getContext(), AddingTypeCategoryActivity.class);
                new_intent.putExtra("Type", finalType);
                new_intent.putExtra("IDType", position);
                view.getContext().startActivity(new_intent);
                ((Activity)view.getContext()).finish();
            }
        });
    }
}