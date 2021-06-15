package com.example.myproject22.Presenter;

import com.example.myproject22.Model.UserClass;

public interface DashboardInterface {

    //Gọi id từ layout
    public void SetInit();

    //Set mẫu để test
    public void SetData();

    //Đưa dữ liệu từ database vào Dashboard
    public void SetComponent(UserClass userClass);

    //Load User
    public void LoadUser();

}
