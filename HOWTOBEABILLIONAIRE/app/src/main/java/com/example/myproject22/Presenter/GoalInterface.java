package com.example.myproject22.Presenter;

import android.view.View;

import org.json.JSONArray;

import java.io.File;

public interface GoalInterface {

    // navigation
    public void HandleNoGoal();
    public boolean HasGoal(String success, JSONArray jsonArray);

    //Utils
    public void ForNoInternet();


    //database
    public void FetchGoalFromServer();

    //init
    public boolean GetBundleData();

    public boolean HasInternet();

    public void AskForPermission();

    public void setUIBeforeInit();

    public void InitViews();

    //animations
    public void LoadAnimations();

    // images
    public void TakeScreenShot(View view);
    public void ShareImage(File file);


    // butotn Click
    public void GoalDetailClick();

    public void NewGoalClick();
    public void GoalHistory();


}
