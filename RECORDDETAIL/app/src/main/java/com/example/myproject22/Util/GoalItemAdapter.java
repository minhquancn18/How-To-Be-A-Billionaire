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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.myproject22.R;
import com.google.android.material.card.MaterialCardView;

public class GoalItemAdapter extends RecyclerView.Adapter<GoalItemAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialCardView cardView = ((MaterialCardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_goal, parent, false));
        return new GoalItemAdapter.ViewHolder(cardView);
    }


    // VFD FDF FDF fd fd fd fd fd fd fd fd fd fd fd fd fd fd fd fd x
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MaterialCardView cardView = holder.cardView;
        ImageView ivGoalRecord = cardView.findViewById(R.id.ivGoalRecord);


        ConstraintLayout cl_information = cardView.findViewById(R.id.cl_information);
        if (position % 3 == 0) {
            Glide.with(cardView.getContext())
                    .load(R.drawable.background12)
                    .transform(new CenterCrop(), new RoundedCorners(10))
                    .into(ivGoalRecord);
        } else {
            Glide.with(cardView.getContext())
                    .load(R.drawable.backgroundhome)
                    .transform(new CenterCrop(), new RoundedCorners(10))
                    .into(ivGoalRecord);
        }

        TextView goalName = cardView.findViewById(R.id.tvGoalName);
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
        return 15;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView cardView;

        public ViewHolder(@NonNull MaterialCardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }
}
