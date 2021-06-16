package com.example.myproject22.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myproject22.Presenter.AddingCategoryInterface;
import com.example.myproject22.Presenter.AddingCategoryPresenter;
import com.example.myproject22.Presenter.AddingMoneyPresentent;
import com.example.myproject22.R;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.example.myproject22.Model.ConnectionClass.urlString;
import static com.example.myproject22.Presenter.AddingMoneyPresentent.convertByteToString;

public class AddingCategoryActivity extends AppCompatActivity implements AddingCategoryInterface {

    EditText etCategory;
    RadioGroup rgCategory;
    RadioButton rbtnIncome;
    RadioButton rbtnOutcome;
    ImageButton btnImage;
    TextView tv;
    ImageButton btnSaving;
    ProgressBar progressBar;

    Bitmap bmImage;
    int id_user;
    int isCategory = 1;

    //Const mặc định để xét permission
    private static final int PERMISSION_IMAGE = 1000;
    private static final int PERMISSION_EXTERNAL_STORAGE = 1001;

    //Presenter
    AddingCategoryPresenter presentent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_adding_category);

        presentent = new AddingCategoryPresenter(this);
        presentent.setInit();
        presentent.getBundleData();

        etCategory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentent.chooseImage();
            }
        });

        rgCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                presentent.checkRadioButtonCategory(group, checkedId);
            }
        });

        btnSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                presentent.savingNewCategory(bmImage);
            }
        });
    }

    @Override
    public void SetInit() {
        etCategory = findViewById(R.id.etCategory);
        rbtnIncome = findViewById(R.id.rbtnIncome);
        rbtnOutcome = findViewById(R.id.rbtnOutcome);
        btnImage = findViewById(R.id.ibtnCategory);
        btnSaving = findViewById(R.id.ibtnSavingCategory);
        tv = findViewById(R.id.tvCategory);
        rgCategory = findViewById(R.id.rgCategory);
        progressBar = findViewById(R.id.pbCategory);
    }

    @Override
    public void ChooseImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddingCategoryActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_picture, null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        ImageButton ivCamera = dialogView.findViewById(R.id.ivCamera);
        ImageButton ivGallery = dialogView.findViewById(R.id.ivGallery);

        AlertDialog dialog = builder.create();
        dialog.show();

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckPermissionImage()) {
                    TakeImageFromCamera();
                    dialog.cancel();
                }
            }
        });

        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckPermissionRead()) {
                    TakeImageFromGallery();
                    dialog.cancel();
                }
            }
        });
    }

    @Override
    public void GetBundleData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        id_user = bundle.getInt("ID_USER");
    }

    @Override
    public void SavingNewCategory() {
        String name = etCategory.getText().toString().trim();
        String image = GetStringImage();
        if(isCategory == 1){
            UploadIncomeCategoryToServer(name, id_user,image);
        }
        else{
            UploadOutcomeCategoryToServer(name,id_user,image);
        }
    }

    @Override
    public Boolean CheckPermissionImage() {
        if (ContextCompat.checkSelfPermission(AddingCategoryActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(AddingCategoryActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(AddingCategoryActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_IMAGE);
            return false;
        }
    }

    @Override
    public Boolean CheckPermissionRead() {
        if (ContextCompat.checkSelfPermission(AddingCategoryActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(AddingCategoryActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_EXTERNAL_STORAGE);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_IMAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TakeImageFromCamera();
                } else {
                    Toast.makeText(AddingCategoryActivity.this, "Camera permission not Granted", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case PERMISSION_EXTERNAL_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TakeImageFromGallery();
                } else {
                    Toast.makeText(AddingCategoryActivity.this, "Read external permission not Granted", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void TakeImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PERMISSION_EXTERNAL_STORAGE);
    }

    private void TakeImageFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PERMISSION_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PERMISSION_EXTERNAL_STORAGE: {
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    btnImage.setScaleX(1.0f);
                    btnImage.setScaleY(1.0f);
                    tv.setVisibility(View.GONE);
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                        bmImage = BitmapFactory.decodeStream(inputStream);
                        btnImage.setImageBitmap(bmImage);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

            case PERMISSION_IMAGE: {
                if (resultCode == RESULT_OK) {
                    btnImage.setScaleX(1.0f);
                    btnImage.setScaleY(1.0f);
                    tv.setVisibility(View.GONE);
                    bmImage = (Bitmap) data.getExtras().get("data");
                    btnImage.setImageBitmap(bmImage);
                }
            }
        }
    }

    public static byte[] encodeTobase64(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public Boolean IsNullImage(Bitmap bitmap) {
        if (bitmap == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void GetNoImage() {
        Toast.makeText(AddingCategoryActivity.this, "Vui lòng chọn hình ảnh cho danh mục.", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public String GetStringImage() {
        String image = "";
        if (presentent.isNullImage(bmImage)) {
            image = "NULL";
        } else {
            byte[] bytes = encodeTobase64(bmImage);
            image = convertByteToString(bytes);
            Log.i("IMAGETEST", image);
        }
        return image;
    }

    @Override
    public void CheckRadioButtonCategory(RadioGroup radioGroup, int idChecked) {
        int checkRadio = radioGroup.getCheckedRadioButtonId();

        if(checkRadio == R.id.rbtnIncome){
            isCategory = 1;
        }
        else{
            isCategory = -1;
        }
    }

    @Override
    public Boolean IsNullName() {
        String name = etCategory.getText().toString().trim();

        if(name.equals("")){
            Toast.makeText(AddingCategoryActivity.this, "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return true;
        }
        else return false;
    }

    public void UploadIncomeCategoryToServer(String name, int id_user, String image){
        StringRequest request = new StringRequest(Request.Method.POST,
                urlString + "insertIncomeCategory.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if(response.equals("Add new income category success")){
                    Toast.makeText(AddingCategoryActivity.this, response, Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(AddingCategoryActivity.this, "Tên danh mục đã tồn tại. Vui lòng chọn tên danh mục khác.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddingCategoryActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(id_user));
                params.put("name", name);
                params.put("image", image);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AddingCategoryActivity.this);
        requestQueue.add(request);
    }

    public void UploadOutcomeCategoryToServer(String name, int id_user, String image){
        StringRequest request = new StringRequest(Request.Method.POST,
                urlString + "insertOutcomeCategory.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if(response.equals("Add new outcome category success")){
                    Toast.makeText(AddingCategoryActivity.this, response, Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(AddingCategoryActivity.this, "Tên danh mục đã tồn tại. Vui lòng chọn tên danh mục khác.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddingCategoryActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(id_user));
                params.put("name", name);
                params.put("image", image);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AddingCategoryActivity.this);
        requestQueue.add(request);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}