package com.example.myproject22.View.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
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
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.myproject22.Model.UserClass;
import com.example.myproject22.R;
import com.example.myproject22.Presenter.Interface.SavingInterface;
import com.example.myproject22.Presenter.Presenter.SavingPresenter;
import com.example.myproject22.Util.FormatImage;
import com.example.myproject22.Util.Formatter;
import com.example.myproject22.Util.MyAxisValueFormatter;
import com.example.myproject22.View.Service.Network_receiver;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.myproject22.Model.ConnectionClass.urlString;

public class SavingActivity extends AppCompatActivity implements SavingInterface {

    private static final int REQUEST_GOAL = 200;
    //region Khởi tạo component
    // for debugg
    double ivSize = 0;

    //region Component cho tiet kiem
    private TextView tvDayStreak;
    private TextView tvTotalSaving;
    private TextView tvUserName;
    private TextView tvUserDate;
    private TextView tvUserUse;
    private CircleImageView ivProfile;
    private ConstraintLayout cl_savingdate;
    private ConstraintLayout cl_savingmoney;
    private ConstraintLayout cl_user;
    private BarChart weekchart;
    private ProgressBar pb_total;
    //endregion


    //region TAM ANIMATION COMPONENTS
    private ConstraintLayout cardSavingMoney;
    private ConstraintLayout cardSavingDate;
    private ConstraintLayout cardChart;
    private LinearLayout cardNavigation;
    private ConstraintLayout cardUser;
    //endregion

    //region Parameter

    //region Xử lí thời gian và tiền
    private ArrayList<Date> arrayDate = new ArrayList<>();
    private int count_date = 0;
    private int count_use = 0;
    private String money_string = "";
    //endregion

    //region Xử lí user
    private UserClass userClass;
    //endregion

    //region Array list và 1 vài xử lí biểu đồ
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final int DAYSOFWEEK = 7;
    ArrayList<String> ngayTrongTuan = new ArrayList<String>();
    ArrayList<BarEntry> recordTietKiem = new ArrayList<>();
    //endregion

    // region Presenter
    private int id_saving = 3;
    private int id_user = 9;
    private int id_income = 3;
    private int id_outcome = 3;
    private SavingPresenter mSavingPresenter;
    //endregion

    //region Broadcast
    private Network_receiver network_receiver;
    //endregion

    //region Const

    //region Adding Money
    private static final int REQUEST_ADD_MONEY = 1000;
    public static final int RESULT_ADD_INCOME = 1001;
    public static final int RESULT_ADD_OUTCOME = 1002;
    public static final int RESULT_ADD_FAIL = 1003;
    //endregion

    //region Update User
    private static final int REQUEST_UPDATE_USER = 1100;
    public static final int RESULT_UPDATE_SUCCESS = 1101;
    public static final int RESULT_UPDATE_FAIL = 1102;
    //endregion

    private Boolean neededToReload = true;
    //endregion

