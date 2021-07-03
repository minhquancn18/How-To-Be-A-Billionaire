package com.example.myproject22.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.myproject22.R;
import com.example.myproject22.Util.FormatImage;
import com.example.myproject22.View.Service.Network_receiver;

import de.hdodenhof.circleimageview.CircleImageView;

public class GuideActivity extends AppCompatActivity {

    //region Khởi tạo giá trị
    private TextView tv_skip;
    private ImageView iv_guide;
    private TextView title;
    private TextView description_1;
    private TextView description_2;
    private TextView greeting;
    private ImageView iv_background;

    //Broadcast reciever
    private Network_receiver network_receiver;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_guide);

        network_receiver = new Network_receiver();

        SetInit();

        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
            }
        });
    }

    //region Xử lí override


    @Override
    protected void onResume() {
        super.onResume();
        LoadAnimation();
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
    //endregion

    //region Set Init
    private void SetInit(){
        tv_skip = findViewById(R.id.tv_skip);
        title = findViewById(R.id.guide_title);
        description_1 = findViewById(R.id.guide_text_1);
        description_2 = findViewById(R.id.guide_text_2);
        greeting = findViewById(R.id.guide_greet);
        iv_background = findViewById(R.id.ivBGImage);
        iv_guide = findViewById(R.id.guide_image);

        FormatImage.LoadImageIntoView(iv_guide, GuideActivity.this, R.drawable.pocket_money);
        FormatImage.LoadImageIntoView(iv_background, GuideActivity.this, R.drawable.background23gif);
    }

    private void LoadAnimation(){
        YoYo.with(Techniques.SlideInLeft)
                .duration(2500)
                .playOn(findViewById(R.id.guide_title));

        YoYo.with(Techniques.SlideInRight)
                .duration(3000)
                .playOn(findViewById(R.id.guide_text_1));

        YoYo.with(Techniques.SlideInRight)
                .duration(3000)
                .playOn(findViewById(R.id.guide_text_2));

        YoYo.with(Techniques.SlideInUp)
                .duration(4000)
                .playOn(findViewById(R.id.guide_greet));

        YoYo.with(Techniques.SlideInRight)
                .duration(2000)
                .playOn(findViewById(R.id.tv_skip));

        YoYo.with(Techniques.FadeInDown)
                .duration(2000)
                .playOn(findViewById(R.id.guide_image));
    }
    //endregion
}