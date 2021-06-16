package com.example.myproject22.Util;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myproject22.Model.DetailItem;
import com.example.myproject22.R;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecordItemAdapter extends RecyclerView.Adapter<RecordItemAdapter.ViewHolder> {

    private static final  int REQUEST_PERMISSION_CODE = 100;
    private ArrayList<DetailItem> arrays;
    private Context context;

    private MediaPlayer mediaPlayer;
    private Boolean flag = true;
    private Boolean isPlaying = false;


    public RecordItemAdapter(ArrayList<DetailItem> arrays, Context context) {
        this.arrays = arrays;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialCardView cardView = ((MaterialCardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_record, parent, false));
        return new RecordItemAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MaterialCardView cardView = holder.cardView;

        TextView tvMoney = cardView.findViewById(R.id.tvMoney);
        TextView tvDescription = cardView.findViewById(R.id.tvDescription);
        TextView tvTime = cardView.findViewById(R.id.tvTime);
        TextView tvName = cardView.findViewById(R.id.tvCategory);
        TextView tvEnd = cardView.findViewById(R.id.tvTimeEnd);
        TextView tvStart = cardView.findViewById(R.id.tvTimeStart);
        SeekBar seekBar = cardView.findViewById(R.id.seekbarProgressAudio);
        CircleImageView btnImage = cardView.findViewById(R.id.circleImageView);
        CircleImageView btnImageCategory = cardView.findViewById(R.id.ivCategoryImage);
        ImageView ivCategory = cardView.findViewById(R.id.circleImageView4);
        ImageButton btnAudio = cardView.findViewById(R.id.btnPlayAudio);

        tvStart.setVisibility(View.INVISIBLE);
        tvEnd.setVisibility(View.INVISIBLE);
        seekBar.setVisibility(View.INVISIBLE);
        btnAudio.setVisibility(View.INVISIBLE);

        DetailItem item = arrays.get(position);
        tvMoney.setText(String.valueOf(item.get_MONEY()));
        tvDescription.setText(item.get_DESCRIPTION());
        tvName.setText(item.get_NAME());
        tvTime.setText(item.get_DATE());

        int IsCategory = item.get_TYPE();
        if(IsCategory == 1){
            ivCategory.setImageResource(R.drawable.round_background_income);
        }
        else if(IsCategory == -1){
            ivCategory.setImageResource(R.drawable.round_background_outcome);
        }

        String image_url = item.get_IMAGE();
        if (image_url.equals("NULL")) {
        } else {
            Glide.with(cardView).load(image_url).into(btnImage);
        }

        String image_category_url = item.get_IMAGECATEGORY();
        if (image_category_url.equals("NULL")) {
        } else {
            Glide.with(cardView).load(image_category_url).into(btnImageCategory);
        }

        String audio_url = item.get_AUDIO();
        if (audio_url.equals("NULL")) {
        } else {
          btnAudio.setVisibility(View.VISIBLE);
        }

        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kiểm tra xem audio của cardview này đã đc tạo chưa
                if(flag){
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(audio_url); //Lấy audio từ url web
                        mediaPlayer.prepare();
                        int duration = mediaPlayer.getDuration(); //Lấy độ dài của video
                        seekBar.setMax(duration); //Set thanh seek bar
                        tvEnd.setText(String.valueOf(duration / 1000));//Set text end (độ dài của video)

                        //Khi nhấn vào nút audio thì chuẩn bị chạy và hiện lại textView End, text view Srart, seekbar
                        tvEnd.setVisibility(View.VISIBLE);
                        tvStart.setVisibility(View.VISIBLE);
                        seekBar.setVisibility(View.VISIBLE);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //Xét cờ bằng false để ko cho những audio khác phát
                    flag = false;
                }

                //Kiểm tra media đang phát hay ngừng
                if(mediaPlayer.isPlaying()){
                    //Dừng media khi bấm nút
                    mediaPlayer.pause();
                    btnAudio.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_play, null));
                } else {
                    //Bắt đầu media
                    mediaPlayer.start();
                    btnAudio.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_pause, null));

                    //Nếu audio đã hết thì mới cho mở cờ (giải phóng media)
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mediaPlayer.stop();
                            mediaPlayer.release();

                            flag = true;

                            //Ẩn những thông tin khác
                            tvStart.setVisibility(View.INVISIBLE);
                            tvEnd.setVisibility(View.INVISIBLE);
                            seekBar.setVisibility(View.INVISIBLE);
                            btnAudio.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_play, null));
                        }
                    });

                    //Set thanh seekbar
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            seekBar.setProgress(mediaPlayer.getCurrentPosition());
                        }
                    }, 100);
                }
            }
        });

        //Xử lí seekbar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView cardView;

        public ViewHolder(MaterialCardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
