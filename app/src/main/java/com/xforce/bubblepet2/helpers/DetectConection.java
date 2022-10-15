package com.xforce.bubblepet2.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class DetectConection {
    Context context;
    private WifiManager wifi;

    private DetectConection(Context context){
        this.context = context;
    }

    public static DetectConection build(Context context){return new DetectConection(context);}

    public boolean detect(){
        try {
            //Conectado
            if (wifi.isWifiEnabled()){
                Toast.makeText(context, "Conectado", Toast.LENGTH_SHORT).show();
                return true;
            }else {
                Toast.makeText(context, "Desconectado", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isOnline(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if ((networkInfo.isAvailable()) && (networkInfo.isConnected())) {
            Toast.makeText(context, "Conectado", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Desconectado", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void networkStateReceiver(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("MIAPP", "Estás online");

            Log.d("MIAPP", " Estado actual: " + networkInfo.getState());

            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // Estas conectado a un Wi-Fi

                Log.d("MIAPP", " Nombre red Wi-Fi: " + networkInfo.getExtraInfo());
            }

        } else {
            Log.d("MIAPP", "Estás offline");
        }
    };

}