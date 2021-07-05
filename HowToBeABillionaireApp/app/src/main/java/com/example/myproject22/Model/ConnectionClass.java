package com.example.myproject22.Model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionClass {

    public static String urlString = "https://howtobeabillionaire.000webhostapp.com/";

    public static String urlImage = "https://howtobeabillionaire.000webhostapp.com/Images/";

    public static String urlImageCategory = "https://howtobeabillionaire.000webhostapp.com/ImagesCategory/";

    public static String urlAudio = "https://howtobeabillionaire.000webhostapp.com/Audios/";

    public static String urlImageGoal = "https://howtobeabillionaire.000webhostapp.com/ImagesGoal/";

    public static boolean hasInternet (Context context){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
