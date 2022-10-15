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
    TextView user;
    TextView userMail;
    TextView petName;
    TextView petAge;
    TextView petColor;
    TextView petBreed;
    TextView petHealth;
    ImageView userImageProfile;
    ImageView petImageProfile;
    FirebaseAuth userAuth;
    DatabaseReference userDataBase;

    //Privates---------------------------------------------

    private DataUser(@NonNull Context _context){
        this.context = _context;
    }

    private DataUser(@NonNull View _contextView){
        this.contextView = _contextView;
    }

    private DataUser(@NonNull FirebaseAuth _userAuth, @NonNull DatabaseReference _userDataBase){
        this.userAuth = _userAuth;
        this.userDataBase = _userDataBase;
    }

    //Public static----------------------------------------

    public static DataUser build(@NonNull Context context){
        return new DataUser(context);
    }

    public static DataUser build(@NonNull View view){
        return new DataUser(view);
    }

    public static DataUser initFirebase(@NonNull FirebaseAuth userAuth, @NonNull DatabaseReference userDataBase){
        return new DataUser(userAuth,userDataBase);
    }

    //Public void------------------------------------------

    public void getData(){
        userAuth = FirebaseAuth.getInstance();
        userDataBase = FirebaseDatabase.getInstance().getReference();

        String id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
        userDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String val;

                    if (snapshot.hasChild("PerfilData")){
                        if (snapshot.child("PerfilData").hasChild("user")){

                            val = Objects.requireNonNull(snapshot.child("PerfilData").child("user").getValue()).toString();
                            user = contextView.findViewById(R.id.userName);
                            user.setText(val);

                        }

                        if (snapshot.child("PerfilData").hasChild("userName")){

                            val = Objects.requireNonNull(snapshot.child("PerfilData").child("userName").getValue()).toString();
                            user = contextView.findViewById(R.id.biografia_perfil_content);
                            user.setText(val);

                        }
                    }

                    /*-----------------*/

                    if (snapshot.hasChild("CountData")){
                        if (snapshot.child("CountData").hasChild("userMail")){

                            val = Objects.requireNonNull(snapshot.child("CountData").child("userMail").getValue()).toString();
                            userMail = contextView.findViewById(R.id.userMail);
                            userMail.setText(val);

                        }
                    }

                    /*-----------------*/

                    if (snapshot.hasChild("PetData")){

                        if (snapshot.child("PetData").hasChild("petName")){

                            val = Objects.requireNonNull(snapshot.child("PetData").child("petName").getValue()).toString();
                            petName = contextView.findViewById(R.id.text_targeta_pet_content_info_1);
                            petName.setText(val);

                        }

                        if (snapshot.child("PetData").hasChild("petEge")){

                            val = Objects.requireNonNull(snapshot.child("PetData").child("petEge").getValue()).toString();
                            petAge = contextView.findViewById(R.id.text_targeta_pet_content_info_2);
                            petAge.setText(val);

                        }

                        if (snapshot.child("PetData").hasChild("petColor")){

                            val = Objects.requireNonNull(snapshot.child("PetData").child("petColor").getValue()).toString();
                            petColor = contextView.findViewById(R.id.text_targeta_pet_content_info_3);
                            petColor.setText(val);

                        }

                        if (snapshot.child("PetData").hasChild("petBreed")){

                            val = Objects.requireNonNull(snapshot.child("PetData").child("petBreed").getValue()).toString();
                            petBreed = contextView.findViewById(R.id.text_targeta_pet_content_info_4);
                            petBreed.setText(val);

                        }

                        if (snapshot.child("PetData").hasChild("petHealth")){

                            val = Objects.requireNonNull(snapshot.child("PetData").child("petHealth").getValue()).toString();
                            petHealth = contextView.findViewById(R.id.text_targeta_pet_content_info_5);
                            petHealth.setText(val);

                        }

                        if (snapshot.child("PetData").hasChild("imgPetPerfil")){

                            val = Objects.requireNonNull(snapshot.child("PetData").child("imgPetPerfil").child("ImageMain").getValue()).toString();
                            petImageProfile = contextView.findViewById(R.id.imagePet);
                            Glide.with(contextView).load(val).into(petImageProfile);

                        }

                    }

                    /*-----------------*/

                    if (snapshot.hasChild("ImageData")){
                        if (snapshot.child("ImageData").hasChild("imgPerfil")){

                            val = Objects.requireNonNull(snapshot.child("ImageData").child("imgPerfil").child("ImageMain").getValue()).toString();
                            userImageProfile = contextView.findViewById(R.id.imgPhoto);
                            Glide.with(contextView).load(val).into(userImageProfile);

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


}
