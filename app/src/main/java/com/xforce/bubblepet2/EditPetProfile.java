package com.xforce.bubblepet2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.xforce.bubblepet2.adapters.TargetaPet;
import com.xforce.bubblepet2.dataFromDataBase.GetDataUser;
import com.xforce.bubblepet2.helpers.ChangeActivity;
import com.xforce.bubblepet2.helpers.msgToast;
import com.xforce.bubblepet2.interfaces.CallbackDataUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditPetProfile extends AppCompatActivity {
    /*-------------------------------------------------------------------------------*/
    /*Variables para texto, campos de texto y contenedores*/
    private EditText petName;
    private EditText petAge;
    private EditText petColor;
    private EditText petBreed;
    private EditText petHealth;
    private String petNameString = " ";
    private String petEgeString = " ";
    private String petColorString = " ";
    private String petBreedString = " ";
    private String petHealthString = " ";
    Uri imageUri;
    View changeImagePet;
    ImageView contImagePet;
    int SELECT_PICTURE = 200;
    FirebaseAuth userAuth;
    DatabaseReference userDataBase;
    /*-------------------------------------------------------------------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet_profile);


        petName = findViewById(R.id.petName);
        petAge = findViewById(R.id.petEdad);
        petColor = findViewById(R.id.petColor);
        petBreed = findViewById(R.id.petRaza);
        petHealth = findViewById(R.id.petEstado);
        View btResetTextName = findViewById(R.id.resetText1);
        View btResetTextEge = findViewById(R.id.resetText2);
        View btResetTextColor = findViewById(R.id.resetText3);
        View btResetTextBreed = findViewById(R.id.resetText4);
        View btResetTextHealth = findViewById(R.id.resetText5);
        TextView saveDatosButton = findViewById(R.id.btSignUpFinish2);
        changeImagePet = findViewById(R.id.selectImageEditProfile);
        contImagePet = findViewById(R.id.imgPhotoPet);
        userAuth = FirebaseAuth.getInstance();
        userDataBase = FirebaseDatabase.getInstance().getReference();


        //Creamos los datos iniciales
        DatabaseReference databaseCreate1 = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(GetDataUser.DataOnActivity.getUserId())
                .child("PetData");
        databaseCreate1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    databaseCreate1.setValue(new TargetaPet(" ", " ", " ", " ", " ", " "));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference databaseCreate2 = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(GetDataUser.DataOnActivity.getUserId())
                .child("ImageData/imgPetPerfil");
        databaseCreate2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.exists()){
                    Map<String, Object> data1 = new HashMap<>();
                    data1.put("ImageMain", " ");
                    databaseCreate2.setValue(data1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //Leemos y cargamos los datos
        DatabaseReference databaseRed = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(GetDataUser.DataOnActivity.getUserId())
                .child("PetData");
        databaseRed.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                TargetaPet targeta = snapshot.getValue(TargetaPet.class);
                Glide.with(getApplicationContext()).load(Objects.requireNonNull(targeta).getImagen()).into(contImagePet);
                petName.setText(targeta.getNombre());
                petAge.setText(targeta.getEdad());
                petColor.setText(targeta.getColor());
                petBreed.setText(targeta.getRaza());
                petHealth.setText(targeta.getSalud());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        ResetText(btResetTextName,petName);/*Reiniciamos el texto*/
        ResetText(btResetTextEge,petAge);/*Reiniciamos el texto*/
        ResetText(btResetTextColor,petColor);/*Reiniciamos el texto*/
        ResetText(btResetTextBreed,petBreed);/*Reiniciamos el texto*/
        ResetText(btResetTextHealth,petHealth);/*Reiniciamos el texto*/
        saveDatosButton.setOnClickListener(v ->{
            petNameString = petName.getText().toString();
            petEgeString = petAge.getText().toString();
            petColorString = petColor.getText().toString();
            petBreedString = petBreed.getText().toString();
            petHealthString = petHealth.getText().toString();

            Map<String,Object> set = new HashMap<>();
            set.put("user"+System.currentTimeMillis(),GetDataUser.DataOnActivity.getUserId());
            /*GetDataUser.DataOnActivity
                    .build(getApplicationContext(),EditPetProfile.this)
                    .pushChildren("targetaFeed",set,false);*/

            GetDataUser.DataOnActivity.send(Boolean.parseBoolean(petBreedString),Boolean.parseBoolean(petHealthString),"test",petNameString,petColorString);



            /*DatabaseReference firebaseRefCopy = FirebaseDatabase.getInstance().getReference().child("targetaFeed");
            FirebaseDatabase.getInstance().getReference().child("targetaFeed").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        Log.e("GetDataUser", "(Database error) Este usuario no existe; Se recomienda revisar su base de datos.");
                    }
                    else {
                        for (DataSnapshot child: snapshot.getChildren()) {
                            String value = child.getValue().toString();
                            Map<String,Object> map = new HashMap<>();
                            map.put(child.getKey(),child.getValue());
                            map.put("user"+(System.currentTimeMillis() / 10),GetDataUser.DataOnActivity.getUserId());

                            if (value.equals(String.valueOf(GetDataUser.DataOnActivity.getUserId()))){
                                Log.d("GetDataUser","[copyPasteDataBase] No se agregara debido a que ya se encuentra enlistado.");
                            }

                            else {
                                firebaseRefCopy.setValue(map);
                            }

                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("GetDataUser", "(Error) Obtención de datos cancelada; " + error);
                }
            });

            GetDataUser
                    .DataOnActivity
                    .build(getApplicationContext(),EditPetProfile.this)
                    .setValuePath("ImageData/imgPetPerfil")
                    .readData(new CallbackDataUser() {
                        @Override
                        public void onReadData(DataSnapshot value) {

                        }

                        @Override
                        public void onChildrenCount(int count) {

                        }

                        @Override
                        public void onChildrenTotalCount(int totalCount) {

                        }

                        @Override
                        public void onHashMapValue(String key, Object value) {
                            Map<String,Object> map = new HashMap<>();
                            map.put("user"+(System.currentTimeMillis() / 10),GetDataUser.DataOnActivity.getUserId());

                            GetDataUser
                                    .DataOnActivity.getInstanceFD()
                                    .getReference("Users")
                                    .child(GetDataUser.DataOnActivity.getUserId())
                                    .child("PetData")
                                    .setValue(new TargetaPet(value.toString(), petNameString, petEgeString, petColorString, petBreedString, petHealthString));

                            GetDataUser
                                    .DataOnActivity
                                    .build(getApplicationContext(),EditPetProfile.this)
                                    .setChild(map).rootPath(GetDataUser.DataOnActivity.getDataBaseRef().toString()+"/targetaFeed")
                                    .copyPasteDataBase("targetaFeed","targetaFeed");

                        }

                    });


            ChangeActivity.build(getApplicationContext(),MainActivity.class).start();*/


        });/*Actualizamos los datos del perfil*/
        changeImagePet.setOnClickListener(v ->{

            Intent i = new Intent();
            i.setAction(Intent.ACTION_GET_CONTENT);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "Elige la foto de tu mascota"), SELECT_PICTURE);

        });/*Elegimos la nueva imagen de usuario*/

    }
    @Override public void onBackPressed() {
        super.onBackPressed();
        ChangeActivity.build(getApplicationContext(),MainActivity.class).start();
    }
    /*-------------------------------------------------------------------------------*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PICTURE) {
            imageUri = data.getData();
            contImagePet.setImageURI(imageUri);

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Subiendo...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            String id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
            StorageReference folder = FirebaseStorage.getInstance().getReference().child("Users").child(id);
            final StorageReference file_name = folder.child(imageUri.getLastPathSegment());
            file_name.putFile(imageUri).addOnProgressListener(taskSnapshot -> {

                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Exportando al " + (int)progress + "%");

            }).addOnSuccessListener(taskSnapshot -> file_name.getDownloadUrl().addOnSuccessListener(uri -> {
                //Enviamos a la base de datos la url de la imagen


                //Enviamos a: ImageData/imgPetPerfil
                Map<String, Object> data1 = new HashMap<>();
                data1.put("ImageMain", String.valueOf(uri));
                DatabaseReference databaseSet1 = FirebaseDatabase.getInstance()
                        .getReference("Users")
                        .child(GetDataUser.DataOnActivity.getUserId())
                        .child("ImageData/imgPetPerfil");
                databaseSet1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseSet1.setValue(data1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                //Enviamos a: ImageData/uploadeds/pet

                GetDataUser.DataOnActivity.send(
                        false,
                        false,
                        GetDataUser.DataOnActivity.getUserPath()+"ImageData/uploadeds/pet",
                        "imagen"+(System.currentTimeMillis() / 10),String.valueOf(uri));


                msgToast("Imagen subida correctamente");
                progressDialog.dismiss();
            })).addOnFailureListener(e -> {
                // Error, Image not uploaded
                progressDialog.dismiss();
                msgToast("Error en la carga " + e.getMessage());
            });


        }
    }

    /*Termina codigo de la seleccion de imagen y envio a la base de datos*/
    /*--------------------*/




    /*Reiniciamos el texto del campo de texto*/
    private void ResetText (View elemTouch, EditText textToReset){
        elemTouch.setOnClickListener(view -> textToReset.setText(""));
    }



    /*Variable para generar el mensaje Toast*/
    private void msgToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }
    /*-------------------------------------------------------------------------------*/
}