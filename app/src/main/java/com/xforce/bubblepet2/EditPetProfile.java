package com.xforce.bubblepet2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.xforce.bubblepet2.dataFromDataBase.GetDataUser;

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


        GetDataUser.DataOnActivity.build(getApplicationContext(),EditPetProfile.this).setElementbyId(petName.getId()).setValuePath("PetData/petName").getData();
        GetDataUser.DataOnActivity.build(getApplicationContext(),EditPetProfile.this).setElementbyId(petAge.getId()).setValuePath("PetData/petEge").getData();
        GetDataUser.DataOnActivity.build(getApplicationContext(),EditPetProfile.this).setElementbyId(petColor.getId()).setValuePath("PetData/petColor").getData();
        GetDataUser.DataOnActivity.build(getApplicationContext(),EditPetProfile.this).setElementbyId(petBreed.getId()).setValuePath("PetData/petBreed").getData();
        GetDataUser.DataOnActivity.build(getApplicationContext(),EditPetProfile.this).setElementbyId(petHealth.getId()).setValuePath("PetData/petHealth").getData();
        GetDataUser.DataOnActivity.build(getApplicationContext(),EditPetProfile.this).setElementbyId(contImagePet.getId()).setValuePath("ImageData/imgPetPerfil/ImageMain").getData();


        saveDatosButton.setOnClickListener(v ->{
            petNameString = petName.getText().toString();
            petEgeString = petAge.getText().toString();
            petColorString = petColor.getText().toString();
            petBreedString = petBreed.getText().toString();
            petHealthString = petHealth.getText().toString();

            SetDataBase();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        });/*Actualizamos los datos del perfil*/
        ResetText(btResetTextName,petName);/*Reiniciamos el texto*/
        ResetText(btResetTextEge,petAge);/*Reiniciamos el texto*/
        ResetText(btResetTextColor,petColor);/*Reiniciamos el texto*/
        ResetText(btResetTextBreed,petBreed);/*Reiniciamos el texto*/
        ResetText(btResetTextHealth,petHealth);/*Reiniciamos el texto*/
        changeImagePet.setOnClickListener(v ->{

            openGallery();

        });/*Elegimos la nueva imagen de usuario*/

    }
    @Override public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
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
            contImagePet.setImageURI(imageUri);

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

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
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