    //endregion

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_saving);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        //region Broadcast
        network_receiver = new Network_receiver();
        //endregion

        //region Khởi tạo presenter và các giá trị mặc định
        mSavingPresenter = new SavingPresenter(this);
        mSavingPresenter.initView();
        mSavingPresenter.getBundleData();
        mSavingPresenter.createDataBarChart();
        //endregion


        //region Xử lí cardview click
        cardUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSavingPresenter.btnUserClick();
            }
        });

    }

    //region Xử lí các hàm override từ Activity


    @Override
    protected void onRestart() {
        super.onRestart();

        setAllInvisible();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("TEST1", neededToReload + "");
        if (neededToReload) {
            neededToReload = false;
            mSavingPresenter.loadDataFromServer();
        } else {
            setAllVisible();
        }
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
    protected void onDestroy() {
        super.onDestroy();
        FormatImage.StopLoadImage(getApplicationContext());
    }
    //endregion

    //region Set init, get bundle
    @Override
    public void InitViews() {

        //region saving
        weekchart = findViewById(R.id.weekChar);
        tvDayStreak = findViewById(R.id.tvDayStreak);
        tvTotalSaving = findViewById(R.id.tvTotalSaving);
        tvUserName = findViewById(R.id.tv_username);
        tvUserDate = findViewById(R.id.tv_userdate);
        tvUserUse = findViewById(R.id.tvDayUse);
        ivProfile = findViewById(R.id.profile_image);
        cl_savingdate = findViewById(R.id.cl_savingdate);
        cl_savingmoney = findViewById(R.id.cl_savingmoney);
        cl_user = findViewById(R.id.cl_user);
        pb_total = findViewById(R.id.pb_total);
        pb_total.bringToFront();
        //endregion


        // for animations
        cardSavingDate = findViewById(R.id.cardDate);
        cardSavingMoney = findViewById(R.id.card_savingmoney);
        cardUser = findViewById(R.id.cardUser);
        cardChart = findViewById(R.id.cardChart);
        cardNavigation = findViewById(R.id.cardNavigation);

        setAllInvisible();

    }

    @Override
    public void GetBundleData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id_user = bundle.getInt("ID_USER");
        id_income = bundle.getInt("ID_INCOME");
        id_outcome = bundle.getInt("ID_OUTCOME");
        id_saving = bundle.getInt("ID_SAVING");
    }
    //endregion

    //region Tạo barchart
    @Override
    public void CreateDataBarChart() {

        //region Load bar chart cột x
        ngayTrongTuan.add("T2");
        ngayTrongTuan.add("T3");
        ngayTrongTuan.add("T4");
        ngayTrongTuan.add("T5");
        ngayTrongTuan.add("T6");
        ngayTrongTuan.add("T7");
        ngayTrongTuan.add("CN");
        //endregion

        //region Khởi tạo bar chart cho cột y tương ứng với từng cột x
        recordTietKiem.add(new BarEntry(0, 0));
        recordTietKiem.add(new BarEntry(1, 0));
        recordTietKiem.add(new BarEntry(2, 0));
        recordTietKiem.add(new BarEntry(3, 0));
        recordTietKiem.add(new BarEntry(4, 0));
        recordTietKiem.add(new BarEntry(5, 0));
        recordTietKiem.add(new BarEntry(6, 0));
        //endregion

    }
    //endregion

    //region Load data from server vào layout

    //region BarChart
    @Override
    public void LoadDataFromServerToBarChart() {

        //region Xử lí BarDataSet
        BarDataSet barDataSet = new BarDataSet(recordTietKiem, "Ngày trong tuần");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(0f);
        barDataSet.setValueTypeface(Typeface.MONOSPACE);
        barDataSet.setBarBorderWidth(1);
        barDataSet.setBarBorderColor(Color.WHITE);

        //endregion

        //region Xử lí BarData
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f);

        barData.setValueTextColor(Color.WHITE);

        //endregion

        //region Xử lí weekchart
        weekchart.setFitBars(true);
        weekchart.setData(barData);
        weekchart.setHighlightFullBarEnabled(true);
        weekchart.getAxisLeft().setTextColor(Color.WHITE);
        weekchart.getAxisRight().setTextSize(0f);
        weekchart.getAxisLeft().setCenterAxisLabels(true);



        ValueFormatter a = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if(Math.abs(value ) >= 1000000){
                    value = value /10000000;
                    return value  + " triệu";
                }

                if(Math.abs(value ) >= 1000){
                    value = value /1000;
                    return value  + " ngàn";
                }
                return value + "VND";
            }

        };


        weekchart.getAxisLeft().setValueFormatter(a);
        weekchart.getAxisLeft().setTextSize(12f);
        weekchart.getAxisLeft().setMinWidth(75f);

        weekchart.getAxisLeft().setTypeface(Typeface.MONOSPACE);


        Legend l = weekchart.getLegend();
        l.setTextColor(Color.WHITE);
        l.setTypeface(Typeface.MONOSPACE);
        l.setTextSize(13);
        //endregion


        //region Xử lí XAxis (Hàng X)
        // set XAxis value formater
        XAxis xAxis = weekchart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(ngayTrongTuan));
        xAxis.setTextColor(Color.WHITE);
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTypeface(Typeface.MONOSPACE);
        xAxis.setTextSize(14f);
        xAxis.setDrawAxisLine(false);

        xAxis.setLabelCount(ngayTrongTuan.size());
        //endregion

        //region Hiện barchart khi đã load xong
        weekchart.setVisibility(View.VISIBLE);
        weekchart.animateY(1000);
        //endregion
    }
    //endregion

    //region User
    @Override
    public void LoadUser(UserClass userClass) {
        tvUserName.setText(userClass.getFULLNAME());
        String date_temp = userClass.getDATESTART();
        String[] slipdate = date_temp.split(" ");
        String[] slipday = slipdate[0].split("-");
        String date_string = "Đã tham gia vào \nngày " + slipday[2] + "/" + slipday[1] + "/" + slipday[0];
        tvUserDate.setText(date_string);

        if (!userClass.getIMAGE().equals("null")) {
            FormatImage.LoadImageIntoView(ivProfile, SavingActivity.this, userClass.getIMAGE());
        } else {
            FormatImage.LoadImageIntoView(ivProfile, SavingActivity.this, R.drawable.avatar);
        }

        Double total = userClass.getINCOME() - userClass.getOUTCOME();
        money_string = SavingPresenter.GetStringMoney(total);

        tvTotalSaving.setText(money_string);

        cl_savingmoney.setVisibility(View.VISIBLE);
        cl_user.setVisibility(View.VISIBLE);
    }
    //endregion

    //region Total
    @Override
    public void LoadDataFromServer() {
        String date_start = SavingPresenter.StringFromDate(SavingPresenter.FindMondayFromDate(new Date()));
        String date_end = SavingPresenter.StringFromDate(SavingPresenter.FindEndOfWeek(SavingPresenter.FindMondayFromDate(new Date())));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadAnimation();
                mSavingPresenter.fetchUserFromServer();
                mSavingPresenter.fetchArrayDateFromServer();
                mSavingPresenter.fetchSavingDetailFromServer(date_start, date_end);
            }
        }, 1000);
    }
    //endregion

    //endregion

    //region Fetch Data from server

    //region Lấy dữ liệu từ bảng savingdetail
    @Override
    public void FetchSavingDetailFromServer(String date_start, String date_end) {
        StringRequest request = new StringRequest(Request.Method.POST,
                urlString + "getSavingDetailByDate.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("RESPONSESAVING", response);

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String date_string = object.getString("DATE");
                            Double money = object.getDouble("MONEY");
                            Log.i("TESTRECORD", String.valueOf(money));
                            //Load saving vào trong bar chart
                            Date date = SavingPresenter.DateFromString(date_string);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            int dow = cal.get(Calendar.DAY_OF_WEEK);
                            if (dow == 1) {
                                recordTietKiem.get(6).setY(money.floatValue());
                            } else {
                                recordTietKiem.get(dow - 2).setY(money.floatValue());
                            }

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mSavingPresenter.loadDataFromServerToBarChart();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(weekchart, "Lỗi kết nối internet", Snackbar.LENGTH_SHORT);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_saving", String.valueOf(id_saving));
                params.put("datestart", date_start);
                params.put("dateend", date_end);
                return params;
            }
        };
        if (getApplicationContext() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(SavingActivity.this);
            requestQueue.add(request);
        }
    }
    //endregion

    //region Lấy dữ liệu ngày từ savingdetail (để tìm chuỗi ngày)
    public void FetchArrayDateFromServer() {
        arrayDate = new ArrayList<>();
        count_date = 0;
        count_use = 0;
        StringRequest request = new StringRequest(Request.Method.POST,
                urlString + "getSavingDetailDate.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("RESPONSESAVING", response);

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String date_string = object.getString("DATE");
                            Date date = SavingPresenter.DateFromString(date_string);

                            if (arrayDate.size() == 0) {
                                arrayDate.add(date);
                                count_date++;
                                count_use++;
                            } else {
                                Date last_array = arrayDate.get(arrayDate.size() - 1);
                                if (SavingPresenter.CalculateDateUse(last_array, date) > 1) {
                                    count_date = 0;
                                }
                                arrayDate.add(date);
                                count_date++;
                                count_use++;
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvDayStreak.setText(String.valueOf(count_date));
                tvUserUse.setText(String.valueOf(count_use));
                cl_savingdate.setVisibility(View.VISIBLE);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(weekchart, "Lỗi kết nối internet", Snackbar.LENGTH_SHORT);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_saving", String.valueOf(id_saving));
                return params;
            }
        };
        if (getApplicationContext() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(SavingActivity.this);
            requestQueue.add(request);
        }
    }
    //endregion


    //region Lấy thông tin user từ bảng user
    @Override
    public void FetchUserFromServer() {
        StringRequest request = new StringRequest(Request.Method.POST,
                urlString + "getUser.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("RESPONSESAVING", response);

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String fullname = object.getString("FULLNAME");
                            String date_string = object.getString("DATESTART");
                            String image_string = object.getString("USERIMAGE");
                            String email = object.getString("EMAIL");
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
                mSavingPresenter.loadUser(userClass);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(weekchart, "Lỗi kết nối internet", Snackbar.LENGTH_SHORT);
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
            RequestQueue requestQueue = Volley.newRequestQueue(SavingActivity.this);
            requestQueue.add(request);
        }
    }
    //endregion

    //endregion

    //region Handle Material Cardview Click
    @Override
    public void BtnUserClick() {
        if (userClass != null) {
            Intent intent = new Intent(SavingActivity.this, UserAcitvity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("ID_USER", id_user);
            bundle.putString("FULLNAME", userClass.getFULLNAME());
            bundle.putString("DATE_START", userClass.getDATESTART());
            bundle.putString("EMAIL", userClass.getEMAIL());
            bundle.putString("IMAGE", userClass.getIMAGE());
            bundle.putDouble("INCOME", userClass.getINCOME());
            bundle.putDouble("OUTCOME", userClass.getOUTCOME());
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_UPDATE_USER);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
        }
    }

    public void GoalClicked(View view) {
        Intent intent = new Intent(this, GoalActivity.class);
        intent.putExtra("ID_USER", id_user);
        startActivityForResult(intent, REQUEST_GOAL);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }

    public void OldRecordClicked(View view) {
        Intent intent = new Intent(SavingActivity.this, ReportCategoryActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("ID_INCOME", id_income);
        bundle.putInt("ID_OUTCOME", id_outcome);
        intent.putExtras(bundle);

        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }

    public void AddRecordClicked(View view) {
        Intent intent = new Intent(SavingActivity.this, AddingActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("ID_USER", id_user);
        bundle.putInt("ID_INCOME", id_income);
        bundle.putInt("ID_OUTCOME", id_outcome);
        intent.putExtras(bundle);

        startActivityForResult(intent, REQUEST_ADD_MONEY);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }

    public void GraphClicked(View view) {
        Intent intent = new Intent(SavingActivity.this, ReportCategoryGraphActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("ID_USER", id_user);
        bundle.putInt("ID_INCOME", id_income);
        bundle.putInt("ID_OUTCOME", id_outcome);
        intent.putExtras(bundle);

        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_ADD_MONEY: {
                if (resultCode == RESULT_ADD_INCOME) {
                    Snackbar snackbar = Snackbar.make(cardNavigation, "Thêm một thu nhập thành công", BaseTransientBottomBar.LENGTH_SHORT);
                    snackbar.setAnchorView(R.id.cardNavigation);
                    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    snackbar.show();
                    neededToReload = true;
                } else if (resultCode == RESULT_ADD_OUTCOME) {
                    Snackbar snackbar = Snackbar.make(cardNavigation, "Thêm một chi tiêu thành công", BaseTransientBottomBar.LENGTH_SHORT);
                    snackbar.setAnchorView(R.id.cardNavigation);
                    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    snackbar.show();
                    neededToReload = true;
                } else {
                    neededToReload = false;
                }
                break;
            }
            case REQUEST_UPDATE_USER: {
                if (resultCode == RESULT_UPDATE_SUCCESS) {
                    neededToReload = true;
                } else {
                    neededToReload = false;
                }
                Log.i("TEST2", neededToReload + "");
                break;
            }
            case REQUEST_GOAL: {
                neededToReload = false;
                return;
            }
        }
    }

    //endregion

    //region ANIMATION
    public void setAllInvisible() {
        pb_total.setVisibility(View.VISIBLE);
        cardChart.setVisibility(View.INVISIBLE);
        cardSavingMoney.setVisibility(View.INVISIBLE);
        cardUser.setVisibility(View.INVISIBLE);
        cardSavingDate.setVisibility(View.INVISIBLE);
        cardNavigation.setVisibility(View.INVISIBLE);

        cl_user.setVisibility(View.INVISIBLE);
        cl_savingmoney.setVisibility(View.INVISIBLE);
        cl_savingdate.setVisibility(View.INVISIBLE);
        weekchart.setVisibility(View.INVISIBLE);

    }

    public void setAllVisible() {
        pb_total.setVisibility(View.GONE);
        cardChart.setVisibility(View.VISIBLE);
        cardSavingMoney.setVisibility(View.VISIBLE);
        cardUser.setVisibility(View.VISIBLE);
        cardSavingDate.setVisibility(View.VISIBLE);
        cardNavigation.setVisibility(View.VISIBLE);

        if (!neededToReload) {
            cl_user.setVisibility(View.VISIBLE);
            cl_savingmoney.setVisibility(View.VISIBLE);
            cl_savingdate.setVisibility(View.VISIBLE);
            weekchart.setVisibility(View.VISIBLE);
        }
    }

    public void loadAnimation() {
        setAllVisible();
        int slide_time = 1800;
        YoYo.with(Techniques.SlideInRight)
                .duration(slide_time)
                .playOn(cardUser);

        YoYo.with(Techniques.SlideInRight)
                .duration(slide_time)
                .playOn(cardSavingDate);

        YoYo.with(Techniques.SlideInRight)
                .duration(slide_time)
                .playOn(cardSavingMoney);

        YoYo.with(Techniques.SlideInRight)
                .duration(slide_time)
                .playOn(cardChart);

        YoYo.with(Techniques.SlideInUp)
                .duration(slide_time + 300)
                .playOn(cardNavigation);
    }

    //endregion


}

