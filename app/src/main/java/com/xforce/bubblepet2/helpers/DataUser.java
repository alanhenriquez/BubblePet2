package com.xforce.bubblepet2.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xforce.bubblepet2.R;

import java.util.Objects;

public class DataUser {

    //Variables--------------------------------------------
    private Context context;
    private View contextView;
    TextView textString;
    ImageView imageView;
    String id;
    String val;
    FirebaseAuth userAuth;
    DatabaseReference userDataBase;

    //Privates---------------------------------------------

    private DataUser(@NonNull Context _context){
        this.userAuth = FirebaseAuth.getInstance();
        this.userDataBase = FirebaseDatabase.getInstance().getReference();
        this.id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
        this.context = _context;
    }

    private DataUser(@NonNull View _contextView){
        this.userAuth = FirebaseAuth.getInstance();
        this.userDataBase = FirebaseDatabase.getInstance().getReference();
        this.id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
        this.contextView = _contextView;
    }

    //Public static----------------------------------------

    public static DataUser build(@NonNull Context context){
        return new DataUser(context);
    }

    public static DataUser build(@NonNull View view){
        return new DataUser(view);
    }

    //Public void------------------------------------------

    public void getData(){

        userDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    if (snapshot.hasChild("PerfilData")){
                        if (snapshot.child("PerfilData").hasChild("user")){

                            val = Objects.requireNonNull(snapshot.child("PerfilData").child("user").getValue()).toString();
                            textString = contextView.findViewById(R.id.userName);
                            textString.setText(val);

                        }

                        if (snapshot.child("PerfilData").hasChild("userName")){

                            val = Objects.requireNonNull(snapshot.child("PerfilData").child("userName").getValue()).toString();
                            textString = contextView.findViewById(R.id.biografia_perfil_content);
                            textString.setText(val);

                        }
                    }

                    /*-----------------*/

                    if (snapshot.hasChild("CountData")){
                        if (snapshot.child("CountData").hasChild("userMail")){

                            val = Objects.requireNonNull(snapshot.child("CountData").child("userMail").getValue()).toString();
                            textString = contextView.findViewById(R.id.userMail);
                            textString.setText(val);

                        }
                    }

                    /*-----------------*/

                    if (snapshot.hasChild("PetData")){

                        if (snapshot.child("PetData").hasChild("petName")){

                            val = Objects.requireNonNull(snapshot.child("PetData").child("petName").getValue()).toString();
                            textString = contextView.findViewById(R.id.text_targeta_pet_content_info_1);
                            textString.setText(val);

                        }

                        if (snapshot.child("PetData").hasChild("petEge")){

                            val = Objects.requireNonNull(snapshot.child("PetData").child("petEge").getValue()).toString();
                            textString = contextView.findViewById(R.id.text_targeta_pet_content_info_2);
                            textString.setText(val);

                        }

                        if (snapshot.child("PetData").hasChild("petColor")){

                            val = Objects.requireNonNull(snapshot.child("PetData").child("petColor").getValue()).toString();
                            textString = contextView.findViewById(R.id.text_targeta_pet_content_info_3);
                            textString.setText(val);

                        }

                        if (snapshot.child("PetData").hasChild("petBreed")){

                            val = Objects.requireNonNull(snapshot.child("PetData").child("petBreed").getValue()).toString();
                            textString = contextView.findViewById(R.id.text_targeta_pet_content_info_4);
                            textString.setText(val);

                        }

                        if (snapshot.child("PetData").hasChild("petHealth")){

                            val = Objects.requireNonNull(snapshot.child("PetData").child("petHealth").getValue()).toString();
                            textString = contextView.findViewById(R.id.text_targeta_pet_content_info_5);
                            textString.setText(val);

                        }

                        if (snapshot.child("PetData").hasChild("imgPetPerfil")){

                            val = Objects.requireNonNull(snapshot.child("PetData").child("imgPetPerfil").child("ImageMain").getValue()).toString();
                            imageView = contextView.findViewById(R.id.imagePet);
                            Glide.with(contextView).load(val).into(imageView);

                        }

                    }

                    /*-----------------*/

                    if (snapshot.hasChild("ImageData")){
                        if (snapshot.child("ImageData").hasChild("imgPerfil")){

                            val = Objects.requireNonNull(snapshot.child("ImageData").child("imgPerfil").child("ImageMain").getValue()).toString();
                            imageView = contextView.findViewById(R.id.imgPhoto);
                            Glide.with(contextView).load(val).into(imageView);

                        }
                    }


                }else {
                    Toast.makeText(contextView.getContext(),"Error", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(contextView.getContext(),"Error de carga", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getChild(){

    }


}
