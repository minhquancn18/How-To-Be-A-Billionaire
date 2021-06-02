package com.example.myproject22.View;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.myproject22.Model.MoneyCategoryClass;
import com.example.myproject22.Presenter.AddingCategoryPresenter;
import com.example.myproject22.Presenter.CategoryMoneyInterface;
import com.example.myproject22.R;
import com.google.android.material.button.MaterialButton;


//Thêm loại thu chi
//Chưa viết vì gặp trục trặc
public class AddingTypeCategoryActivity extends AppCompatActivity implements CategoryMoneyInterface {

    private AddingCategoryPresenter addingCategoryPresenter;
    private MaterialButton btnChoose;
    private RadioGroup rgType;
    private RadioButton rBtnAdding;
    private RadioButton rBtnSpending;
    private ImageButton iBtnCategory;

    private int isType = 1;
    private byte[] image;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_type);

        addingCategoryPresenter = new AddingCategoryPresenter(this,this);
        btnChoose = findViewById(R.id.btn_category_parent);
        rgType = findViewById(R.id.rg_category);
        rBtnAdding = findViewById(R.id.rBtn_add);
        rBtnSpending = findViewById(R.id.rBtn_spend);
        iBtnCategory = findViewById(R.id.iBtn_category);

        Intent old_itent = getIntent();
        Bundle old_bundle = old_itent.getExtras();
        MoneyCategoryClass moneyCategoryClass = addingCategoryPresenter.getMoneyCategoryClass(old_bundle);

        btnChoose.setText(moneyCategoryClass.getNameType());

        Bitmap bitmap = BitmapFactory.decodeByteArray(moneyCategoryClass.getImageResource(), 0, moneyCategoryClass.getImageResource().length);
        bitmap = Bitmap.createScaledBitmap(bitmap,96,96,true);
        Drawable icon = new BitmapDrawable(bitmap);
        btnChoose.setIcon(icon);

        if(moneyCategoryClass.isBoolType() >= 0){
            isType = 1;
            rBtnAdding.setChecked(true);
        }
        else{
            isType = -1;
            rBtnSpending.setChecked(true);
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
                Intent intent = new Intent(v.getContext(), CategoryParentActivity.class);
                intent.putExtra("Type", isType);
                intent.putExtra("ImageType",image);
                intent.putExtra("Name",name);
                v.getContext().startActivity(intent);
            }
        });

        iBtnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ImageListActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void GetDataFail() {

    }
}