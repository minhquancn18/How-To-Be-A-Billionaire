package com.example.myproject22.Model;

public class UserClass {

    //region Component
    private int ID_USER;
    private String USERNAME;
    private String EMAIL;
    private String PASSWORD;
    private String FULLNAME;
    private String DATESTART;
    private String IMAGE;
    private double INCOME;
    private double OUTCOME;
    //endregion

    //region Constructor
    public UserClass(String FULLNAME, String DATESTART, String IMAGE) {
        this.FULLNAME = FULLNAME;
        this.DATESTART = DATESTART;
        this.IMAGE = IMAGE;
    }

    public UserClass(String EMAIL ,String FULLNAME, String DATESTART, String IMAGE, double INCOME, double OUTCOME) {
        this.EMAIL = EMAIL;
        this.FULLNAME = FULLNAME;
        this.DATESTART = DATESTART;
        this.IMAGE = IMAGE;
        this.INCOME = INCOME;
        this.OUTCOME = OUTCOME;
    }

    public UserClass(String FULLNAME, String DATESTART, String IMAGE, double INCOME, double OUTCOME) {
        this.FULLNAME = FULLNAME;
        this.DATESTART = DATESTART;
        this.IMAGE = IMAGE;
        this.INCOME = INCOME;
        this.OUTCOME = OUTCOME;
    }

    //endregion

    //region Get and Set
    public int getID_USER() {
        return ID_USER;
    }

    public void setID_USER(int ID_USER) {
        this.ID_USER = ID_USER;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getFULLNAME() {
        return FULLNAME;
    }

    public void setFULLNAME(String FULLNAME) {
        this.FULLNAME = FULLNAME;
    }

    public String getDATESTART() {
        return DATESTART;
    }

    public void setDATESTART(String DATESTART) {
        this.DATESTART = DATESTART;
    }

    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public double getINCOME() {
        return INCOME;
    }

    public void setINCOME(double INCOME) {
        this.INCOME = INCOME;
    }

    public double getOUTCOME() {
        return OUTCOME;
    }

    public void setOUTCOME(double OUTCOME) {
        this.OUTCOME = OUTCOME;
    }
    //endregion

}
