package com.example.myproject22;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.Currency;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

import me.abhinay.input.CurrencyEditText;

public class GoalActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 11;

    ImageView ivImage;
    ImageView chooseImage;
    Uri image_uri;

    CurrencyEditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        ivImage = findViewById(R.id.ivImage);

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


    public void onDoneClick(View view) {

        CurrencyEditText etGoalMoney = findViewById(R.id.etGoalMoney);
        TextInputLayout etGoalDesc = findViewById(R.id.etDesc);
        TextInputLayout etGoalName = findViewById(R.id.etGoalName);

        double goalMoney = 0;
        try {
            goalMoney = etGoalMoney.getCleanDoubleValue();
        } catch (Exception e) {
        }

        String name = etGoalName.getEditText().getText().toString();
        String description = etGoalDesc.getEditText().getText().toString();
        byte[] image = imageViewToByte(ivImage);

        saveAllIntoDatabase(goalMoney, name, description, image);

        this.finish();
    }


    // choose image
    public void onChooseImageClick(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    // convert Image to add to database
    public byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        byte[] compressed = compress(byteArray);

        Toast.makeText(GoalActivity.this, String.valueOf(byteArray.length), Toast.LENGTH_SHORT).show();
        return compressed;
    }


    // compress image so that it becomes lighter ?
    public static byte[] compress(byte[] data) {

        try {
            Deflater deflater = new Deflater();
            deflater.setInput(data);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

            deflater.finish();
            byte[] buffer = new byte[1024];
            while (!deflater.finished()) {
                int count = deflater.deflate(buffer); // returns the generated code... index
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
            byte[] output = outputStream.toByteArray();
            return output;

        } catch (Exception e) {

        }
        return null;
    }


    public void saveAllIntoDatabase(double goalMoney, String name, String description, byte[] image) {
        SavingDatabaseHelper savingDatabaseHelper = new SavingDatabaseHelper(this, null, null, 0);
        Cursor cursor = savingDatabaseHelper.getMucTieu();

        if (!cursor.moveToFirst()) {
            savingDatabaseHelper.insertMucTieu(name, description, goalMoney, 0, image);
            return;
        }


        int isFinished = cursor.getInt(6);
        if (isFinished == 1) {
            savingDatabaseHelper.insertMucTieu(name, description, goalMoney, 0, image);
            return;
        }


    }
}