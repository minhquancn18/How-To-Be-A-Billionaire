package com.example.myproject22.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.myproject22.R;
import com.example.myproject22.Util.CategoryPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class ReportCategoryGraphActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_report_graph);


        CategoryPagerAdapter categoryPagerAdapter = new CategoryPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.category_graph_pager);
        viewPager.setAdapter(categoryPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.category_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.icon_edit);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_music);



    }



}