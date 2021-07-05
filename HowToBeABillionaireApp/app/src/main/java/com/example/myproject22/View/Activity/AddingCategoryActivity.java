package com.example.myproject22.View.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.myproject22.Model.SharePreferenceClass;
import com.example.myproject22.Presenter.Interface.AddingCategoryInterface;
import com.example.myproject22.Presenter.Presenter.AddingCategoryPresenter;
import com.example.myproject22.R;
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

import static com.example.myproject22.Model.ConnectionClass.urlString;
import static com.example.myproject22.Presenter.Presenter.AddingCategoryPresenter.encodeTobase64;
import static com.example.myproject22.Presenter.Presenter.AddingMoneyPresentent.convertByteToString;

public class AddingCategoryActivity extends AppCompatActivity implements AddingCategoryInterface {

    //region Khởi tạo các component

    //region Component cho layout
    private EditText etCategory;
    private RadioGroup rgCategory;
    private RadioButton rbtnIncome;
    private RadioButton rbtnOutcome;
    private TextInputLayout til_category;
    private ImageButton btnImage;
    private TextView tv;
    private ImageButton btnSaving;
    private ProgressBar progressBar;
    private CoordinatorLayout mSnackbarLayout;
    //endregion

    //region Parameter xử lí hình ảnh
    private File photoFile = null;
    private String mCurrentPhotoPath;
    private Bitmap bmImage;
    private int id_user;
    private int isCategory = 1;
    //endregion

    //region Const mặc định để xét permission
    private static final int PERMISSION_IMAGE = 1000;
    private static final int PERMISSION_EXTERNAL_STORAGE = 1001;
    //endregion

    //Presenter
    AddingCategoryPresenter presentent;

    //SharePreference
    private SharePreferenceClass settings;

