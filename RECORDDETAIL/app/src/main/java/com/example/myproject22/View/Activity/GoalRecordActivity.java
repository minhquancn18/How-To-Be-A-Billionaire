package com.example.myproject22.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.myproject22.Model.ConnectionClass;
import com.example.myproject22.Model.GoalRecord;
import com.example.myproject22.R;
import com.example.myproject22.Util.FormatImage;
import com.example.myproject22.Util.Formatter;
import com.example.myproject22.Util.GoalItemAdapter;
import com.example.myproject22.View.Activity.GoalActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GoalRecordActivity extends AppCompatActivity {


    RecyclerView goal_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_record);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        goal_recycler = findViewById(R.id.goal_recycler);
        FetchGoalsFromServer();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);

    }
    //region INIT VIEWS

    //endregion


    //region DATABASE HANDLE

    public void FetchGoalsFromServer() {
        StringRequest request = new StringRequest(Request.Method.POST,
                ConnectionClass.urlString + "getFinishGoal.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {
                        ArrayList<GoalRecord> goals = new ArrayList<>(); // prepare for adapter
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            GoalRecord record = new GoalRecord(
                                    object.get("NAME_GOAL").toString(),
                                    Float.parseFloat(object.get("MONEY_GOAL").toString()),
                                    object.get("DATE_START").toString(),
                                    object.get("IMAGE_GOAL").toString()
                            );
                            Log.d("SSSSS", record.getDate_start());
                            goals.add(record);
                        }

                        GoalItemAdapter adapter = new GoalItemAdapter(goals);
                        goal_recycler.setAdapter(adapter);
                        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                        goal_recycler.setLayoutManager(linearLayoutManager);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("iduser", String.valueOf(1));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    //endregion


}






