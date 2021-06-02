package com.example.myproject22.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.myproject22.Model.SavingDatabaseHelper;
import com.example.myproject22.Presenter.ImageAdapter;
import com.example.myproject22.R;

public class ImageListActivity extends AppCompatActivity {

    GridView gvImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        gvImage = findViewById(R.id.gv_image);
        SavingDatabaseHelper dbHelper = new SavingDatabaseHelper(this);
        ImageAdapter imageAdapter = new ImageAdapter(dbHelper.getImageCategory(),this);
        gvImage.setAdapter(imageAdapter);
    }
}