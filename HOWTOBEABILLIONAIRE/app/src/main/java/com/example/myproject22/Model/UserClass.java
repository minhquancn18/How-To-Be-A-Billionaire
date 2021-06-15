package com.example.myproject22.Model;

public class UserClass {
    private int ID_USER;
    private String USERNAME;
    private String PASSWORD;
    private String EMAIL;
    private String FULLNAME;
    private String DateStart;
    private Double INCOME;
    private String USERIMAGE;

    public UserClass(String FULLNAME, String dateStart, Double INCOME, String USERIMAGE) {
        this.FULLNAME = FULLNAME;
        DateStart = dateStart;
        this.INCOME = INCOME;
        this.USERIMAGE = USERIMAGE;
    }

    public UserClass(int ID_USER, String USERNAME, String PASSWORD, String EMAIL) {
        this.ID_USER = ID_USER;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.EMAIL = EMAIL;
    }

    public UserClass(String USERNAME, String PASSWORD) {
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }

    public UserClass(String FULLNAME, String dateStart, Double INCOME) {
        this.FULLNAME = FULLNAME;
        DateStart = dateStart;
        this.INCOME = INCOME;
    }

    public int getID_USER() {
        return ID_USER;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getFULLNAME() {
        return FULLNAME;
    }

    public String getDateStart() {
        return DateStart;
    }

    public Double getINCOME() {
        return INCOME;
    }

    public String getUSERIMAGE() {
        return USERIMAGE;
    }

    public void setID_USER(int ID_USER) {
        this.ID_USER = ID_USER;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public void setFULLNAME(String FULLNAME) {
        this.FULLNAME = FULLNAME;
    }

    public void setDateStart(String dateStart) {
        DateStart = dateStart;
    }

    public void setINCOME(Double INCOME) {
        this.INCOME = INCOME;
    }

    public void setUSERIMAGE(String USERIMAGE) {
        this.USERIMAGE = USERIMAGE;
    }
}
