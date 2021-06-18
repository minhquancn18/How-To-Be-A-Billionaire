package com.example.myproject22.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.myproject22.Model.SavingDatabaseHelper;
import com.example.myproject22.R;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import me.abhinay.input.CurrencyEditText;

import static com.example.myproject22.Util.FormatImage.BitmapToByte;

public class GoalActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 11;

    ImageView ivImage;
    ImageView chooseImage;
    Uri image_uri;

    CurrencyEditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_goal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1111);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        SpannableString s = new SpannableString("My Title");
        s.setSpan(new TypefaceSpan("monospace"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case GALLERY_REQUEST:
                        Uri selectedImage = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                            ivImage.setImageBitmap(bitmap);

                        } catch (IOException e) {
                            Log.i("TAG", "Some exception " + e);
                        }
                        break;
                }
            }

            ivImage.setImageURI(image_uri);
        }
    }

    // choose image
    public void onChooseImageClick(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
            return true;
        }
        return false;
    }

    public void saveAllIntoDatabase(double goalMoney, String name, String description, byte[] image) {
        SavingDatabaseHelper savingDatabaseHelper = new SavingDatabaseHelper(this, null, null, 0);
        savingDatabaseHelper.insertMucTieu(name, description, goalMoney, 0, image);
    }
}