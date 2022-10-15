package com.xforce.bubblepet2.helpers;

import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {

    //Public static------------------------------------

    public static boolean validate(@NonNull EditText _email){
        // Patrón para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // El email a validar
        String email = _email.getText().toString();
        Matcher mather = pattern.matcher(email);

        if (email.isEmpty()){
            _email.requestFocus();
            return false;
        }else {
            return mather.find();
        }
    }

    public static boolean validate(@NonNull String _email){
        // Patrón para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // El email a validar
        Matcher mather = pattern.matcher(_email);

        if (_email.isEmpty()){
            return false;
        }else {
            return mather.find();
        }
    }

}