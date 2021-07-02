package com.example.myproject22.View.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myproject22.Model.DetailItem;
import com.example.myproject22.Presenter.Interface.ReportCategoryDetailInterface;
import com.example.myproject22.Presenter.Presenter.ReportCategoryDetailPresenter;
import com.example.myproject22.R;
import com.example.myproject22.Util.RecordItemAdapter;
import com.example.myproject22.View.Service.Network_receiver;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.myproject22.Model.ConnectionClass.urlAudio;
import static com.example.myproject22.Model.ConnectionClass.urlImage;
import static com.example.myproject22.Model.ConnectionClass.urlImageCategory;
import static com.example.myproject22.Model.ConnectionClass.urlString;

public class ReportCategoryDetailActivity extends AppCompatActivity implements ReportCategoryDetailInterface {

    //region Khởi tạo component

    //region Component
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private TextView tvDate;
    private CoordinatorLayout mSnackbarLayout;
    //endregion

    //region parameter
    private String dateString;
    private int id_income;
    private int id_outcome;

    private Boolean isIncome = false;
    private Boolean isOutcome = false;
    //endregion

    //region ArrayList
    private ArrayList<DetailItem> incomedetail = new ArrayList<>();
    private ArrayList<DetailItem> outcomedetail = new ArrayList<>();
    private ArrayList<DetailItem> totaldetail = new ArrayList<>();
    //endregion

    //Presenter
    private ReportCategoryDetailPresenter presenter;

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

        setContentView(R.layout.activity_report_category_detail);

        //region Broadcast
        network_receiver = new Network_receiver();
        //endregion

        //region Khởi tạo presenter và các giá trị ban đầu
        presenter = new ReportCategoryDetailPresenter(this);
        presenter.setInit();
        presenter.getBundleData();
        presenter.loadData();
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

    //region Set init, get bundle
    @Override
    public void SetInit() {
        recyclerView = findViewById(R.id.record_recycler);
        toolbar = findViewById(R.id.toolbar);
        tvDate = findViewById(R.id.tvDate);
        progressBar = findViewById(R.id.pbDetailReport);
        toolbar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        mSnackbarLayout = findViewById(R.id.cl_snackbar);
    }

