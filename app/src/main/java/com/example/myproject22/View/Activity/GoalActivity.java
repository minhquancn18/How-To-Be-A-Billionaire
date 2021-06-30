package com.example.myproject22.View.Activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
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
import com.example.myproject22.Model.GoalRecord;
import com.example.myproject22.Presenter.Interface.GoalInterface;
import com.example.myproject22.Presenter.Presenter.GoalPresenter;
import com.example.myproject22.R;
import com.example.myproject22.Util.FormatImage;
import com.example.myproject22.Util.Formatter;
import com.example.myproject22.View.Service.Network_receiver;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoalActivity extends AppCompatActivity implements GoalInterface {

    //region UI COMPONENTS
    TextView tvGoalName;
    TextView tvGoalMoney;
    TextView tvMoneySaving;
    TextView tvGoalStartDay;
    TextView tvGoalDayCount;
    TextRoundCornerProgressBar rprogress;
    ImageView ivGoalImage;
    TextView tvDescription;
    ConstraintLayout myLayout;

    //endregion


    //region GLOBAL VARIABLES
    public static final String MESSAGE = "Msg";
    boolean neededToReload = true;
    String message = "Hiện tại bạn chưa có mục tiêu nào";
    private static int id_user = 1;
    public static final int RESULT_ADD_OK = 10;
    private static final int REQUEST_NEW_GOAL = 11;
    public static final int RESULT_ADD_FAILED = 12;
    private static final int REQUEST_VIEW_HISTORY = 13;


    // handle goal
    String success;
    JSONArray jsonArray;

    // presenter
    GoalPresenter mGoalPresenter;

    //Broadcast
    private Network_receiver network_receiver;
    //endregion


    //region DEFAULT FUNCTIONS
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        network_receiver = new Network_receiver();

        mGoalPresenter = new GoalPresenter(this);
        mGoalPresenter.SetUp();
        mGoalPresenter.LoadData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mGoalPresenter.LoadDataOnRestart(neededToReload);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_NEW_GOAL: {
                if (resultCode == RESULT_ADD_OK) {
                    Snackbar snackbar = Snackbar.make(tvGoalMoney, "Thêm một mục tiêu thành công", BaseTransientBottomBar.LENGTH_SHORT);
                    snackbar.setAnchorView(R.id.tvMoneyGoal);
                    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    snackbar.show();
                    neededToReload = true;
                }
                if (resultCode == RESULT_ADD_FAILED) {
                    neededToReload = false;
                    if (!HasGoal(success, jsonArray)) {
                        finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                    }
                }
                return;
            }
            case REQUEST_VIEW_HISTORY: {
                neededToReload = false;
                return;

            }

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);

    }
    //endregion


    //region ANIMATIONS
    @Override
    public void LoadAnimations() {
        YoYo.with(Techniques.Pulse)
                .duration(2000)
                .delay(500)
                .playOn(myLayout);

    }

    //endregion


    //region INIT FUNCTIONS
    @Override
    public boolean GetBundleData() {
        Intent intent = getIntent();
        id_user = intent.getIntExtra("ID_USER", 0);
        return (id_user != 0);
    }

    @Override
    public boolean HasInternet() {
        return ConnectionClass.hasInternet(this);
    }

    @Override
    public void AskForPermission() {
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void setUIBeforeInit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.TRANSPARENT);
    }

    @Override
    public void InitViews() {
        tvGoalMoney = findViewById(R.id.tvMoneyGoal);
        tvMoneySaving = findViewById(R.id.tvMoneySaving);
        tvGoalName = findViewById(R.id.tvGoalName);
        tvGoalStartDay = findViewById(R.id.tvGoalDayStart);
        tvGoalDayCount = findViewById(R.id.tvGoalDayCount);
        rprogress = findViewById(R.id.rprogress);
        ivGoalImage = findViewById(R.id.ivGoalImage);
        myLayout = findViewById(R.id.mylayout);
        tvDescription = findViewById(R.id.tvGoalDescription);
        tvDescription.setAlpha(0.f);
        tvDescription.setTranslationY(200);


        Glide.with(this)
                .load(R.drawable.fish_gif)
                .into(ivGoalImage);
    }


    //endregion


    //region BUTTON CLICK HANDLE
    public void NewGoalClicked(View view) {
        NewGoalClick();
    }

    public void GoalDetailClicked(View view) {
        GoalDetailClick();
    }

    public void GoalHistoryClicked(View view) {
        GoalHistory();
    }

    public void SreenShotClicked(View view) {
        TakeScreenShot(getWindow().getDecorView());
    }

    //endregion


    //region DATABASE HANDLE

    @Override
    public void HandleNoGoal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_finish_goal, null);
        builder.setView(dialogView);


        //init  view
        ImageView ivAnimation = dialogView.findViewById(R.id.ivAnimation);
        Button btnAddGoal = dialogView.findViewById(R.id.btnAddNewGoal);
        Button btnGoBack = dialogView.findViewById(R.id.btnGoBack);



        // gain value
        Glide.with(this)
                .load(R.drawable.dialog2_gif)
                .into(ivAnimation);

        YoYo.with(Techniques.Bounce)
                .duration(1000)
                .playOn(dialogView);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnAddGoal.setOnClickListener(p -> {
            NewGoalClick();
            dialog.dismiss();
        });
        btnGoBack.setOnClickListener(p ->
        {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public boolean HasGoal(String success, JSONArray jsonArray) {
        return (success.equals("1") && jsonArray.length() > 0);
    }

    @Override
    public void ForNoInternet() {
        tvGoalName.setText("Không có kết nối mạng");
    }

    @Override
    public void FetchGoalFromServer() {
        StringRequest request = new StringRequest(Request.Method.POST,
                ConnectionClass.urlString + "getGoal.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    success = jsonObject.getString("success");
                    jsonArray = jsonObject.getJSONArray("data");
                    if (HasGoal(success, jsonArray)) {
                        JSONObject object = jsonArray.getJSONObject(0);
                        String name_goal = object.getString("NAME_GOAL");
                        String description_goal = object.getString("DESCRIPTION_GOAL");
                        String money_goal = object.getString("MONEY_GOAL");
                        String money_saving = object.getString("MONEY_SAVING");
                        String image = object.getString("IMAGE_GOAL");
                        String date_start = object.getString("DATE_START");
                        float progress = ((float) Integer.parseInt(money_saving) / Integer.parseInt(money_goal)) * 100;
                        String urlImage = ConnectionClass.urlImageGoal + image;

                        String[] tem = date_start.split(" ");
                        long diff_in_days = GoalRecord.getDayDiffByStr(tem[3]);
                        if (!image.equals("null"))
                            FormatImage.LoadImageIntoView(ivGoalImage, GoalActivity.this, urlImage);

                        tvGoalName.setText(name_goal);
                        tvMoneySaving.setText(Formatter.getCurrencyStr(money_saving));
                        tvGoalMoney.setText(Formatter.getCurrencyStr(money_goal) + " VND");
                        tvGoalStartDay.setText(date_start);
                        tvDescription.setText(description_goal);
                        rprogress.setProgress(progress);
                        rprogress.setProgressText(((int) progress) + "%");
                        tvGoalDayCount.setText(diff_in_days + " ngày");

                    } else { // no goal -> need add new
                        HandleNoGoal();
                    }
                } catch (JSONException e) {
                    Snackbar snackbar = Snackbar.make(tvGoalDayCount, e.getMessage() + "JSON", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar a = Snackbar.make(tvGoalDayCount, error.getMessage() + "ERROR", BaseTransientBottomBar.LENGTH_LONG);
                a.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(id_user));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    //endregion


    //region IMAGE HANDLE
    @Override
    public void TakeScreenShot(View view) {
        //This is used to provide file name with Date a format
        Date date = new Date();
        CharSequence format = DateFormat.format("MM-dd-yyyy_hh:mm:ss", date);

        //It will make sure to store file to given below Directory and If the file Directory dosen't exist then it will create it.
        try {
            File mainDir = new File(
                    this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "FilShare");
            if (!mainDir.exists()) {
                boolean mkdir = mainDir.mkdir();
            }

            //Providing file name along with Bitmap to capture screenview
            String path = mainDir + "/" + "TrendOceans" + "-" + format + ".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);


            File imageFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            ShareImage(imageFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void ShareImage(File file) {
        //Using sub-class of Content provider
        Uri uri = FileProvider.getUriForFile(
                this,
                "com.example.myproject22.provider",
                file);

        //Explicit intent
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, "Đây là mục tiêu của mình");
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        //It will show the application which are available to share Image; else Toast message will throw.
        try {
            this.startActivity(Intent.createChooser(intent, "Share With"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    //endregion


    //region BUTTON OVERRIDE
    @Override
    public void GoalDetailClick() {
        if (tvDescription.getAlpha() == 0f) {
            tvDescription.animate()
                    .translationY(0)
                    .setDuration(600)
                    .alpha(1.0f)
                    .setListener(null);
        } else {
            tvDescription.animate()
                    .translationY(200)
                    .alpha(0f)
                    .setDuration(500)
                    .setListener(null);
        }
    }

    @Override
    public void NewGoalClick() {
        Intent intent = new Intent(this, NewGoalActivity.class);
        intent.putExtra("ID_USER", id_user);
        startActivityForResult(intent, REQUEST_NEW_GOAL);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }

    @Override
    public void GoalHistory() {
        Intent intent = new Intent(this, GoalRecordActivity.class);
        startActivityForResult(intent, REQUEST_VIEW_HISTORY);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }

    //endregion
}
