package com.xforce.bubblepet2.helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.BoolRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.xforce.bubblepet2.Login;
import com.xforce.bubblepet2.MainActivity;
import com.xforce.bubblepet2.R;
import com.xforce.bubblepet2.dataFromDataBase.GetDataUser;

public class DialogShow {

    //Variables-----------------------
    private final Context context;
    int aceptar,cancelar,layoutToDialog;
    Activity activity;
    String title,content,textBtGranted,textBtDenied;
    View aceptarBt = null;
    View cancelarBt = null;
    TextView titleView = null;
    TextView contentView = null;
    Dialog dialog;
    boolean useAceptar = false;
    boolean useCancelar = false;
    boolean useLayoutToDialog = false;
    boolean useTitle = false;
    boolean useContent = false;
    boolean usetextBtGranted = false;
    boolean usetextBtDenied = false;
    boolean useSaveData = false;
    boolean useSignOut = false;
    boolean useDeleteCount = false;

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

    public static DialogShow saveData(@NonNull Context context, @NonNull Activity activity){
        return new DialogShow(context,activity);

    }

    public static DialogShow signOut(@NonNull Context context, @NonNull Activity activity){
        return new DialogShow(context,activity);

    }

    public static DialogShow deleteCount(@NonNull Context context, @NonNull Activity activity){
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
        Structure();
    }

    public void showSaveData(boolean use){
        useSaveData = use;
        if (!useSaveData){
            Log.d("DialogShow","En caso de no usar una accion especial mejor solo utilice: show()");
        }
        else {
            Structure();
        }
    }

    public void showSignOut(boolean use){
        useSignOut = use;
        if (!useSignOut){
            Log.d("DialogShow","En caso de no usar una accion especial mejor solo utilice: show()");
        }
        else {
            Structure();
        }
    }

    public void showDeleteCount(boolean use){
        useDeleteCount = use;
        if (!useDeleteCount){
            Log.d("DialogShow","En caso de no usar una accion especial mejor solo utilice: show()");
        }
        else {
            Structure();
        }
    }

    private void Structure(){
        if (useAceptar && useCancelar && useLayoutToDialog){
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(layoutToDialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            if (useTitle){
                titleView = dialog.findViewById(R.id.dialogBoxTitleTx);
                titleView.setText(title);
            }

            if (useContent){
                contentView = dialog.findViewById(R.id.dialogBoxContentTx);
                contentView.setText(content);
            }

            aceptarBt = dialog.findViewById(aceptar);
            cancelarBt = dialog.findViewById(cancelar);
            cancelarBt.setOnClickListener(v -> dialog.dismiss());

            if (useSaveData){
                aceptarBt.setOnClickListener(v -> {
                    TextView p = activity.findViewById(R.id.userEditProfile);
                    TextView p2 = activity.findViewById(R.id.userNameEditProfile);
                    GetDataUser.DataOnActivity
                            .build(context)
                            .setChangeActivity(MainActivity.class)
                            .setMessage("Datos actualizados ;D")
                            .setValuePath("PerfilData")
                            .setChild("user", p.getText().toString())
                            .setChild("userName", p2.getText().toString())
                            .setData();
                    dialog.dismiss();
                });
            }
            else if (useSignOut){
                aceptarBt.setOnClickListener(v -> {
                    GetDataUser.DataOnActivity
                            .build(context)
                            .setChangeActivity(Login.class)
                            .setMessage("Cerraste sesion")
                            .signOut();
                });
            }
            else if (useDeleteCount){
                Log.d("DialogShow","Borrar cuenta");
            }

            dialog.show();
        }
        else if (useAceptar && useCancelar){
            Log.d("DialogShow","Es nesesario utilizar un layout. Se recomienda usar: setLayout(@LayoutRes int layoutToDialog)");
        }
        else if (useAceptar && useLayoutToDialog){
            Log.d("DialogShow","Es nesesario agregar un boton para cancelar. Se recomienda usar: deniedBt(int id)");
        }
        else if (useCancelar && useLayoutToDialog){
            Log.d("DialogShow","Es nesesario agregar un boton para aceptar. Se recomienda usar: grantedBt(int id)");
        }
        else if (useLayoutToDialog){
            Log.d("DialogShow","Es nesesario agregar botones de accion. Se recomienda usar: grantedBt(int id) y deniedBt(int id)");
        }
        else {
            Log.d("DialogShow","Acceso denegado. Se requiere indicar los botones de accion y su plantilla");
        }
    }







}
