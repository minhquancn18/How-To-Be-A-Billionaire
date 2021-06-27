package com.example.myproject22.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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
import com.example.myproject22.Model.DetailItem;
import com.example.myproject22.Presenter.Interface.ReportCategoryDetailInterface;
import com.example.myproject22.Presenter.Presenter.ReportCategoryDetailPresenter;
import com.example.myproject22.R;
import com.example.myproject22.Util.RecordItemAdapter;

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

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private TextView tvDate;

    private String dateString;
    private int id_income;
    private int id_outcome;

    private Boolean isIncome = false;
    private Boolean isOutcome = false;

    private ArrayList<DetailItem> incomedetail = new ArrayList<>();
    private ArrayList<DetailItem> outcomedetail = new ArrayList<>();
    private ArrayList<DetailItem> totaldetail = new ArrayList<>();

    //Presenter
    private ReportCategoryDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_category_detail);

        presenter = new ReportCategoryDetailPresenter(this);
        presenter.setInit();
        presenter.getBundleData();
        presenter.loadRecycleView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        isIncome = false;
        isOutcome = false;

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        incomedetail = new ArrayList<>();
        outcomedetail = new ArrayList<>();
        totaldetail = new ArrayList<>();
        LoadData();
    }

    @Override
    public void SetInit() {
        recyclerView = findViewById(R.id.record_recycler);
        toolbar = findViewById(R.id.toolbar);
        tvDate = findViewById(R.id.tvDate);
        progressBar = findViewById(R.id.pbDetailReport);
        toolbar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void GetBundleData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        dateString = bundle.getString("DATE_STRING");
        id_income = bundle.getInt("ID_INCOME");
        id_outcome = bundle.getInt("ID_OUTCOME");
    }

    @Override
    public void LoadRecycleView() {

    }

    public void FetchIncomeDetailInServer() {
        StringRequest request = new StringRequest(Request.Method.POST,
                urlString + "getIncomeDetailByDate.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {
                        isIncome = true;
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String smoney = object.getString("MONEY");
                                String description = object.getString("DESCRIPTION");
                                String sdate = object.getString("DATE");
                                String name = object.getString("NAME");
                                String image = object.getString("IMAGE");
                                String imagecategory = object.getString("IMAGECATEGORY");
                                String audio = object.getString("AUDIO");

                                SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                Date dateObj = curFormater.parse(sdate);

                                Double money = Double.parseDouble(smoney);
                                String[] splitdate = sdate.split(" ");
                                String stime = splitdate[1];

                                String url_image = image.equals("null") ? "NULL" : urlImage + image;
                                String url_image_category = imagecategory.equals("null") ? "NULL" : urlImageCategory + imagecategory;
                                String url_audio = audio.equals("null") ? "NULL" : urlAudio + audio;

                                DetailItem item = new DetailItem(money, description, stime, name, 1, url_image, url_image_category, url_audio, dateObj);
                                incomedetail.add(item);

                            }
                        }
                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
                if (outcomedetail.size() > 0 || isOutcome == true) {
                    LoadTotalArray();
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReportCategoryDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(ReportCategoryDetailActivity.this);
        requestQueue.add(request);
    }

    public void FetchOutcomeDetailInServer() {
        StringRequest request = new StringRequest(Request.Method.POST,
                urlString + "getOutcomeDetailByDate.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {
                        isOutcome = true;
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String smoney = object.getString("MONEY");
                                String description = object.getString("DESCRIPTION");
                                String sdate = object.getString("DATE");
                                String name = object.getString("NAME");
                                String image = object.getString("IMAGE");
                                String image_category = object.getString("IMAGECATEGORY");
                                String audio = object.getString("AUDIO");

                                SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                Date dateObj = curFormater.parse(sdate);

                                Double money = Double.parseDouble(smoney);
                                String[] splitdate = sdate.split(" ");
                                String stime = splitdate[1];

                                String url_image = image.equals("null") ? "NULL" : urlImage + image;
                                String url_image_category = image_category.equals("null") ? "NULL" : urlImageCategory + image_category;
                                String url_audio = audio.equals("null") ? "NULL" : urlAudio + audio;

                                DetailItem item = new DetailItem(money, description, stime, name, -1, url_image,url_image_category, url_audio, dateObj);
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
                Toast.makeText(ReportCategoryDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(ReportCategoryDetailActivity.this);
        requestQueue.add(request);
    }

    Runnable a = new Runnable() {
        @Override
        public void run() {
            FetchIncomeDetailInServer();
            FetchOutcomeDetailInServer();
        }
    };

    public void LoadData() {
        Handler handler = new Handler();
        handler.post(a);
    }

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
            totaldetail.add(totaldetail.get(j));
            j++;
        }

        setSupportActionBar(toolbar);
        tvDate.setText("    " + dateString);
        toolbar.setVisibility(View.VISIBLE);

        RecordItemAdapter recordItemAdapter = new RecordItemAdapter(totaldetail, ReportCategoryDetailActivity.this);
        recyclerView.setAdapter(recordItemAdapter);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);

    }
}