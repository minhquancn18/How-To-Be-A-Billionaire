package com.example.myproject22.View.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myproject22.Model.ConnectionClass;
import com.example.myproject22.Presenter.Interface.NewGoalInterface;
import com.example.myproject22.Presenter.Presenter.NewGoalPresenter;
import com.example.myproject22.R;
import com.example.myproject22.Util.FormatImage;
import com.example.myproject22.View.Service.Network_receiver;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.abhinay.input.CurrencyEditText;

public class NewGoalActivity extends AppCompatActivity implements NewGoalInterface {

    //region UI COMPONENTS
    EditText etGoalDescription;
    EditText etGoalName;
    CurrencyEditText etGoalMoney;
    ImageView ivGoal;
    Uri image_uri;
    Button btnGoalDone;
    ImageView ivGifLoading;
    TextInputLayout til_money;
    TextInputLayout til_name;
    //endregion


    //region GLOBAL VARIABLES
    public static final String REQUEST_ADD_NEW = "add";
    private int id_user = 0;
    private static final int GALLERY_REQUEST = 11;
    private NewGoalPresenter mNewGoalPresenter;
    private Network_receiver network_receiver;
    //endregion


    //region HANDLE IMAGE
    private File photoFile = null;
    private String mCurrentPhotoPath;
    private Bitmap bmImage;

    //Const mặc định để xét permission
    private static final int PERMISSION_IMAGE = 1000;
    private static final int PERMISSION_EXTERNAL_STORAGE = 1001;
    //endregion



    //region INIT FUNCTION
    @Override
    public void SetUpBeforeInit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public void InitView() {
        etGoalDescription = findViewById(R.id.etGoalDesc);
        etGoalName = findViewById(R.id.etGoalName);
        etGoalMoney = findViewById(R.id.etGoalMoney);
        ivGoal = findViewById(R.id.ivGoal);
        btnGoalDone = findViewById(R.id.btnGoalDone);
        ivGifLoading = findViewById(R.id.ivGifLoading);
        til_money = findViewById(R.id.til_money);
        til_name = findViewById(R.id.til_name);
        etGoalMoney.setDecimals(false);

        Glide.with(this).load(R.drawable.audio_play_git).into(ivGifLoading);
    }

    @Override
    public void GetBundle() {
        Intent intent = getIntent();
        id_user = intent.getIntExtra("ID_USER", 0);
    }

    @Override
    public void getMessage() {
        Intent intent = getIntent();
        String msg = intent.getStringExtra(GoalActivity.MESSAGE);
        if (msg != null) {
            Snackbar snackbar = Snackbar.make(etGoalDescription, msg, BaseTransientBottomBar.LENGTH_LONG);
            snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            snackbar.show();
        }
    }

    //endregion

    //region Check Condition
    //Điều kiện tiền không được để trống
    public Boolean GetNoMoney(String money) {
        if (money.isEmpty() || money.equals("0.0")) {
            til_money.setError("Vui lòng nhập số tiền mục tiêu");
            return false;
        } else {
            til_money.setError(null);
            return true;
        }
    }

