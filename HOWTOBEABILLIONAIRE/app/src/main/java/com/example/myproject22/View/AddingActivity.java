package com.example.myproject22.View;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myproject22.MainActivity;
import com.example.myproject22.Model.MoneyCategoryClass;
import com.example.myproject22.Model.MoneyInformationClass;
import com.example.myproject22.Model.SavingDatabaseHelper;
import com.example.myproject22.Presenter.AddingMoneyInterface;
import com.example.myproject22.Presenter.AddingMoneyPresentent;
import com.example.myproject22.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddingActivity extends AppCompatActivity implements AddingMoneyInterface {

    //Const
    private static final int PERMISSION_IMAGE = 1000;
    private static final int IMAGECAPTURE_CODE = 1001;
    private static final int PERMISSION_AUDIO = 1002;
    //Tạo SQLiteHelper để kết nối tới cơ sở dữ liệu
    private SavingDatabaseHelper db = new SavingDatabaseHelper(this);

    //Khởi tạo các component để thực hiện event
    private TextInputLayout moneyTextField;
    private TextInputLayout descriptionTextField;
    private Button btnSaving;
    private MaterialButton btnChooseType;
    private AddingMoneyPresentent addingMoneyPresentent;
    private ImageButton iBtnCamera;
    private FloatingActionButton fBtnMic;
    private FloatingActionButton fBtnRecord;

    //Uri image
    Uri image_uri;
    Bitmap bmCamera;
    Boolean isRecord = false;
    Boolean isPlaying = false;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    String recordFile = "NO";
    File fileToPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);

        //Liên kết tới các component bên layout
        moneyTextField = findViewById(R.id.moneyTextField);
        descriptionTextField = findViewById(R.id.descriptiontextField);
        btnChooseType = findViewById(R.id.btn_ChossingType);
        btnSaving = findViewById(R.id.btn_saving);
        addingMoneyPresentent = new AddingMoneyPresentent(this, this);
        fBtnMic = findViewById(R.id.fBtn_mic);
        fBtnRecord = findViewById(R.id.fBtn_record);
        iBtnCamera = findViewById(R.id.iBtn_camera);

        if (savedInstanceState != null) {
            moneyTextField.getEditText().setText(savedInstanceState.getString("textKey"));
        }

        //Kết nối từ Adding event tới AddingTypeActivity để chọn loại thu hoặc chi
        btnChooseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sMoney = moneyTextField.getEditText().getText().toString(); //Lấy dữ liệu từ money text, khác null
                String sDescription = descriptionTextField.getEditText().getText().toString().trim(); //lấy dữ liệu từ description text, null cũng ko sao
                addingMoneyPresentent.onButtonCategoryClicked(sMoney, sDescription);
            }
        });

        //Bắt Intent nhận được thông qua AddingTypeActivity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //Kiểm tra dữ liệu có bắt được hay chưa
        MoneyInformationClass moneyInformationClass = addingMoneyPresentent.GetIntentData(bundle);

        DecimalFormat precious = new DecimalFormat("0");
        moneyTextField.getEditText().setText(precious.format(moneyInformationClass.getMoney()));

        descriptionTextField.getEditText().setText(moneyInformationClass.getDescription());

        MoneyCategoryClass moneyCategoryClass = moneyInformationClass.getMoneyCategory();
        btnChooseType.setText(moneyCategoryClass.getNameType());

        Bitmap bitmap = BitmapFactory.decodeByteArray(moneyCategoryClass.getImageResource(), 0, moneyCategoryClass.getImageResource().length);
        bitmap = Bitmap.createScaledBitmap(bitmap, 96, 96, true);
        Drawable icon = new BitmapDrawable(bitmap);
        btnChooseType.setIcon(icon);

        //Thực hiện event save chi tiền tiền thu-chi vào database và trở vể giao diện chính nếu lưu thành công
        btnSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] audio = null;
                if (!recordFile.equals("NO")) {
                    String outputFile = AddingActivity.this.getExternalFilesDir("/").getAbsolutePath() + "/" + recordFile;

                    try {
                        FileInputStream inputStream = new FileInputStream(outputFile);
                        BufferedInputStream bif = new BufferedInputStream(inputStream);
                        audio = new byte[bif.available()];
                        bif.read(audio);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String sMoney = moneyTextField.getEditText().getText().toString(); //Lấy dữ liệu từ money text, khác null
                String sDescription = descriptionTextField.getEditText().getText().toString().trim(); //lấy dữ liệu từ description text, null cũng ko sao
                //Kiểm tra xem dữ liệu đưa vào có bị lỗi gì hay ko
                Boolean bool = addingMoneyPresentent.AddMoneyIntoDB(sMoney, sDescription, moneyCategoryClass, bmCamera,audio, db);
                //Nếu lưu thành công thì thoát ra ngoài
                if (bool == true) {
                    finish();
                }
            }
        });

        //Change text to null when click in edit text
        moneyTextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String sMoney = moneyTextField.getEditText().getText().toString();

                if (sMoney.equals("0")) {
                    moneyTextField.getEditText().setText("");
                }
            }
        });

        //Change number text to default when click description if number text null
        descriptionTextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String sMoney = moneyTextField.getEditText().getText().toString();

                if (sMoney.equals("")) {
                    moneyTextField.getEditText().setText(R.string.money);
                }
            }
        });

        iBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingMoneyPresentent.CaptureImage();
            }
        });

        fBtnMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingMoneyPresentent.CaptureRecord();
            }
        });

        fBtnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingMoneyPresentent.CaptureAudio();
            }
        });
    }

    @Override
    public void AddingCategoryFail() {
        Toast.makeText(getApplicationContext(), "Lỗi rất nhiều lỗi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetBuddleSuccessful() {
        Toast.makeText(getApplicationContext(), "Thêm thông tin loại thu chi thành công.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetNoMoneyData() {
        Toast.makeText(getApplicationContext(), "Nhập thông tin về tiền!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetNoCategoryData() {
        Toast.makeText(getApplicationContext(), "Chọn loại thu chi.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetAddSuccessful() {
        Toast.makeText(getApplicationContext(), "Thêm tiền thu thành công!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetSpendSuccessful() {
        Toast.makeText(getApplicationContext(), "Thêm tiền chi thành công!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetDataFail() {
        Toast.makeText(getApplicationContext(), "Thêm dữ liệu không thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ButtonCategoryClickWithBoth(Context context, String sMoney, String sDescription) {
        Intent intent = new Intent(context, CategoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("MoneyText", sMoney);
        bundle.putString("DescriptionText", sDescription);

        intent.putExtras(bundle);
        context.startActivity(intent);
        //Xóa activity này khi chuyển qua AddingTypeActivity
        AddingActivity.this.finish();
    }

    @Override
    public void CapturePicture() {
        if (checkPermissionCamera()) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
            image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            //Camera intent
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
            startActivityForResult(pictureIntent, IMAGECAPTURE_CODE);
        }
    }

    @Override
    public void OpenPicture(View view) {

    }

    @Override
    public void CaptureRecord() {
        if (isRecord) {
            //Stop recording
            StopRecord();
            Toast.makeText(this, "Stop " + recordFile, Toast.LENGTH_SHORT).show();
            fBtnMic.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(AddingActivity.this, R.color.black)));
            isRecord = false;
        } else {
            if (checkPermissionRecord()) {
                StartRecord();
                Toast.makeText(this, "Start record", Toast.LENGTH_SHORT).show();
                fBtnMic.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(AddingActivity.this, R.color.blue)));
                isRecord = true;
            }
        }
    }

    @Override
    public void CaptureAudio() {
        if (!recordFile.equals("NO")) {
            if (isPlaying) {
                StopAudio();

            } else {
                StartAudio();
                Toast.makeText(this, "Start " + recordFile, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No Audio is saving now", Toast.LENGTH_SHORT).show();
        }
    }

    //Stop record
    private void StopRecord() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    //Start record
    private void StartRecord() {
        String recordPath = AddingActivity.this.getExternalFilesDir("/").getAbsolutePath();
        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss", Locale.forLanguageTag("vi_VN"));
        Date now = new Date();
        recordFile = "Record_" + format.format(now) + ".3gp";

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(recordPath + "/" + recordFile);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaRecorder.start();
    }

    private void StartAudio() {
        mediaPlayer = new MediaPlayer();
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (isSDPresent) {
            String outputFile = AddingActivity.this.getExternalFilesDir("/").getAbsolutePath() + "/" + recordFile;
            try {
                mediaPlayer.setDataSource(outputFile);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.start();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    StopAudio();
                }
            });
            isPlaying = true;
        }
    }

    private void StopAudio() {

        mediaPlayer.release();
        mediaPlayer = null;
        isPlaying = false;
        Toast.makeText(this, "Stop audio", Toast.LENGTH_SHORT).show();
    }

    //Check permission Record
    public Boolean checkPermissionRecord() {
        if (ContextCompat.checkSelfPermission(AddingActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(AddingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(AddingActivity.this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_AUDIO);
            return false;
        }
    }

    //Check permission Camera
    public Boolean checkPermissionCamera() {
        //Request for camera
        if (ContextCompat.checkSelfPermission(AddingActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(AddingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(AddingActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_IMAGE);
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGECAPTURE_CODE) {
            //Set capture to imageView
            iBtnCamera.setImageURI(image_uri);
            try {
                bmCamera = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("textKey", moneyTextField.getEditText().getText().toString());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}

