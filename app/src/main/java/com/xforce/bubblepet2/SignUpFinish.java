package com.xforce.bubblepet2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.xforce.bubblepet2.helpers.ChangeActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpFinish extends AppCompatActivity {
    /*-------------------------------------------------------------------------------*/
    /*Variables para texto, campos de texto y contenedores*/
    private EditText user;
    private EditText userName;
    private String userString = " ";
    private String userNameString = " ";

    //ImageView para la imagen de usuario
   /* Uri imageUri;
    View changeImageUser;//Boton para selecionar imagen
    ImageView contImageUser;//Contenedor con la imagen del usuario
    int SELECT_PICTURE = 200;// constant to compare the activity result code
    private static final int File = 1;*/

    /*Acceso a Firebase*/
    FirebaseAuth userAuth;
    DatabaseReference userDataBase;
    /*-------------------------------------------------------------------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_finish);

        userAuth = FirebaseAuth.getInstance();
        userDataBase = FirebaseDatabase.getInstance().getReference();
        user = findViewById(R.id.userSignUpFinish);
        userName = findViewById(R.id.userNameSignUpFinish);
        View btResetTextUser = findViewById(R.id.resetText1);
        View btResetTextUserName = findViewById(R.id.resetText2);
        TextView btSignUpFinish = findViewById(R.id.btSignUpFinish);
        /*changeImageUser = findViewById(R.id.selectImageEditProfile);
        contImageUser = findViewById(R.id.imgPhotoUserEditProfile);*/


        btSignUpFinish.setOnClickListener(view -> {
            userString = user.getText().toString();
            userNameString = userName.getText().toString();

            if(!userString.isEmpty() && !userNameString.isEmpty()){
                userDataBase.child("GlobalUse").addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String image;

                            /*Obtenemos los valores del usuario convertidos a cadena*/
                            image = Objects.requireNonNull(snapshot.child("imageDefault").getValue()).toString();
                            Map<String, Object> data = new HashMap<>();
                            data.put("ImageMain", image);
                            String id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
                            userDataBase.child("Users").child(id).child("ImageData").child("imgPerfil").setValue(data).addOnCompleteListener(task1 -> msgToast("Todo listo"));


                        }else {
                            msgToast("Creación de usuario cancelado");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        msgToast("Error de carga");
                    }
                });

                Map<String, Object> data = new HashMap<>();
                data.put("user", userString);
                data.put("userName", userNameString);

                String id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
                userDataBase.child("Users").child(id).child("PerfilData").setValue(data).addOnCompleteListener(task1 -> {
                    msgToast("Bienvenido");
                });

            }else{
                if (userString.isEmpty()){
                    msgToast("Ingrese su nombre");
                    user.requestFocus();
                }else {
                    msgToast("Ingrese su nombre de usuario");
                    userName.requestFocus();
                }
            }

            ChangeActivity.build(getApplicationContext(),MainActivity.class).start();
        });
        ResetText(btResetTextUser,user);
        ResetText(btResetTextUserName,userName);
        /*changeImageUser.setOnClickListener(v ->{

            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            // pass the constant to compare it with the returned requestCode
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);

        });*/
    }

    @Override public void onBackPressed() {

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
    /*-------------------------------------------------------------------------------*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*
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
        */
    }

    /*Agregamos la Url de la imagen a la base de datos*/
    private void setDataImageBase(String link){
        Map<String, Object> data = new HashMap<>();
        data.put("ImageMain", link);
        String id = Objects.requireNonNull(userAuth.getCurrentUser()).getUid();
        userDataBase.child("Users").child(id).child("ImageData").child("imgPerfil").setValue(data).addOnCompleteListener(task1 -> msgToast("Datos actualizados"));

    }

    /*Termina codigo de la seleccion de imagen y envio a la base de datos*/
    /*--------------------*/

    private void ResetText (View elemTouch, EditText textToReset){
        elemTouch.setOnClickListener(view -> textToReset.setText(""));
    }

    private void msgToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }
    /*-------------------------------------------------------------------------------*/
}