package com.example.myproject22.View.Activity;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myproject22.Model.ConnectionClass;
import com.example.myproject22.R;
import com.example.myproject22.Util.FormatImage;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GoalActivity extends AppCompatActivity {

    //region UI COMPONENTS
    EditText etGoalDescription;
    EditText etGoalName;
    EditText etGoalMoney;
    ImageView ivGoal;
    Uri image_uri;
    Button btnGoalDone;
    ImageView ivGifLoading;

    //endregion

    //region GLOBAL VARIABLES
    private static final int id_user = 1;
    private static final int GALLERY_REQUEST = 11;

    //endregion


    //region INIT FUNCTION
    public void InitView() {
        etGoalDescription = findViewById(R.id.etGoalDesc);
        etGoalName = findViewById(R.id.etGoalName);
        etGoalMoney = findViewById(R.id.etGoalMoney);
        ivGoal = findViewById(R.id.ivGoal);
        btnGoalDone  = findViewById(R.id.btnGoalDone);
        ivGifLoading  = findViewById(R.id.ivGifLoading);
        Glide.with(this).load(R.drawable.audio_play_git).into(ivGifLoading);
    }
    //endregion


    //region DEFAULT FUNCTION
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_goal);
        InitView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case GALLERY_REQUEST:
                        Uri selectedImage = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                            ivGoal.setImageBitmap(bitmap);

                        } catch (IOException e) {
                            Log.i("TAG", "Some exception " + e);
                        }
                        break;
                }
            }
            ivGoal.setImageURI(image_uri);
        }
    }
    //endregion


    //region NORMAL FUNCTION

    @SuppressLint("ResourceAsColor")
    public void DisableViews(){
        btnGoalDone.setTextColor(R.color.black);
        btnGoalDone.setEnabled(false);
        ivGifLoading.setVisibility(View.VISIBLE);
    }

    @SuppressLint("ResourceAsColor")
    public void EnableViews(){
        btnGoalDone.setEnabled(true);

        btnGoalDone.setTextColor(ColorStateList.valueOf(R.color.av_yellow));
        Toast.makeText(this, "EEEE", Toast.LENGTH_SHORT).show();
        ivGifLoading.setVisibility(View.INVISIBLE);
    }


    //endregion


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    //region BUTTON CLICK HANDLE
    public void onChooseImageClick(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("MyData", "DSD");
        setResult(SavingActivity.RESULT_FIRST_USER, intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }


    @SuppressLint("ResourceAsColor")
    public void btnGoalDoneClicked(View view) {
        InsertGoalToServer();
        DisableViews();
    }

    //endregion

    //region DATABASE HANDLE
    public void InsertGoalToServer() {
        StringRequest request = new StringRequest(Request.Method.POST,
                ConnectionClass.urlString + "insertGoal22.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // if success -> back to last activity -> show snackbar
                if(response == "1") {
                    Intent data = new Intent();
                    data.putExtra("RESPONSE", response);
                    setResult(SavingActivity.RESULT_ADD_OK, data);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                }
                else{
                    // -> show error right here
                    Snackbar snackbar = Snackbar.make(ivGoal, response, Snackbar.LENGTH_SHORT);
                    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    snackbar.show();

                    EnableViews();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(ivGoal, "ERROR " + error.getMessage(), Snackbar.LENGTH_SHORT);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                // make loading state




                Map<String, String> map = new HashMap<>();
                // get Image
                BitmapDrawable tem = ((BitmapDrawable) ivGoal.getDrawable());

                // format day for date_add
                SimpleDateFormat formatter = new SimpleDateFormat("h:mm a 'ngày' dd.MM.yyyy");
                String dayStr = formatter.format(new Date());

                // goalname and goalmoney are required
                if (!etGoalName.getText().toString().isEmpty())
                    map.put("name", String.valueOf(etGoalName.getText()));
                if (!etGoalMoney.getText().equals(""))
                    map.put("moneygoal", String.valueOf(etGoalMoney.getText()));


                map.put("id_user", String.valueOf(id_user));
                map.put("description", String.valueOf(etGoalDescription.getText()));
                map.put("imageGoal", String.valueOf(FormatImage.convertByteToString(
                        FormatImage.BitmapToByte(tem.getBitmap()))));
                map.put("date_start", dayStr);
                return map;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    //endregion


}
