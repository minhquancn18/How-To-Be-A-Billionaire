package com.example.myproject22.Util;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import com.example.myproject22.View.Activity.RecordDetailActivity;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecordItemAdapter extends RecyclerView.Adapter<RecordItemAdapter.ViewHolder> {

    //region Component
    private ArrayList<DetailItem> arrays;
    private Context context;
    //endregion

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

        //region Component
        MaterialCardView cardView = holder.cardView;

        TextView tvMoney = cardView.findViewById(R.id.tvMoney);
        TextView tvDescription = cardView.findViewById(R.id.tvDescription);
        TextView tvTime = cardView.findViewById(R.id.tvTime);
        TextView tvName = cardView.findViewById(R.id.tvCategory);
        CircleImageView btnImage = cardView.findViewById(R.id.circleImageView);
        CircleImageView btnImageCategory = cardView.findViewById(R.id.ivCategoryImage);
        ImageView ivCategory = cardView.findViewById(R.id.circleImageView4);
        ImageView ivSetAudio = cardView.findViewById(R.id.ivSetAudio);
        //endregion

        //region Gán dữ liệu

        DetailItem item = arrays.get(position);

        //region Gán dữ liệu cơ bản
        String money = Formatter.getCurrencyStr(String.valueOf(item.get_MONEY())) + " VND";
        tvMoney.setText(money);
        tvDescription.setText(item.get_DESCRIPTION());
        tvName.setText(item.get_NAME());
        tvTime.setText(item.get_DATE());
        //endregion

        //region Gán dữ liệu cho loại thu chi
        int IsCategory = item.get_TYPE();
        if(IsCategory == 1){
            ivCategory.setImageResource(R.drawable.round_background_income);
            ivSetAudio.setImageResource(R.drawable.round_background_income);
        }
        else if(IsCategory == -1){
            ivCategory.setImageResource(R.drawable.round_background_outcome);
            ivSetAudio.setImageResource(R.drawable.round_background_outcome);
        }
        //endregion

        //region Gán hình ảnh
        String image_url = item.get_IMAGE();
        if (image_url.equals("NULL")) {
            btnImage.setImageResource(R.drawable.backgroundflower);
        } else {
            Glide.with(cardView).load(image_url).into(btnImage);
        }

        String image_category_url = item.get_IMAGECATEGORY();
        if (image_category_url.equals("NULL")) {
        } else {
            Glide.with(cardView).load(image_category_url).into(btnImageCategory);
        }
        //endregion

        //endregion

        //region Handle Click (âm thanh và không có âm thanh)
        String audio_url = item.get_AUDIO();
        if(audio_url.equals("NULL")){
            ivSetAudio.setVisibility(View.INVISIBLE);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), RecordDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("IS_CATEGORY", item.get_TYPE());
                    bundle.putInt("ID_DETAIL", item.get_ID_DETAIL());
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                    ((Activity)v.getContext()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                }
            });
        }else{
            ivSetAudio.setVisibility(View.VISIBLE);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), RecordDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("IS_CATEGORY", item.get_TYPE());
                    bundle.putInt("ID_DETAIL", item.get_ID_DETAIL());
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                    ((Activity)v.getContext()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                }
            });
        }
        //endregion
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
