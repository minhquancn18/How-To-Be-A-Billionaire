package com.example.myproject22.View;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myproject22.Model.ConnectionClass;
import com.example.myproject22.Model.MoneyCategoryClass;
import com.example.myproject22.Model.SavingDatabaseHelper;
import com.example.myproject22.Presenter.AddingCategoryPresenter;
import com.example.myproject22.Presenter.CategoryMoneyInterface;
import com.example.myproject22.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;


//Thêm loại thu chi
//Chưa viết vì gặp trục trặc
public class AddingTypeCategoryActivity extends AppCompatActivity implements CategoryMoneyInterface {

    private AddingCategoryPresenter addingCategoryPresenter;
    private MaterialButton btnChoose;
    private MaterialButton btnSave;
    private RadioGroup rgType;
    private RadioButton rBtnAdding;
    private RadioButton rBtnSpending;
    private ImageButton iBtnCategory;
    private TextInputLayout tilCategory;

    private int isType = 1;
    private byte[] image;
    private String name;
    private  String parentName = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_category);

        addingCategoryPresenter = new AddingCategoryPresenter(this,this);
        btnChoose = findViewById(R.id.btn_category_parent);
        rgType = findViewById(R.id.rg_category);
        rBtnAdding = findViewById(R.id.rBtn_add);
        rBtnSpending = findViewById(R.id.rBtn_spend);
        iBtnCategory = findViewById(R.id.iBtn_category);
        tilCategory = findViewById(R.id.tf_category);
        btnSave = findViewById(R.id.btn_saving_category);

        Intent old_intent = getIntent();
        Bundle old_bundle = old_intent.getExtras();
        MoneyCategoryClass moneyCategoryClass = addingCategoryPresenter.getMoneyCategoryClass(old_bundle);

        if(moneyCategoryClass.getID() > 0) {

            MoneyCategoryClass parentCategory = moneyCategoryClass.getListChild().get(0);
            parentName = parentCategory.getNameType();
            btnChoose.setText(parentName);

            Bitmap bitmap = BitmapFactory.decodeByteArray(parentCategory.getImageResource(), 0, parentCategory.getImageResource().length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 96, 96, true);
            Drawable icon = new BitmapDrawable(bitmap);
            btnChoose.setIcon(icon);


            if (parentCategory.isBoolType() >= 0) {
                isType = 1;
                rBtnAdding.setChecked(true);
            } else {
                isType = -1;
                rBtnSpending.setChecked(true);
            }

            tilCategory.getEditText().setText(moneyCategoryClass.getNameType());

            image = moneyCategoryClass.getImageResource();
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(image,0,image.length);
            bitmapImage = Bitmap.createScaledBitmap(bitmapImage, 96, 96, true);
            iBtnCategory.setImageBitmap(bitmapImage);

        }
        else if (moneyCategoryClass.getID() == 0){
            image = moneyCategoryClass.getImageResource();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 96, 96, true);
            iBtnCategory.setImageBitmap(bitmap);

            tilCategory.getEditText().setText(moneyCategoryClass.getNameType());

            rBtnAdding.setChecked(true);
        }
        else{
            btnChoose.setText(moneyCategoryClass.getNameType());
            image = moneyCategoryClass.getImageResource();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 96, 96, true);
            Drawable icon = new BitmapDrawable(bitmap);
            btnChoose.setIcon(icon);


            if (moneyCategoryClass.isBoolType() >= 0) {
                isType = 1;
                rBtnAdding.setChecked(true);
            } else {
                isType = -1;
                rBtnSpending.setChecked(true);
            }
        }

        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int iChecked = rgType.getCheckedRadioButtonId();
                if(iChecked == R.id.rBtn_add){
                    isType = 1;
                }
                else if(iChecked == R.id.rBtn_spend){
                    isType = -1;
                }
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = tilCategory.getEditText().getText().toString();
                Intent intent = new Intent(v.getContext(), CategoryParentActivity.class);
                intent.putExtra("Type", isType);
                intent.putExtra("ImageType",image);
                intent.putExtra("Name",name);
                v.getContext().startActivity(intent);
                ((Activity)v.getContext()).finish();
            }
        });

        iBtnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ImageListActivity.class);
                v.getContext().startActivity(intent);
                ((Activity)v.getContext()).finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = tilCategory.getEditText().getText().toString();
                SavingDatabaseHelper dbHelper = new SavingDatabaseHelper(v.getContext());
                addingCategoryPresenter.SaveNewCategory(name,image,isType,parentName,dbHelper);
                Intent intent = new Intent(v.getContext(), AddingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void GetDataFail() {
        Toast.makeText(getApplicationContext(), "Thêm dữ liệu không thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void NullNameData() {
        Toast.makeText(getApplicationContext(), "Nhập thông tin về tên!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetAddSuccessful() {
        Toast.makeText(getApplicationContext(), "Thêm danh mục thu thành công!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetSpendSuccessful() {
        Toast.makeText(getApplicationContext(), "Thêm danh mục chi thành công!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
        finish();
    }
}