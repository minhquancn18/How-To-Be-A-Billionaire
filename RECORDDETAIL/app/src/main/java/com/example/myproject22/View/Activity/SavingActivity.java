package com.example.myproject22.View.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.myproject22.Model.ConnectionClass;
import com.example.myproject22.R;
import com.example.myproject22.Presenter.SavingInterface;
import com.example.myproject22.Util.FormatImage;
import com.example.myproject22.Util.Formatter;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SavingActivity extends AppCompatActivity implements SavingInterface {

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
    private static final int id_user = 1;
    public static final int RESULT_ADD_OK = 10;
    private static final int REQUEST_NEW_GOAL = 11;
    public static final int RESULT_ADD_FAILED = 12;
    //endregion


    //region DEFAULT FUNCTIONS

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_saving);

     /*   View overlay = findViewById(R.id.mylayout);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);*/

        InitView();
        LoadAnimation();
        FetchGoalDataFromServer();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LoadAnimation();
        FetchGoalDataFromServer();
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
                    return;
                }
                if (requestCode == RESULT_ADD_FAILED) {
                    return;
                }
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
        tvGoalStartDay = findViewById(R.id.tvGoalDateStart);
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
        Intent intent = new Intent(this, GoalActivity.class);
        startActivityForResult(intent, REQUEST_NEW_GOAL);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }

    public void ShowDescriptionClicked(View view) {
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
                        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                        long diff_in_times = new Date().getTime() - formatter.parse(tem[3]).getTime();

                        long diff_in_days = TimeUnit
                                .MILLISECONDS
                                .toDays(diff_in_times)
                                % 365;


                        if (!image.equals("null"))
                            FormatImage.LoadImageIntoView(ivGoalImage, SavingActivity.this, urlImage);


                        tvGoalName.setText(name_goal);
                        tvMoneySaving.setText(Formatter.getCurrencyStr(money_saving));
                        tvGoalMoney.setText(Formatter.getCurrencyStr(money_goal) + " VND");
                        tvGoalStartDay.setText(date_start);
                        tvDescription.setText(description_goal);
                        rprogress.setProgress(progress);
                        rprogress.setProgressText(((int) progress) + "%");
                        tvGoalDayCount.setText(diff_in_days + " ngày");

                    }
                } catch (JSONException | ParseException e) {
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

    //endregion


    //endregion


}



