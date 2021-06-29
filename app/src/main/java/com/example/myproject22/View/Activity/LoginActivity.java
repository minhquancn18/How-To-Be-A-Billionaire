package com.example.myproject22.View.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
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
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.myproject22.Model.ConnectionClass;
import com.example.myproject22.Model.SharePreferenceClass;
import com.example.myproject22.Presenter.Interface.LoginInterface;
import com.example.myproject22.Presenter.Presenter.LoginPresenter;
import com.example.myproject22.R;
import com.example.myproject22.View.Service.Network_receiver;
import com.example.myproject22.View.Service.Notification_recevier;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements LoginInterface {

    //region Khởi tạo Component

    //region Component
    private TextInputLayout til_username;
    private TextInputLayout tif_password;
    private TextInputEditText et_username;
    private TextInputEditText et_password;
    private MaterialButton btnLogin;
    private TextView tvSignUp;
    private TextView tvForget;
    private CoordinatorLayout mSnackbarLayout;
    private CheckBox cbRemember;

    // animations
    private MaterialCardView cardLogin;

    //endregion

    //region Presenter
    private LoginPresenter presenter;
    //endregion

    //region Share Preference
    private SharePreferenceClass settings;
    private String username = "";
    private String password = "";
    private Boolean isRememeber = false;
    //endregion

    //region Const
    private static final int REQUEST_SIGN_UP = 1001;
    private static final int REQUEST_FORGOT = 1101;
    public static final int RESULT_SIGN_UP_SUCCESS = 1002;
    public static final int RESULT_FORGOT_SUCCESS = 1102;
    //endregion

    //Broadcast reciever
    private Network_receiver network_receiver;
    //

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_login);

        //region Khởi tạo presenter và kiểm tra kết nối internet
        presenter = new LoginPresenter(this);
        presenter.setInit();
        //endregion

        //region Broadcast
        network_receiver = new Network_receiver();
        //endregion

        //region Share Preference

        settings = new SharePreferenceClass(this);
        settings.setIsUpdateUser(false);
        settings.setIsUpdateCategory(false);

        //Kiểm tra lần đầu đăng nhập
        if (settings.isFirstTime()) {
            settings.setFirstTime(false);

            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
        }
        //Nếu đã sử dụng rồi thì kiểm tra đã đăng xuất chưa
        else {
            //Nếu đăng xuất thì kiểm tra có ghi nhớ mật khẩu không
            if (settings.isLogOut()) {
                if (settings.isRemember()) {
                    isRememeber = true;
                    username = settings.getUsername();
                    password = settings.getPassword();
                    Log.i("TEST1", username + " " + password);

                }
            }
            //Nếu chưa đăng xuất thì tự động đăng nhập
            else {
                int id_user = settings.getIdUser();
                int id_income = settings.getIdIncome();
                int id_outcome = settings.getIdOutcome();
                int id_saving = settings.getIdSaving();

                Intent intent = new Intent(LoginActivity.this, SavingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID_USER", id_user);
                bundle.putInt("ID_INCOME", id_income);
                bundle.putInt("ID_OUTCOME", id_outcome);
                bundle.putInt("ID_SAVING", id_saving);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
            }
        }

        //endregion

        //region Xử lí notification
        SetNotification();
        //endregion

        //region Xử lí các textview sự kiện và button đăng nhập
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.hideKeyboard(v);
                presenter.textViewClick();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.hideKeyboard(v);
                presenter.btnSignIn();
            }
        });

        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.hideKeyboard(v);
                presenter.textViewForgetClick();
            }
        });
        //endregion

        //region Xử lí các edittext

        et_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.hideKeyboard(v);
                } else {
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
                if (s.length() > 0) {
                    til_username.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0)
                    tif_password.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.hideKeyboard(v);
                } else {
                    tif_password.setError(null);
                }
            }
        });

        //endregion

        //region Xử lí Checkbox
        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    settings.setUsername("");
                    settings.setPassword("");
                }
                settings.setRemember(isChecked);
            }
        });
        //endregion

    }

    //region Xử lí override từ activity
    //region Xử lí override
    @Override
    protected void onResume() {
        super.onResume();
        cbRemember.setChecked(isRememeber);
        et_username.setText(username);
        et_password.setText(password);
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

    //endregion

    //region Xử lí khởi tạo component to layout và keyboard
    @Override
    public void SetInIt() {
        til_username = findViewById(R.id.til_user);
        tif_password = findViewById(R.id.til_password);
        et_username = findViewById(R.id.et_user);
        et_password = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvRegister);
        tvForget = findViewById(R.id.btnForget);
        //pb_signin = findViewById(R.id.pb_signin);
        mSnackbarLayout = findViewById(R.id.cl_snackbar);
        cbRemember = findViewById(R.id.cb_remember);


        //animations
        cardLogin = findViewById(R.id.cardLogin);
        YoYo.with(Techniques.Bounce)
                .duration(2000)
                .playOn(cardLogin);

    }

    @Override
    public void HideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //endregion

    //region Kiểm tra điều kiện trước khi nhấn button đăng nhập
    @Override
    public Boolean GetNoUserName(String username) {
        if (username.isEmpty()) {
            til_username.setError("Username không được để trống");
            /*et_username.setError("Username không được để trống");*/
            //pb_signin.setVisibility(View.INVISIBLE);
            return false;
        } else {
            til_username.setError(null);
            /*et_username.setError(null);*/
            return true;
        }
    }

    @Override
    public Boolean GetNoPassword(String password) {
        if (password.isEmpty()) {
            tif_password.setError("Mật khẩu không được để trống");
            /*et_password.setError("Mật khẩu không được để trống");*/
            //pb_signin.setVisibility(View.INVISIBLE);
            return false;
        } else {
            tif_password.setError(null);
            /*et_password.setError(null);*/
            return true;
        }
    }
    //endregion

    //region Xử lí button đăng nhập
    @Override
    public void BtnSignIn() {
        //pb_signin.setVisibility(View.VISIBLE);

        String username = et_username.getText().toString().trim();
        if (presenter.getNoUserName(username) == false) {
            return;
        }

        String password = et_password.getText().toString().trim();
        if (presenter.getNoPassword(password) == false) {
            return;
        }

        YoYo.with(Techniques.Tada)
                .repeat(2)
                .duration(2000)
                .playOn(cardLogin);

        presenter.loginFromServer(username, password);
    }

    //Check dữ liệu từ server
    @Override
    public void LoginFromServer(String username, String password) {


        StringRequest request = new StringRequest(Request.Method.POST,
                ConnectionClass.urlString + "logIn.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //pb_signin.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("Login Success")) {
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                //region Khởi tạo giá trị
                                int id_user = object.getInt("ID_USER");
                                int id_income = object.getInt("ID_INCOME");
                                int id_outcome = object.getInt("ID_OUTCOME");
                                int id_saving = object.getInt("ID_SAVING");
                                //endregion

                                //region Setting isRemember
                                if (settings.isRemember()) {
                                    String username = et_username.getText().toString().trim();
                                    String password = et_password.getText().toString().trim();

                                    settings.setUsername(username);
                                    settings.setPassword(password);
                                }
                                //endregion

                                //region Lưu để tự động đăng nhập
                                settings.setIsLogOut(false);
                                settings.setIdUser(id_user);
                                settings.setIdIncome(id_income);
                                settings.setIdOutcome(id_outcome);
                                settings.setIdSaving(id_saving);

                                //endregion

                                //region Intent
                                Intent intent = new Intent(LoginActivity.this, SavingActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("ID_USER", id_user);
                                bundle.putInt("ID_INCOME", id_income);
                                bundle.putInt("ID_OUTCOME", id_outcome);
                                bundle.putInt("ID_SAVING", id_saving);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                                //endregion
                            }
                        }
                    } else if (success.equals("Password wrong")) {
                        tif_password.setError("Nhập sai mật khẩu");
                        /*Toast.makeText(LoginActivity.this, success, Toast.LENGTH_SHORT).show();*/
                    } else if (success.equals("This account has not been register yet")) {
                        til_username.setError("Tài khoản này chưa được khởi tạo");
                        tif_password.setError("Tài khoản này chưa được khởi tạo");
                    } else {
                        Log.i("RESPONSELOGIN", success);
                        Snackbar snackbar = Snackbar.make(tvSignUp, "Đăng nhập thật bại", Snackbar.LENGTH_SHORT);
                        snackbar.setAnchorView(btnLogin);
                        snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                        snackbar.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(mSnackbarLayout, "Lỗi kết nối internet", Snackbar.LENGTH_SHORT);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        if (getApplicationContext() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(request);
        }
    }
    //endregion

    //region Xử lí các textview click
    @Override
    public void TextViewClick() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivityForResult(intent, REQUEST_SIGN_UP);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }

    @Override
    public void TextViewForgetClick() {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivityForResult(intent, REQUEST_FORGOT);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_SIGN_UP: {
                if (resultCode == RESULT_SIGN_UP_SUCCESS) {
                    Snackbar.make(tvSignUp, "Đăng ký tài khoản thành công", Snackbar.LENGTH_SHORT)
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                            .show();
                }
                break;
            }
            case REQUEST_FORGOT: {
                if (resultCode == RESULT_FORGOT_SUCCESS) {
                    Snackbar.make(tvSignUp, "Thay đổi mật khẩu mới thành công", Snackbar.LENGTH_SHORT)
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                            .show();
                }
                break;
            }
        }
    }

    //endregion

    //region Set Notification
    public void SetNotification() {
        Calendar calendar = Calendar.getInstance();
        Log.i("TEST1", calendar.toString());
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Intent intent = new Intent(getApplicationContext(), Notification_recevier.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }
    //endregion
}