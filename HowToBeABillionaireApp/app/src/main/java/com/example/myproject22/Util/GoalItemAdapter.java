package com.example.myproject22.Util;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.myproject22.Model.ConnectionClass;
import com.example.myproject22.Model.GoalRecord;
import com.example.myproject22.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class GoalItemAdapter extends RecyclerView.Adapter<GoalItemAdapter.ViewHolder> {


    ArrayList<GoalRecord> goals;

    public GoalItemAdapter(ArrayList<GoalRecord> goals) {
        this.goals = goals;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialCardView cardView = ((MaterialCardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_goal, parent, false));
        return new ViewHolder(cardView);
    }


    // VFD FDF FDF fd fd fd fd fd fd fd fd fd fd fd fd fd fd fd fd x
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MaterialCardView cardView = holder.cardView;
        ConstraintLayout cl_information = cardView.findViewById(R.id.cl_information);

        ImageView ivGoalRecord = cardView.findViewById(R.id.ivGoalRecord);
        TextView goalName = cardView.findViewById(R.id.tvGoalName);
        TextView goalMoney = cardView.findViewById(R.id.tvGoalMoney);
        TextView goalDayStart = cardView.findViewById(R.id.tvGoalDayStart);
        TextView goalDayCount = cardView.findViewById(R.id.tvGoalDayCount);

        goalMoney.setText(Formatter.getCurrencyStr(goals.get(position).getGoalMoney().toString()) + "VND");
        goalName.setText(goals.get(position).getGoalName());
        goalDayStart.setText(goals.get(position).getDate_start());
        goalDayCount.setText(String.valueOf(
                GoalRecord.getDayDiffByStr(goals.get(position).getDate_start()) + " ngày"
        ));

        String urlGoal = ConnectionClass.urlImageGoal + goals.get(position).getGoalImage();

        FormatImage.LoadImageIntoView(ivGoalRecord, cardView.getContext(), urlGoal);

        Context a = cardView.getContext();
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.FlipInY)
                        .duration(500)
                        .withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                                if (goalName.getVisibility() == View.VISIBLE) {
                                    cl_information.setVisibility(View.VISIBLE);
                                } else {
                                    cl_information.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if (goalName.getVisibility() == View.VISIBLE) {
                                    goalName.setVisibility(View.INVISIBLE);
                                } else {
                                    goalName.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        })
                        .playOn(cardView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView cardView;

        public ViewHolder(@NonNull MaterialCardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }
}
