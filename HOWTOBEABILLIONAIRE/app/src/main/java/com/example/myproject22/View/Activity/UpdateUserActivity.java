package com.example.myproject22.View.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myproject22.Model.SharePreferenceClass;
import com.example.myproject22.Model.UserClass;
import com.example.myproject22.Presenter.SavingPresenter;
import com.example.myproject22.Presenter.UpdateUserInterface;
import com.example.myproject22.Presenter.UpdateUserPresenter;
import com.example.myproject22.R;
import com.example.myproject22.View.Service.Network_receiver;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.myproject22.Model.ConnectionClass.urlString;
import static com.example.myproject22.Presenter.AddingMoneyPresentent.convertByteToString;
import static com.example.myproject22.Presenter.AddingMoneyPresentent.encodeTobase64;

public class UpdateUserActivity extends AppCompatActivity implements UpdateUserInterface {

    //region Khởi tạo các component

    //region Component
    private ConstraintLayout cl_total;
    private ProgressBar pb_user;
    private TextInputLayout til_fullname;
    private TextInputLayout til_email;
    private TextInputEditText et_fullname;
    private TextInputEditText et_email;
    private CircleImageView iv_profile;
    private MaterialButton btnSave;
    private MaterialButton btnCancel;
    //endregion

    //region presenter
    private UpdateUserPresenter presenter;
    private int id_user = 0;
    private UserClass userClass;
    //endregion

    //region Share Preference
    private SharePreferenceClass settings;
    //endregion

    //region Broadcast
    private Network_receiver network_receiver;
    //endregion

    //region Xử lí profile image
    private File photoFile = null;
    private String mCurrentPhotoPath;
    private Bitmap bmImage;

