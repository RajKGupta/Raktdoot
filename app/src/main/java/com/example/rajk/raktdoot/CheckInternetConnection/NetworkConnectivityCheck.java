package com.example.rajk.raktdoot.CheckInternetConnection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.example.rajk.raktdoot.MainActivity;

public class NetworkConnectivityCheck {
    public boolean internetAvailable = false;
    private BroadcastReceiver networkChangeReceiver;

    NetworkConnectivityCheck(){

        this.networkChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int networkState = NetworkUtil.getConnectionStatus(context);
                if(networkState == NetworkUtil.NOT_CONNECTED){
                    internetAvailable = false;
                 //   MainActivity.tvStatus.setText("OFFLINE");

                } else if(networkState == NetworkUtil.MOBILE){
                    internetAvailable = true;
                 //   MainActivity.tvStatus.setText("ONLINE"); // you do something here.
                } else if(networkState == NetworkUtil.WIFI){
                    internetAvailable = true;
                 //   MainActivity.tvStatus.setText("ONLINE"); // you do something here
                }
            }
        };
    }



    public void register(Context context){
        context.registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    public void unregister(Context context){
        context.unregisterReceiver(networkChangeReceiver);
    }
}