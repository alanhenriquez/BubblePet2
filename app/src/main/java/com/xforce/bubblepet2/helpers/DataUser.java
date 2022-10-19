package com.xforce.bubblepet2.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DataUser {

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
            this.userAuth = FirebaseAuth.getInstance();
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
        String id, val, elementPathValue;
        int elementIdValue;
        boolean elementId = false;
        boolean elementPath = false;
        FirebaseAuth userAuth;
        DatabaseReference userDataBase;

        //Privates---------------------------------------------

        private DataOnActivity(@NonNull Context _contextView){
            this.userAuth = FirebaseAuth.getInstance();
            this.userDataBase = getDataBaseRef();
            this.id = getUserId();
            this.context = _contextView;
        }

        //Public static----------------------------------------

        public static DataOnActivity build(@NonNull Context _context){
            return new DataOnActivity(_context);
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
                                if (findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatTextView")) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    textView = findViewById(elementIdValue);
                                    textView.setText(val);

                                }else if (findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatEditText")) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    editText = findViewById(elementIdValue);
                                    editText.setText(val);

                                }else if (findViewById(elementIdValue).getClass().getName().equals("androidx.appcompat.widget.AppCompatImageView")) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    imageView = findViewById(elementIdValue);
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
