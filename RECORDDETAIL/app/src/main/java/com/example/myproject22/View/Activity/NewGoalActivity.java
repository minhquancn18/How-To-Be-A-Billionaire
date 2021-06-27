package com.example.myproject22.View.Activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
import com.example.myproject22.Presenter.Interface.NewGoalInterface;
import com.example.myproject22.Presenter.Presenter.NewGoalPresenter;
import com.example.myproject22.R;
import com.example.myproject22.Util.FormatImage;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import me.abhinay.input.CurrencyEditText;

public class NewGoalActivity extends AppCompatActivity implements NewGoalInterface {

    //region UI COMPONENTS
    EditText etGoalDescription;
    EditText etGoalName;
    CurrencyEditText etGoalMoney;
    ImageView ivGoal;
    Uri image_uri;
    Button btnGoalDone;
    ImageView ivGifLoading;
    //endregion


    //region GLOBAL VARIABLES
    public static final String REQUEST_ADD_NEW = "add";
    private static final int id_user = 1;
    private static final int GALLERY_REQUEST = 11;
    private NewGoalPresenter mNewGoalPresenter;
    //endregion


    //region INIT FUNCTION
    @Override
    public void SetUpBeforeInit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public void InitView() {
        etGoalDescription = findViewById(R.id.etGoalDesc);
        etGoalName = findViewById(R.id.etGoalName);
        etGoalMoney = findViewById(R.id.etGoalMoney);
        ivGoal = findViewById(R.id.ivGoal);
        btnGoalDone = findViewById(R.id.btnGoalDone);
        ivGifLoading = findViewById(R.id.ivGifLoading);
        etGoalMoney.setDecimals(false);

        Glide.with(this).load(R.drawable.audio_play_git).into(ivGifLoading);
    }

    @Override
    public void GetBundle() {

    }

    @Override
    public void getMessage() {
        Intent intent = getIntent();
        String msg = intent.getStringExtra(GoalActivity.MESSAGE);
        if(msg != null){
            Snackbar snackbar = Snackbar.make(etGoalDescription, msg, BaseTransientBottomBar.LENGTH_LONG);
            snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            snackbar.show();
        }
    }

    //endregion


    //region DEFAULT FUNCTION
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new_goal);

        mNewGoalPresenter = new NewGoalPresenter(this);
        mNewGoalPresenter.SetUp();
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


    //region IMAGE HANDLE
    @Override
    public void ChooseImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }
    //endregion


    //region ANIMATIONS
    @Override
    public void DisableViews() {
        btnGoalDone.setEnabled(false);
        ivGifLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void EnableViews() {
        btnGoalDone.setEnabled(true);
        ivGifLoading.setVisibility(View.INVISIBLE);
    }
    //endregion


    //region BUTTON CLICK HANDLE
    public void onChooseImageClick(View view) {
        ChooseImage();
    }

    @Override
    public void onBackPressed() {
        PressBack();
    }


    @Override
    public void PressBack() {
        setResult(GoalActivity.RESULT_ADD_FAILED);
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }


    public void btnGoalDoneClicked(View view) {
        mNewGoalPresenter.AddGoalToServer(this, view);
    }
    //endregion


    //region DATABASE HANDLE
    @Override
    public void AddNewGoalToServer() {
        StringRequest request = new StringRequest(Request.Method.POST,
                ConnectionClass.urlString + "insertGoal22.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // if success -> back to last activity -> show snackbar
                if (response.equals("1")) {
                    Intent data = new Intent();
                    setResult(GoalActivity.RESULT_ADD_OK);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                } else {
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

                Map<String, String> map = new HashMap<>();
                // get Image
                BitmapDrawable tem = ((BitmapDrawable) ivGoal.getDrawable());

                // format day for date_add
                SimpleDateFormat formatter = new SimpleDateFormat("h:mm a 'ngày' dd.MM.yyyy");
                String dayStr = formatter.format(new Date());

                // goalname and goalmoney are required
                if (!etGoalName.getText().toString().isEmpty())
                    map.put("name", String.valueOf(etGoalName.getText()));
                if (!etGoalMoney.getText().toString().isEmpty())
                    map.put("moneygoal", String.valueOf(etGoalMoney.getCleanDoubleValue()));


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
