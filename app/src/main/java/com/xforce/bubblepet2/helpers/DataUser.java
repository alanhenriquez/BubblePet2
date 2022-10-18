package com.xforce.bubblepet2.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DataUser {

    //Variables--------------------------------------------
    private Context context;
    private View contextView;
    View view;
    TextView textView;
    EditText editText;
    ImageView imageView;
    String id, val, elementPathValue;
    int elementIdValue;
    boolean fragment;
    boolean elementId = false;
    boolean elementPath = false;
    FirebaseAuth userAuth;
    DatabaseReference userDataBase;

    //Privates---------------------------------------------

    private DataUser(@NonNull Context _context){
        this.userAuth = FirebaseAuth.getInstance();
        this.userDataBase = FirebaseDatabase.getInstance().getReference();
        this.id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
        this.context = _context;
        this.fragment = false;
    }

    private DataUser(@NonNull View _contextView){
        this.userAuth = FirebaseAuth.getInstance();
        this.userDataBase = FirebaseDatabase.getInstance().getReference();
        this.id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
        this.contextView = _contextView;
        this.fragment = true;
    }

    //Public static----------------------------------------

    public static DataUser build(@NonNull Context context){
        return new DataUser(context);
    }

    public static DataUser build(@NonNull View view){
        return new DataUser(view);
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

        if (!fragment){
            userDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        if (elementId && elementPath){
                            if (snapshot.child(elementPathValue).exists()){
                                if (view.findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatTextView")) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    textView = view.findViewById(elementIdValue);
                                    textView.setText(val);

                                }else if (view.findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatEditText")) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    editText = view.findViewById(elementIdValue);
                                    editText.setText(val);

                                }else if (view.findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatImageView")) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    imageView = view.findViewById(elementIdValue);
                                    Glide.with(context).load(val).into(imageView);

                                }else {
                                    msgToast.build(context).message("El Objeto id no es compatible");
                                }
                            }else {
                                msgToast.build(context).message("El Path de datos no exite");
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
        else {
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

    }

    //Public DataUser------------------------------------------

    public DataUser setElementbyId(int _id){
        this.elementIdValue = _id;
        this.elementId = true;
        return this;
    }

    public DataUser setValuePath(String _path){
        this.elementPathValue = _path;
        this.elementPath = true;
        return this;
    }

}
