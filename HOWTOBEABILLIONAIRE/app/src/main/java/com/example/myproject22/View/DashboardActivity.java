package com.example.myproject22.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myproject22.Model.CategoryClass;
import com.example.myproject22.Model.ConnectionClass;
import com.example.myproject22.Model.UserClass;
import com.example.myproject22.Presenter.DashboardInterface;
import com.example.myproject22.Presenter.DashboardPresenter;
import com.example.myproject22.R;
import com.example.myproject22.Util.CategoryAdapter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.alterac.blurkit.BlurKit;
import io.alterac.blurkit.BlurLayout;

import static com.example.myproject22.Model.ConnectionClass.urlString;

public class DashboardActivity extends AppCompatActivity implements DashboardInterface {

    private int id_user;
    private int id_income;
    private int id_outcome;

    BlurLayout blurLayout;

    private TextView tvName;
    private TextView tvDate;
    private TextView tvDateUse;
    private TextView tvMoney;
    private CircleImageView imageUser;
    private ImageButton btnAddRecord;
    private ImageButton btnGraph;
    private ImageButton btnMaybe;
    private UserClass userClass;

    //Presenter
    private DashboardPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        BlurKit.init(this);

        presenter = new DashboardPresenter(this);
        presenter.setData();
        presenter.setInit();


        btnAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddingClick();
            }
        });


        MotionLayout a= findViewById(R.id.motion1);
        a.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                if(i == R.id.end1){
                    btnAddingClick();
                }
                if(i == R.id.end){
                    Intent intent = new Intent(DashboardActivity.this, SavingActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void btnAddingClick(){
        Intent intent = new Intent(DashboardActivity.this, AddingActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("ID_USER", id_user);
        bundle.putInt("ID_INCOME", id_income);
        bundle.putInt("ID_OUTCOME", id_outcome);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void SetData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle == null){
            id_user = 1;
            id_income = 1;
            id_outcome= 1;
        }
        else{
            id_user = bundle.getInt("ID_USER");
            id_income = bundle.getInt("ID_INCOME");
            id_outcome = bundle.getInt("ID_OUTCOME");
        }

    }

    @Override
    public void SetComponent(UserClass classUser) {
        String name = classUser.getFULLNAME();
        tvName.setText(name);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        tvDate.setText(currentDate);

        //Get date and calculate date to set on TextView UseDate
        String datestart = classUser.getDateStart();
        String[] splitDate = datestart.split(" ");
        if(splitDate.length > 0){
            String fromdate = splitDate[0];
            String[] splitFromdate = fromdate.split("-");
            Date datefrom = new Date(Integer.parseInt(splitFromdate[0]), Integer.parseInt(splitFromdate[1]),Integer.parseInt(splitFromdate[2]));

            String todate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String[] splitTodate = todate.split("-");
            Date dateto = new Date(Integer.parseInt(splitTodate[0]),Integer.parseInt(splitTodate[1]),Integer.parseInt(splitTodate[2]));
            int diffdate = CalculateDateUse(datefrom,dateto);
            tvDateUse.setText(String.valueOf(diffdate));
        }

        Double money = classUser.getINCOME();
        tvMoney.setText(String.valueOf(money));

        String url = classUser.getUSERIMAGE();
        if(url == null){

        }else{
            Glide.with(DashboardActivity.this).load(url).into(imageUser);
        }
    }

    @Override
    public void LoadUser() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(user_runnable);
    }

    @Override
    public void SetInit() {
        blurLayout = findViewById(R.id.blurLayout);
        tvName = findViewById(R.id.textView);
        tvDate = findViewById(R.id.textView3);
        tvDateUse = findViewById(R.id.textView5);
        tvMoney = findViewById(R.id.textView7);
        imageUser = findViewById(R.id.circleImageView);
        btnAddRecord = findViewById(R.id.btnNewRecord);
        btnMaybe = findViewById(R.id.btnMaybe);
        btnGraph = findViewById(R.id.btnGraph);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadUser();
    }

    Runnable user_runnable = new Runnable() {
        @Override
        public void run() {
            FetchUserFromServer();
        }
    };

    public void FetchUserFromServer(){
        String url_string = urlString + "getUser.php";
        StringRequest request = new StringRequest(Request.Method.POST, url_string, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(DashboardActivity.this,response,Toast.LENGTH_SHORT).show();
                    String success = jsonObject.getString("success");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            String name = object.getString("FULLNAME");
                            String datestart = object.getString("DATESTART");
                            String income = object.getString("INCOME");
                            String imageuser = object.getString("USERIMAGE");
                            if(imageuser.equals("null")){
                                userClass = new UserClass(name,datestart,Double.parseDouble(income));
                                SetComponent(userClass);
                            }
                            else{
                                String url_image = urlString + "ImagesUser/" + imageuser;
                                userClass = new UserClass(name, datestart, Double.parseDouble(income), url_image);
                                SetComponent(userClass);
                            }

                            blurLayout.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DashboardActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(id_user));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
        requestQueue.add(request);
    }

    public static int CalculateDateUse(Date fromDate, Date toDate){
        if(fromDate==null||toDate==null)
            return 0;
        return (int)( (toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
    }
}