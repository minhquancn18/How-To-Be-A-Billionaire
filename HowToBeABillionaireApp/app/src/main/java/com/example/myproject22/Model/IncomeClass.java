package com.example.myproject22.Model;

public class IncomeClass {

    //region Component
    private String category;
    private Double money;
    //endregion

    //region Constructor
    public IncomeClass(String category, Double money) {
        this.category = category;
        this.money = money;
    }
    //endregion

    //region Get and Set
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
    //endregion

}
