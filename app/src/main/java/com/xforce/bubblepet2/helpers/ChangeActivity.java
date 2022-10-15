package com.xforce.bubblepet2.helpers;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ChangeActivity extends AppCompatActivity {

    //Variables-----------------------
    private final Context context;
    private final Class<?> cls;


    //Privates-----------------------------------

    private ChangeActivity(@NonNull Context _context, @NonNull Class<?> cls){
        this.context = _context;
        this.cls = cls;
    }

    //Public static-----------------------------------

    public static ChangeActivity build(@NonNull Context context, @NonNull Class<?> cls){
        return new ChangeActivity(context,cls);
    }

    //Public void------------------------------------

    public void start(){
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        finish();
    }


}
