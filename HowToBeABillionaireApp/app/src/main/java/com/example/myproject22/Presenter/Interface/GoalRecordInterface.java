package com.example.myproject22.Presenter.Interface;

import org.json.JSONArray;

public interface GoalRecordInterface {


    public void SetUpBeforeInit();
    public  void InitViews();
    public void OnBackPress();
    public boolean hasGoalRecord();
    public void FetchGoalRecordFromServer();
    public void ShowNoGoal();



}
