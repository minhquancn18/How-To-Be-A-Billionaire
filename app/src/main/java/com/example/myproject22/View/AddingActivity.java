package com.example.myproject22.View;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myproject22.MainActivity;
import com.example.myproject22.Model.MoneyCategoryClass;
import com.example.myproject22.Model.SavingDatabaseHelper;
import com.example.myproject22.Presenter.AddingMoneyInterface;
import com.example.myproject22.Presenter.AddingMoneyPresentent;
import com.example.myproject22.R;
import com.gauravk.audiovisualizer.visualizer.BarVisualizer;
import com.gauravk.audiovisualizer.visualizer.BlastVisualizer;
import com.gauravk.audiovisualizer.visualizer.WaveVisualizer;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import io.alterac.blurkit.BlurLayout;

public class AddingActivity extends AppCompatActivity {

    //Tạo SQLiteHelper để kết nối tới cơ sở dữ liệu
    private SavingDatabaseHelper db = new SavingDatabaseHelper(this, null, null, 1);

    //Khởi tạo các component để thực hiện event

    WaveVisualizer mVisualizer;
    BlurLayout blurLayout;
    MediaPlayer mediaPlayer;
    ImageButton btnPlay;


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
}

