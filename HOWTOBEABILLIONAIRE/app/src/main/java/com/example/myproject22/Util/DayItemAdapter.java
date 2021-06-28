package com.example.myproject22.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject22.Model.DayItem;
import com.example.myproject22.R;
import com.example.myproject22.View.Activity.ReportCategoryDetailActivity;
import com.google.android.material.card.MaterialCardView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DayItemAdapter extends RecyclerView.Adapter<DayItemAdapter.ViewHolder> {

    //region Component
    ArrayList<DayItem> days;
    Context context;
    int id_income;
    int id_outcome;
    //endregion

    public DayItemAdapter(ArrayList<DayItem> days, Context context, int id_income, int id_outcome) {
        this.days = days;
        this.context = context;
        this.id_income = id_income;
        this.id_outcome = id_outcome;
    }


    @NonNull
    @Override
    public DayItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialCardView cardView = ((MaterialCardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_day, parent, false));
        return new DayItemAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //region Component
        MaterialCardView cardView = holder.cardView;
        TextView tvDay = cardView.findViewById(R.id.tvDay);
        TextView tvDayDetail = cardView.findViewById(R.id.tvDayDetail);
        TextView tvNumberOfRecord = cardView.findViewById(R.id.tvNumberOfRecord);
        //endregion

        //region Gán dữ liệu

        //region Xử lí ngày
        DateFormat formater = new SimpleDateFormat("EEEE");
        tvDay.setText(formater.format(days.get(position).date));

        formater = new SimpleDateFormat("dd.MM.yyyy");
        tvDayDetail.setText(formater.format(days.get(position).date));
        tvNumberOfRecord.setText(String.valueOf(days.get(position).numberOfRecord));
        //endregion

        //region Đổi màu ngày thứ 2 và ngày hiện tại
        Calendar cal = Calendar.getInstance();
        cal.setTime(days.get(position).getDate());
        int dow = cal.get(Calendar.DAY_OF_WEEK);

        Calendar total_cal = Calendar.getInstance();
        Date currentdate = total_cal.getTime();
        Date date = days.get(position).getDate();

        if(dow == Calendar.MONDAY)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cardView.setCardBackgroundColor(context.getColor(R.color.teal_200));
            }
        } else if(DayItem.CalculateDateUse(date,currentdate) == 0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cardView.setCardBackgroundColor(context.getColor(R.color.teal_200));
            }
        }
        //endregion

        //endregion

        //region Handle Click Cardview
        String sDate = days.get(position).getDateString();
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(context, ReportCategoryDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID_INCOME", id_income);
                bundle.putInt("ID_OUTCOME",id_outcome);
                bundle.putString("DATE_STRING", sDate);
                intent.putExtras(bundle);
                context.startActivity(intent);
                ((Activity)v.getContext()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
            }
        });
        //endregion

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
