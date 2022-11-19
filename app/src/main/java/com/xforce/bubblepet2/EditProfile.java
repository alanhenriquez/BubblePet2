package com.xforce.bubblepet2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
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
import com.xforce.bubblepet2.dataFromDataBase.GetDataUser;
import com.xforce.bubblepet2.helpers.DialogShow;
import com.xforce.bubblepet2.helpers.msgToast;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditProfile extends AppCompatActivity {
    /*-------------------------------------------------------------------------------*/
    /*Variables para texto, campos de texto y contenedores*/
    //*******************************************
    private EditText user;
    private EditText userName;
    private EditText userPassword;
    private String userString = " ";
    private String userNameString = " ";
    TextView signOut;
    View showPassword;
    //ImageView para la imagen de usuario
    Uri imageUri;
    View changeImageUser;//Boton para selecionar imagen
    ImageView contImageUser;//Contenedor con la imagen del usuario
    int SELECT_PICTURE = 200;// constant to compare the activity result code
    /*Acceso a Firebase y AwesomeValidation*/
    FirebaseAuth userAuth;
    DatabaseReference userDataBase;
    Context context;
    /*-------------------------------------------------------------------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        user = findViewById(R.id.userEditProfile);
        userName = findViewById(R.id.userNameEditProfile);
        userPassword = findViewById(R.id.userPasswordEditProfile);
        signOut = findViewById(R.id.botonCerrarSesionEditProfile);
        showPassword = findViewById(R.id.showPassword);
        changeImageUser = findViewById(R.id.selectImageEditProfile);
        contImageUser = findViewById(R.id.imgPhotoUserEditProfile);
        userAuth = FirebaseAuth.getInstance();
        userDataBase = FirebaseDatabase.getInstance().getReference();
        TextView saveDatosButton = findViewById(R.id.botonGuardarDatosEditProfile);
        TextView eraseCountButton = findViewById(R.id.botonGuardarPasswordEditProfile);

        context = this;

        GetDataUser.DataOnActivity
                .build(getApplicationContext(),EditProfile.this)
                .setElementbyId(user.getId()).setValuePath("PerfilData/user").getData();
        GetDataUser.DataOnActivity
                .build(getApplicationContext(),EditProfile.this)
                .setElementbyId(userName.getId()).setValuePath("PerfilData/userName").getData();
        GetDataUser.DataOnActivity
                .build(getApplicationContext(),EditProfile.this)
                .setElementbyId(userPassword.getId()).setValuePath("CountData/userPassword").getData();
        GetDataUser.DataOnActivity
                .build(getApplicationContext(),EditProfile.this)
                .setElementbyId(contImageUser.getId()).setValuePath("ImageData/imgPerfil/ImageMain").getData();



        ShowPassword(showPassword,userPassword);

        changeImageUser.setOnClickListener(v ->{
            msgToast("Selecciona tu imagen");
            openGallery();
        });

        saveDatosButton.setOnClickListener(v ->{
            DialogShow.saveData(context,EditProfile.this)
                    .deniedBt(R.id.cancelar)
                    .grantedBt(R.id.aceptar)
                    .setLayout(R.layout.dialog)
                    .setTitle("Desea guardar los datos?")
                    .setContent("Si estas de acuerdo con todos tus cambios puedes precionar el boton: Aceptar")
                    .showSaveData(true);
        });

        signOut.setOnClickListener(view -> {
            DialogShow.signOut(context,EditProfile.this)
                    .deniedBt(R.id.cancelar)
                    .grantedBt(R.id.aceptar)
                    .setLayout(R.layout.dialog)
                    .setTitle("Deseas cerrar sesion?")
                    .setContent(" ")
                    .showSignOut(true);
        });

        eraseCountButton.setOnClickListener(v ->{
            DialogShow
                    .build(context,EditProfile.this)
                    .deniedBt(R.id.cancelar)
                    .grantedBt(R.id.aceptar)
                    .setLayout(R.layout.dialog1)
                    .setTitle("Quieres eliminar tu cuenta?")
                    .setContent("Primero deberas ingresar tus credenciales")
                    .showDeleteCount(true);
        });





    }
    @Override public void onBackPressed() {
        super.onBackPressed();
        ChangeActivity.build(getApplicationContext(),MainActivity.class).start();

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

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Subiendo...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setOnCancelListener(new Dialog.OnCancelListener() {
                @Override public void onCancel(DialogInterface dialog) {
                    // DO SOME STUFF HERE
                }
            });

            String id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
            StorageReference folder = FirebaseStorage.getInstance().getReference().child("Users").child(id);
            final StorageReference file_name = folder.child(imageUri.getLastPathSegment());
            file_name.putFile(imageUri).addOnProgressListener(taskSnapshot -> {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()
                        / taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Exportando al " + (int)progress + "%");
            }).addOnSuccessListener(taskSnapshot -> file_name.getDownloadUrl().addOnSuccessListener(uri -> {
                //Enviamos a la base de datos la url de la imagen
                setDataImageBase(String.valueOf(uri));
                progressDialog.dismiss();
                msgToast("Se subio correctamente");
            })).addOnFailureListener(e -> {
                // Error, Image not uploaded
                progressDialog.dismiss();
                msgToast("Error en la carga " + e.getMessage());
            });

        }
    }



    /*Agregamos la Url de la imagen a la base de datos*/
    private void setDataImageBase(String link){
        Map<String, Object> data = new HashMap<>();
        data.put("ImageMain", link);
        String id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
        userDataBase.child("Users").child(id).child("ImageData").child("imgPerfil").setValue(data).addOnCompleteListener(task1 -> msgToast("Datos actualizados"));
        ChangeActivity.build(getApplicationContext(), Login.class).start();
    }
    /*Termina codigo de la seleccion de imagen y envio a la base de datos*/
    /*--------------------*/





    /*Convertimos a string el contenido de los campos de texto*/
    private void getString(){
        userString = user.getText().toString();
        userNameString = userName.getText().toString();
        String userPasswordString = userPassword.getText().toString();
    }


    /*Agregamos la informacion a la base de datos*/
    private void setDataBase(){
        Map<String, Object> data = new HashMap<>();
        data.put("user", userString);
        data.put("userName", userNameString);
        String id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
        userDataBase.child("Users").child(id).child("PerfilData").setValue(data).addOnCompleteListener(task1 -> msgToast("Datos actualizados"));
    }
    /*Eliminamos al usuario*/
    private void DeleteUser() {

    }


    /*Mostramos la contraseña del campo de texto*/
    @SuppressLint("ClickableViewAccessibility")
    private void ShowPassword (View elemTouch, EditText passwordToShow){
        elemTouch.setOnTouchListener((v, event) -> {
            switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN:
                    passwordToShow.setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
                case MotionEvent.ACTION_UP:
                    passwordToShow.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    break;
            }
            return true;
        });
    }
    /*Variable para generar el mensaje Toast*/
    private void msgToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }
    /*-------------------------------------------------------------------------------*/
}