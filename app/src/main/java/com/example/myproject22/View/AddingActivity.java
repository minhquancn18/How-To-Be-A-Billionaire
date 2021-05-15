package com.example.myproject22.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myproject22.Model.MoneyCategoryClass;
import com.example.myproject22.Model.SavingDatabaseHelper;
import com.example.myproject22.Presenter.AddingMoneyInterface;
import com.example.myproject22.Presenter.AddingMoneyPresentent;
import com.example.myproject22.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class AddingActivity extends AppCompatActivity implements AddingMoneyInterface {

    //Tạo SQLiteHelper để kết nối tới cơ sở dữ liệu
    private SavingDatabaseHelper db = new SavingDatabaseHelper(this, null, null, 1);

    //Khởi tạo các component để thực hiện event
    private TextInputLayout moneyTextField;
    private TextInputLayout descriptionTextField;
    private Button btnSaving;
    private MaterialButton btnChooseType;
    private AddingMoneyPresentent addingMoneyPresentent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);

        //Liên kết tới các component bên layout
        moneyTextField = findViewById(R.id.moneyTextField);
        descriptionTextField = findViewById(R.id.descriptiontextField);
        btnChooseType = findViewById(R.id.btn_ChossingType);
        btnSaving = findViewById(R.id.btn_saving);
        addingMoneyPresentent = new AddingMoneyPresentent(this);

        //Kết nối từ Adding event tới AddingTypeActivity để chọn loại thu hoặc chi
        btnChooseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CategoryActivity.class);
                v.getContext().startActivity(intent);
                //Xóa activity này khi chuyển qua AddingTypeActivity
                AddingActivity.this.finish();
            }
        });

        //Bắt Intent nhận được thông qua AddingTypeActivity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //Kiểm tra dữ liệu có bắt được hay chưa
        MoneyCategoryClass moneyCategoryClass = addingMoneyPresentent.GetIntentData(bundle);
        btnChooseType.setText(moneyCategoryClass.getNameType());
        btnChooseType.setIconResource(moneyCategoryClass.getImageResourceID());

        //Thực hiện event save chi tiền tiền thu-chi vào database và trở vể giao diện chính nếu lưu thành công
        btnSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sMoney = moneyTextField.getEditText().getText().toString(); //Lấy dữ liệu từ money text, khác null
                String sDescription = descriptionTextField.getEditText().getText().toString().trim(); //lấy dữ liệu từ description text, null cũng ko sao
                //Kiểm tra xem dữ liệu đưa vào có bị lỗi gì hay ko
                Boolean bool = addingMoneyPresentent.AddMoneyIntoDB(sMoney, sDescription, moneyCategoryClass, db);
                //Nếu lưu thành công thì thoát ra ngoài
                if (bool == true) {
                    finish();
                }
            }
        });
    }

    @Override
    public void AddingCategoryFail() {
        Toast.makeText(getApplicationContext(), "Lỗi rất nhiều lỗi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetBuddleSuccessful() {
        Toast.makeText(getApplicationContext(), "Thêm thông tin loại thu chi thành công.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetNoMoneyData() {
        Toast.makeText(getApplicationContext(), "Nhập thông tin về tiền!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetNoCategoryData() {
        Toast.makeText(getApplicationContext(), "Chọn loại thu chi.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetAddSuccessful() {
        Toast.makeText(getApplicationContext(), "Thêm tiền thu thành công!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetSpendSuccessful() {
        Toast.makeText(getApplicationContext(), "Thêm tiền chi thành công!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetDataFail() {
        Toast.makeText(getApplicationContext(), "Thêm dữ liệu không thành công", Toast.LENGTH_SHORT).show();
    }
}

