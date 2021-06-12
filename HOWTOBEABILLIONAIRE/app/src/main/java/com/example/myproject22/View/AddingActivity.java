package com.example.myproject22.View;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject22.Model.CategoryClass;
import com.example.myproject22.Presenter.AddingMoneyInterface;
import com.example.myproject22.Presenter.AddingMoneyPresentent;
import com.example.myproject22.R;
import com.example.myproject22.Util.CategoryItemAdapter;
import com.example.myproject22.Util.FormatImage;
import com.gauravk.audiovisualizer.visualizer.WaveVisualizer;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.alterac.blurkit.BlurLayout;

import static com.example.myproject22.Model.ConnectionClass.urlString;
import static com.example.myproject22.Presenter.AddingMoneyPresentent.convertByteToString;
import static com.example.myproject22.Presenter.AddingMoneyPresentent.isNumeric;

public class AddingActivity extends AppCompatActivity implements AddingMoneyInterface {
    //Khởi tạo các component để thực hiện event

    //Component về camera
    private ImageButton btnImage;
    private TextView tvImage;
    private Bitmap bmImage;
    private Uri image_uri;
    String encodedImage;

    //Component về record và audio
    private ImageButton btnPlay;
    private ImageButton btnRecord;
    WaveVisualizer mVisualizer;
    BlurLayout blurLayout;
    MediaPlayer mediaPlayer;
    Boolean isRecord = false;
    Boolean isPlaying = false;
    MediaRecorder mediaRecorder;
    String recordFile = "NO";

    //Component về list category
    private RecyclerView categoryRecycler;
    private RecyclerView categoryRecycler1;
    private BottomSheetBehavior bottomSheetBehavior;
    private ConstraintLayout playerSheet;
    private ProgressBar progressBar1;
    private ProgressBar progressBar2;
    TextView tvChooseImage;

    //Component về tiền
    private EditText etMoney;
    private SeekBar seekBar;
    private EditText etDescription;

    private ImageButton btnSaving;
    private ProgressBar progressBar3;

    //Các array list để lấy danh sách
    private ArrayList<CategoryClass> arrayList = new ArrayList<>();
    private ArrayList<CategoryClass> arrayList1 = new ArrayList<>();
    private ArrayList<byte[]> images = new ArrayList<>();
    private ArrayList<String> categoryNames = new ArrayList<>();
    private ArrayList<byte[]> images1 = new ArrayList<>();
    private ArrayList<String> categoryNames1 = new ArrayList<>();

    //Const mặc định để xét permission
    private static final int PERMISSION_IMAGE = 1000;
    private static final int PERMISSION_EXTERNAL_STORAGE = 1001;
    private static final int PERMISSION_AUDIO = 1002;

    //Presenter
    AddingMoneyPresentent addingMoneyPresentent;

