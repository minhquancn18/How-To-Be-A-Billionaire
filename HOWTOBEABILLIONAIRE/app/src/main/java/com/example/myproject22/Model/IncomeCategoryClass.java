package com.example.myproject22.Model;

public class IncomeCategoryClass {
    private int ID_INCOME;
    private String NAME;
    private String IMAGE;
    private int CHILD;
    private String PARENT_NAME;

    public int Get_ID_INCOME(){return this.ID_INCOME;}

    public String Get_NAME(){return this.NAME;}
    public void Set_NAME(String name){this.NAME = name;}

    public String Get_IMAGE(){return this.IMAGE;}
    public void Set_IMAGE(String image){this.IMAGE = image;}

    public int Get_CHILD(){return this.CHILD;}
    public void Set_CHILD(int child){this.CHILD = child;}

    public String Get_PARENT_NAME(){return this.PARENT_NAME;}
    public void Set_PARENT_NAME(String parent_name){this.PARENT_NAME = parent_name;}
}
