package com.hfad.project_1.Model;

public class MoneyInformationClass {
    private Double money;
    private String description;
    private MoneyCategoryClass moneyCategory;

    public MoneyInformationClass(Double money, String description, MoneyCategoryClass moneyCategory)
    {
        this.money = money;
        this.description = description;
        this.moneyCategory = moneyCategory;
    }

    public Double getMoney(){return money;}

    public String getDescription(){return description;}

    public MoneyCategoryClass getMoneyCategory(){return moneyCategory;}

    public Boolean isValidMoney() {return !(money == 0);}

    public Boolean isValidMoneyCategory() {
        Boolean boolName = moneyCategory.getNameType().equals("Chọn loại");
        return boolName;
    }
}
