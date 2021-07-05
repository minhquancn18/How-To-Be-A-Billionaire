package com.example.myproject22.Presenter.Presenter;

import com.example.myproject22.Presenter.Interface.GoalInterface;

public class GoalPresenter {
    GoalInterface mGoalInterface;

    public GoalPresenter(GoalInterface mGoalInterface) {
        this.mGoalInterface = mGoalInterface;
    }

    public void LoadData() {
        if (mGoalInterface.HasInternet() && mGoalInterface.GetBundleData()) {
            mGoalInterface.LoadAnimations();
            mGoalInterface.FetchGoalFromServer();
        }
        if (!mGoalInterface.HasInternet()) {
            mGoalInterface.ForNoInternet();
        }
    }

    public void SetUp() {
        mGoalInterface.AskForPermission();
        mGoalInterface.setUIBeforeInit();
        mGoalInterface.InitViews();
    }

    public void LoadDataOnRestart(boolean needToReLoad) {
        if (needToReLoad && mGoalInterface.HasInternet()) {
            mGoalInterface.LoadAnimations();
            mGoalInterface.FetchGoalFromServer();
        } else needToReLoad = true;
    }

}
