package com.example.myproject22.View.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.myproject22.Model.SharePreferenceClass;
import com.example.myproject22.Model.UserClass;
import com.example.myproject22.Presenter.Interface.UserInterface;
import com.example.myproject22.Presenter.Presenter.UserPresenter;
import com.example.myproject22.R;
import com.example.myproject22.Util.FormatImage;
import com.example.myproject22.Util.Formatter;
import com.example.myproject22.View.Service.Network_receiver;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.alterac.blurkit.BlurLayout;

import static com.example.myproject22.Model.ConnectionClass.urlString;

public class UserAcitvity extends AppCompatActivity implements UserInterface {

    //region Khởi tạo giá trị ban đầu

    //region Component
    private TextView tv_name;
    private TextView tv_date;
    private TextView tv_income;
    private TextView tv_money;
    private CircleImageView iv_profile;
    private MaterialButton btnUpdate;
    private MaterialButton btnPassword;
    private MaterialButton btnMap;
    private MaterialButton btnLogout;
    private ConstraintLayout cl_total;

    // ANIMATIONS
    private ImageView ivLoad;
    private ImageView ivSign;

    private BlurLayout cardInfor;
    private BlurLayout cardUser;
    private BlurLayout cardPassword;
    private BlurLayout cardBank;
    private BlurLayout cardLogOut;
    //endregion

    //region Presenter
    private int id_user = 1;
    private UserClass userClass;
    private UserPresenter presenter;
    //endregion

    //region Share Preference
    private SharePreferenceClass settings;
    //endregion

    //region Broadcast
    private Network_receiver network_receiver;
    //endregion

    //region Const Update User
    private static final int REQUEST_UPDATE_USER = 1001;
    private static final int REQUEST_UPDATE_PASSWORD = 1101;
    public static final int RESULT_PASSWORD_SUCCESS = 1002;
    public static final int RESULT_SUCCESS = 1002;
    public static final int RESULT_FAIL = 1003;
    private Boolean neededToReload = false;
    private Boolean isUpdate = false;
    //endregion

    //region Bundle data
    private String username;
    private String userimage;
    private String useremail;
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
        setContentView(R.layout.activity_user_acitvity);

        //region Share Preference
        settings = new SharePreferenceClass(this);
        //endregion

        //region Broadcast
        network_receiver = new Network_receiver();
        //endregion

        //region Khởi tạo presenter và thiết lập các giá trị ban đầu
        presenter = new UserPresenter(this);
        presenter.setInit();
        presenter.getBundleData();
        //endregion

