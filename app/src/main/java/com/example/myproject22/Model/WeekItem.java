package com.example.myproject22.Model;

public class WeekItem {

    //region Component
    private int num;
    private String name;
    private String datestart;
    private String dateend;
    //endregion

    //region Constructor
    public WeekItem(int num, String name, String datestart, String dateend) {
        this.num = num;
        this.name = name;
        this.datestart = datestart;
        this.dateend = dateend;
    }
    //endregion

    //region Get and Set

    public int getNum(){
        return num;
    }

    public void setNum(int num){
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatestart() {
        return datestart;
    }

    public void setDatestart(String datestart) {
        this.datestart = datestart;
    }

    public String getDateend() {
        return dateend;
    }

    public void setDateend(String dateend) {
        this.dateend = dateend;
    }
    //endregion

}
