package com.example.myproject22.Presenter;

import android.content.Context;
import android.view.View;

import com.example.myproject22.Model.ConnectionClass;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class NewGoalPresenter {

    NewGoalInterface mNewGoalInterface;

    public NewGoalPresenter(NewGoalInterface mNewGoalInterface) {
        this.mNewGoalInterface = mNewGoalInterface;
    }

    public void SetUp() {
        mNewGoalInterface.SetUpBeforeInit();
        mNewGoalInterface.InitView();
        mNewGoalInterface.getMessage();
        mNewGoalInterface.GetBundle();
    }

    public void AddGoalToServer(Context context, View view) {
        if (ConnectionClass.hasInternet(context)) {
            mNewGoalInterface.AddNewGoalToServer();
            mNewGoalInterface.DisableViews();
        } else {
            Snackbar snackbar = Snackbar.make(context, view, "Bạn cần có kết nối internet để thêm một mục tiêu !", BaseTransientBottomBar.LENGTH_SHORT);
            snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            snackbar.show();
        }
    }
}
