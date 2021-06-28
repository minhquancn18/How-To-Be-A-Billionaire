package com.example.myproject22.Presenter.Presenter;

import com.example.myproject22.Model.UserClass;
import com.example.myproject22.Presenter.Interface.UserInterface;

public class UserPresenter {
    UserInterface anInterface;

    public UserPresenter(UserInterface anInterface) {
        this.anInterface = anInterface;
    }

    //region Set Init, get bundle

    public void setInit() {
        anInterface.SetInit();
    }

    public void getBundleData() {
        anInterface.GetBundleData();
    }

    //endregion

    //region Handle Button Click

    public void btnUpdateUser() {
        anInterface.BtnUpdateUser();
    }

    public void btnPassword() {
        anInterface.BtnPassword();
    }

    public void btnMap() {
        anInterface.BtnMap();
    }

    public void btnLogout(){
        anInterface.BtnLogOut();
    }

    //endregion

    //region Fetch User

    public void fetchUserFromServer(){
        anInterface.FetchUserFromServer();
    }

    //endregion

    //region Loading

    public void loadUser(UserClass userClass){
        anInterface.LoadUser(userClass);
    }

    public void loadDataToLayout() {
        anInterface.LoadDataToLayout();
    }

    //endregion

    //region Set dialog for map

    public void showCustomDialog() {
        anInterface.ShowCustomDialog();
    }

    public void showGPSDisabledAlertToUser() {
        anInterface.ShowGPSDisabledAlertToUser();
    }

    //endregion
}
