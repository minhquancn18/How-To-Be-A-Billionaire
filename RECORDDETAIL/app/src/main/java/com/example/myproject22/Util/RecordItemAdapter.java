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
        CircleImageView btnImage = cardView.findViewById(R.id.circleImageView);
        CircleImageView btnImageCategory = cardView.findViewById(R.id.ivCategoryImage);
        ImageView ivCategory = cardView.findViewById(R.id.circleImageView4);


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
