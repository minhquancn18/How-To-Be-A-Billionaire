package com.example.myproject22.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.myproject22.R;
import com.example.myproject22.Util.CategoryGraphPagerAdapter;
import com.example.myproject22.View.Service.Network_receiver;
import com.google.android.material.tabs.TabLayout;

public class ReportCategoryGraphActivity extends AppCompatActivity  {

    //region Parameter
    private int id_user = 1 ;
    private int id_income = 1;
    private int id_outcome = 1;
    //endregion

    //region Broadcast Receiver
    private Network_receiver network_receiver;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_report_graph);

        GetBundleData();

        //region Broadcast
        network_receiver = new Network_receiver();
        //endregion

        //region Xử lí ViewPager
        CategoryGraphPagerAdapter categoryGraphPagerAdapter = new CategoryGraphPagerAdapter(getSupportFragmentManager(), id_user, id_income, id_outcome);
        ViewPager viewPager = findViewById(R.id.category_graph_pager);
        viewPager.setAdapter(categoryGraphPagerAdapter);
        //endregion

        //region Xử lí TabLayout
        TabLayout tabLayout = findViewById(R.id.category_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.icon_edit);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_music);
        //endregion

    }

    //region Get bundle
    public void GetBundleData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id_user = bundle.getInt("ID_USER",1);
        id_income = bundle.getInt("ID_INCOME", 1);
        id_outcome = bundle.getInt("ID_OUTCOME", 1);
    }
    //endregion

    //region Xử lí override Activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }
    //endregion

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
}