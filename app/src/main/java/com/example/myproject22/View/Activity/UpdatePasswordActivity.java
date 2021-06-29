package com.example.myproject22.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.myproject22.Model.ConnectionClass;
import com.example.myproject22.Presenter.Interface.UpdatePasswordInterface;
import com.example.myproject22.Presenter.Presenter.UpdatePasswordPresenter;
import com.example.myproject22.R;
import com.example.myproject22.Util.FormatImage;
import com.example.myproject22.View.Service.Network_receiver;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class UpdatePasswordActivity extends AppCompatActivity implements UpdatePasswordInterface {

    //region Khởi tạo component và các giá trị ban đầu

    //region Password
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
    //endregion

    //region Component
    private TextInputLayout til_oldpassword;
    private TextInputLayout til_newpassword;
    private TextInputLayout til_newconfirm;
    private TextInputEditText et_oldpassword;
    private TextInputEditText et_newpassword;
    private TextInputEditText et_newconfirm;
    private MaterialButton btnSave;
    private MaterialButton btnCancel;

    //animations
     private ConstraintLayout cl_total;
     private ImageView ivBackground;
    //endregion

    //region Presenter
    private UpdatePasswordPresenter presenter;
    private int id_user;
    //endregion

    //region Broadcast
    private Network_receiver network_receiver;
    //endregion

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_update_password);

        //region Khởi tạo presenter và gán các giá trị ban đầu
        presenter = new UpdatePasswordPresenter(this);
        presenter.setInit();
        presenter.getBundleData();
        //endregion

        //region Broadcast
        network_receiver = new Network_receiver();
        //endregion

        //region Xử lí các button click
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
        //endregion

        //region Xử lí các edittext
        et_oldpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() < 4)
                    til_oldpassword.setError("Mật khẩu tối thiểu 4 ký tự");
                else
                    til_oldpassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_oldpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    presenter.hideKeyboard(v);
                }
                else{
                    til_oldpassword.setError(null);
                }
            }
        });

        et_newpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() < 4)
                    til_newpassword.setError("Mật khẩu tối thiểu 4 ký tự");
                else
                    til_newpassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_newpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    presenter.hideKeyboard(v);
                }
                else{
                    til_newpassword.setError(null);
                }
            }
        });

        et_newconfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() < 4)
                    til_newconfirm.setError("Mật khẩu tối thiểu 4 ký tự");
                else
                    til_newconfirm.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_newconfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    presenter.hideKeyboard(v);
                }
                else{
                    til_newconfirm.setError(null);
                }
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

    //region Set Init, get bundle, keyboard
    @Override
    public void SetInit() {
        til_newconfirm = findViewById(R.id.til_password_confirm);
        til_newpassword = findViewById(R.id.til_new_password);
        til_oldpassword = findViewById(R.id.til_password);
        et_newconfirm = findViewById(R.id.et_password_confirm);
        et_newpassword = findViewById(R.id.et_new_pasword);
        et_oldpassword = findViewById(R.id.et_password);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);



        // animations
        ivBackground  = findViewById(R.id.ivBackground);
        cl_total = findViewById(R.id.cl_total);

        Glide.with(this).load(R.drawable.background_gif).into(ivBackground);


    }

    @Override
    public void GetBundleData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id_user =bundle.getInt("ID_USER");
    }

    @Override
    public void HideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //endregion

    //region Xử lí các button click
    @Override
    public void BtnSaveClick() {

        String oldpassword = et_oldpassword.getText().toString().trim();
        if(!presenter.getNoOldPassword(oldpassword)){
            return;
        }

        String newpassword = et_newpassword.getText().toString().trim();
        if(!presenter.getNoPassword(newpassword, oldpassword)){
            return;
        }

        String confirmpassword = et_newconfirm.getText().toString().trim();
        if(!presenter.getNoConfirmPassword(newpassword, confirmpassword)){
            return;
        }

        presenter.uploadPasswordToServer(oldpassword,newpassword);
    }

    @Override
    public void BtnCancelClick() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }
    //endregion

    //region Kiểm tra điều kiện

    //Điều kiện password mới là phải có tối thiểu 4 ký tự (khác password cũ, có ít nhất 1 chữ)
    @Override
    public Boolean GetNoPassword(String password,String oldpassword) {
        if (password.isEmpty()) {
            til_newpassword.setError("Mật khẩu không được để trống");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            til_newpassword.setError("Mật khẩu quá yếu");
            return false;
        } else if(password.equals(oldpassword)){
            til_newpassword.setError("Mật khẩu mới không được giống mật khẩu cũ");
            return false;
        } else {
            til_newpassword.setError(null);
            return  true;
        }
    }

    //Điều kiện password cữ là phải tối thiểu 4 ký tự
    @Override
    public Boolean GetNoOldPassword(String password) {
        if (password.isEmpty()) {
            til_oldpassword.setError("Mật khẩu không được để trống");
            return false;
        }
        else {
            til_oldpassword.setError(null);
            return  true;
        }
    }

    //Điều kiện password confirm là phải giống password mới
    @Override
    public Boolean GetNoConfirmPassword(String password, String password_confirm) {
        if (password_confirm.isEmpty()) {
            til_newconfirm.setError("Mật khẩu không được để trống");
            return false;
        } else if (!password_confirm.equals(password)) {
            til_newconfirm.setError("Mật khẩu xác nhận không trùng khớp");
            return false;
        } else {
            til_newpassword.setError(null);
            return true;
        }
    }
    //endregion

    //region Upload password lên server
    @Override
    public void UploadPasswordToServer(String oldpassword, String newpassword) {
        StringRequest request = new StringRequest(Request.Method.POST,
                ConnectionClass.urlString + "updatePasswordUser.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Update password success")) {
                    setResult(UserAcitvity.RESULT_PASSWORD_SUCCESS);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                } else if(response.equals("Password wrong")){
                    til_oldpassword.setError("Mật khẩu cũ không đúng");
                }
                else{
                    Log.i("RESPONSEUPDATEPASSWORD", response);
                    Snackbar snackbar = Snackbar.make(btnCancel,"Có lỗi trong lúc đổi mật khẩu. Vui lòng thử lại sau.",Snackbar.LENGTH_SHORT);
                    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    snackbar.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(btnCancel,"Lỗi kết nối internet",Snackbar.LENGTH_SHORT);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.show();
                /*Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();*/
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(id_user));
                params.put("password", oldpassword);
                params.put("new_password", newpassword);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(UpdatePasswordActivity.this);
        requestQueue.add(request);
    }
    //endregion

    //region Xử lí override Activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }
    //endregion

}