package com.example.myproject22.View.Service;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.myproject22.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class Network_receiver extends BroadcastReceiver {
    private Boolean isOpen = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        Dialog dialog = new Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_inconnect_network);

        Button btn_retry = dialog.findViewById(R.id.btn_retry);
        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
                    if(isNetworkAvailable(v.getContext())){
                        dialog.dismiss();
                        ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                    }
                }
            }
        });


        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            if(!isNetworkAvailable(context)){
                dialog.show();
                isOpen = true;
            }
            else{
                if(isOpen){
                    Toast.makeText(context, "Đã kết nối mạng thành công. Vui lòng thủ lại", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager == null){
            return  false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Network network = connectivityManager.getActiveNetwork();

            if(network == null){
                return false;
            }

            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
            return capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        }
        else{
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            return networkInfo != null && networkInfo.isConnected();
        }
    }
}
