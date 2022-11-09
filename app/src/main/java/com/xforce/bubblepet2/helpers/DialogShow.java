package com.xforce.bubblepet2.helpers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;

import com.xforce.bubblepet2.MainActivity;
import com.xforce.bubblepet2.R;
import com.xforce.bubblepet2.dataFromDataBase.GetDataUser;

public class DialogShow {
    public DialogShow(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog);

        Button aceptar = dialog.findViewById(R.id.aceptar);
        Button cancelar = dialog.findViewById(R.id.cancelar);

        aceptar.setOnClickListener(v -> {
            GetDataUser.DataOnActivity.setData(context)
                    .setChangeActivity(MainActivity.class)
                    .setMessage("Datos actualizados ;D")
                    .setValuePath("CountData/test")
                    .setChild("prueba1","contenido")
                    .setChild("prueba2","contenido")
                    .setChild("prueba3","contenido")
                    .setChild("prueba4","contenido")
                    .setChild("prueba5","contenido")
                    .setData();
            dialog.dismiss();
        });
        cancelar.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();

    }
}
