package com.xforce.bubblepet2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.xforce.bubblepet2.helpers.ChangeActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpFinish2 extends AppCompatActivity {
    /*-------------------------------------------------------------------------------*/
    /*Variables para texto, campos de texto y contenedores*/
    private EditText petName;
    private EditText petEge;
    private EditText petColor;
    private EditText petBreed;
    private EditText petHealth;
    private String petNameString = " ";
    private String petEgeString = " ";
    private String petColorString = " ";
    private String petBreedString = " ";
    private String petHealthString = " ";
    //ImageView para la imagen de usuario
    Uri imageUri;
    View changeImageUser;//Boton para selecionar imagen
    ImageView contImageUser;//Contenedor con la imagen del usuario
    int SELECT_PICTURE = 200;// constant to compare the activity result code
    /*Acceso a Firebase*/
    FirebaseAuth userAuth;
    DatabaseReference userDataBase;
    /*-------------------------------------------------------------------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_finish2);
        /* Acceso a Instancias FireBase
         * Estos accesos los encontraras en el build.gradle tanto de proyecto como app*/
        userAuth = FirebaseAuth.getInstance();
        userDataBase = FirebaseDatabase.getInstance().getReference();
        /*Simples variables antes definidas accediendo a los id*/
        petName = findViewById(R.id.petSignUpFinish2);
        petEge = findViewById(R.id.petEdadSignUpFinish2);
        petColor = findViewById(R.id.petColorSignUpFinish2);
        petBreed = findViewById(R.id.petRazaSignUpFinish2);
        petHealth = findViewById(R.id.petEstadoSignUpFinish2);
        View btResetTextName = findViewById(R.id.resetText1);
        View btResetTextEge = findViewById(R.id.resetText2);
        View btResetTextColor = findViewById(R.id.resetText3);
        View btResetTextBreed = findViewById(R.id.resetText4);
        View btResetTextHealth = findViewById(R.id.resetText5);
        TextView btSignUpFinish = findViewById(R.id.btSignUpFinish2);
        changeImageUser = findViewById(R.id.selectImageEditProfile);
        contImageUser = findViewById(R.id.imgPhotoUserEditProfile);
        /*Botones y acciones*/
        btSignUpFinish.setOnClickListener(view -> {
            petNameString = petName.getText().toString();
            petEgeString = petEge.getText().toString();
            petColorString = petColor.getText().toString();
            petBreedString = petBreed.getText().toString();
            petHealthString = petHealth.getText().toString();
            if(!petNameString.isEmpty() &&
                    !petEgeString.isEmpty() &&
                    !petColorString.isEmpty() &&
                    !petBreedString.isEmpty() &&
                    !petHealthString.isEmpty()){

                if ((contImageUser.getDrawable() == null)){
                    setDefaultDataImageBase();
                    msgToast("Selecciona la imagen de tu mascota");
                }else {
                    SetDataBase();
                }
            }else{
                if (petNameString.isEmpty()){
                    msgToast("Ingrese el nombre de su mascota");
                    petName.requestFocus();
                }else if (petEgeString.isEmpty()){
                    msgToast("Ingrese la edad de su mascota");
                    petEge.requestFocus();
                }else if (petColorString.isEmpty()){
                    msgToast("Ingrese el color de su mascota");
                    petColor.requestFocus();
                }else if (petBreedString.isEmpty()){
                    msgToast("Ingrese la raza de su mascota");
                    petBreed.requestFocus();
                }else {
                    msgToast("Ingrese el estado de salud de su mascota");
                    petHealth.requestFocus();
                }
            }
        });/*Finalizamos registro*/
        ResetText(btResetTextName,petName);/*Reiniciamos el texto*/
        ResetText(btResetTextEge,petEge);/*Reiniciamos el texto*/
        ResetText(btResetTextColor,petColor);/*Reiniciamos el texto*/
        ResetText(btResetTextBreed,petBreed);/*Reiniciamos el texto*/
        ResetText(btResetTextHealth,petHealth);/*Reiniciamos el texto*/
        changeImageUser.setOnClickListener(v ->{

            openGallery();

        });/*Elegimos la nueva imagen de usuario*/
    }

    @Override public void onBackPressed() {

        DeleteUser();

    }
    /*-------------------------------------------------------------------------------*/


    /*--------------------*/
    /*Codigo de la seleccion de imagen y envio a la base de datos*/
    private void openGallery(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        // pass the constant to compare it with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PICTURE) {
            imageUri = data.getData();
            contImageUser.setImageURI(imageUri);
            String id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
            StorageReference folder = FirebaseStorage.getInstance().getReference().child("Users").child(id);
            final StorageReference file_name = folder.child(imageUri.getLastPathSegment());
            file_name.putFile(imageUri).addOnSuccessListener(taskSnapshot ->
                    file_name.getDownloadUrl().addOnSuccessListener(uri -> {
                        //Enviamos a la base de datos la url de la imagen
                        setDataImageBase(String.valueOf(uri));
                        msgToast("Se subio correctamente");
                    }));
        }
    }
    /*Agregamos la Url de la imagen a la base de datos*/
    private void setDataImageBase(String link){
        Map<String, Object> data = new HashMap<>();
        data.put("ImageMain", link);
        String id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
        userDataBase.child("Users").child(id).child("ImageData").child("imgPetPerfil").setValue(data).addOnCompleteListener(task1 -> msgToast("Datos actualizados"));

    }

    private void setDefaultDataImageBase(){
        Map<String, Object> data = new HashMap<>();
        data.put("ImageMain", " ");
        String id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
        userDataBase.child("Users").child(id).child("ImageData").child("imgPerfil").setValue(data).addOnCompleteListener(task1 -> msgToast("Datos actualizados"));
    }
    /*Termina codigo de la seleccion de imagen y envio a la base de datos*/
    /*--------------------*/
    /*Eliminamos al usuario*/
    private void DeleteUser() {
        String id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
        userDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
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
                    AuthCredential credential = EmailAuthProvider.getCredential(mail, password);
                    // Pida al usuario que vuelva a proporcionar sus credenciales de inicio de sesión
                    if (user != null) {
                        user.reauthenticate(credential).addOnCompleteListener(task ->
                                user.delete().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        ChangeActivity.build(getApplicationContext(),Login.class).start();
                                    }
                                }));
                    }
                    /*-----------------*/
                    /*Removemos la informacion del usuario desde la base de datos*/
                    userDataBase.child("Users").child(id).removeValue();
                }else {
                    msgToast("Creación de usuario cancelado");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                msgToast("Error de carga");
            }
        });
    }
    /*Agregamos la informacion a la base de datos*/
    private void SetDataBase(){
        Map<String, Object> data = new HashMap<>();
        data.put("petName", petNameString);
        data.put("petEge", petEgeString);
        data.put("petColor", petColorString);
        data.put("petBreed", petBreedString);
        data.put("petHealth", petHealthString);
        String id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
        userDataBase.child("Users").child(id).child("PetData").setValue(data).addOnCompleteListener(task1 -> {
            ChangeActivity.build(getApplicationContext(),MainActivity.class).start();
            msgToast("Registro Exitoso");
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
    /*-------------------------------------------------------------------------------*/
}