    //Const mặc định để xét permission
    private static final int PERMISSION_IMAGE = 1000;
    private static final int PERMISSION_EXTERNAL_STORAGE = 1001;
    //endregion

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update_user);

        //region SharePreference
        settings = new SharePreferenceClass(this);
        //endregion

        //region Broadcast
        network_receiver = new Network_receiver();
        //endregion

        //region Khởi tạo present và các giá trị ban đầu
        presenter = new UpdateUserPresenter(this);
        presenter.setInit();
        presenter.getBundleData();
        //endregion

        //region Xử lí button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.btnSaveClick();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.btnCancelClick();
            }
        });

        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.chooseImage();
            }
        });
        //endregion

        //region Xử lí các edittext
        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    presenter.hideKeyboard(v);
                }
                else{
                    til_email.setError(null);
                }
            }
        });

        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0)
                    til_email.setError("Vui lòng nhập email");
                else
                    til_email.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_fullname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    presenter.hideKeyboard(v);
                }
                else{
                    til_fullname.setError(null);
                }
            }
        });

        et_fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0)
                    til_fullname.setError("Vui lòng nhập họ và tên");
                else
                    til_fullname.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //endregion
    }

    //region Xử lí override Activity
    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadDataToLayout();
    }

    @Override
    public void onBackPressed() {
        setResult(UserAcitvity.RESULT_FAIL);
        super.onBackPressed();
        DeleteImage();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
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

    //region Set Init, get bundle, keyboard
    @Override
    public void SetInit() {
        cl_total = findViewById(R.id.cl_total);
        pb_user = findViewById(R.id.pb_user);
        til_email = findViewById(R.id.til_email);
        til_fullname = findViewById(R.id.til_fullname);
        et_email = findViewById(R.id.et_email);
        et_fullname = findViewById(R.id.et_fullname);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        iv_profile = findViewById(R.id.profile_image);

        pb_user.bringToFront();
        cl_total.setVisibility(View.INVISIBLE);
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

    //region Fetch User
    @Override
    public void FetchUserFromServer() {
        StringRequest request = new StringRequest(Request.Method.POST,
                urlString + "getUser.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("RESPONSEUPDATEUSER", response);
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String fullname = object.getString("FULLNAME");
                            String email = object.getString("EMAIL");
                            String date_string = object.getString("DATESTART");
                            String image_string = object.getString("USERIMAGE");


                            if(!image_string.equals("null")){
                                String url_image = urlString + "ImagesUser/" + image_string;
                                userClass = new UserClass(email, fullname, date_string, url_image);
                            }
                            else{
                                userClass = new UserClass(email, fullname, date_string, image_string);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                presenter.loadUser(userClass);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(btnCancel, "Lỗi kết nối internet", Snackbar.LENGTH_SHORT);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(id_user));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(UpdateUserActivity.this);
        requestQueue.add(request);
    }
    //endregion

    //region Load data từ server vào layout
    @Override
    public void LoadUser(UserClass userClass) {


        et_fullname.setText(userClass.getFULLNAME());
        et_email.setText(userClass.getEMAIL());

        if(!userClass.getIMAGE().equals("null")){
            Glide.with(UpdateUserActivity.this).load(userClass.getIMAGE()).into(iv_profile);
        }

        pb_user.setVisibility(View.GONE);
        cl_total.setVisibility(View.VISIBLE);
    }

    @Override
    public void LoadDataToLayout() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FetchUserFromServer();
            }
        },1000);
    }
    //endregion

    //region Xử lí các button click
    @Override
    public void BtnSaveClick() {
        String fullname = et_fullname.getText().toString().trim();
        if(!presenter.getNoFullName(fullname)){
            return;
        }

        String email = et_email.getText().toString().trim();
        if(!presenter.getNoEmail(email)){
            return;
        }

        String image_string = presenter.getStringImage();
        if(image_string.equals("NULL")){
            pb_user.bringToFront();
            pb_user.setVisibility(View.VISIBLE);
            presenter.uploadUserToServerNoImage(fullname,email);
        }
        else{
            pb_user.bringToFront();
            pb_user.setVisibility(View.VISIBLE);
            presenter.uploadUserToServer(fullname,email,image_string);
        }
    }

    @Override
    public void BtnCancelClick() {
        setResult(UserAcitvity.RESULT_FAIL);
        finish();
        DeleteImage();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_in_left);
    }


    //endregion

    //region Xử lý image

    @Override
    public void ChooseImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateUserActivity.this);
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
                Dexter.withContext(UpdateUserActivity.this).withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if(multiplePermissionsReport.areAllPermissionsGranted())
                        {
                            presenter.takeImageFromCamera();
                        }
                        else{
                            Snackbar snackbar = Snackbar.make(btnCancel,"All permissions are not granted",Snackbar.LENGTH_SHORT);
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
                Dexter.withContext(UpdateUserActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        presenter.takeImageFromGallery();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Snackbar snackbar = Snackbar.make(btnCancel,"Permission is not granted",Snackbar.LENGTH_SHORT);
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

    //Take image from gallery
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
            Snackbar snackbar = Snackbar.make(btnCancel,"Lỗi truy cập hình ảnh",Snackbar.LENGTH_SHORT);
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

    //Create file for image from camera
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

    //Get image through intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PERMISSION_EXTERNAL_STORAGE: {
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                        bmImage = BitmapFactory.decodeStream(inputStream);
                        iv_profile.setImageBitmap(bmImage);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

            case PERMISSION_IMAGE: {
                if (resultCode == RESULT_OK) {
                    bmImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    iv_profile.setImageBitmap(bmImage);
                }
            }
        }
    }

    //Delete image after upload or back previous activity
    @Override
    public void DeleteImage() {
        if (photoFile != null) {
            if (photoFile.exists()) {
                photoFile.delete();
            }
        }
    }

    //Check image null or not
    @Override
    public Boolean IsNullImage(Bitmap bitmap) {
        if (bitmap == null) {
            return true;
        } else {
            return false;
        }
    }

    //Convert image from bitmap to string
    @Override
    public String GetStringImage() {
        String image = "";
        if (presenter.isNullImage(bmImage)) {
            image = "NULL";
        } else {
            byte[] bytes = encodeTobase64(bmImage);
            image = convertByteToString(bytes);
            Log.i("IMAGETEST", image);
        }
        return image;
    }
    //endregion

    //region Kiểm tra điều kiện

    //Điều kiện email là nhập đúng định dạng email và không được để trống
    @Override
    public Boolean GetNoEmail(String email){
        if (email.isEmpty()) {
            til_email.setError("Vui lòng nhập email");
            /*et_email.setError("Vui lòng nhập email");*/
            pb_user.setVisibility(View.INVISIBLE);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            til_email.setError("Vui lòng nhập email chính xác");
            /*et_email.setError("Vui lòng nhập email chính xác");*/
            pb_user.setVisibility(View.INVISIBLE);
            return  false;
        } else {
            til_email.setError(null);
            /*et_email.setError(null);*/
            return true;
        }
    }

    //Điều kiện fullname là không được để trống
    @Override
    public Boolean GetNoFullName(String fullname){
        if (fullname.isEmpty()) {
            til_fullname.setError("Vui lòng nhập họ và tên");
            /*et_salary.setError("Vui lòng nhập họ và tên");*/
            pb_user.setVisibility(View.INVISIBLE);
            return false;
        } else {
            til_fullname.setError(null);
            /*et_username.setError(null);*/
            return true;
        }
    }
    //endregion

    //region Upload User to Server
    //Include Image
    @Override
    public void UploadUserToServer(String fullname, String email, String image) {
        StringRequest request = new StringRequest(Request.Method.POST,
                urlString + "updateUser.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pb_user.setVisibility(View.INVISIBLE);
                if(response.equals("Update user success")){

                    settings.setIsUpdateUser(true);
                    setResult(UserAcitvity.RESULT_SUCCESS);

                    finish();
                    DeleteImage();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_in_left);
                }
                else{
                    Log.i("RESPONSEUSER", response);
                    Snackbar snackbar = Snackbar.make(btnCancel, "Cập nhật thông tin thất bại", Snackbar.LENGTH_SHORT);
                    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    snackbar.show();
                    DeleteImage();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(btnCancel, "Lỗi kết nối internet", Snackbar.LENGTH_SHORT);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(id_user));
                params.put("fullname", fullname);
                params.put("email", email);
                params.put("userimage", image);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(UpdateUserActivity.this);
        requestQueue.add(request);
    }
    //Not Image
    @Override
    public void UploadUserToServerNoImage(String fullname, String email) {
        StringRequest request = new StringRequest(Request.Method.POST,
                urlString + "updateUserNoImage.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pb_user.setVisibility(View.INVISIBLE);
                if(response.equals("Update user success")){

                    settings.setIsUpdateUser(true);
                    setResult(UserAcitvity.RESULT_SUCCESS);

                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_in_left);
                }
                else{
                    Log.i("RESPONSEUSER", response);
                    Snackbar snackbar = Snackbar.make(btnCancel, "Cập nhật thông tin thất bại", Snackbar.LENGTH_SHORT);
                    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    snackbar.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(btnCancel, "Lỗi kết nối internet", Snackbar.LENGTH_SHORT);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(id_user));
                params.put("fullname", fullname);
                params.put("email", email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(UpdateUserActivity.this);
        requestQueue.add(request);
    }
    //endregion

}