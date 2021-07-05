package com.example.myproject22.Presenter.Interface;

import com.example.myproject22.Model.UserClass;

public interface UserInterface {

    //region Set Init, get Bundle
    public void SetInit();
    public void GetBundleData();
    public void LoadAnimations();
    //endregion

    //region Handle Button Click
    public void BtnUpdateUser();
    public void BtnPassword();
    public void BtnMap();
    public void BtnLogOut();
    //endregion

    //region Fetch User
    public void FetchUserFromServer();
    //endregion

    //region Loading
    public void LoadUser(UserClass userClass);
    public void LoadDataToLayout();
    //endregion

    //region Set dialog for map
    public void ShowCustomDialog();
    public void ShowGPSDisabledAlertToUser();
    //endregion
}
