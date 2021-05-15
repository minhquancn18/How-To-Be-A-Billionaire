package com.example.myproject22;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myproject22.View.AddingActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn_Adding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Adding = (Button) findViewById(R.id.btn_adding);
        btn_Adding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddingActivity.class);
                v.getContext().startActivity(intent);
                finish();
            }
        });
    }
}