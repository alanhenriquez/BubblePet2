package com.xforce.bubblepet2.dataFromDataBase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xforce.bubblepet2.Login;
import com.xforce.bubblepet2.R;
import com.xforce.bubblepet2.helpers.ChangeActivity;
import com.xforce.bubblepet2.helpers.msgToast;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GetDataUser {

    //Public DataUser------------------------------------------

    public static class DataOnFragment extends Fragment{


        //Variables--------------------------------------------

        private final View contextView;
        private Context context;
        private Class<?> cls;
        Fragment fragment;
        TextView textView;
        EditText editText;
        ImageView imageView;
        Activity activity;
        String id,val,elementPathValue,message,objectString,valueString;
        Map<String, Object> data = new HashMap<>();
        int elementIdValue;
        boolean elementId = false;
        boolean elementPath = false;
        boolean isChangeActivity = false;
        boolean isSetMessage = false;
        boolean isObjectString = false;
        boolean isValueString = false;
        boolean useActivity = false;
        boolean useContext = false;
        FirebaseAuth userAuth;
        DatabaseReference userDataBase;

        //Privates---------------------------------------------

        private DataOnFragment(@NonNull View contextView){
            this.userAuth = getInstance();
            this.userDataBase = getDataBaseRef();
            this.id = getUserId();
            this.contextView = contextView;
        }

        private DataOnFragment(@NonNull View contextView, @NonNull Fragment fragment){
            this.userAuth = getInstance();
            this.userDataBase = getDataBaseRef();
            this.id = getUserId();
            this.contextView = contextView;
            this.context = fragment.requireActivity().getApplicationContext();
            this.useContext = true;
            this.fragment = fragment;
        }

        //Public static----------------------------------------

        public static DataOnFragment build(@NonNull View view){
            return new DataOnFragment(view);

        }

        public static DataOnFragment build(@NonNull View contextView, @NonNull Fragment fragment){
            return new DataOnFragment(contextView,fragment);

        }

        public static FirebaseAuth getInstance(){
            return FirebaseAuth.getInstance();

        }

        public static DatabaseReference getDataBaseRef(){
            return FirebaseDatabase.getInstance().getReference();

        }

        public static String getUserId(){
            return Objects.requireNonNull(getInstance().getCurrentUser()).getUid();

        }

        public static boolean isValidContextForGlide(final Context context){
            if (context == null){
                return false;
            }

            if (context instanceof Activity){
                final Activity activity = (Activity) context;
                return !activity.isDestroyed() || !activity.isFinishing();
            }

            return true;
        }

        //Public DataOnFragment------------------------------------------

        public DataOnFragment setChangeActivity(@NonNull Class<?> cls){
            this.cls = cls;
            this.isChangeActivity = true;
            return this;
        }

        public DataOnFragment setMessage(String message){
            this.message = message;
            this.isSetMessage = true;
            return this;
        }

        public DataOnFragment setChild(String object, String string){
            this.objectString = object;
            this.valueString = string;
            this.isObjectString = true;
            this.data.put(objectString, valueString);
            return this;
        }

        public DataOnFragment setChild(String string){
            this.valueString = string;
            this.isValueString = true;
            return this;
        }

        public DataOnFragment setElementbyId(int _id){
            this.elementIdValue = _id;
            this.elementId = true;
            return this;
        }

        public DataOnFragment setValuePath(String _path){
            this.elementPathValue = _path;
            this.elementPath = true;
            return this;
        }

        //Public void------------------------------------------

        public void getData(){
            userDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        msgToast.build(contextView.getContext()).message("Este elemento no existe");
                    }
                    else {
                        if (elementId && elementPath){
                            if (snapshot.child(elementPathValue).exists()){
                                if (contextView.findViewById(elementIdValue) instanceof TextView) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    textView = contextView.findViewById(elementIdValue);
                                    textView.setText(val);

                                }
                                else if (contextView.findViewById(elementIdValue) instanceof EditText) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    editText = contextView.findViewById(elementIdValue);
                                    editText.setText(val);

                                }
                                else if (contextView.findViewById(elementIdValue) instanceof ImageView) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    imageView = contextView.findViewById(elementIdValue);
                                    if (GetDataUser.DataOnFragment.isValidContextForGlide(context)){
                                        Glide.with(context).load(val).into(imageView);
                                    }

                                }
                                else {
                                    msgToast.build(contextView.getContext()).message("El Objeto id no es compatible");
                                }
                            }else {
                                msgToast.build(contextView.getContext()).message("El Path de datos no exite");
                                if (contextView.findViewById(elementIdValue) instanceof TextView) {

                                    val = "Datos no encontrados";
                                    textView = contextView.findViewById(elementIdValue);
                                    textView.setText(val);
                                    textView.setTextColor(ContextCompat.getColor(contextView.getContext(),R.color.rojo10));

                                }
                                else if (contextView.findViewById(elementIdValue) instanceof EditText) {

                                    val = "Datos no encontrados";
                                    editText = contextView.findViewById(elementIdValue);
                                    editText.setText("");
                                    editText.setHint(val);
                                    editText.setHintTextColor(ContextCompat.getColor(contextView.getContext(),R.color.rojo10));

                                }
                                else if (contextView.findViewById(elementIdValue) instanceof ImageView) {

                                    Drawable val = ContextCompat.getDrawable(contextView.getContext(),R.drawable.default_image_global);
                                    imageView = contextView.findViewById(elementIdValue);
                                    if (GetDataUser.DataOnFragment.isValidContextForGlide(context)){
                                        Glide.with(context).load(val).into(imageView);
                                    }

                                }
                                else {
                                    msgToast.build(contextView.getContext()).message("El Objeto id no es compatible");
                                }
                            }
                        }
                        else if (elementId){
                            msgToast.build(contextView.getContext()).message("Falta proporcionar el dato de setValuePath(String _path)");
                        }
                        else if (elementPath){
                            msgToast.build(contextView.getContext()).message("Falta proporcionar el dato de setElementbyId(int _id)");
                        }
                        else{
                            msgToast.build(contextView.getContext()).message("Falta proporcionar los datos de setElementbyId(int _id) y setValuePath(String _path)");
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    msgToast.build(contextView.getContext()).message("Error de carga");
                }
            });
        }

        public String getString(){
            final String[] value = {""};
            userDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        value[0] = "Este elemento no existe";
                    }else {
                        if (!elementPath){
                            value[0] = "Falta proporcionar el dato de setValuePath(String _path)";
                        }else{
                            if (!snapshot.child(elementPathValue).exists()){
                                value[0] = "El Path de datos no exite";
                            }else {
                                value[0] = String.valueOf(Objects.requireNonNull(snapshot.child(elementPathValue).getValue()));
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    value[0] = "Error de carga";
                }
            });
            return value[0];
        }

        public void setData(){
            if (!useContext){
                msgToast.build(contextView.getContext()).message("Se recomienda utilizar build(@NonNull View contextView, @NonNull Fragment fragment)");
            }
            else {
                if (isObjectString){
                    if (!elementPath){
                        msgToast.build(context).message("Error causado por introducción nula de datos");
                        msgToast.build(context).message("Se solicita usar: setValuePath()");
                    }else {
                        userDataBase.child("Users").child(id).child(elementPathValue).setValue(data).addOnCompleteListener(task -> {
                            if (isChangeActivity){
                                ChangeActivity.build(context,cls).start();
                            }

                            if (isSetMessage){
                                msgToast.build(context).message(message);
                            }
                        });
                    }
                }
                else if (isValueString){
                    String data = valueString;
                    if (!elementPath){
                        msgToast.build(context).message("Error causado por introducción nula de datos");
                        msgToast.build(context).message("Se solicita usar: setValuePath()");
                    }else {
                        userDataBase.child("Users").child(id).child(elementPathValue).setValue(data).addOnCompleteListener(task -> {
                            if (isChangeActivity){
                                ChangeActivity.build(context,cls).start();
                            }

                            if (isSetMessage){
                                msgToast.build(context).message(message);
                            }
                        });
                    }
                }
                else {
                    msgToast.build(context).message("Error causado por introducción nula de datos");
                    msgToast.build(context).message("Se solicita usar: setChild()");
                }
            }
        }



    }

    public static class DataOnActivity extends AppCompatActivity{

        //Variables--------------------------------------------

        private final Context context;
        private Class<?> cls;
        TextView textView;
        EditText editText;
        ImageView imageView;
        Activity activity;
        String id,val,elementPathValue,message,objectString,valueString,
                credentialEmail,credentialPassword;
        Map<String, Object> data = new HashMap<>();
        int elementIdValue;
        boolean elementId = false;
        boolean elementPath = false;
        boolean isChangeActivity = false;
        boolean isSetMessage = false;
        boolean isObjectString = false;
        boolean isValueString = false;
        boolean useActivity = false;
        boolean useCredentialEmail = false;
        boolean useCredentialPassword = false;
        FirebaseAuth userAuth;
        DatabaseReference userDataBase;

        //Privates---------------------------------------------

        private DataOnActivity(@NonNull Context contextView, Activity activity){
            this.userAuth = FirebaseAuth.getInstance();
            this.userDataBase = getDataBaseRef();
            this.id = getUserId();
            this.context = contextView;
            this.activity = activity;
            this.useActivity = true;
        }

        private DataOnActivity(@NonNull Context contextView){
            this.userAuth = FirebaseAuth.getInstance();
            this.userDataBase = getDataBaseRef();
            this.id = getUserId();
            this.context = contextView;
        }

        //Public static----------------------------------------

        public static DataOnActivity build(@NonNull Context context,Activity activity){
            return new DataOnActivity(context,activity);
        }

        public static DataOnActivity setData(@NonNull Context context){
            return new DataOnActivity(context);
        }

        public static FirebaseAuth getInstance(){

            return FirebaseAuth.getInstance();
        }

        public static DatabaseReference getDataBaseRef(){
            return FirebaseDatabase.getInstance().getReference();
        }

        public static FirebaseUser getCurrentUser(){
            return getInstance().getCurrentUser();

        }

        public static String getUserId(){
            return getCurrentUser().getUid();

        }

        //Public DataOnActivity------------------------------------------

        public DataOnActivity setChangeActivity(@NonNull Class<?> cls){
            this.cls = cls;
            this.isChangeActivity = true;
            return this;
        }

        public DataOnActivity setMessage(String message){
            this.message = message;
            this.isSetMessage = true;
            return this;
        }

        public DataOnActivity setChild(String object, String string){
            this.objectString = object;
            this.valueString = string;
            this.isObjectString = true;
            this.data.put(objectString, valueString);
            return this;
        }

        public DataOnActivity setChild(String string){
            this.valueString = string;
            this.isValueString = true;
            return this;
        }

        public DataOnActivity setElementbyId(int _id){
            this.elementIdValue = _id;
            this.elementId = true;
            return this;
        }

        public DataOnActivity setValuePath(String _path){
            this.elementPathValue = _path;
            this.elementPath = true;
            return this;
        }

        public DataOnActivity getCredentials(String email,String password){
            this.credentialEmail = email;
            this.credentialPassword = password;
            this.useCredentialEmail = true;
            this.useCredentialPassword = true;
            return this;
        }

        //Public void------------------------------------------

        public void getData(){
            userDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        msgToast.build(context).message("Este elemento no existe");
                    }
                    else {
                        if (!useActivity){
                            msgToast.build(context).message("Se recomienda utilizar build(@NonNull Context context,Activity activity)");
                        }
                        else {
                            if (elementId && elementPath){
                                if (snapshot.child(elementPathValue).exists()){
                                    if (activity.findViewById(elementIdValue) instanceof TextView) {

                                        val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                        textView = activity.findViewById(elementIdValue);
                                        textView.setText(val);

                                    }else if (activity.findViewById(elementIdValue) instanceof EditText) {

                                        val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                        editText = activity.findViewById(elementIdValue);
                                        editText.setText(val);

                                    }else if (activity.findViewById(elementIdValue) instanceof ImageView) {

                                        val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                        imageView = activity.findViewById(elementIdValue);
                                        Glide.with(context).load(val).into(imageView);

                                    }else {
                                        msgToast.build(context).message("El Objeto id no es compatible");
                                    }
                                }
                                else {
                                    msgToast.build(context).message("El Path de datos no exite");
                                    if (activity.findViewById(elementIdValue) instanceof TextView) {

                                        val = "Datos no encontrados";
                                        textView = activity.findViewById(elementIdValue);
                                        textView.setText(val);
                                        textView.setTextColor(ContextCompat.getColor(context,R.color.rojo10));

                                    }
                                    else if (activity.findViewById(elementIdValue) instanceof EditText) {

                                        val = "Datos no encontrados";
                                        editText = activity.findViewById(elementIdValue);
                                        editText.setText("");
                                        editText.setHint(val);
                                        editText.setHintTextColor(ContextCompat.getColor(context,R.color.rojo10));

                                    }
                                    else if (activity.findViewById(elementIdValue) instanceof ImageView) {

                                        Drawable val = ContextCompat.getDrawable(context,R.drawable.default_image_global);
                                        imageView = activity.findViewById(elementIdValue);
                                        Glide.with(context).load(val).into(imageView);

                                    }
                                    else {
                                        msgToast.build(context).message("El Objeto id no es compatible");
                                    }
                                }
                            }
                            else if (elementId){
                                msgToast.build(context).message("Falta proporcionar el dato de setValuePath(String _path)");
                            }
                            else if (elementPath){
                                msgToast.build(context).message("Falta proporcionar el dato de setElementbyId(int _id)");
                            }
                            else{
                                msgToast.build(context).message("Falta proporcionar los datos de setElementbyId(int _id) y setValuePath(String _path)");
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    msgToast.build(context).message("Error de carga");
                }
            });
        }

        public String getString(){
            final String[] value = {""};
            userDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        value[0] = "Este elemento no existe";
                    }else {
                        if (!elementPath){
                            value[0] = "Falta proporcionar el dato de setValuePath(String _path)";
                        }else{
                            if (!snapshot.child(elementPathValue).exists()){
                                value[0] = "El Path de datos no exite";
                            }else {
                                value[0] = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    value[0] = "Error de carga";
                }
            });
            return value[0];
        }

        public void setData(){

            if (isObjectString){
                if (!elementPath){
                    msgToast.build(context).message("Error causado por introducción nula de datos");
                    msgToast.build(context).message("Se solicita usar: setValuePath()");
                }else {
                    userDataBase.child("Users").child(id).child(elementPathValue).setValue(data).addOnCompleteListener(task -> {
                        if (isChangeActivity){
                            ChangeActivity.build(context,cls).start();
                        }

                        if (isSetMessage){
                            msgToast.build(context).message(message);
                        }
                    });
                }
            }
            else if (isValueString){
                String data = valueString;
                if (!elementPath){
                    msgToast.build(context).message("Error causado por introducción nula de datos");
                    msgToast.build(context).message("Se solicita usar: setValuePath()");
                }else {
                    userDataBase.child("Users").child(id).child(elementPathValue).setValue(data).addOnCompleteListener(task -> {
                        if (isChangeActivity){
                            ChangeActivity.build(context,cls).start();
                        }

                        if (isSetMessage){
                            msgToast.build(context).message(message);
                        }
                    });
                }
            }
            else {
                msgToast.build(context).message("Error causado por introducción nula de datos");
                msgToast.build(context).message("Se solicita usar: setChild()");
            }

        }

        public void signOut(){
            getInstance().signOut();
            if (isChangeActivity){
                ChangeActivity.build(context,cls).start();
            }

            if (isSetMessage){
                msgToast.build(context).message(message);
            }
        }

        public void deleteUser(){
            userDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        msgToast.build(context).message("Estos datos no existen");
                    }else {
                        String mail;
                        String password;
                        /*-----------------*/
                        /*Obtenemos los valores del usuario convertidos a cadena*/
                        mail = Objects.requireNonNull(snapshot.child("CountData").child("userMail").getValue()).toString();
                        password = Objects.requireNonNull(snapshot.child("CountData").child("userPassword").getValue()).toString();
                        /*-----------------*/
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        // Obtenga las credenciales de autenticación del usuario para volver a autenticarse. El siguiente ejemplo muestra
                        // credenciales de correo electrónico y contraseña, pero hay múltiples proveedores posibles,
                        // como GoogleAuthProvider o FacebookAuthProvider.
                        AuthCredential credential = EmailAuthProvider.getCredential(credentialEmail, credentialPassword);
                        // Pida al usuario que vuelva a proporcionar sus credenciales de inicio de sesión
                        if (getCurrentUser() != null) {
                            user.reauthenticate(credential).addOnCompleteListener(task ->
                                    user.delete().addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Intent intent = new Intent(getApplicationContext(), Login.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }));
                        }
                        /*-----------------*/
                        /*Removemos la informacion del usuario desde la base de datos*/
                        userDataBase.child("Users").child(id).removeValue();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    msgToast.build(context).message("Error de carga");
                }
            });
        }


    }

}
