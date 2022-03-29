package com.example.githubrepoviewer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkChangeReceiver";

    @Override
    public void onReceive(Context context,  Intent intent) {

        if(isConnected(context)) {

        }

    }

    private boolean isConnected(Context context) {
        try {


            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo == null) {
                return false;
            } else {
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
