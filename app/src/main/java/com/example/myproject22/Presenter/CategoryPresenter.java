package com.example.myproject22.Presenter;

import android.content.Context;

public class CategoryPresenter {
    private CategoryMoneyInterface categoryMoneyInterface;
    private Context context;

    public CategoryPresenter(CategoryMoneyInterface categoryMoneyInterface, Context context){
        this.categoryMoneyInterface = categoryMoneyInterface;
        this.context = context;
    }


}