    public Boolean GetNoName(String name) {
        if (name.isEmpty()) {
            til_name.setError("Vui lòng nhập tên mục tiêu");
            return false;
        } else {
            til_name.setError(null);
            return true;
        }
    }
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new_goal);

        network_receiver = new Network_receiver();

        mNewGoalPresenter = new NewGoalPresenter(this);
        mNewGoalPresenter.SetUp();

        //region Xử lí textinputlayout
        etGoalName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    HideKeyboard(v);
                } else {
                    til_name.setError(null);
                }
            }
        });

        etGoalMoney.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    HideKeyboard(v);
                } else {
                    til_money.setError(null);
                }
            }
        });

        etGoalDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    HideKeyboard(v);
                }
            }
        });
        //endregion
    }

    //region DEFAULT FUNCTION


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PERMISSION_EXTERNAL_STORAGE: {
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                        bmImage = BitmapFactory.decodeStream(inputStream);
                        ivGoal.setImageBitmap(bmImage);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

            case PERMISSION_IMAGE: {
                if (resultCode == RESULT_OK) {
                    bmImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    ivGoal.setImageBitmap(bmImage);
                }
            }
        }
    }


    @Override
    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FormatImage.StopLoadImage(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(network_receiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(network_receiver);
    }
    //endregion


    //region IMAGE HANDLE
    @Override
    public void ChooseImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_picture, null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        ImageButton ivCamera = dialogView.findViewById(R.id.ivCamera);
        ImageButton ivGallery = dialogView.findViewById(R.id.ivGallery);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(NewGoalActivity.this).withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            TakeImageFromCamera();
                        } else {
                            Snackbar snackbar = Snackbar.make(etGoalDescription, "All permissions are not granted", Snackbar.LENGTH_SHORT);
                            snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                            snackbar.show();
                            /*Toast.makeText(AddingCategoryActivity.this, "All permissions are not granted", Toast.LENGTH_SHORT).show();*/
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
                dialog.dismiss();
            }
        });

        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(NewGoalActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        TakeImageFromGallery();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Snackbar snackbar = Snackbar.make(etGoalDescription, "Permission is not granted", Snackbar.LENGTH_SHORT);
                        snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                        snackbar.show();
                        /*Toast.makeText(AddingCategoryActivity.this, "Permission is not granted", Toast.LENGTH_SHORT).show();*/
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
                dialog.dismiss();
            }
        });
    }


    @Override
    public void TakeImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            Snackbar snackbar = Snackbar.make(etGoalDescription, "Lỗi truy cập hình ảnh", Snackbar.LENGTH_SHORT);
            snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            snackbar.show();
            /*Toast.makeText(AddingCategoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();*/
        }

        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.myproject22.provider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, PERMISSION_IMAGE);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
        }
    }


    @Override
    public void TakeImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PERMISSION_EXTERNAL_STORAGE);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }
    //endregion


    //region ANIMATIONS
    @Override
    public void DisableViews() {
        btnGoalDone.setEnabled(false);
        ivGifLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void EnableViews() {
        btnGoalDone.setEnabled(true);
        ivGifLoading.setVisibility(View.INVISIBLE);
    }
    //endregion


    //region BUTTON CLICK HANDLE
    public void onChooseImageClick(View view) {
        ChooseImage();
    }

    @Override
    public void onBackPressed() {
        PressBack();
    }


    @Override
    public void PressBack() {
        setResult(GoalActivity.RESULT_ADD_FAILED);
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }


    public void btnGoalDoneClicked(View view) {
        String money = String.valueOf(etGoalMoney.getCleanDoubleValue());
        Log.i("TESTGOAL", money);
        String name = etGoalName.getText().toString().trim();
        if (GetNoMoney(money) && GetNoName(name)) {
            mNewGoalPresenter.AddGoalToServer(this, view);
        }
    }
    //endregion


    //region DATABASE HANDLE
    @Override
    public void AddNewGoalToServer() {
        StringRequest request = new StringRequest(Request.Method.POST,
                ConnectionClass.urlString + "insertGoal22.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // if success -> back to last activity -> show snackbar
                if (response.equals("1")) {
                    Intent data = new Intent();
                    setResult(GoalActivity.RESULT_ADD_OK);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                } else {
                    // -> show error right here
                    Snackbar snackbar = Snackbar.make(ivGoal, response, Snackbar.LENGTH_SHORT);
                    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    snackbar.show();
                    EnableViews();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(ivGoal, "Lỗi kết nối internet ", Snackbar.LENGTH_SHORT);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                // get Image
                BitmapDrawable tem = ((BitmapDrawable) ivGoal.getDrawable());

                // format day for date_add
                SimpleDateFormat formatter = new SimpleDateFormat("h:mm a 'ngày' dd.MM.yyyy");
                String dayStr = formatter.format(new Date());

                // goalname and goalmoney are required
                if (!etGoalName.getText().toString().isEmpty())
                    map.put("name", String.valueOf(etGoalName.getText()));
                if (!etGoalMoney.getText().toString().isEmpty())
                    map.put("moneygoal", String.valueOf(etGoalMoney.getCleanDoubleValue()));


                map.put("id_user", String.valueOf(id_user));
                map.put("description", String.valueOf(etGoalDescription.getText()));
                map.put("imageGoal", String.valueOf(FormatImage.convertByteToString(
                        FormatImage.BitmapToByte(tem.getBitmap()))));
                map.put("date_start", dayStr);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    //endregion


    //region Hide KeyBoard
    public void HideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    //endregion
}
