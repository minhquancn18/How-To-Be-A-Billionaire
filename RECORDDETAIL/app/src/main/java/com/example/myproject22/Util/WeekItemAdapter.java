package com.example.myproject22.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject22.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import static com.example.myproject22.Util.FormatImage.ByteToBitmap;

public class WeekItemAdapter extends  RecyclerView.Adapter<WeekItemAdapter.ViewHolder> {

    ArrayList<String>weeks = new ArrayList<>();
    Context context;

    public WeekItemAdapter(ArrayList<String> weeks, Context context) {
        this.weeks = weeks;
        this.context = context;
    }

    public WeekItemAdapter(ArrayList<String> weeks) {
        this.weeks = weeks;
    }

    @NonNull
    @Override
    public WeekItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialCardView cardView = ((MaterialCardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_week, parent, false));
        return new WeekItemAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekItemAdapter.ViewHolder holder, int position) {
        MaterialCardView cardView = holder.cardView;
        TextView week = ((TextView) cardView.findViewById(R.id.tvWeek));
        week.setText(weeks.get(position));
    }

    @Override
    public int getItemCount() {
        return weeks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView cardView;
        public ViewHolder(MaterialCardView cardView) {
            super(cardView);
            this.cardView = cardView;

        }
    }
}
