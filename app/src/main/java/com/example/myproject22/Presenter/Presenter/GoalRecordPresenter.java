package com.example.myproject22.Presenter.Presenter;

import com.example.myproject22.Presenter.Interface.GoalRecordInterface;

public class GoalRecordPresenter {
    GoalRecordInterface mGoalRecordInterface;

    public GoalRecordPresenter(GoalRecordInterface mGoalRecordInterface) {
        this.mGoalRecordInterface = mGoalRecordInterface;
    }

    public void LoadData() {
        mGoalRecordInterface.GetBundle();
        mGoalRecordInterface.SetUpBeforeInit();
        mGoalRecordInterface.InitViews();
        mGoalRecordInterface.FetchGoalRecordFromServer();

    }
}
