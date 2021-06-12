package com.example.myproject22.View;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject22.Model.SavingDatabaseHelper;
import com.example.myproject22.R;
import com.example.myproject22.Util.CategoryItemAdapter;
import com.example.myproject22.Util.FormatImage;
import com.gauravk.audiovisualizer.visualizer.WaveVisualizer;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

import io.alterac.blurkit.BlurLayout;

public class AddingActivity extends AppCompatActivity {

    //Tạo SQLiteHelper để kết nối tới cơ sở dữ liệu
    private SavingDatabaseHelper db = new SavingDatabaseHelper(this, null, null, 1);

    //Khởi tạo các component để thực hiện event

    WaveVisualizer mVisualizer;
    BlurLayout blurLayout;
    MediaPlayer mediaPlayer;
    ImageButton btnPlay;

    private BottomSheetBehavior bottomSheetBehavior;
    private ConstraintLayout playerSheet;
    TextView tvChooseImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_adding);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                1);

        btnPlay = findViewById(R.id.btnPlay);
        blurLayout = findViewById(R.id.blurLayout);
        mVisualizer = findViewById(R.id.blast);
        playerSheet = findViewById(R.id.player_sheet);
        tvChooseImage = findViewById(R.id.tvChooseCategory);
        mediaPlayer = MediaPlayer.create(this, R.raw.demo);
        RecyclerView categoryRecycler = findViewById(R.id.category_recycler);
        RecyclerView categoryRecycler1 = findViewById(R.id.category_recycler2);


        int audioSessionId = mediaPlayer.getAudioSessionId();
        mVisualizer.setAudioSessionId(audioSessionId);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVisualizer.setVisibility(View.INVISIBLE);
                btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.icon_play, null));
            }
        });
        mVisualizer.setVisibility(View.INVISIBLE);
        bottomSheetBehavior = BottomSheetBehavior.from(playerSheet);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            ImageButton btnList = findViewById(R.id.btnList);

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    btnList.setImageDrawable(getDrawable(R.drawable.icon_arrow_up));

                }

                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    btnList.setImageDrawable(getDrawable(R.drawable.icon_arrow_down));

                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //We cant do anything here for this app
            }
        });


        ArrayList<byte[]> images = new ArrayList<>();
        ArrayList<String> categoryNames = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Bitmap bitmap = ((BitmapDrawable) btnPlay.getDrawable()).getBitmap();
            images.add(FormatImage.BitmapToByte(bitmap));
            categoryNames.add("Mua bim bim");
        }

        CategoryItemAdapter adapter = new CategoryItemAdapter(images, categoryNames, bottomSheetBehavior, tvChooseImage);
        categoryRecycler.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoryRecycler.setLayoutManager(layoutManager);




        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(RecyclerView.HORIZONTAL);

        categoryRecycler1.setAdapter(adapter);
        categoryRecycler1.setLayoutManager(layoutManager1);



    }

    @Override
    protected void onStart() {
        super.onStart();
        blurLayout.startBlur();
    }

    @Override
    protected void onStop() {
        blurLayout.pauseBlur();
        super.onStop();
    }

    public void PauseClicked(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mVisualizer.setVisibility(View.INVISIBLE);
            btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.icon_play, null));


        } else {
            mediaPlayer.start();
            mVisualizer.setVisibility(View.VISIBLE);
            btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.icon_pause, null));

        }
    }

    public void AddImageClicked(View view) {
        ((ImageButton) view).setImageDrawable(getDrawable(R.drawable.avatar));
        ((ImageButton) view).setScaleType(ImageView.ScaleType.CENTER_CROP);
        ((ImageButton) view).setScaleX(1);
        ((ImageButton) view).setScaleY(1);

    }
}

