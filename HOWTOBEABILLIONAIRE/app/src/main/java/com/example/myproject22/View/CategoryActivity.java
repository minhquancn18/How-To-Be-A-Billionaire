package com.example.myproject22.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myproject22.Presenter.SectionsPagerAdapter;
import com.example.myproject22.R;
import com.google.android.material.tabs.TabLayout;

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
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            Intent new_intent = new Intent(this, AddingActivity.class);
            new_intent.putExtras(bundle);
            startActivity(new_intent);
            this.finish();
        }
        else{
            Intent new_intent = new Intent(this, AddingActivity.class);
            startActivity(new_intent);
            this.finish();
        }
    }
}