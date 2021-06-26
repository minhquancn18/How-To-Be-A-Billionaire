package com.example.myproject22.View.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.myproject22.BuildConfig;
import com.example.myproject22.GoalRecordActivity;
import com.example.myproject22.Model.ConnectionClass;
import com.example.myproject22.Model.GoalRecord;
import com.example.myproject22.R;
import com.example.myproject22.Presenter.SavingInterface;
import com.example.myproject22.Util.FormatImage;
import com.example.myproject22.Util.Formatter;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mindorks.Screenshot;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import kotlin.ranges.UIntRange;

public class GoalActivity extends AppCompatActivity implements SavingInterface {

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
    boolean neededToReload = true;
    private static final int id_user = 1;
    public static final int RESULT_ADD_OK = 10;
    private static final int REQUEST_NEW_GOAL = 11;
    public static final int RESULT_ADD_FAILED = 12;
    private static final int REQUEST_VIEW_HISTORY = 13;

    //endregion


    //region DEFAULT FUNCTIONS
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                    }
                }).check();


        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.TRANSPARENT);


        InitView();
        LoadAnimation();
        FetchGoalDataFromServer();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (neededToReload) {
            LoadAnimation();
            FetchGoalDataFromServer();
        } else neededToReload = true;
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
                }
                return;
            }
            case REQUEST_VIEW_HISTORY: {
                neededToReload = false;
                return;

            }

        }
    }
    //endregion


    //region NORMAL FUNCTIONS
    public void LoadAnimation() {
        YoYo.with(Techniques.Pulse)
                .duration(2000)
                .delay(500)
                .playOn(myLayout);
    }

    //endregion


    //region OVERRIDE INTERFACE FUNCTION
    @Override
    public void LoadGoal() {

    }

    //endregion


    //region INIT FUNCTION
    public void InitView() {
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
    }
    //endregion


    //region BUTTON CLICK HANDLE
    public void NewGoalClicked(View view) {
        Intent intent = new Intent(this, NewGoalActivity.class);
        startActivityForResult(intent, REQUEST_NEW_GOAL);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }

    public void GoalDetailClicked(View view) {
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

    public void GoalHistoryClicked(View view) {
        Intent intent = new Intent(this, GoalRecordActivity.class);
        startActivityForResult(intent, REQUEST_VIEW_HISTORY);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }

    public void SreenShotClicked(View view) {


        takeScreenShot(getWindow().getDecorView());
    }

    //endregion


    //region DATABASE HANDLE

    //region GET DATA
    public void FetchGoalDataFromServer() {
        StringRequest request = new StringRequest(Request.Method.POST,
                ConnectionClass.urlString + "getGoal.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (success.equals("1") && jsonArray.length() > 0) {
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
                        Intent intent = new Intent(GoalActivity.this, NewGoalActivity.class);
                        intent.putExtra(NewGoalActivity.REQUEST_ADD_NEW, true);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
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
                params.put("id_user", String.valueOf(1));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    private void takeScreenShot(View view) {
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

            shareScreenShot(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shareScreenShot(File imageFile) {

        //Using sub-class of Content provider
        Uri uri = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + "." + getLocalClassName() + ".provider",
                imageFile);

        //Explicit intent
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "This is Sample App to take ScreenShot");
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        //It will show the application which are available to share Image; else Toast message will throw.
        try {
            this.startActivity(Intent.createChooser(intent, "Share With"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    private Uri saveImageExternal(Bitmap image) {
        //TODO - Should be processed in another thread
        Uri uri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "to-share.png");
            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.close();
            uri = Uri.fromFile(file);
        } catch (IOException e) {
            Log.d("ds", "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
    }
}