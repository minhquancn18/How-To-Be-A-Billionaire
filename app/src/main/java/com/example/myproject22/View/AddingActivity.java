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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_adding);

        btnPlay = findViewById(R.id.btnPlay);
        blurLayout = findViewById(R.id.blurLayout);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                1);

        mVisualizer = findViewById(R.id.blast);
        mediaPlayer = MediaPlayer.create(this, R.raw.demo);

        //TODO: init MediaPlayer and play the audio
        //get the AudioSessionId from your MediaPlayer and pass it to the visualizer
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


        playerSheet = findViewById(R.id.player_sheet);
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

        RecyclerView categoryRecycler = findViewById(R.id.category_recycler);
        CategoryItemAdapter adapter = new CategoryItemAdapter(images, categoryNames);
        categoryRecycler.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoryRecycler.setLayoutManager(layoutManager);

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

