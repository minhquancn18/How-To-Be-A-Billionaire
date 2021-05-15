package com.hfad.project_1.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.hfad.project_1.R;
import com.hfad.project_1.Presenter.SectionsPagerAdapter;

public class CategoryActivity extends AppCompatActivity {

    private ViewPager pager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        SectionsPagerAdapter pagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);

        // set up adapter
        pagerAdapter.AddFragment(new SpendingCategoryFragment(), "Khoản Chi");
        pagerAdapter.AddFragment(new AddingCategoryFragment(), "Khoản Thu");
        pager.setAdapter(pagerAdapter);

        // set up tablayout
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

    }

    //Xóa layout này khi quay lại Activity trước đó
    @Override
    public void onBackPressed() {
        /*Intent intent = new Intent(this,  AddingActivity.class);
        startActivity(intent);*/
        this.finish();
    }
}