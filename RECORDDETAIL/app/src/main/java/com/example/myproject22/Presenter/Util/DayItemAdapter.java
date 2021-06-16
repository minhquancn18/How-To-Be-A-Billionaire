package com.example.myproject22.Util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject22.Model.DayItem;
import com.example.myproject22.R;
import com.example.myproject22.View.Activity.ReportCategoryDetailActivity;
import com.example.myproject22.View.Fragment.IncomeCategoryDetailFragment;
import com.google.android.material.card.MaterialCardView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import io.alterac.blurkit.BlurKit;

public class DayItemAdapter extends RecyclerView.Adapter<DayItemAdapter.ViewHolder> {

    ArrayList<DayItem> days;
    Context context;
    boolean isForIncome;

    public DayItemAdapter(ArrayList<DayItem> days, Context context, boolean isForIncome) {
        this.days = days;
        this.context = context;
        this.isForIncome = isForIncome;
    }


    @NonNull
    @Override
    public DayItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialCardView cardView = ((MaterialCardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_day, parent, false));
        return new DayItemAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull DayItemAdapter.ViewHolder holder, int position) {
        MaterialCardView cardView = holder.cardView;
        TextView tvDay = cardView.findViewById(R.id.tvDay);
        TextView tvDayDetail = cardView.findViewById(R.id.tvDayDetail);
        TextView tvNumberOfRecord = cardView.findViewById(R.id.tvNumberOfRecord);

        DateFormat formater = new SimpleDateFormat("EEEE");
        String weekday = formater.format(days.get(position).date);
        tvDay.setText(formater.format(days.get(position).date));

        formater = new SimpleDateFormat("dd.MM.yyyy");
        tvDayDetail.setText(formater.format(days.get(position).date));
        tvNumberOfRecord.setText(String.valueOf(days.get(position).numberOfRecord));

        if(weekday.equals("Monday"))
        {
            cardView.setCardBackgroundColor(context.getColor(R.color.teal_200));
        }


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent  = new Intent(context, ReportCategoryDetailActivity.class);

                if(isForIncome){
                    // send extra for intent
                }
                else{
                    // send extra
                }
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;

        public ViewHolder(MaterialCardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