    //Broadcast
    private Network_receiver network_receiver;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_adding_category);

        //region SharePreference
        settings = new SharePreferenceClass(this);
        //endregion

        //region Broadcast
        network_receiver = new Network_receiver();
        //endregion

        //region Khởi tạo presenter và các giá trị ban đầu
        presentent = new AddingCategoryPresenter(this);
        presentent.setInit();
        presentent.getBundleData();
        //endregion

        //region Xử lí các edit text, button
        etCategory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presentent.hideKeyboard(v);
                } else {
                    til_category.setError(null);
                }
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentent.hideKeyboard(v);
                presentent.chooseImage();
            }
        });

        rgCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                presentent.checkRadioButtonCategory(group, checkedId);
            }
        });
        //endregion

        //region Xử lí button lưu
        btnSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentent.hideKeyboard(v);
                progressBar.setVisibility(View.VISIBLE);
                presentent.savingNewCategory(bmImage);
            }
        });
        //endregion
    }

    //region Xử lí override Activity
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

    //region Xử lí khởi tạo, lấy bundle và keyboard
    @Override
    public void SetInit() {
        etCategory = findViewById(R.id.etCategory);
        rbtnIncome = findViewById(R.id.rbtnIncome);
        rbtnOutcome = findViewById(R.id.rbtnOutcome);
        btnImage = findViewById(R.id.ibtnCategory);
        btnSaving = findViewById(R.id.ibtnSavingCategory);
        tv = findViewById(R.id.tvCategory);
        rgCategory = findViewById(R.id.rgCategory);
        progressBar = findViewById(R.id.pbCategory);
        mSnackbarLayout = findViewById(R.id.cl_snackbar);
        til_category = findViewById(R.id.til_category);

        YoYo.with(Techniques.Shake)
                .repeat(Animation.INFINITE)
                .delay(1000)
                .duration(3000)
                .playOn(btnSaving);
    }

    @Override
    public void GetBundleData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        id_user = bundle.getInt("ID_USER");
    }

    @Override
    public void HideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //endregion

    //region Xử lí image và tên image
    //region Xử lí image
    @Override
    public void ChooseImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddingCategoryActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_picture, null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        ImageButton ivCamera = dialogView.findViewById(R.id.ivCamera);
        ImageButton ivGallery = dialogView.findViewById(R.id.ivGallery);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(AddingCategoryActivity.this).withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            presentent.takeImageFromCamera();
                        } else {
                            Snackbar snackbar = Snackbar.make(mSnackbarLayout, "Bạn chưa cấp đủ quyền truy cập.", Snackbar.LENGTH_SHORT);
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
                Dexter.withContext(AddingCategoryActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        presentent.takeImageFromGallery();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Snackbar snackbar = Snackbar.make(mSnackbarLayout, "Bạn chưa cấp quyền truy cập", Snackbar.LENGTH_SHORT);
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
    public void TakeImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PERMISSION_EXTERNAL_STORAGE);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }

    //Take image from camera
    @Override
    public void TakeImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            Snackbar snackbar = Snackbar.make(mSnackbarLayout, "Lỗi chỉnh sửa hình ảnh", Snackbar.LENGTH_SHORT);
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

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PERMISSION_EXTERNAL_STORAGE: {
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    btnImage.setPadding(0, 0, 0, 0);
                    btnImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    btnImage.setImageTintList(ColorStateList.valueOf(android.R.color.transparent));
                    tv.setVisibility(View.GONE);
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                        bmImage = BitmapFactory.decodeStream(inputStream);


                        Glide.with(this)
                                .asBitmap()
                                .load(bmImage)
                                .transform(new CenterCrop(), new RoundedCorners(20))
                                .into(btnImage);
                        //btnImage.setImageBitmap(bmImage);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

            case PERMISSION_IMAGE: {
                if (resultCode == RESULT_OK) {
                    btnImage.setPadding(0, 0, 0, 0);
                    btnImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    btnImage.setImageTintList(ColorStateList.valueOf(android.R.color.transparent));
                    tv.setVisibility(View.GONE);
                    bmImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                    Glide.with(this)
                            .asBitmap()
                            .load(bmImage)
                            .transform(new CenterCrop(), new RoundedCorners(20))
                            .into(btnImage);

                    //btnImage.setImageBitmap(bmImage);

                }
            }
        }
    }
    //endregion

    //region Xử lí String image
    @Override
    public Boolean IsNullImage(Bitmap bitmap) {
        if (bitmap == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void GetNoImage() {
        Snackbar snackbar = Snackbar.make(mSnackbarLayout, "Vui lòng chọn hình ảnh cho danh mục.", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
        snackbar.show();
        /*Toast.makeText(AddingCategoryActivity.this, "Vui lòng chọn hình ảnh cho danh mục.", Toast.LENGTH_SHORT).show();*/
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public String GetStringImage() {
        String image = "";
        if (presentent.isNullImage(bmImage)) {
            image = "NULL";
        } else {
            byte[] bytes = encodeTobase64(bmImage);
            image = convertByteToString(bytes);
            Log.i("IMAGETEST", image);
        }
        return image;
    }

    //endregion

    //endregion

    //region Kiểm tra điều kiện để lưu danh mục
    @Override
    public void CheckRadioButtonCategory(RadioGroup radioGroup, int idChecked) {
        int checkRadio = radioGroup.getCheckedRadioButtonId();

        if (checkRadio == R.id.rbtnIncome) {
            isCategory = 1;
        } else {
            isCategory = -1;
        }
    }

    @Override
    public Boolean IsNullName() {
        String name = etCategory.getText().toString().trim();

        if (name.isEmpty()) {
            til_category.setError("Vui lòng nhập tên cho danh mục.");
            /*Toast.makeText(AddingCategoryActivity.this, "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();*/
            progressBar.setVisibility(View.GONE);
            return true;
        } else {
            til_category.setError(null);
            return false;
        }
    }
    //endregion

    //region Lưu danh mục (danh mục thu và danh mục chi)
    @Override
    public void SavingNewCategory() {
        String name = etCategory.getText().toString().trim();
        String image = GetStringImage();
        if (isCategory == 1) {
            UploadIncomeCategoryToServer(name, id_user, image);
        } else {
            UploadOutcomeCategoryToServer(name, id_user, image);
        }
    }

    public void UploadIncomeCategoryToServer(String name, int id_user, String image) {
        StringRequest request = new StringRequest(Request.Method.POST,
                urlString + "insertIncomeCategory.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response.equals("Add new income category success")) {
                    settings.setIsUpdateCategory(true);
                    DeleteImage();
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_in_left);
                } else {
                    Log.i("RESPONSECATEGORY", response);
                    til_category.setError("Tên danh mục đã tồn tại. Vui lòng chọn tên danh mục khác");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(mSnackbarLayout, "Lỗi kết nối internet", Snackbar.LENGTH_SHORT);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.show();
                /*Toast.makeText(AddingCategoryActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();*/
                progressBar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(id_user));
                params.put("name", name);
                params.put("image", image);
                return params;
            }
        };
        if (getApplicationContext() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(AddingCategoryActivity.this);
            requestQueue.add(request);
        }
    }

    public void UploadOutcomeCategoryToServer(String name, int id_user, String image) {
        StringRequest request = new StringRequest(Request.Method.POST,
                urlString + "insertOutcomeCategory.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response.equals("Add new outcome category success")) {
                    settings.setIsUpdateCategory(true);
                    DeleteImage();
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_in_left);
                } else {
                    Log.i("RESPONSECATEGORY", response);
                    Snackbar snackbar = Snackbar.make(mSnackbarLayout, "Tên danh mục đã tồn tại. Vui lòng chọn tên danh mục khác.", Snackbar.LENGTH_SHORT);
                    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    snackbar.show();
                    /*Toast.makeText(AddingCategoryActivity.this, "Tên danh mục đã tồn tại. Vui lòng chọn tên danh mục khác.", Toast.LENGTH_SHORT).show();*/
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(mSnackbarLayout, "Lỗi kết nối internet", Snackbar.LENGTH_SHORT);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.show();
                /*Toast.makeText(AddingCategoryActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();*/
                progressBar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(id_user));
                params.put("name", name);
                params.put("image", image);
                return params;
            }
        };
        if (getApplicationContext() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(AddingCategoryActivity.this);
            requestQueue.add(request);
        }
    }
    //endregion

    //region Xóa ảnh ki đã lưu hoặc quay lại
    @Override
    public void DeleteImage() {
        if (photoFile != null) {
            if (photoFile.exists()) {
                photoFile.delete();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DeleteImage();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }
    //endregion

}