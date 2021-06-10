package com.example.myproject22.Model;

import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ConnectionClass {
    public static String urlString = "https://howtobebillionaire.000webhostapp.com/";

    public static void insertIncomeCategory(String name, byte[] image, int child, String parent) {
        //Start ProgressBar first (Set visibility VISIBLE)
        String simage = Base64.encodeToString(image, Base64.DEFAULT);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[4];
                field[0] = "name";
                field[1] = "image";
                field[2] = "child";
                field[3] = "parent";
                //Creating array for data
                String[] data = new String[4];
                data[0] = name;
                data[1] = simage;
                data[2] = String.valueOf(child);
                data[3] = parent;
                PutData putData = new PutData(urlString + "insertIncomeCategory.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        Log.i("PutData", result);
                        if (result.equals("Add new income category Success"))
                            Log.i("PutData", result);
                    }
                }
                //End Write and Read data with URL
            }
        });
    }

    public static ArrayList<IncomeCategoryClass> getIncomeCategory() {
        //Start ProgressBar first (Set visibility VISIBLE)
        ArrayList<IncomeCategoryClass> arrayList = new ArrayList<>();

        FetchData fetchData = new FetchData(urlString + "getIncomeCategory.php");
        if (fetchData.startFetch()) {
            if (fetchData.onComplete()) {
                String result = fetchData.getResult();
                Gson gson = new Gson();
                IncomeCategoryClass[] IncomeCategory = gson.fromJson(result, (Type) IncomeCategoryClass[].class);

                for(int i = 0; i < IncomeCategory.length; i++){
                    arrayList.add(IncomeCategory[i]);
                }
                //End ProgressBar (Set visibility to GONE)
                Log.i("FetchData", result);
            }
        }
        return arrayList;
    }
}
