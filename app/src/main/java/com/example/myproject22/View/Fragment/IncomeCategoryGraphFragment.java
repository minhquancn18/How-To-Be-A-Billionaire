package com.example.myproject22.View.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.example.myproject22.Model.IncomeClass;
import com.example.myproject22.Model.WeekItem;
import com.example.myproject22.R;
import com.example.myproject22.Util.WeekIncomeAdapter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.myproject22.Model.ConnectionClass.urlString;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IncomeCategoryGraphFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IncomeCategoryGraphFragment extends Fragment implements WeekIncomeAdapter.EventListener {

    //region Default Fragment
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IncomeCategoryGraphFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IncomeCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IncomeCategoryGraphFragment newInstance(String param1, String param2) {
        IncomeCategoryGraphFragment fragment = new IncomeCategoryGraphFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //endregion

    //region Constructor để lấy dữ liệu
    public IncomeCategoryGraphFragment(int id_user, int id_income) {
        // Required empty public constructor
        this.id_user = id_user;
        this.id_income = id_income;
    }
    //endregion

    //region Khởi tạo component

    //region Component
    private ProgressBar pb1;
    private ProgressBar pb2;
    private ArrayList<WeekItem> weeks = new ArrayList<>();
    private RecyclerView weekRecycler;
    private WeekIncomeAdapter adapter;
    private PieChart pieChart;
    private HorizontalBarChart weekchart;
    //endregion

    //region parameter
    private ArrayList<IncomeClass> income = new ArrayList<>();
    private int id_user;
    private int id_income;
    //endregion

    //endregion

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SetInit(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_income_category_graph, container, false);
    }

    //region Load dữ liệu khi nhấn vào fragment income
    @Override
    public void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FetchDateFromServer();
            }
        }, 1000);
    }
    //endregion

    //region Set init
    public void SetInit(View view){
        pb1 = view.findViewById(R.id.pb1);
        pb2 = view.findViewById(R.id.pb2);
        pb1.bringToFront();
        pb2.bringToFront();

        pieChart = view.findViewById(R.id.pie_chart);
        pieChart.setVisibility(View.INVISIBLE);

        weekchart = view.findViewById(R.id.weekChart1);
        weekchart.setVisibility(View.INVISIBLE);
        weekRecycler = view.findViewById(R.id.week_recycler);
    }
    //endregion

    //region Fetch Date from server (tìm ngày bắt đầu)
    public void FetchDateFromServer() {
        weeks = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST,
                urlString + "getDateByUser.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("RESPONSEGRAPH",response);

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String sdate = object.getString("DATESTART");

                            GetArrayWeek(sdate);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new WeekIncomeAdapter(weeks, getContext(), IncomeCategoryGraphFragment.this);

                weekRecycler.setAdapter(adapter);
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
                layoutManager1.setOrientation(RecyclerView.HORIZONTAL);
                weekRecycler.setLayoutManager(layoutManager1);


                String datestart = weeks.get(0).getDatestart();
                String dateend = weeks.get(0).getDateend();
                FetchIncomeFromServer(datestart,dateend);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(weekRecycler, "Lỗi kết nối internet", Snackbar.LENGTH_SHORT)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                        .show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(id_user));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
    //endregion

    //region Xử lí để tìm ngày bắt đầu và ngày kết thúc

    //Công thức tìm ngày
    public static long CalculateDateUse(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null)
            return 0;
        return (long) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    //Tìm chuỗi ngày weekrecycleview
    public void GetArrayWeek(String sDate) {

        //region Chuẩn bị
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime(); //Lấy thời gian hiện tại
        //endregion

        //region So sánh theo kiểu (lấy ngày hiện tại so với sDate để tìm tổng số tuần)
        try {
            Date datefrom = curFormater.parse(sDate); // Chuyển đổi kiểu dữ liệu sang Date
            int i = 1;  //Đếm số tuần
            do{

                //Kiểu dữ liệu để lưu String trong WeekItem
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

                //Kiểu dữ liệu để lưu Date trong mysql
                SimpleDateFormat formattest = new SimpleDateFormat("yyyy-MM-dd");

                //Format String trong WeekItem
                String date_from = formattest.format(datefrom);
                String date_string = simpleDateFormat.format(datefrom);
                String s ="Tuần " + i + " " + date_string;

                //Tìm tuần tiếp theo để so sánh
                Calendar c = Calendar.getInstance();
                c.setTime(datefrom);
                c.add(Calendar.DATE, 7);
                datefrom = c.getTime();

                Date dateto = datefrom;
                //Gán thời gian sau khi cộng để tìm date_to
                String date_to = formattest.format(dateto);
                WeekItem weekItem = new WeekItem(i, s, date_from, date_to);
                weeks.add(weekItem);

                Log.i("GetData", s + "\n" + date_string + "\n" + date_to);
                i++;

            }while(CalculateDateUse(datefrom, now) > 0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //endregion

        //region Sắp xếp
        Collections.sort(weeks, new Comparator<WeekItem>() {
            @Override
            public int compare(WeekItem o1, WeekItem o2) {
                if(o1.getNum() < o2.getNum()){
                    return  1;
                } else {
                    if(o1.getNum() == o2.getNum())
                        return  0;
                    else
                        return -1;
                }
            }
        });
        //endregion
    }
    //endregion

    //region Xử lí biểu đồ tròn (PieChart)
    public void dataPiechart() {
        //Kiểu tra incomelist có khác null không
        int m = income.size();
        if (m > 0) {

            //region Xử lí PieEntry
            ArrayList<PieEntry> Entries = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                IncomeClass item = income.get(i);
                PieEntry incomePE = new PieEntry(item.getMoney().floatValue(), item.getCategory());
                Entries.add(incomePE);
            }
            //endregion

            //region Xử lí PieDataSet
            PieDataSet dataSet = new PieDataSet(Entries, "Danh mục");
            dataSet.setColors(ColorTemplate.LIBERTY_COLORS); // lib is best until now
            //endregion

            //region Xử lí PieData
            PieData data = new PieData(dataSet);
            data.setDrawValues(false); // no text
            data.setValueTextSize(16f);
            data.setValueTextColor(Color.WHITE);
            data.setValueTypeface(Typeface.MONOSPACE);
            data.setValueFormatter(new PercentFormatter(pieChart));
            //endregion

            //region Xử lí PieChart
            pieChart.setDrawHoleEnabled(true);
            pieChart.setData(data);
            pieChart.setUsePercentValues(true); // set precent
            pieChart.setEntryLabelColor(Color.BLACK);
            pieChart.setCenterText("Tiền thu");
            pieChart.setCenterTextSize(14f);
            pieChart.setCenterTextTypeface(Typeface.MONOSPACE);
            pieChart.getDescription().setEnabled(false);
            //endregion

            //region Xử lý Lengend
            Legend l = pieChart.getLegend();
            l.setTextColor(Color.WHITE);
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);
            l.setWordWrapEnabled(true);
            l.setDrawInside(true);
            l.setEnabled(true);
            //endregion

            //region Xử lí animate PieChart
            pieChart.animateY(1200, Easing.EaseInBack);
            pieChart.animate();
            //endregion

            //region Hiện piechart khi đã loadxong
            pieChart.invalidate();
            pb1.setVisibility(View.GONE);
            pieChart.setVisibility(View.VISIBLE);
            //endregion
        }
    }
    //endregion

    //region Xử lí biểu đồ ngang (BarChart)
    public void dataBarchart() {
        //Kiểm tra income list có khác null không
        int m = income.size();
        if (m > 0) {

            //region Xử lí BarEntry
            ArrayList<BarEntry> dataList = new ArrayList<>();
            ArrayList<String> danhMucList = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                IncomeClass item = income.get(i);
                float a = (float) i;
                float b = item.getMoney().floatValue();
                String c = item.getCategory();
                BarEntry incomeBE = new BarEntry(a, b);
                dataList.add(incomeBE);
                danhMucList.add(c);
            }
            //endregion

            //region Xử lí BarDataSet
            BarDataSet barDataSet = new BarDataSet(dataList, "Tiền theo tháng");
            barDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
            barDataSet.setValueTextColor(Color.WHITE);
            barDataSet.setValueTextSize(20f);
            barDataSet.setValueTypeface(Typeface.MONOSPACE);
            //endregion

            //region Xử lí BarData
            BarData barData = new BarData(barDataSet);
            barData.setBarWidth(0.2f);
            //endregion

            //region Xử lí weekchart
            weekchart.setFitBars(true);
            weekchart.setData(barData);
            weekchart.getDescription().setText("");
            weekchart.setHighlightFullBarEnabled(true);
            //endregion

            //region Xử lí YAxis (Cột Y)
            YAxis yAxis = weekchart.getAxisLeft();
            yAxis.setTextColor(Color.WHITE);
            yAxis.setTextSize(10);
            //endregion

            //region Xử lí XAxis (Hàng X)
            // set XAxis value formater
            XAxis xAxis = weekchart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(danhMucList));

            xAxis.setTextColor(Color.WHITE);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(12f);
            xAxis.setDrawAxisLine(false);
            xAxis.setDrawGridLines(true);
            xAxis.setGranularity(1f);
            xAxis.setDrawLabels(true);
            xAxis.setLabelCount(danhMucList.size());
            //endregion

            //region Xử lý Lengend
            Legend l2 = weekchart.getLegend();
            l2.setTextColor(Color.WHITE);
            l2.setTextSize(15);
            //endregion

            //region Hiện BarChart khi đã load xong
            weekchart.invalidate();
            pb2.setVisibility(View.GONE);
            weekchart.setVisibility(View.VISIBLE);
            //endregion

        }
    }
    //endregion

    //region Fetch Income from server (Dựa vào ngày bắt đầu và ngày kết thúc)
    @Override
    public void FetchIncomeFromServer(String datestart, String dateend) {
        income = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST,
                urlString + "getIncomeByDate.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("RESPONSEGRAPH", response);

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String name = object.getString("NAME");
                            Double money = object.getDouble("TOTAL");

                            IncomeClass incomeClass = new IncomeClass(name, money);
                            income.add(incomeClass);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(income.size() > 0) {
                    dataPiechart();
                    dataBarchart();
                }
                else
                {
                    InvisibleChart();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(weekRecycler, "Lỗi kết nối internet", Snackbar.LENGTH_SHORT)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                        .show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_income", String.valueOf(id_income));
                params.put("datestart", datestart);
                params.put("dateend", dateend);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(IncomeCategoryGraphFragment.this.getActivity());
        requestQueue.add(request);
    }
    //endregion

    //region Ẩn chart khi không có dữ liệu
    public void InvisibleChart(){
        pb1.setVisibility(View.GONE);
        pb2.setVisibility(View.GONE);
        pieChart.setVisibility(View.INVISIBLE);
        weekchart.setVisibility(View.INVISIBLE);
    }
    //endregion


}