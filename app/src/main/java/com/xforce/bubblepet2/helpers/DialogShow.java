package com.xforce.bubblepet2.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.xforce.bubblepet2.EditProfile;
import com.xforce.bubblepet2.Login;
import com.xforce.bubblepet2.MainActivity;
import com.xforce.bubblepet2.R;
import com.xforce.bubblepet2.dataFromDataBase.GetDataUser;
import com.xforce.bubblepet2.interfaces.CallbackDataUser;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DialogShow {

    //Variables-----------------------
    Dialog dialog;
    Activity activity;
    private final Context context;
    int aceptar,cancelar,layoutToDialog;
    String title,content,textBtGranted,textBtDenied;
    TextView aceptarBt,cancelarBt,titleView,contentView;
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

    private DialogShow(@NonNull Context context, @NonNull Activity activity){
        this.context = context;
        this.activity = activity;
    }

    //Public static-----------------------------------

    public static DialogShow build(@NonNull Context context, @NonNull Activity activity){
        return new DialogShow(context,activity);
    }

    //Public DialogShow------------------------------------

    public DialogShow grantedBt(@IdRes int id){
        this.aceptar = id;
        this.useAceptar = true;
        return this;
    }

    public DialogShow deniedBt(@IdRes int id){
        this.cancelar = id;
        this.useCancelar = true;
        return this;
    }

    public DialogShow setTextBtDenied(String title){
        this.usetextBtDenied = true;
        if (title.length() <= 10){
            this.textBtDenied = title;
        }
        else {
            this.textBtDenied = " ";
            Log.e("DialogShow","(Design error) Se requiere que el numero maximo de letras sea 10 en: .setTextBtDenied(String title)");
        }
        return this;
    }

    public DialogShow setTextBtGranted(String title){
        this.usetextBtGranted = true;
        if (title.length() <= 10){
            this.textBtGranted = title;
        }
        else {
            this.textBtGranted = " ";
            Log.e("DialogShow","(Design error) Se requiere que el numero maximo de letras sea 10 en: .setTextBtGranted(String title)");
        }
        return this;
    }

    public DialogShow setTitle(String title){
        this.useTitle = true;
        if (title.length() <= 28){
            this.title = title;
        }
        else {
            this.textBtGranted = " ";
            Log.e("DialogShow","(Design error) Se requiere que el numero maximo de letras sea 28 en: .setTitle(String title)");
        }
        return this;
    }

    public DialogShow setContent(String content){
        this.useContent = true;
        if (content.length() <= 200){
            this.content = content;
        }
        else {
            this.textBtGranted = " ";
            Log.e("DialogShow","(Design error) Se requiere que el numero maximo de letras sea 200 en: .setContent(String content)");
        }
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
            Log.w("DialogShow","En caso de no usar una accion especial mejor solo utilice: show()");
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
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(layoutToDialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            aceptarBt = dialog.findViewById(aceptar);
            cancelarBt = dialog.findViewById(cancelar);
            titleView = dialog.findViewById(R.id.dialogBoxTitleTx);
            contentView = dialog.findViewById(R.id.dialogBoxContentTx);
            contentView.setVisibility(View.GONE);

            if (useTitle){
                titleView.setText(title);
            }

            if (useContent){
                contentView.setVisibility(View.VISIBLE);
                contentView.setText(content);
            }

            if (usetextBtGranted){
                aceptarBt.setText(textBtGranted);
            }

            if (usetextBtDenied){
                cancelarBt.setText(textBtDenied);
            }

            cancelarBt.setOnClickListener(v -> dialog.dismiss());

            if (useSaveData){
                aceptarBt.setOnClickListener(v -> {
                    TextView p = activity.findViewById(R.id.userEditProfile);
                    TextView p2 = activity.findViewById(R.id.userNameEditProfile);
                    GetDataUser.DataOnActivity
                            .build(context,activity)
                            .setChangeActivity(MainActivity.class)
                            .setMessage("Datos actualizados ;D")
                            .setValuePath("PerfilData")
                            .setChild("user", p.getText().toString())
                            .setChild("userName", p2.getText().toString())
                            .uploadData();
                    dialog.dismiss();
                    Log.d("DialogShow","Se guardaron los cambios exitosamente");
                });
            }
            else if (useSignOut){
                aceptarBt.setOnClickListener(v -> {
                    GetDataUser.DataOnActivity
                            .build(context,activity)
                            .setChangeActivity(Login.class)
                            .setMessage("Cerraste sesion")
                            .signOut();
                    Log.d("DialogShow","Has cerrado sesion con exito");
                });
            }
            else if (useDeleteCount){
                aceptarBt.setOnClickListener(v -> {
                    /*GetDataUser.DataOnActivity.build(context,activity).setMessage("Archivo eliminado").deleteUserAccount();*/
                });
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
