package com.xforce.bubblepet2.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.xforce.bubblepet2.MainActivity;
import com.xforce.bubblepet2.R;
import com.xforce.bubblepet2.dataFromDataBase.GetDataUser;

public class DialogShow {

    //Variables-----------------------
    private final Context context;
    int aceptar,cancelar,layoutToDialog;
    Activity activity;
    String title,content,textBtGranted,textBtDenied;
    View aceptarBt = null,cancelarBt = null;
    Dialog dialog;
    boolean useAceptar = false,
            useCancelar = false,
            useLayoutToDialog = false,
            useTitle = false,
            useContent = false,
            usetextBtGranted = false,
            usetextBtDenied = false;

    //Privates-----------------------------------

    private DialogShow(@NonNull Context context){
        this.context = context;
    }

    private DialogShow(@NonNull Context context, @NonNull Activity activity){
        this.context = context;
        this.activity = activity;
    }

    //Public static-----------------------------------

    public static DialogShow build(@NonNull Context context){
        return new DialogShow(context);
    }

    public static DialogShow build(@NonNull Context context, @NonNull Activity activity){
        return new DialogShow(context,activity);
    }

    //Public DialogShow------------------------------------

    public DialogShow grantedBt(int id){
        this.aceptar = id;
        this.useAceptar = true;
        return this;
    }

    public DialogShow deniedBt(int id){
        this.cancelar = id;
        this.useCancelar = true;
        return this;
    }

    public DialogShow setTitle(String title){
        this.title = title;
        this.useTitle = true;
        return this;
    }

    public DialogShow setContent(String content){
        this.content = content;
        this.useContent = true;
        return this;
    }

    public DialogShow setLayout(@LayoutRes int layoutToDialog){
        this.layoutToDialog = layoutToDialog;
        this.useLayoutToDialog = true;
        return this;
    }



    //Public void------------------------------------

    public void show(){
        if (useAceptar && useCancelar && useLayoutToDialog){
            Structure();
        }
    }


    private void Structure(){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(layoutToDialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        aceptarBt = dialog.findViewById(aceptar);
        cancelarBt = dialog.findViewById(cancelar);
        cancelarBt.setOnClickListener(v -> {
            dialog.dismiss();
        });
        aceptarBt.setOnClickListener(v -> {
            TextView p = activity.findViewById(R.id.userEditProfile);
            TextView p2 = activity.findViewById(R.id.userNameEditProfile);
            GetDataUser.DataOnActivity.setData(context)
                    .setChangeActivity(MainActivity.class)
                    .setMessage("Datos actualizados ;D")
                    .setValuePath("PerfilData")
                    .setChild("user", p.getText().toString())
                    .setChild("userName", p2.getText().toString())
                    .setData();
            dialog.dismiss();
        });
        dialog.show();
    }






}
