package com.xforce.bubblepet2.helpers;

import android.content.Context;
import android.widget.Toast;

public class msgToast {
    //Variables-----------------------
    private final Context context;


    //Privates-----------------------------------
    private msgToast(Context context){
        this.context = context.getApplicationContext();
    }


    //Public static------------------------------------
    public static msgToast build(Context context){
        return new msgToast(context);
    }


    //Public void------------------------------------
    public void message(String mensaje){
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void message(int message){
        Toast.makeText(context, String.valueOf(message), Toast.LENGTH_SHORT).show();
    }

    public void message(char message){
        Toast.makeText(context, String.valueOf(message), Toast.LENGTH_SHORT).show();
    }

    public void message(float message){
        Toast.makeText(context, String.valueOf(message), Toast.LENGTH_SHORT).show();
    }

    public void message(long mensaje){
        Toast.makeText(context, String.valueOf(mensaje), Toast.LENGTH_SHORT).show();
    }

    public void message(double mensaje){
        Toast.makeText(context, String.valueOf(mensaje), Toast.LENGTH_SHORT).show();
    }

    public void message(boolean mensaje){
        Toast.makeText(context, String.valueOf(mensaje), Toast.LENGTH_SHORT).show();
    }

    public void message(Object mensaje){
        Toast.makeText(context, String.valueOf(mensaje), Toast.LENGTH_SHORT).show();
    }

    public void message(char[] mensaje){
        Toast.makeText(context, String.valueOf(mensaje), Toast.LENGTH_SHORT).show();
    }

    public void message(char[] mensaje, int offset, int count){
        Toast.makeText(context, String.valueOf(mensaje,offset,count), Toast.LENGTH_SHORT).show();
    }

}
