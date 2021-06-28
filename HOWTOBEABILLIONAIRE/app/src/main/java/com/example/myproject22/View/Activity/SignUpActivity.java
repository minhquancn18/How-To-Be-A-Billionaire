package com.example.myproject22.View.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myproject22.Model.ConnectionClass;
import com.example.myproject22.Model.UserClass;
import com.example.myproject22.Presenter.SignUpInterface;
import com.example.myproject22.Presenter.SignUpPresenter;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.example.myproject22.Presenter.AddingCategoryPresenter.encodeTobase64;
import static com.example.myproject22.Presenter.AddingMoneyPresentent.convertByteToString;

public class SignUpActivity extends AppCompatActivity implements SignUpInterface {

    //region Khởi tạo component
    //Xử lí cài dặt password
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //tối thiểu 1 số
                    //"(?=.*[a-z])" +         //tối thiểu 1 ký tự viết thường
                    //"(?=.*[A-Z])" +         /tối thiểu 1 ký tự viết hoa
                    "(?=.*[a-zA-Z])" +      //bát kỳ ký tự nào
                    //"(?=.*[@#$%^&+=])" +    //tối thiểu 1 ký tự đặt biệt
                    "(?=\\S+$)" +           //không có khoảng trắng
                    ".{4,}" +               //tối thiểu 4 ký tự
                    "$");

    //region Component
    private TextInputLayout til_username;
    private TextInputLayout til_fullname;
    private TextInputLayout til_salary;
    private TextInputLayout til_email;
    private TextInputLayout til_password;
    private TextInputEditText et_username;
    private TextInputEditText et_fullname;
    private TextInputEditText et_salary;
    private TextInputEditText et_email;
    private TextInputEditText et_password;
    private MaterialButton btn_signup;
    private ImageButton ibtn_user;
    private TextView tv_login;
    private ProgressBar pb_signup;
    private CoordinatorLayout mSnackbarLayout;
    //endregion

    //region Image
    private File photoFile = null;
    String mCurrentPhotoPath;
    Bitmap bmImage;

    //Const mặc định để xét permission
    private static final int PERMISSION_IMAGE = 1000;
    private static final int PERMISSION_EXTERNAL_STORAGE = 1001;

    //endregion

    //Presenter
    private SignUpPresenter presenter;

    //Broadcast
    private Network_receiver network_receiver;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        //region Khởi tạo presenter
        presenter = new SignUpPresenter(this);
        presenter.setInit();
        //endregion

        //region Broadcast
        network_receiver = new Network_receiver();
        //endregion

        //region Xử lí các button và textview click
        ibtn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.chooseImage();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.btnSignUp();
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.textViewClick();
            }
        });

        //endregion

        //region Xử lí các edittext
        et_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.hideKeyboard(v);
                }else{
                    til_username.setError(null);
                }
            }
        });

        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 15){
                    til_username.setError("Username quá dài");
                }else{
                    til_username.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_fullname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.hideKeyboard(v);
                }else{
                    til_fullname.setError(null);
                }
            }
        });

        et_salary.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.hideKeyboard(v);
                }else {
                    til_salary.setError(null);
                }
            }
        });

        et_salary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 15){
                    til_salary.setError("Số tiền nhập quá lớn");
                }else{
                    til_salary.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.hideKeyboard(v);
                }else{
                    til_email.setError(null);
                }
            }
        });

        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.hideKeyboard(v);
                } else {
                    til_password.setError(null);
                }
            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() < 4){
                    til_password.setError("Mật khẩu tối thiểu 4 ký tự");
                }
                else{
                    til_password.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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

    //region Khởi tạo component và keyboard
    @Override
    public void SetInit(){
        til_email = findViewById(R.id.til_email);
        til_fullname = findViewById(R.id.til_fullname);
        til_salary = findViewById(R.id.til_salary);
        til_password = findViewById(R.id.til_password);
        til_username = findViewById(R.id.til_user);
        et_username = findViewById(R.id.et_user);
        et_fullname = findViewById(R.id.et_fullname);
        et_salary = findViewById(R.id.et_salary);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_signup = findViewById(R.id.btnSignup);
        ibtn_user = findViewById(R.id.ibtn_user);
        tv_login = findViewById(R.id.tvRegister);
        pb_signup = findViewById(R.id.pb_signup);
        mSnackbarLayout = findViewById(R.id.cl_snackbar);
    }

    @Override
    public void HideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    //endregion

    //region Kiểm tra điều kiện khi nhấn button
    //Điều kiện username là không được để trống, tối đa 15 ký tự, không khoảng trăng
    @Override
    public Boolean GetNoUserName(String username){
        if (username.isEmpty()) {
            til_username.setError("Username không được để trống");
            /*et_username.setError("Username không được để trống");*/
            pb_signup.setVisibility(View.INVISIBLE);
            return false;
        } else if (username.length() > 15) {
            til_username.setError("Username quá dài");
            /*et_username.setError("Username quá dài");*/
            pb_signup.setVisibility(View.INVISIBLE);
            return false;
        } else if(username.contains(" ")) {
            til_username.setError("Username không được nhập khoảng cách");
            pb_signup.setVisibility(View.INVISIBLE);
            return false;
        }else {
            til_username.setError(null);
            /*et_username.setError(null);*/
            return true;
        }
    }

    //Điều kiện password là không được để trống, tối thiểu 4 ký tự (1 từ)
    @Override
    public Boolean GetNoPassword(String password){
        if (password.isEmpty()) {
            til_password.setError("Mật khẩu không được để trống");
            /*et_password.setError("Mật khẩu không được để trống");*/
            pb_signup.setVisibility(View.INVISIBLE);
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            til_password.setError("Mật khẩu quá yếu");
            /*et_password.setError("Mật khẩu quá yếu");*/
            pb_signup.setVisibility(View.INVISIBLE);
            return false;
        } else {
            til_password.setError(null);
            /*et_password.setError(null);*/
            return true;
        }
    }

    //Điều kiện salary tối đa 15 ký tự, không được để trống
    @Override
    public Boolean GetNoSalary(String salary){
        if (salary.isEmpty()) {
            til_salary.setError("Vui lòng nhập thu nhập ban đầu");
            /*et_salary.setError("Vui lòng nhập thu nhập ban đầu");*/
            pb_signup.setVisibility(View.INVISIBLE);
            return false;
        } else if (salary.length() > 15) {
            til_salary.setError("Số tiền nhập quá lớn");
            /*et_salary.setError("Số tiền nhập quá lớn");*/
            pb_signup.setVisibility(View.INVISIBLE);
            return false;
        } else {
            til_salary.setError(null);
            /*et_username.setError(null);*/
            return true;
        }
    }

    //Điều kiện fullname không được để trống
    @Override
    public Boolean GetNoFullName(String fullname){
        if (fullname.isEmpty()) {
            til_fullname.setError("Vui lòng nhập họ và tên");
            /*et_salary.setError("Vui lòng nhập họ và tên");*/
            pb_signup.setVisibility(View.INVISIBLE);
            return false;
        } else {
            til_fullname.setError(null);
            /*et_username.setError(null);*/
            return true;
        }
    }

    //Điều kiện email là phải nhập đúng định dạng email và không được để trống
    @Override
    public Boolean GetNoEmail(String email){
        if (email.isEmpty()) {
            til_email.setError("Vui lòng nhập email");
            /*et_email.setError("Vui lòng nhập email");*/
            pb_signup.setVisibility(View.INVISIBLE);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            til_email.setError("Vui lòng nhập email chính xác");
            /*et_email.setError("Vui lòng nhập email chính xác");*/
            pb_signup.setVisibility(View.INVISIBLE);
            return  false;
        } else {
            til_email.setError(null);
            /*et_email.setError(null);*/
            return true;
        }
    }

    //endregion

    //region Xử lí hình ảnh

    //region Lấy ảnh
    @Override
    public void ChooseImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
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
                Dexter.withContext(SignUpActivity.this).withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if(multiplePermissionsReport.areAllPermissionsGranted())
                        {
                            presenter.takeImageFromCamera();
                        }
                        else{
                            presenter.denyPermission();
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
                Dexter.withContext(SignUpActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        presenter.takeImageFromGallery();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        presenter.denyPermission();
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
    }

    //Take image from camera
    @Override
    public void TakeImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            photoFile = presenter.createImageFile();
        } catch (IOException e) {
            Snackbar snackbar = Snackbar.make(mSnackbarLayout,e.getMessage(),Snackbar.LENGTH_SHORT);
            snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            snackbar.show();
            /*Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();*/
        }

        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.myproject22.provider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, PERMISSION_IMAGE);
        }
    }

    @Override
    public File CreateImageFile() throws IOException {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PERMISSION_EXTERNAL_STORAGE: {
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    ibtn_user.setScaleX(1.0f);
                    ibtn_user.setScaleY(1.0f);
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                        bmImage = BitmapFactory.decodeStream(inputStream);
                        ibtn_user.setImageBitmap(bmImage);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

            case PERMISSION_IMAGE: {
                if (resultCode == RESULT_OK) {
                    ibtn_user.setScaleX(1.0f);
                    ibtn_user.setScaleY(1.0f);
                    bmImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    ibtn_user.setImageBitmap(bmImage);
                }
            }
        }
    }

    //endregion

    //region Tìm chuỗi String của ảnh
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
        if (presenter.isNullImage(bmImage)) {
            image = "";
        } else {
            byte[] bytes = encodeTobase64(bmImage);
            image = convertByteToString(bytes);
        }
        return image;
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

    //endregion

    @Override
    public void DenyPermission() {
        Snackbar snackbar = Snackbar.make(mSnackbarLayout,"Bạn chưa cấp quyền sử dụng.",Snackbar.LENGTH_SHORT);
        snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
        snackbar.show();
        /*Toast.makeText(SignUpActivity.this, "Permission is not granted", Toast.LENGTH_SHORT).show();*/
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    //endregion

    //region Xử lí button và textview
    @Override
    public void BtnSignUp(){
        pb_signup.setVisibility(View.VISIBLE);

        String username = et_username.getText().toString().trim();
        if(presenter.getNoUserName(username) == false){
            return;
        }

        String fullname = et_fullname.getText().toString().trim();
        if(presenter.getNoFullName(fullname) == false){
            return;
        }

        String salary_string = et_salary.getText().toString().trim();
        if(presenter.getNoSalary(salary_string) == false){
            return;
        }

        String email = et_email.getText().toString().trim();
        if(presenter.getNoEmail(email) == false){
            return;
        }

        String password = et_password.getText().toString().trim();
        if(presenter.getNoPassword(password) == false){
            return;
        }

        String image = presenter.getStringImage();
        if(image.equals("")){
            presenter.uploadUserNoImageToServer(username,password,email,fullname,salary_string);
        }
        else{
            presenter.uploadUserToServer(username,password,email,fullname,salary_string,image);
        }
    }

    //region Upload User lên server
    @Override
    public void UploadUserToServer(String username, String password, String email,
                                   String fullname, String salary, String image){
        String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        StringRequest request = new StringRequest(Request.Method.POST,
                ConnectionClass.urlString + "signUp.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_SHORT).show();*/
                pb_signup.setVisibility(View.GONE);
                if (response.equals("Sign Up success")) {
                    setResult(LoginActivity.RESULT_SIGN_UP_SUCCESS);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_in_left);
                }
                else if(response.equals("This username or email has already exist.")){
                    til_username.setError("Tài khoản hoặc email đã tồn tại");
                    til_email.setError("Tài khoản hoặc email đã tồn tại");
                }
                else{
                    Log.i("RESPONSESIGNUP", response);
                    Snackbar snackbar = Snackbar.make(mSnackbarLayout,"Đăng ký không thành công",Snackbar.LENGTH_SHORT);
                    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    snackbar.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(mSnackbarLayout,"Lỗi kết nối internet",Snackbar.LENGTH_SHORT);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.show();
                /*Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();*/
                pb_signup.setVisibility(View.GONE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                params.put("email",email);
                params.put("fullname",fullname);
                params.put("datestart",currentDateandTime);
                params.put("salary",salary);
                params.put("userimage",image);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
        requestQueue.add(request);
    }

    @Override
    public void UploadUserNoImageToServer(String username, String password, String email,
                                   String fullname, String salary){
        String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        StringRequest request = new StringRequest(Request.Method.POST,
                ConnectionClass.urlString + "signUpNoImage.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pb_signup.setVisibility(View.GONE);
                if (response.equals("Sign Up success")) {
                    Toast.makeText(SignUpActivity.this, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_in_left);
                }
                else if(response.equals("This username or email has already exist.")){
                    til_username.setError("Tài khoản hoặc email đã tồn tại");
                    til_email.setError("Tài khoản hoặc email đã tồn tại");
                }
                else{
                    Log.i("RESPONSESIGNUP", response);
                    Snackbar snackbar = Snackbar.make(mSnackbarLayout,"Đăng ký không thành công",Snackbar.LENGTH_SHORT);
                    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    snackbar.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(mSnackbarLayout,"Lỗi kết nối internet",Snackbar.LENGTH_SHORT);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.show();
                /*Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();*/
                pb_signup.setVisibility(View.GONE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                params.put("email",email);
                params.put("fullname",fullname);
                params.put("datestart",currentDateandTime);
                params.put("salary",salary);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
        requestQueue.add(request);
    }

    //endregion

    @Override
    public void TextViewClick(){
        finish();
        DeleteImage();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_in_left);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DeleteImage();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }

    //endregion



}