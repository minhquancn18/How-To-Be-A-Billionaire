package com.example.myproject22.Model;


import com.example.myproject22.R;

import java.util.ArrayList;
import java.util.HashMap;

//Hàm này kế thừa từ moneyType
public class SpendingCategoryClass extends MoneyCategoryClass {
    public SpendingCategoryClass(String name, int bool, int ID) {
        super(name, bool, ID);
    }

    public void setSpending() {
        boolType = -1;
    }

    //Tạo ra list thư mục cha
    public static ArrayList<SpendingCategoryClass> spendingTypes = new ArrayList<SpendingCategoryClass>() {{
        add(new SpendingCategoryClass("Ăn uống", -1, R.drawable.eating));
        add(new SpendingCategoryClass("Hóa đơn", -1, R.drawable.bill));
        add(new SpendingCategoryClass("Di chuyển", -1, R.drawable.transport));
        add(new SpendingCategoryClass("Mua sắm", -1, R.drawable.shopping));
        add(new SpendingCategoryClass("Bạn bè", -1, R.drawable.friends));
        add(new SpendingCategoryClass("Giải trí", -1, R.drawable.entertainment));
        add(new SpendingCategoryClass("Sức khỏe", -1, R.drawable.health));
        add(new SpendingCategoryClass("Gia đình", -1, R.drawable.home));
        add(new SpendingCategoryClass("Giáo dục", -1, R.drawable.education));
        add(new SpendingCategoryClass("Chi tiêu khác", -1, R.drawable.other));
    }};

    //Tạo ra list thư mục con
    public static HashMap<String, ArrayList<SpendingCategoryClass>> getData() {

        HashMap<String, ArrayList<SpendingCategoryClass>> childSpendingTypes = new HashMap<String, ArrayList<SpendingCategoryClass>>();

        ArrayList<SpendingCategoryClass> eating = new ArrayList<SpendingCategoryClass>() {{
            add(new SpendingCategoryClass("Nhà hàng", -1, R.drawable.restaurant));
            add(new SpendingCategoryClass("Cà phê", -1, R.drawable.coffee));
            add(new SpendingCategoryClass("Thức ăn", -1, R.drawable.food));
        }};

        ArrayList<SpendingCategoryClass> bill = new ArrayList<SpendingCategoryClass>() {{
            add(new SpendingCategoryClass("Hóa đơn điện", -1, R.drawable.electricity_bill));
            add(new SpendingCategoryClass("Hóa đơn nước", -1, R.drawable.water_bill));
            add(new SpendingCategoryClass("Hóa đơn mạng", -1, R.drawable.network_bill));
            add(new SpendingCategoryClass("Hóa đơn gas", -1, R.drawable.gas_bill));
        }};

        ArrayList<SpendingCategoryClass> transport = new ArrayList<SpendingCategoryClass>() {{
            add(new SpendingCategoryClass("Taxi", -1, R.drawable.taxi));
            add(new SpendingCategoryClass("Gửi xe", -1, R.drawable.parking));
            add(new SpendingCategoryClass("Xăng dầu", -1, R.drawable.gas));
            add(new SpendingCategoryClass("Bảo dưỡng", -1, R.drawable.maintenance));
        }};

        ArrayList<SpendingCategoryClass> shopping = new ArrayList<SpendingCategoryClass>() {{
            add(new SpendingCategoryClass("Quần áo", -1, R.drawable.clothes));
            add(new SpendingCategoryClass("Giày dép", -1, R.drawable.shoes));
            add(new SpendingCategoryClass("Phụ kiện", -1, R.drawable.tools));
            add(new SpendingCategoryClass("Thiết bị điện tử", -1, R.drawable.e_device));
        }};

        ArrayList<SpendingCategoryClass> friends = new ArrayList<SpendingCategoryClass>() {{
            add(new SpendingCategoryClass("Cưới hỏi", -1, R.drawable.wedding));
            add(new SpendingCategoryClass("Tang lễ", -1, R.drawable.funeral));
            add(new SpendingCategoryClass("Từ thiện", -1, R.drawable.charity));
            add(new SpendingCategoryClass("Người yêu", -1, R.drawable.lover));
            add(new SpendingCategoryClass("Quà cáp", -1, R.drawable.gift));
        }};

        ArrayList<SpendingCategoryClass> entertainment = new ArrayList<SpendingCategoryClass>() {{
            add(new SpendingCategoryClass("Phim ảnh", -1, R.drawable.film));
            add(new SpendingCategoryClass("Trò chơi", -1, R.drawable.game));
            add(new SpendingCategoryClass("Du lịch", -1, R.drawable.travel));
            add(new SpendingCategoryClass("Thể thao", -1, R.drawable.sports));
        }};

        ArrayList<SpendingCategoryClass> health = new ArrayList<SpendingCategoryClass>() {{
            add(new SpendingCategoryClass("Khám chữa bệnh", -1, R.drawable.healthcare));
            add(new SpendingCategoryClass("Thuốc", -1, R.drawable.medicine));
            add(new SpendingCategoryClass("Chăm sóc cá nhân", -1, R.drawable.personal_care));
            add(new SpendingCategoryClass("Bảo hiểm", -1, R.drawable.insurance));
        }};

        ArrayList<SpendingCategoryClass> home = new ArrayList<SpendingCategoryClass>() {{
            add(new SpendingCategoryClass("Con cái", -1, R.drawable.children));
            add(new SpendingCategoryClass("Sửa chữa nhà cửa", -1, R.drawable.home_repair));
            add(new SpendingCategoryClass("Dịch vụ gia đình", -1, R.drawable.family_service));
            add(new SpendingCategoryClass("Thú cưng", -1, R.drawable.pet));
        }};

        ArrayList<SpendingCategoryClass> education = new ArrayList<SpendingCategoryClass>() {{
            add(new SpendingCategoryClass("Sách", -1, R.drawable.books));
            add(new SpendingCategoryClass("Phần mềm giáo dục", -1, R.drawable.education_software));
            add(new SpendingCategoryClass("Khóa học", -1, R.drawable.course));
        }};

        ArrayList<SpendingCategoryClass> other = new ArrayList<SpendingCategoryClass>();
        ArrayList<SpendingCategoryClass> plus = new ArrayList<SpendingCategoryClass>();

        childSpendingTypes.put("Ăn uống", eating);
        childSpendingTypes.put("Hóa đơn", bill);
        childSpendingTypes.put("Di chuyển", transport);
        childSpendingTypes.put("Mua sắm", shopping);
        childSpendingTypes.put("Bạn bè", friends);
        childSpendingTypes.put("Giải trí", entertainment);
        childSpendingTypes.put("Sức khỏe", health);
        childSpendingTypes.put("Gia đình", home);
        childSpendingTypes.put("Giáo dục", education);
        childSpendingTypes.put("Chi tiêu khác", other);

        return childSpendingTypes;
    }

    ;

    public static void SetArrayList(ArrayList<SpendingCategoryClass> spendTypes) {
        spendingTypes.addAll(spendTypes);
    }
}
