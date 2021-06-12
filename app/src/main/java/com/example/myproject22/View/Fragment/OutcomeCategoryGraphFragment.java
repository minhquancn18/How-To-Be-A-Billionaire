package com.example.myproject22.View.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myproject22.R;
import com.example.myproject22.Util.WeekItemAdapter;
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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OutcomeCategoryGraphFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OutcomeCategoryGraphFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OutcomeCategoryGraphFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OutcomeCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OutcomeCategoryGraphFragment newInstance(String param1, String param2) {
        OutcomeCategoryGraphFragment fragment = new OutcomeCategoryGraphFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_outcome_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PieChart pieChart;
        pieChart = view.findViewById(R.id.pie_chart);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.2f, "Nhà hàng "));
        entries.add(new PieEntry(0.2f, "Mua sắm"));
        entries.add(new PieEntry(0.2f, "Tiền điện"));
        entries.add(new PieEntry(0.2f, "Tiền nước"));

        PieDataSet dataSet = new PieDataSet(entries, "Danh mục");
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS); // lib is best until now
        PieData data = new PieData(dataSet);
        data.setDrawValues(false); // no text
        data.setValueTextSize(16f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(Typeface.MONOSPACE);
        data.setValueFormatter(new PercentFormatter(pieChart));


        //pieChart.setHoleRadius(0);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setData(data);
        pieChart.setUsePercentValues(true); // set precent

        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Tiền chi");
        pieChart.setCenterTextSize(14f);
        pieChart.setCenterTextTypeface(Typeface.MONOSPACE);
        pieChart.getDescription().setEnabled(false);


        Legend l = pieChart.getLegend();
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setWordWrapEnabled(true);
        l.setDrawInside(true);
        l.setEnabled(true);

        pieChart.animateY(1200, Easing.EaseInBack);
        pieChart.animate();


        HorizontalBarChart weekchart = view.findViewById(R.id.weekChart1);

        ArrayList<BarEntry> dataList = new ArrayList<>();
        ArrayList<String> danhMucList = new ArrayList<>();


        dataList.add(new BarEntry(0, 5211221f));
        dataList.add(new BarEntry(1, 1212122f));
        dataList.add(new BarEntry(2, 1221212f));
        dataList.add(new BarEntry(3, 1221212f));


        danhMucList.add("Nhà hàng");
        danhMucList.add("Di động");
        danhMucList.add("Mua sắm");
        danhMucList.add("Bim bim");

        BarDataSet barDataSet = new BarDataSet(dataList, "Tiền theo tháng");
        barDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(20f);
        barDataSet.setValueTypeface(Typeface.MONOSPACE);
        BarData barData = new BarData(barDataSet);


        barData.setBarWidth(0.5f);
        weekchart.setFitBars(true);
        weekchart.setData(barData);
        weekchart.getDescription().setText("");
        weekchart.setHighlightFullBarEnabled(true);

        YAxis yAxis = weekchart.getAxisLeft();
        yAxis.setTextColor(Color.WHITE);
        yAxis.setTextSize(12);


        // set XAxis value formater
        XAxis xAxis = weekchart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(danhMucList));

        xAxis.setTextColor(Color.WHITE);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(14f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f);
        xAxis.setDrawLabels(true);
        xAxis.setLabelCount(danhMucList.size());

        Legend l2 = weekchart.getLegend();
        l2.setTextColor(Color.WHITE);
        l2.setTextSize(15);

        ArrayList<String>weeks = new ArrayList<>();

        for(int i=0 ;i< 12; i++) {
            weeks.add("Tuần 1.1.2020");
        }


        RecyclerView weekRecycler = view.findViewById(R.id.week_recycler);
        WeekItemAdapter adapter = new WeekItemAdapter(weeks, getContext());

        weekRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        weekRecycler.setLayoutManager(layoutManager1);
    }
}