    //Parameter for saving
    int isCategory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_adding);

        addingMoneyPresentent = new AddingMoneyPresentent(this);

        addingMoneyPresentent.setInit();
        addingMoneyPresentent.loadingIncomeCategory();

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingMoneyPresentent.chooseImage();
            }
        });

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(AddingActivity.this,"Start", Toast.LENGTH_SHORT).show();*/
                addingMoneyPresentent.CaptureRecord();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingMoneyPresentent.CaptureAudio();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = progress;
                String svalue = String.valueOf(value);
                etMoney.setText(svalue);
                etMoney.setSelection(svalue.length());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addingMoneyPresentent.SetOnTextChange(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etMoney.isEnabled() == false) {
                    etMoney.setEnabled(true);
                }
            }
        });

        btnSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar3.setVisibility(View.VISIBLE);
                String money = etMoney.getText().toString().trim();
                String description = etDescription.getText().toString().trim();

                String category = tvChooseImage.getText().toString().trim();
                int category_id = addingMoneyPresentent.findIdByName(category);

                String image = addingMoneyPresentent.getStringImage();
                String audio = addingMoneyPresentent.getStringAudio();

                addingMoneyPresentent.savingMoneyData(money, description, category_id, image, audio);
            }
        });
    }

    @Override
    public void LoadCategory() {
        progressBar1.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.VISIBLE);
        new Handler().postDelayed(category, 3000);
    }

    Runnable category = new Runnable() {
        @Override
        public void run() {
            FetchData fetchData = new FetchData(urlString + "getIncomeCategory.php");
            if (fetchData.startFetch()) {
                if (fetchData.onComplete()) {
                    String result = fetchData.getResult();
                    Gson gson = new Gson();
                    CategoryClass[] IncomeCategory = gson.fromJson(result, (Type) CategoryClass[].class);

                    for (int i = 0; i < IncomeCategory.length; i++) {
                        arrayList.add(IncomeCategory[i]);
                    }

                    for (int i = 0; i < arrayList.size(); i++) {
                        String sname = arrayList.get(i).Get_NAME();
                        String simage = arrayList.get(i).Get_IMAGE();
                        if (simage != null) {
                            byte[] image = Base64.decode(simage, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                            bitmap = Bitmap.createScaledBitmap(bitmap, 96, 96, true);
                            images.add(FormatImage.BitmapToByte(bitmap));
                        } else {
                            Bitmap bitmap = ((BitmapDrawable) btnPlay.getDrawable()).getBitmap();
                            images.add(FormatImage.BitmapToByte(bitmap));
                        }
                        categoryNames.add(sname);
                    }

                    CategoryItemAdapter adapter = new CategoryItemAdapter(images, categoryNames, bottomSheetBehavior, tvChooseImage);
                    categoryRecycler.setAdapter(adapter);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(AddingActivity.this);
                    layoutManager.setOrientation(RecyclerView.HORIZONTAL);
                    categoryRecycler.setLayoutManager(layoutManager);

                    progressBar1.setVisibility(View.GONE);
                    //End ProgressBar (Set visibility to GONE)
                    Log.i("FetchData", result);
                }
            }


            FetchData fetchData1 = new FetchData(urlString + "getSpendingCategory.php");
            if (fetchData1.startFetch()) {
                if (fetchData1.onComplete()) {
                    String result = fetchData1.getResult();
                    Gson gson = new Gson();
                    CategoryClass[] SpendingCategory = gson.fromJson(result, (Type) CategoryClass[].class);

                    for (int i = 0; i < SpendingCategory.length; i++) {
                        arrayList1.add(SpendingCategory[i]);
                    }

                    for (int i = 0; i < arrayList1.size(); i++) {
                        String sname = arrayList1.get(i).Get_NAME();
                        String simage = arrayList1.get(i).Get_IMAGE();
                        if (simage != null) {
                            byte[] image = Base64.decode(simage, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                            bitmap = Bitmap.createScaledBitmap(bitmap, 96, 96, true);
                            images1.add(FormatImage.BitmapToByte(bitmap));
                        } else {
                            Bitmap bitmap = ((BitmapDrawable) btnPlay.getDrawable()).getBitmap();
                            images1.add(FormatImage.BitmapToByte(bitmap));
                        }
                        categoryNames1.add(sname);
                    }

                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(AddingActivity.this);
                    layoutManager1.setOrientation(RecyclerView.HORIZONTAL);

                    CategoryItemAdapter adapter1 = new CategoryItemAdapter(images1, categoryNames1, bottomSheetBehavior, tvChooseImage);
                    categoryRecycler1.setAdapter(adapter1);
                    categoryRecycler1.setLayoutManager(layoutManager1);

                    progressBar2.setVisibility(View.GONE);
                    //End ProgressBar (Set visibility to GONE)
                    Log.i("FetchData", result);
                }
            }
        }
    };

    @Override
    public Boolean CheckPermissionRecord() {
        if (ContextCompat.checkSelfPermission(AddingActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(AddingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(AddingActivity.this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_AUDIO);
            return false;
        }
    }

    @Override
    public Boolean CheckPermissionImage() {
        if (ContextCompat.checkSelfPermission(AddingActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(AddingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(AddingActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_IMAGE);
            return false;
        }
    }

    @Override
    public Boolean CheckPermissionRead() {
        if (ContextCompat.checkSelfPermission(AddingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(AddingActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_EXTERNAL_STORAGE);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_IMAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TakeImageFromCamera();
                } else {
                    Toast.makeText(AddingActivity.this, "Camera permission not Granted", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case PERMISSION_EXTERNAL_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TakeImageFromGallery();
                } else {
                    Toast.makeText(AddingActivity.this, "Read external permission not Granted", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        blurLayout.startBlur();
    }

    @Override
    protected void onStop() {
        blurLayout.pauseBlur();
        super.onStop();
    }

    public void PauseClicked(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mVisualizer.setVisibility(View.INVISIBLE);
            btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.icon_play, null));


        } else {
            mediaPlayer.start();
            mVisualizer.setVisibility(View.VISIBLE);
            btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.icon_pause, null));

        }
    }

    public void AddImageClicked(View view) {
        ((ImageButton) view).setImageDrawable(getDrawable(R.drawable.avatar));
        ((ImageButton) view).setScaleType(ImageView.ScaleType.CENTER_CROP);
        ((ImageButton) view).setScaleX(1);
        ((ImageButton) view).setScaleY(1);

    }

    @Override
    public void AddingCategoryFail() {

    }

    @Override
    public void GetBuddleSuccessful() {

    }

    @Override
    public void GetNoMoneyData() {
        Toast.makeText(getApplicationContext(), "Nhập thông tin về tiền!", Toast.LENGTH_SHORT).show();
        progressBar3.setVisibility(View.GONE);
    }

    @Override
    public void GetNoCategoryData() {
        Toast.makeText(getApplicationContext(), "Chọn loại thu chi.", Toast.LENGTH_SHORT).show();
        progressBar3.setVisibility(View.GONE);
    }

    @Override
    public void GetAddSuccessful() {

    }

    @Override
    public void GetSpendSuccessful() {

    }

    @Override
    public void GetDataFail() {

    }

    @Override
    public void SetInit() {
        btnSaving = findViewById(R.id.imageButton2);
        btnImage = findViewById(R.id.imageButton);
        tvImage = findViewById(R.id.textView11);
        btnPlay = findViewById(R.id.btnPlay);
        btnRecord = findViewById(R.id.imageView2);
        blurLayout = findViewById(R.id.blurLayout);
        mVisualizer = findViewById(R.id.blast);
        playerSheet = findViewById(R.id.player_sheet);
        tvChooseImage = findViewById(R.id.tvChooseCategory);
        categoryRecycler = findViewById(R.id.category_recycler);
        categoryRecycler1 = findViewById(R.id.category_recycler2);
        progressBar1 = findViewById(R.id.progress1);
        progressBar2 = findViewById(R.id.progress2);
        etMoney = findViewById(R.id.editTextNumber2);
        etDescription = findViewById(R.id.editTextTextMultiLine);
        progressBar3 = findViewById(R.id.progress3);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(5000000);

        bottomSheetBehavior = BottomSheetBehavior.from(playerSheet);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            ImageButton btnList = findViewById(R.id.btnList);

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    btnList.setImageDrawable(getDrawable(R.drawable.icon_arrow_up));

                }

                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    btnList.setImageDrawable(getDrawable(R.drawable.icon_arrow_down));
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //We cant do anything here for this app
            }
        });
    }

    @Override
    public void ChooseImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddingActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_picture, null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        ImageButton ivCamera = dialogView.findViewById(R.id.ivCamera);
        ImageButton ivGallery = dialogView.findViewById(R.id.ivGallery);

        AlertDialog dialog = builder.create();
        dialog.show();

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckPermissionImage()) {
                    TakeImageFromCamera();
                    dialog.cancel();
                }
            }
        });

        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckPermissionRead()) {
                    TakeImageFromGallery();
                    dialog.cancel();
                }
            }
        });
    }

    @Override
    public void CaptureRecord() {
        if (isRecord) {
            //Stop recording
            StopRecord();
            mVisualizer.setVisibility(View.INVISIBLE);
            btnRecord.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_surround_sound_24, null));
            Toast.makeText(this, "Stop " + recordFile, Toast.LENGTH_SHORT).show();
            isRecord = false;
        } else {
            if (CheckPermissionRecord()) {
                StartRecord();
                mVisualizer.setVisibility(View.VISIBLE);
                btnRecord.setImageDrawable(getResources().getDrawable(R.drawable.icon_pause, null));
                Toast.makeText(this, "Start record", Toast.LENGTH_SHORT).show();

                isRecord = true;
            } else {
                Toast.makeText(this, "Not permission granted", Toast.LENGTH_SHORT).show();
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
            }
        } else {
            Toast.makeText(this, "No Audio is saving now", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void IsValidNumber(CharSequence s) {
        if (s.length() > 9) {
            etMoney.setEnabled(false);
            etMoney.setText("");
        } else if (s.length() > 0) {
            etMoney.setSelection(s.length());
            if (isNumeric(s.toString())) {
                int progress = Integer.parseInt(s.toString());
                if (progress > 5000000) {
                    seekBar.setProgress(5000000);
                } else {
                    int value = progress;
                    seekBar.setProgress(value);
                }
            } else {
                etMoney.setEnabled(false);
                etMoney.setText("");
            }
        }
    }

    @Override
    public int FindIdByName(String name) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (name.equals(arrayList.get(i).Get_NAME())) {
                isCategory = 1;
                return arrayList.get(i).Get_ID();
            }
        }
        for (int i = 0; i < arrayList1.size(); i++) {
            if (name.equals(arrayList1.get(i).Get_NAME())) {
                isCategory = -1;
                return arrayList1.get(i).Get_ID();
            }
        }
        return 0;
    }

    @Override
    public Boolean IsNullImage(Bitmap bitmap) {
        if (bitmap == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String GetStringImage() {
        String image = "";
        if (addingMoneyPresentent.isNullImage(bmImage)) {
            image = "NULL";
        } else {
            byte[] image_byte = FormatImage.BitmapToByte(bmImage);
            image = convertByteToString(image_byte);
            Log.i("IMAGETEST", image);
        }

        return image;
    }

    @Override
    public Boolean IsNullAudio() {
        if (recordFile.equals("NO")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String GetStringAudio() {
        String audio = "";
        if (addingMoneyPresentent.isNullAudio()) {
            audio = "NULL";
        } else {
            byte[] audio_byte = addingMoneyPresentent.convert3gbToByte();
            audio = convertByteToString(audio_byte);
            Log.i("AUDIOTEST", audio);
        }

        return audio;
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

    //Start audio
    private void StartAudio() {
        mediaPlayer = new MediaPlayer();
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (isSDPresent) {
            String outputFile = AddingActivity.this.getExternalFilesDir("/").getAbsolutePath() + "/" + recordFile;

            if (IsValidFile(outputFile) == false) {
                recordFile = "NO";
                return;
            } else {
                Toast.makeText(this, "Start " + recordFile, Toast.LENGTH_SHORT).show();
                try {
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int audioSessionId = mediaPlayer.getAudioSessionId();
                mVisualizer.setAudioSessionId(audioSessionId);

                mediaPlayer.start();

                mVisualizer.setVisibility(View.VISIBLE);
                btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.icon_pause, null));

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        StopAudio();
                    }
                });
                isPlaying = true;
            }
        }
    }

    private Boolean IsValidFile(String filename) {
        File file = new File(filename);

        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;

        if (fileSizeInKB > 30) {
            Toast.makeText(AddingActivity.this, "Size of file too much", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    //Stop audio
    private void StopAudio() {

        mediaPlayer.release();
        mediaPlayer = null;
        isPlaying = false;
        mVisualizer.setVisibility(View.INVISIBLE);
        btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.icon_play, null));
        Toast.makeText(this, "Stop audio", Toast.LENGTH_SHORT).show();
    }

    private void TakeImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PERMISSION_EXTERNAL_STORAGE);
    }

    private void TakeImageFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PERMISSION_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PERMISSION_EXTERNAL_STORAGE: {
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    btnImage.setScaleX(1.0f);
                    btnImage.setScaleY(1.0f);
                    tvImage.setVisibility(View.GONE);
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                        bmImage = BitmapFactory.decodeStream(inputStream);
                        btnImage.setImageBitmap(bmImage);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

            case PERMISSION_IMAGE: {
                if (resultCode == RESULT_OK) {
                    btnImage.setScaleX(1.0f);
                    btnImage.setScaleY(1.0f);
                    tvImage.setVisibility(View.GONE);
                    bmImage = (Bitmap) data.getExtras().get("data");
                    btnImage.setImageBitmap(bmImage);
                }
            }
        }
    }

    //Convert 3gp to byte[] (file name from recordFile)
    @Override
    public byte[] Convert3gbToByte() {
        byte[] audio = null;
        String outputFile = AddingActivity.this.getExternalFilesDir("/").getAbsolutePath() + "/" + recordFile;
        if (IsValidFile(outputFile)) {
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
        return audio;
    }

    @Override
    public void SavingMoneyData(String money, String description, int category_id, String image, String audio) {
        String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        //insert data to php database
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                if (audio.equals("NULL")) {
                    String[] field = new String[6];
                    field[0] = "idmoney";
                    field[1] = "money";
                    field[2] = "idcategory";
                    field[3] = "description";
                    field[4] = "date";
                    field[5] = "image";
                    //Creating array for data
                    String[] data = new String[6];
                    data[0] = "1";
                    data[1] = money;
                    data[2] = String.valueOf(category_id);
                    data[3] = description;
                    data[4] = currentDateandTime;
                    data[5] = image;
                    if (isCategory == 1) {
                        PutData putData = new PutData(urlString + "insertIncomeDetailNoAudio.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar3.setVisibility(View.GONE);
                                String result = putData.getResult();
                                Log.i("PutData", result);
                                Toast.makeText(AddingActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        PutData putData = new PutData(urlString + "insertSpendingDetailNoAudio.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar3.setVisibility(View.GONE);
                                String result = putData.getResult();
                                Log.i("PutData", result);
                                Toast.makeText(AddingActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    //End Write and Read data with URL
                } else {
                    String[] field = new String[7];
                    field[0] = "idmoney";
                    field[1] = "money";
                    field[2] = "idcategory";
                    field[3] = "description";
                    field[4] = "date";
                    field[5] = "image";
                    field[6] = "audio";
                    //Creating array for data
                    String[] data = new String[7];
                    data[0] = "1";
                    data[1] = money;
                    data[2] = String.valueOf(category_id);
                    data[3] = description;
                    data[4] = currentDateandTime;
                    data[5] = image;
                    data[6] = audio;
                    if (isCategory == 1) {
                        PutData putData = new PutData(urlString + "insertIncomeDetail.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar3.setVisibility(View.GONE);
                                String result = putData.getResult();
                                Log.i("PutData", result);
                                Toast.makeText(AddingActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        PutData putData = new PutData(urlString + "insertSpendingDetail.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar3.setVisibility(View.GONE);
                                String result = putData.getResult();
                                Log.i("PutData", result);
                                Toast.makeText(AddingActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    //End Write and Read data with URL
                }
            }
        });
    }
}