    @Override
    public void GetBundleData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        dateString = bundle.getString("DATE_STRING");
        id_income = bundle.getInt("ID_INCOME");
        id_outcome = bundle.getInt("ID_OUTCOME");
    }

    //endregion

    //region Fetch income, outcome from server
    @Override
    public void FetchIncomeDetailInServer() {
        StringRequest request = new StringRequest(Request.Method.POST,
                urlString + "getIncomeDetailByDate.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("RESPONSECATEGORYDETAIL", response);

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {
                        isIncome = true;
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String sid = object.getString("ID_INCOMEDETAIL");
                                String smoney = object.getString("MONEY");
                                String description = object.getString("DESCRIPTION");
                                String sdate = object.getString("DATE");
                                String name = object.getString("NAME");
                                String image = object.getString("IMAGE");
                                String imagecategory = object.getString("IMAGECATEGORY");
                                String audio = object.getString("AUDIO");

                                SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                Date dateObj = curFormater.parse(sdate);

                                int id_detail = Integer.parseInt(sid);

                                Double money = Double.parseDouble(smoney);
                                String[] splitdate = sdate.split(" ");
                                String stime = splitdate[1];

                                String url_image = image.equals("null") ? "NULL" : urlImage + image;
                                String url_image_category = imagecategory.equals("null") ? "NULL" : urlImageCategory + imagecategory;
                                String url_audio = audio.equals("null") ? "NULL" : urlAudio + audio;

                                DetailItem item = new DetailItem(id_detail, money, description, stime, name, 1, url_image, url_image_category, url_audio, dateObj);
                                incomedetail.add(item);

                            }
                        }
                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
                if (outcomedetail.size() > 0 || isOutcome == true) {
                    presenter.loadTotalArray();
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(mSnackbarLayout, "Lỗi kết nối internet", Snackbar.LENGTH_SHORT);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.show();
                /*Toast.makeText(ReportCategoryDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();*/
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_income", String.valueOf(id_income));
                params.put("date", dateString);
                return params;
            }
        };
        if (getApplicationContext() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(ReportCategoryDetailActivity.this);
            requestQueue.add(request);
        }
    }

    @Override
    public void FetchOutcomeDetailInServer() {
        StringRequest request = new StringRequest(Request.Method.POST,
                urlString + "getOutcomeDetailByDate.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("RESPONSECATEGORYDETAIL", response);

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {
                        isOutcome = true;
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String sid = object.getString("ID_OUTCOMEDETAIL");
                                String smoney = object.getString("MONEY");
                                String description = object.getString("DESCRIPTION");
                                String sdate = object.getString("DATE");
                                String name = object.getString("NAME");
                                String image = object.getString("IMAGE");
                                String image_category = object.getString("IMAGECATEGORY");
                                String audio = object.getString("AUDIO");

                                SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                Date dateObj = curFormater.parse(sdate);

                                int id_detail = Integer.parseInt(sid);

                                Double money = Double.parseDouble(smoney);
                                String[] splitdate = sdate.split(" ");
                                String stime = splitdate[1];

                                String url_image = image.equals("null") ? "NULL" : urlImage + image;
                                String url_image_category = image_category.equals("null") ? "NULL" : urlImageCategory + image_category;
                                String url_audio = audio.equals("null") ? "NULL" : urlAudio + audio;

                                DetailItem item = new DetailItem(id_detail, money, description, stime, name, -1, url_image, url_image_category, url_audio, dateObj);
                                outcomedetail.add(item);
                            }
                        }
                    }

                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
                if (incomedetail.size() > 0 || isIncome == true) {
                    LoadTotalArray();
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(mSnackbarLayout, "Lỗi kết nối từ internet", Snackbar.LENGTH_SHORT);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.show();
                /*Toast.makeText(ReportCategoryDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();*/
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_outcome", String.valueOf(id_outcome));
                params.put("date", dateString);
                return params;
            }
        };
        if (getApplicationContext() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(ReportCategoryDetailActivity.this);
            requestQueue.add(request);
        }
    }
    //endregion

    //region Load dữ liệu từ server vào layout
    @Override
    public void LoadData() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.fetchIncomeFromServer();
                presenter.fetchOutcomeFromServer();
            }
        }, 500);
    }

    @Override
    public void LoadTotalArray() {
        int i = 0, j = 0;
        int m = incomedetail.size();
        int n = outcomedetail.size();
        while (i < m && j < n) {
            if (incomedetail.get(i).get_DATEFULL().getTime() < outcomedetail.get(j).get_DATEFULL().getTime()) {
                totaldetail.add(incomedetail.get(i));
                i++;
            } else if (incomedetail.get(i).get_DATEFULL().getTime() > outcomedetail.get(j).get_DATEFULL().getTime()) {
                totaldetail.add(outcomedetail.get(j));
                j++;
            } else {
                DetailItem item1 = incomedetail.get(i);
                DetailItem item2 = incomedetail.get(j);
                totaldetail.add(item1);
                totaldetail.add(item2);
                i++;
                j++;
            }
        }

        while (i < m) {
            totaldetail.add(incomedetail.get(i));
            i++;
        }

        while (j < n) {
            totaldetail.add(outcomedetail.get(j));
            j++;
        }

        setSupportActionBar(toolbar);
        tvDate.setText("  " + dateString);
        toolbar.setVisibility(View.VISIBLE);

        RecordItemAdapter recordItemAdapter = new RecordItemAdapter(totaldetail, ReportCategoryDetailActivity.this);
        recyclerView.setAdapter(recordItemAdapter);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                boolean check_ScrollingUp = false;

                @Override
                public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);


                }

                @Override
                public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0) {
                        // Scrolling up
                        if(!check_ScrollingUp)
                        {
                            toolbar.startAnimation(AnimationUtils.loadAnimation(ReportCategoryDetailActivity.this,R.anim.trans_downwards));
                            check_ScrollingUp = true;
                        }

                    } else {
                        // User scrolls down
                        if(check_ScrollingUp )
                        {
                            toolbar.startAnimation(AnimationUtils
                                            .loadAnimation(ReportCategoryDetailActivity.this,R.anim.trans_upwards));
                            check_ScrollingUp = false;

                        }
                    }
                }
            });
        }

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