package com.example.myproject22.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.myproject22.Model.SavingDatabaseHelper;
import com.example.myproject22.Presenter.ImageAdapter;
import com.example.myproject22.R;

import java.util.ArrayList;

public class ImageListActivity extends AppCompatActivity {

    GridView gvImage;
    ArrayList<byte[]> imagesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        gvImage = findViewById(R.id.gv_image);
        SavingDatabaseHelper dbHelper = new SavingDatabaseHelper(this);
        imagesList = dbHelper.getImageCategory();
        ImageAdapter imageAdapter = new ImageAdapter(imagesList,this);
        gvImage.setAdapter(imageAdapter);

        gvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), AddingTypeCategoryActivity.class);
                intent.putExtra("ImageChoose", imagesList.get(position));
                view.getContext().startActivity(intent);
                ((Activity)view.getContext()).finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, AddingTypeCategoryActivity.class);
        startActivity(intent);
        finish();
    }
}