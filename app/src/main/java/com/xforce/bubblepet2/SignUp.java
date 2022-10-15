package com.xforce.bubblepet2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.xforce.bubblepet2.helpers.ChangeActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    /*-------------------------------------------------------------------------------*/
    /*Variables para texto, campos de texto y contenedores*/
    private EditText userEmail;
    private EditText userPassword;
    private String userEmailString = " ";
    private String userPasswordString = " ";
    /*Acceso a Firebase*/
    FirebaseAuth userAuth;
    DatabaseReference userDataBase;
    /*-------------------------------------------------------------------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);





        /* Acceso a Instancias FireBase
         * Estos accesos los encontraras en el build.gradle tanto de proyecto como app*/
        userAuth = FirebaseAuth.getInstance();
        userDataBase = FirebaseDatabase.getInstance().getReference();





        /*Simples variables antes definidas accediendo a los id*/
        userEmail = findViewById(R.id.userMailSignUp);
        userPassword = findViewById(R.id.userPasswordSignUp);
        View btResetTextMail = findViewById(R.id.resetText1);
        View btResetTextPassword = findViewById(R.id.resetText2);
        TextView btSignUp = findViewById(R.id.btSignUp);





        /*Botones y acciones*/
        btSignUp.setOnClickListener(view -> {
            if (ValidarEmail(userEmail)){
                userEmailString = userEmail.getText().toString();
                userPasswordString = userPassword.getText().toString();

                if(!userEmailString.isEmpty() && !userPasswordString.isEmpty()){
                    registerUser();
                }else{
                    if (userEmailString.isEmpty()){
                        msgToast("Ingrese su correo electronico");
                        userEmail.requestFocus();
                    }else{
                        msgToast("Ingrese una contraseña");
                        userPassword.requestFocus();
                    }
                }
            }
        });/*Creamos el registro del usuario y logueamos*/
        ResetText(btResetTextMail,userEmail);/*Reiniciamos el texto del campo Mail*/
        ResetText(btResetTextPassword,userPassword);/*Reiniciamos el texto del campo Password*/
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        ChangeActivity.build(getApplicationContext(),Login.class).start();
    }
    /*-------------------------------------------------------------------------------*/







    /*Creamos al usuario y lo registramos*/
    private void registerUser(){
        //Autenticaremos al usuario mediante su correo y contraseña
        userAuth.createUserWithEmailAndPassword(userEmailString, userPasswordString).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                SetDataBase();
            }else{
                String errorCode = ((FirebaseAuthException) Objects.requireNonNull(task.getException())).getErrorCode();
                dameToastdeerror(errorCode, userEmail, userPassword);
            }
        });
    }



    /*Agregamos la informacion a la base de datos*/
    private void SetDataBase(){
        Map<String, Object> data = new HashMap<>();
        data.put("userMail", userEmailString);
        data.put("userPassword", userPasswordString);

        String id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
        userDataBase.child("Users").child(id).child("CountData").setValue(data).addOnCompleteListener(task1 -> {

            ChangeActivity.build(getApplicationContext(),SignUpFinish.class).start();

        });
    }



    /*Reiniciamos el texto del campo de texto*/
    private void ResetText (View elemTouch, EditText textToReset){
        elemTouch.setOnClickListener(view -> textToReset.setText(""));
    }



    /*Variable para generar el mensaje Toast*/
    private void msgToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }



    /*Variable para generar el mensaje Toast del tipo de error*/
    private void dameToastdeerror(String error, EditText mail, EditText password) {

        switch (error) {
            case "ERROR_INVALID_CUSTOM_TOKEN":
                msgToast("El formato del token personalizado es incorrecto. Por favor revise la documentación");
                break;
            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                msgToast("El token personalizado corresponde a una audiencia diferente.");
                break;
            case "ERROR_INVALID_CREDENTIAL":
                msgToast("La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.");
                break;
            case "ERROR_INVALID_EMAIL":
                msgToast("El correo electrónico no es correcto.");
                /*mail.setError("La dirección de correo electrónico está mal formateada.");*/
                mail.requestFocus();
                break;
            case "ERROR_WRONG_PASSWORD":
                msgToast("La contraseña no es correcta");
                /*msgToast("La contraseña no es válida o el usuario no tiene contraseña.");*/
                /*password.setError("la contraseña es incorrecta ");*/
                password.requestFocus();
                break;
            case "ERROR_USER_MISMATCH":
                msgToast("Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente.");
                break;
            case "ERROR_REQUIRES_RECENT_LOGIN":
                msgToast("Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.");
                break;
            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                msgToast("Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.");
                break;
            case "ERROR_EMAIL_ALREADY_IN_USE":
                msgToast("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                mail.requestFocus();
                break;
            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                msgToast("Esta credencial ya está asociada con una cuenta de usuario diferente.");
                break;
            case "ERROR_USER_DISABLED":
                msgToast("La cuenta de usuario ha sido inhabilitada por un administrador.");
                break;
            case "ERROR_USER_TOKEN_EXPIRED":
                msgToast("La credencial del usuario ha expirado. El usuario debe iniciar sesión nuevamente.");
                break;
            case "ERROR_USER_NOT_FOUND":
                msgToast("No hay registro de usuario.");
                break;
            case "ERROR_INVALID_USER_TOKEN":
                msgToast("La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.");
                break;
            case "ERROR_OPERATION_NOT_ALLOWED":
                msgToast("Esta operación no está permitida. Debes habilitar este servicio en la consola.");
                break;
            case "ERROR_WEAK_PASSWORD":
                msgToast("La contraseña proporcionada no es válida.");
                /*password.setError("La contraseña no es válida, debe tener al menos 6 caracteres");*/
                password.requestFocus();
                break;
        }
    }



    /*Validar Email*/
    private boolean ValidarEmail(EditText args) {
        // Patrón para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // El email a validar
        String email = args.getText().toString();
        Matcher mather = pattern.matcher(email);

        if (email.isEmpty()){
            args.requestFocus();
            msgToast("Ingrese un correo electronico");
            return false;
        }else {
            if (mather.find()) {
                /*El email ingresado es válido.*/
                return true;
            } else {
                msgToast("Su email ingresado es inválido.");
                return false;
            }
        }


    }





}