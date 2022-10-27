package com.xforce.bubblepet2.dataFromDataBase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xforce.bubblepet2.R;
import com.xforce.bubblepet2.helpers.msgToast;

import java.util.Objects;

public class GetDataUser {

    //Public DataUser------------------------------------------

    public static class DataOnFragment extends Fragment{


        //Variables--------------------------------------------

        private final View contextView;
        TextView textView;
        EditText editText;
        ImageView imageView;
        String id, val, elementPathValue;
        int elementIdValue;
        boolean elementId = false;
        boolean elementPath = false;
        FirebaseAuth userAuth;
        DatabaseReference userDataBase;

        //Privates---------------------------------------------

        private DataOnFragment(@NonNull View _contextView){
            this.userAuth = getInstance();
            this.userDataBase = getDataBaseRef();
            this.id = getUserId();
            this.contextView = _contextView;
        }

        //Public static----------------------------------------

        public static DataOnFragment build(@NonNull View view){
            return new DataOnFragment(view);

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

        //Public void------------------------------------------

        public void getData(){
            userDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        if (elementId && elementPath){
                            if (snapshot.child(elementPathValue).exists()){
                                if (contextView.findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatTextView")) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    textView = contextView.findViewById(elementIdValue);
                                    textView.setText(val);

                                }else if (contextView.findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatEditText")) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    editText = contextView.findViewById(elementIdValue);
                                    editText.setText(val);

                                }else if (contextView.findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatImageView")) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    imageView = contextView.findViewById(elementIdValue);
                                    Glide.with(contextView).load(val).into(imageView);

                                }else {
                                    msgToast.build(contextView.getContext()).message("El Objeto id no es compatible");
                                }
                            }else {
                                msgToast.build(contextView.getContext()).message("El Path de datos no exite");
                                if (contextView.findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatTextView")) {

                                    val = "Datos no encontrados";
                                    textView = contextView.findViewById(elementIdValue);
                                    textView.setText(val);
                                    textView.setTextColor(ContextCompat.getColor(contextView.getContext(),R.color.rojo10));

                                }else if (contextView.findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatEditText")) {

                                    val = "Datos no encontrados";
                                    editText = contextView.findViewById(elementIdValue);
                                    editText.setText("");
                                    editText.setHint(val);
                                    editText.setHintTextColor(ContextCompat.getColor(contextView.getContext(),R.color.rojo10));

                                }else if (contextView.findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatImageView")) {

                                    Drawable val = ContextCompat.getDrawable(contextView.getContext(),R.drawable.default_image_global);
                                    imageView = contextView.findViewById(elementIdValue);
                                    Glide.with(contextView).load(val).into(imageView);

                                }else {
                                    msgToast.build(contextView.getContext()).message("El Objeto id no es compatible");
                                }
                            }
                        }else if (elementId){
                            msgToast.build(contextView.getContext()).message("Falta proporcionar el dato de setValuePath(String _path)");
                        }else if (elementPath){
                            msgToast.build(contextView.getContext()).message("Falta proporcionar el dato de setElementbyId(int _id)");
                        }else{
                            msgToast.build(contextView.getContext()).message("Falta proporcionar los datos de setElementbyId(int _id) y setValuePath(String _path)");
                        }

                    }else {
                        msgToast.build(contextView.getContext()).message("Este elemento no existe");
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
                    if (snapshot.exists()) {

                        if (elementPath){
                            if (snapshot.child(elementPathValue).exists()){
                                value[0] = String.valueOf(Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString());

                            }else {
                                value[0] = "El Path de datos no exite";
                            }
                        }else{
                            value[0] = "Falta proporcionar el dato de setValuePath(String _path)";
                        }

                    }else {
                        value[0] = "Este elemento no existe";
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    value[0] = "Error de carga";
                }
            });
            return value[0];
        }

        //Public DataOnFragment------------------------------------------

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

    }

    public static class DataOnActivity extends AppCompatActivity{

        //Variables--------------------------------------------

        private final Context context;
        TextView textView;
        EditText editText;
        ImageView imageView;
        Activity activity;
        String id;
        String val;
        String elementPathValue;
        int elementIdValue;
        boolean elementId = false;
        boolean elementPath = false;
        FirebaseAuth userAuth;
        DatabaseReference userDataBase;

        //Privates---------------------------------------------

        private DataOnActivity(@NonNull Context _contextView, Activity _activity){
            this.userAuth = FirebaseAuth.getInstance();
            this.userDataBase = getDataBaseRef();
            this.id = getUserId();
            this.context = _contextView;
            this.activity = _activity;
        }

        //Public static----------------------------------------

        public static DataOnActivity build(@NonNull Context _context,Activity _activity){
            return new DataOnActivity(_context,_activity);
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

        //Public void------------------------------------------

        public void getData(){
            userDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        if (elementId && elementPath){
                            if (snapshot.child(elementPathValue).exists()){
                                if (activity.findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatTextView")) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    textView = activity.findViewById(elementIdValue);
                                    textView.setText(val);

                                }else if (activity.findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatEditText")) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    editText = activity.findViewById(elementIdValue);
                                    editText.setText(val);

                                }else if (activity.findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatImageView")) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    imageView = activity.findViewById(elementIdValue);
                                    Glide.with(context).load(val).into(imageView);

                                }else {
                                    msgToast.build(context).message("El Objeto id no es compatible");
                                }
                            }else {
                                msgToast.build(context).message("El Path de datos no exite");
                                if (activity.findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatTextView")) {

                                    val = "Datos no encontrados";
                                    textView = activity.findViewById(elementIdValue);
                                    textView.setText(val);
                                    textView.setTextColor(ContextCompat.getColor(context,R.color.rojo10));

                                }else if (activity.findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatEditText")) {

                                    val = "Datos no encontrados";
                                    editText = activity.findViewById(elementIdValue);
                                    editText.setText("");
                                    editText.setHint(val);
                                    editText.setHintTextColor(ContextCompat.getColor(context,R.color.rojo10));

                                }else if (activity.findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatImageView")) {

                                    Drawable val = ContextCompat.getDrawable(context,R.drawable.default_image_global);
                                    imageView = activity.findViewById(elementIdValue);
                                    Glide.with(context).load(val).into(imageView);

                                }else {
                                    msgToast.build(context).message("El Objeto id no es compatible");
                                }
                            }
                        }else if (elementId){
                            msgToast.build(context).message("Falta proporcionar el dato de setValuePath(String _path)");
                        }else if (elementPath){
                            msgToast.build(context).message("Falta proporcionar el dato de setElementbyId(int _id)");
                        }else{
                            msgToast.build(context).message("Falta proporcionar los datos de setElementbyId(int _id) y setValuePath(String _path)");
                        }

                    }else {
                        msgToast.build(context).message("Este elemento no existe");
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
                    if (snapshot.exists()) {

                        if (elementPath){
                            if (snapshot.child(elementPathValue).exists()){
                                value[0] = String.valueOf(Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString());

                            }else {
                                value[0] = "El Path de datos no exite";
                            }
                        }else{
                            value[0] = "Falta proporcionar el dato de setValuePath(String _path)";
                        }

                    }else {
                        value[0] = "Este elemento no existe";
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    value[0] = "Error de carga";
                }
            });
            return value[0];
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

    }

}
