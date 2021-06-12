package com.example.myproject22.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.myproject22.R;
import com.example.myproject22.Util.CategoryPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class ReportCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        CategoryPagerAdapter categoryPagerAdapter = new CategoryPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.category_pager);
        viewPager.setAdapter(categoryPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.category_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.icon_edit);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_music);
    }
}