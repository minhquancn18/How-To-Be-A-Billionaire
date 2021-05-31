package com.example.myproject22.Model;


import com.example.myproject22.R;

import java.util.ArrayList;
import java.util.HashMap;

//Hàm này kế thừa từ MoneyType
public class AddingCategoryClass extends MoneyCategoryClass {
    public AddingCategoryClass(String name, int bool, int ID) {
        super(name, bool, ID);
    }

    public void setAdding() {
        boolType = 1;
    }

    //Tạo ra list thư mục cha
    public static ArrayList<AddingCategoryClass> addingtypes = new ArrayList<AddingCategoryClass>() {{
        add(new AddingCategoryClass("Tiền Thưởng", 1, R.drawable.bonus));
        add(new AddingCategoryClass("Lương", 1, R.drawable.salary));
        add(new AddingCategoryClass("Bán đồ", 1, R.drawable.sale));
        add(new AddingCategoryClass("Thu nhập khác", 1, R.drawable.other));
    }};

    //Tạo ra list thư mục con
    public static HashMap<String, ArrayList<AddingCategoryClass>> getData() {

        HashMap<String, ArrayList<AddingCategoryClass>> childAddingTypes = new HashMap<String, ArrayList<AddingCategoryClass>>();

        ArrayList<AddingCategoryClass> bonus = new ArrayList<AddingCategoryClass>();
        ArrayList<AddingCategoryClass> salary = new ArrayList<AddingCategoryClass>();
        ArrayList<AddingCategoryClass> sale = new ArrayList<AddingCategoryClass>();
        ArrayList<AddingCategoryClass> other = new ArrayList<AddingCategoryClass>();
        ArrayList<AddingCategoryClass> plus = new ArrayList<AddingCategoryClass>();

        childAddingTypes.put("Tiền Thưởng", bonus);
        childAddingTypes.put("Lương", salary);
        childAddingTypes.put("Bán đồ", sale);
        childAddingTypes.put("Thu nhập khác", other);

        return childAddingTypes;
    }

    ;

    public static void SetArrayList(ArrayList<AddingCategoryClass> addTypes) {
        addingtypes.addAll(addTypes);
    }

    public AddingCategoryClass(int id,String nameType, int boolType, byte[] imageResource, ArrayList<MoneyCategoryClass> listChild )
    {
        super(id, nameType, boolType, imageResource, listChild);
    }

    public AddingCategoryClass(int id,String nameType, int boolType, byte[] imageResource)
    {
        super(id, nameType, boolType, imageResource);
    }
}