        //region Xử lí button click
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.btnLogout();
            }
        });

        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.btnPassword();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.btnUpdateUser();
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.btnMap();
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

    @Override
    protected void onResume() {
        super.onResume();

        if (neededToReload) {
            neededToReload = false;
            presenter.loadDataToLayout();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FormatImage.StopLoadImage(getApplicationContext());
    }
    //endregion

    //region Set Init, get bundle, Load Animations
    @Override
    public void SetInit() {
        tv_name = findViewById(R.id.tv_username);
        tv_date = findViewById(R.id.tv_userdate);
        tv_income = findViewById(R.id.tv_income);
        tv_money = findViewById(R.id.tv_money);
        iv_profile = findViewById(R.id.profile_image);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnPassword = findViewById(R.id.btnPassword);
        btnMap = findViewById(R.id.btnMap);
        btnLogout = findViewById(R.id.btnLogOut);
        cl_total = findViewById(R.id.cl_total);

        // animations
        ivSign = findViewById(R.id.ivSign);
        ivLoad = findViewById(R.id.ivLoad);

        cardBank = findViewById(R.id.cardBank);
        cardUser = findViewById(R.id.cardUser);
        cardInfor = findViewById(R.id.cardInfor);
        cardPassword = findViewById(R.id.cardPassword);
        cardLogOut = findViewById(R.id.cardLogOut);
        Glide.with(this)
                .load(R.drawable.background_gif).into(ivLoad);
    }

    @Override
    public void GetBundleData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id_user = bundle.getInt("ID_USER");

        String fullname = bundle.getString("FULLNAME");
        String email = bundle.getString("EMAIL");
        String image = bundle.getString("IMAGE");
        String datestart = bundle.getString("DATE_START");
        Double income = bundle.getDouble("INCOME");
        Double outcome = bundle.getDouble("OUTCOME");
        userClass = new UserClass(email,fullname,datestart,image,income,outcome);

        presenter.loadUser(userClass);
    }

    @Override
    public void LoadAnimations() {

        // LoadAnimation
        YoYo.with(Techniques.Tada)
                .duration(3000)
                .playOn(cardUser);


        YoYo.with(Techniques.Bounce).repeat(Animation.INFINITE)
                .duration(2500)
                .playOn(cardInfor);


        YoYo.with(Techniques.Pulse).repeat(Animation.INFINITE)
                .duration(2500)
                .playOn(cardLogOut);


        YoYo.with(Techniques.Shake).repeat(Animation.INFINITE)
                .duration(2500)
                .playOn(cardPassword);


        YoYo.with(Techniques.Wobble)
                .repeat(Animation.INFINITE)
                .duration(2500)
                .playOn(cardBank);

    }

    //endregion


    //region Xử lí button click

    //region Button Cập nhật thông tin
    @Override
    public void BtnUpdateUser() {
        Intent intent = new Intent(UserAcitvity.this, UpdateUserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ID_USER", id_user);
        bundle.putString("FULLNAME", username);
        bundle.putString("EMAIL", useremail);
        bundle.putString("IMAGE", userimage);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_UPDATE_USER);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_UPDATE_USER: {
                if (resultCode == RESULT_SUCCESS) {
                    Snackbar.make(btnLogout, "Cập nhật thông tin thành công", Snackbar.LENGTH_SHORT)
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                            .show();
                    neededToReload = true;
                } else {
                    neededToReload = false;
                }
                break;
            }
            case REQUEST_UPDATE_PASSWORD: {
                if (resultCode == RESULT_PASSWORD_SUCCESS) {
                    Snackbar.make(btnLogout, "Cập nhật mật khẩu thành công", Snackbar.LENGTH_SHORT)
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                            .show();
                }
                break;
            }
        }
    }

    //endregion

    //region Button Thay đổi mật khẩu
    @Override
    public void BtnPassword() {
        Intent intent = new Intent(UserAcitvity.this, UpdatePasswordActivity.class);
        intent.putExtra("ID_USER", id_user);
        startActivityForResult(intent, REQUEST_UPDATE_PASSWORD);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }
    //endregion

    //region Button tìm kiếm ngân hàng, ATM
    @Override
    public void BtnMap() {
        //Sử dụng dexter để check Permission
        Dexter.withContext(UserAcitvity.this)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {

                            //region Xử lí khi mọi permission granted
                            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                presenter.showCustomDialog();
                            } else {
                                presenter.showGPSDisabledAlertToUser();
                            }
                            //endregion

                        } else {
                            Snackbar snackbar = Snackbar.make(btnLogout, "Bạn chưa cấp quyền truy cập", Snackbar.LENGTH_SHORT);
                            snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
    //endregion

    //region Button đăng xuất
    @Override
    public void BtnLogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserAcitvity.this, android.R.style.Theme_Material_Dialog_NoActionBar);
        builder.setMessage("Bạn thật sự muốn đăng xuât")
                .setTitle("Đăng xuất")
                .setCancelable(true)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        settings.setIsLogOut(true);
                        settings.setFirstTime(false);
                        Intent i = new Intent(UserAcitvity.this, LoginActivity.class);
                        // set the new task and clear flags
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();

        // setting theme
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                dialog.getButton(d.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                dialog.getButton(d.BUTTON_POSITIVE).setTextColor(Color.WHITE);
            }
        });


        dialog.show();
    }
    //endregion

    //endregion

    //region Fetch User
    @Override
    public void FetchUserFromServer() {
        StringRequest request = new StringRequest(Request.Method.POST,
                urlString + "getUser.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("RESPONSEUSER", response);
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
                            Double income = object.getDouble("INCOME");
                            Double outcome = object.getDouble("OUTCOME");

                            if (!image_string.equals("null")) {
                                String url_image = urlString + "ImagesUser/" + image_string;
                                userClass = new UserClass(email, fullname, date_string, url_image, income, outcome);
                            } else {
                                userClass = new UserClass(email, fullname, date_string, image_string, income, outcome);
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
                Snackbar snackbar = Snackbar.make(btnMap, "Lỗi kết nối internet", Snackbar.LENGTH_SHORT);
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
        if (getApplicationContext() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(UserAcitvity.this);
            requestQueue.add(request);
        }
    }
    //endregion

    //region Load data from server to layout
    @Override
    public void LoadUser(UserClass userClass) {

        tv_name.setText(userClass.getFULLNAME());
        String date_temp = userClass.getDATESTART();
        String[] slipdate = date_temp.split(" ");
        String[] slipday = slipdate[0].split("-");
        String date_string = "Từ " + slipday[2] + "." + slipday[1] + "." + slipday[0];
        tv_date.setText(date_string);

        if (!userClass.getIMAGE().equals("null")) {
            FormatImage.LoadImageIntoView(iv_profile, UserAcitvity.this, userClass.getIMAGE());
        }
        else{
            FormatImage.LoadImageIntoView(iv_profile, UserAcitvity.this, R.drawable.avatar);
        }

        Double total = userClass.getINCOME() - userClass.getOUTCOME();
        long money = total.longValue();
        if (money < 0) {
            Log.i("MONEY1", String.valueOf(money));
            String money_string = "Đang nợ";
            tv_income.setText(money_string);
            String smoney = Formatter.getCurrencyStr(String.valueOf(-money));
            smoney = smoney + "VND";
            ivSign.setImageDrawable(getDrawable(R.drawable.ic_money_minus));
            tv_money.setText(smoney);
        } else {
            Log.i("MONEY1", String.valueOf(money));
            String money_string = "Đang có";
            tv_income.setText(money_string);
            String smoney = Formatter.getCurrencyStr(String.valueOf(money));
            smoney = smoney + "VND";
            ivSign.setImageDrawable(getDrawable(R.drawable.ic_money_add));
            tv_money.setText(smoney);
        }

        //region Load Bundle Data
        username = userClass.getFULLNAME();
        userimage = userClass.getIMAGE();
        useremail = userClass.getEMAIL();
        //endregion

        cl_total.setVisibility(View.VISIBLE);
        LoadAnimations();
      /*  YoYo.with(Techniques.SlideInUp)
                .duration(2000)
                .playOn(btnMap);*/
    }

    @Override
    public void LoadDataToLayout() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FetchUserFromServer();
            }
        }, 1000);
    }

    //endregion

    //region Dialog cho Map
    @Override
    public void ShowCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserAcitvity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_map, null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        EditText et_find = dialogView.findViewById(R.id.et_findlocation);
        MaterialButton btnBank = dialogView.findViewById(R.id.btnBank);
        MaterialButton btnAtm = dialogView.findViewById(R.id.btnATM);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        et_find.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        btnBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locat = et_find.getText().toString().trim();
                if (!locat.isEmpty()) {
                    locat = locat + " ";
                }
                String find_string = "geo:0,0?z=15&q=" + locat + "bank";
                Uri uri = Uri.parse(find_string);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
            }
        });

        btnAtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locat = et_find.getText().toString().trim();
                if (!locat.isEmpty()) {
                    locat = locat + " ";
                }
                String find_string = "geo:0,0?z=15&q=" + locat + "atm";
                Uri uri = Uri.parse(find_string);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void ShowGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Hiện tại máy bạn chưa bật định vị. Bạn có muốn bật định vị không?")
                .setTitle("Định vị")
                .setCancelable(false)
                .setPositiveButton("Vâng",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Không",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    //endregion

    //region Xử lí override Activity
    @Override
    public void onBackPressed() {

        isUpdate = settings.getIsUpdateUser();
        settings.setIsUpdateUser(false);
        if (isUpdate == false) {
            setResult(SavingActivity.RESULT_UPDATE_FAIL);
        } else {
            setResult(SavingActivity.RESULT_UPDATE_SUCCESS);
        }

        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }
    //endregion

}