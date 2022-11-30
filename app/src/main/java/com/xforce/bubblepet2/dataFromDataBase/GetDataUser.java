package com.xforce.bubblepet2.dataFromDataBase;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.xforce.bubblepet2.MainActivity;
import com.xforce.bubblepet2.R;
import com.xforce.bubblepet2.helpers.ChangeActivity;
import com.xforce.bubblepet2.helpers.msgToast;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GetDataUser {

    //Public DataUser------------------------------------------

    public static class DataOnFragment extends Fragment{


        //Variables--------------------------------------------

        private final View contextView;
        private Context context;
        private Class<?> cls;
        Fragment fragment;
        TextView textView;
        EditText editText;
        ImageView imageView;
        String id,val,elementPathValue,message,objectString,valueString;
        Map<String, Object> data = new HashMap<>();
        int elementIdValue;
        boolean elementId = false;
        boolean elementPath = false;
        boolean isChangeActivity = false;
        boolean isSetMessage = false;
        boolean isObjectString = false;
        boolean isValueString = false;
        boolean useContext = false;
        FirebaseAuth userAuth;
        DatabaseReference userDataBase;

        //Privates---------------------------------------------

        private DataOnFragment(@NonNull View contextView){
            this.userAuth = getInstance();
            this.userDataBase = getDataBaseRef();
            this.id = getUserId();
            this.contextView = contextView;
        }

        private DataOnFragment(@NonNull View contextView, @NonNull Fragment fragment){
            this.userAuth = getInstance();
            this.userDataBase = getDataBaseRef();
            this.id = getUserId();
            this.contextView = contextView;
            this.context = fragment.requireActivity().getApplicationContext();
            this.useContext = true;
            this.fragment = fragment;
        }

        //Public static----------------------------------------

        public static DataOnFragment build(@NonNull View view){
            return new DataOnFragment(view);

        }

        public static DataOnFragment build(@NonNull View contextView, @NonNull Fragment fragment){
            return new DataOnFragment(contextView,fragment);

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

        public static boolean isValidContextForGlide(final Context context){
            if (context == null){
                return false;
            }

            if (context instanceof Activity){
                final Activity activity = (Activity) context;
                return !activity.isDestroyed() || !activity.isFinishing();
            }


            return true;
        }

        //Public DataOnFragment------------------------------------------

        public DataOnFragment setChangeActivity(@NonNull Class<?> cls){
            this.cls = cls;
            this.isChangeActivity = true;
            return this;
        }

        public DataOnFragment setMessage(String message){
            this.message = message;
            this.isSetMessage = true;
            return this;
        }

        public DataOnFragment setChild(String object, String string){
            this.objectString = object;
            this.valueString = string;
            this.isObjectString = true;
            this.data.put(objectString, valueString);
            return this;
        }

        public DataOnFragment setChild(String string){
            this.valueString = string;
            this.isValueString = true;
            return this;
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

        //Public void------------------------------------------

        public void getData(){
            userDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        msgToast.build(contextView.getContext()).message("Este elemento no existe");
                    }
                    else {
                        if (elementId && elementPath){
                            if (snapshot.child(elementPathValue).exists()){
                                if (contextView.findViewById(elementIdValue) instanceof TextView) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    textView = contextView.findViewById(elementIdValue);
                                    textView.setText(val);

                                }
                                else if (contextView.findViewById(elementIdValue) instanceof EditText) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    editText = contextView.findViewById(elementIdValue);
                                    editText.setText(val);

                                }
                                else if (contextView.findViewById(elementIdValue) instanceof ImageView) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    imageView = contextView.findViewById(elementIdValue);
                                    if (GetDataUser.DataOnFragment.isValidContextForGlide(context)){
                                        Glide.with(context).load(val).into(imageView);
                                    }

                                }
                                else {
                                    msgToast.build(contextView.getContext()).message("El Objeto id no es compatible");
                                }
                            }else {
                                msgToast.build(contextView.getContext()).message("El Path de datos no exite");
                                if (contextView.findViewById(elementIdValue) instanceof TextView) {

                                    val = "Datos no encontrados";
                                    textView = contextView.findViewById(elementIdValue);
                                    textView.setText(val);
                                    textView.setTextColor(ContextCompat.getColor(contextView.getContext(),R.color.rojo10));

                                }
                                else if (contextView.findViewById(elementIdValue) instanceof EditText) {

                                    val = "Datos no encontrados";
                                    editText = contextView.findViewById(elementIdValue);
                                    editText.setText("");
                                    editText.setHint(val);
                                    editText.setHintTextColor(ContextCompat.getColor(contextView.getContext(),R.color.rojo10));

                                }
                                else if (contextView.findViewById(elementIdValue) instanceof ImageView) {

                                    Drawable val = ContextCompat.getDrawable(contextView.getContext(),R.drawable.default_image_global);
                                    imageView = contextView.findViewById(elementIdValue);
                                    if (GetDataUser.DataOnFragment.isValidContextForGlide(context)){
                                        Glide.with(context).load(val).into(imageView);
                                    }

                                }
                                else {
                                    msgToast.build(contextView.getContext()).message("El Objeto id no es compatible");
                                }
                            }
                        }
                        else if (elementId){
                            msgToast.build(contextView.getContext()).message("Falta proporcionar el dato de setValuePath(String _path)");
                        }
                        else if (elementPath){
                            msgToast.build(contextView.getContext()).message("Falta proporcionar el dato de setElementbyId(int _id)");
                        }
                        else{
                            msgToast.build(contextView.getContext()).message("Falta proporcionar los datos de setElementbyId(int _id) y setValuePath(String _path)");
                        }
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
                    if (!snapshot.exists()) {
                        value[0] = "Este elemento no existe";
                    }else {
                        if (!elementPath){
                            value[0] = "Falta proporcionar el dato de setValuePath(String _path)";
                        }else{
                            if (!snapshot.child(elementPathValue).exists()){
                                value[0] = "El Path de datos no exite";
                            }else {
                                value[0] = String.valueOf(Objects.requireNonNull(snapshot.child(elementPathValue).getValue()));
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    value[0] = "Error de carga";
                }
            });
            return value[0];
        }

        public void setData(){
            if (!useContext){
                msgToast.build(contextView.getContext()).message("Se recomienda utilizar build(@NonNull View contextView, @NonNull Fragment fragment)");
            }
            else {
                if (isObjectString){
                    if (!elementPath){
                        msgToast.build(context).message("Error causado por introducción nula de datos");
                        msgToast.build(context).message("Se solicita usar: setValuePath()");
                    }else {
                        userDataBase.child("Users").child(id).child(elementPathValue).setValue(data).addOnCompleteListener(task -> {
                            if (isChangeActivity){
                                ChangeActivity.build(context,cls).start();
                            }

                            if (isSetMessage){
                                msgToast.build(context).message(message);
                            }
                        });
                    }
                }
                else if (isValueString){
                    String data = valueString;
                    if (!elementPath){
                        msgToast.build(context).message("Error causado por introducción nula de datos");
                        msgToast.build(context).message("Se solicita usar: setValuePath()");
                    }else {
                        userDataBase.child("Users").child(id).child(elementPathValue).setValue(data).addOnCompleteListener(task -> {
                            if (isChangeActivity){
                                ChangeActivity.build(context,cls).start();
                            }

                            if (isSetMessage){
                                msgToast.build(context).message(message);
                            }
                        });
                    }
                }
                else {
                    msgToast.build(context).message("Error causado por introducción nula de datos");
                    msgToast.build(context).message("Se solicita usar: setChild()");
                }
            }
        }



    }

    public static class DataOnActivity extends AppCompatActivity{

        //Variables--------------------------------------------

        private final Context context;
        private Class<?> cls;
        TextView textView;
        EditText editText;
        ImageView imageView,contImageUser;
        Activity activity;
        String id,val,elementPathValue,message,objectString,valueString,
                credentialEmail,credentialPassword, deleteFile;
        Map<String, Object> data = new HashMap<>();
        Uri imageUri;
        int elementIdValue;
        int SELECT_PICTURE = 200;
        boolean elementId = false;
        boolean elementPath = false;
        boolean elementImagePath = false;
        boolean isChangeActivity = false;
        boolean isSetMessage = false;
        boolean isObjectString = false;
        boolean isChildMap = false;
        boolean isValueString = false;
        boolean useCredentialEmail = false;
        boolean useCredentialPassword = false;
        boolean useLogIt = false;
        boolean useDeleteFile = false;
        FirebaseAuth userAuth;
        DatabaseReference userDataBase;

        //Privates---------------------------------------------

        private DataOnActivity(@NonNull Context contextView, Activity activity){
            this.userAuth = FirebaseAuth.getInstance();
            this.userDataBase = getDataBaseRef();
            this.id = getUserId();
            this.context = contextView;
            this.activity = activity;
        }

        //Public static----------------------------------------

        public static DataOnActivity build(@NonNull Context context,Activity activity){
            return new DataOnActivity(context,activity);
        }

        public static FirebaseAuth getInstance(){

            return FirebaseAuth.getInstance();
        }

        public static DatabaseReference getDataBaseRef(){
            return FirebaseDatabase.getInstance().getReference();
        }

        public static StorageReference getStorageRef(){
            return FirebaseStorage.getInstance().getReference();
        }

        public static FirebaseUser getCurrentUser(){
            return getInstance().getCurrentUser();

        }

        public static String getUserId(){
            return getCurrentUser().getUid();

        }

        //Public DataOnActivity------------------------------------------

        public DataOnActivity setChangeActivity(@NonNull Class<?> cls){
            this.cls = cls;
            this.isChangeActivity = true;
            return this;
        }

        public DataOnActivity setMessage(@NonNull String message){
            this.message = message;
            this.isSetMessage = true;
            return this;
        }

        public DataOnActivity setChild(@NonNull String object,@NonNull String string){
            this.objectString = object;
            this.valueString = string;
            this.isObjectString = true;
            this.data.put(objectString, valueString);
            return this;
        }

        public DataOnActivity setChild(@NonNull Map<String, Object> map){
            this.isChildMap = true;
            this.data.putAll(map);
            return this;
        }

        public DataOnActivity setChild(@NonNull String object,@IdRes int id){
            View temp = activity.findViewById(id);
            if (temp instanceof TextView){
                this.objectString = object;
                this.valueString = ((TextView) temp).getText().toString();
            }
            this.isObjectString = true;
            this.data.put(objectString, valueString);
            return this;
        }

        public DataOnActivity setChild(@NonNull String string){
            this.valueString = string;
            this.isValueString = true;
            return this;
        }

        public DataOnActivity setValuePath(@NonNull String path){
            this.elementPathValue = path;
            this.elementPath = true;
            return this;
        }

        public DataOnActivity chooseElementbyId(@IdRes int id){
            this.elementIdValue = id;
            this.elementId = true;
            return this;
        }

        public DataOnActivity getCredentials(@NonNull String email,@NonNull String password){
            this.credentialEmail = email;
            this.credentialPassword = password;
            this.useCredentialEmail = true;
            this.useCredentialPassword = true;
            return this;
        }

        public DataOnActivity logIt(boolean result){
            this.useLogIt = result;
            return this;
        }

        public DataOnActivity pathFileToDelete(String url){
            this.deleteFile = url;
            this.useDeleteFile = true;
            return this;
        }


        //Public void------------------------------------------

        public void getData(){
            userDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        msgToast.build(context).message("Este elemento no existe");
                    }
                    else {
                        if (elementId && elementPath){
                            if (snapshot.child(elementPathValue).exists()){
                                if (activity.findViewById(elementIdValue) instanceof TextView) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    textView = activity.findViewById(elementIdValue);
                                    textView.setText(val);

                                }else if (activity.findViewById(elementIdValue) instanceof EditText) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    editText = activity.findViewById(elementIdValue);
                                    editText.setText(val);

                                }else if (activity.findViewById(elementIdValue) instanceof ImageView) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    imageView = activity.findViewById(elementIdValue);
                                    Glide.with(context).load(val).into(imageView);

                                }else {
                                    msgToast.build(context).message("El Objeto id no es compatible");
                                }
                            }
                            else {
                                msgToast.build(context).message("El Path de datos no exite");
                                if (activity.findViewById(elementIdValue) instanceof TextView) {

                                    val = "Datos no encontrados";
                                    textView = activity.findViewById(elementIdValue);
                                    textView.setText(val);
                                    textView.setTextColor(ContextCompat.getColor(context,R.color.rojo10));

                                }
                                else if (activity.findViewById(elementIdValue) instanceof EditText) {

                                    val = "Datos no encontrados";
                                    editText = activity.findViewById(elementIdValue);
                                    editText.setText("");
                                    editText.setHint(val);
                                    editText.setHintTextColor(ContextCompat.getColor(context,R.color.rojo10));

                                }
                                else if (activity.findViewById(elementIdValue) instanceof ImageView) {

                                    Drawable val = ContextCompat.getDrawable(context,R.drawable.default_image_global);
                                    imageView = activity.findViewById(elementIdValue);
                                    Glide.with(context).load(val).into(imageView);

                                }
                                else {
                                    msgToast.build(context).message("El Objeto id no es compatible");
                                }
                            }
                        }
                        else if (elementId){
                            msgToast.build(context).message("Falta proporcionar el dato de setValuePath(String _path)");
                        }
                        else if (elementPath){
                            msgToast.build(context).message("Falta proporcionar el dato de setElementbyId(int _id)");
                        }
                        else{
                            msgToast.build(context).message("Falta proporcionar los datos de setElementbyId(int _id) y setValuePath(String _path)");
                        }
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
                    if (!snapshot.exists()) {
                        value[0] = "Este elemento no existe";
                    }else {
                        if (!elementPath){
                            value[0] = "Falta proporcionar el dato de setValuePath(String _path)";
                        }else{
                            if (!snapshot.child(elementPathValue).exists()){
                                value[0] = "El Path de datos no exite";
                            }else {
                                value[0] = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    value[0] = "Error de carga";
                }
            });
            return value[0];
        }

        public void uploadData(){

            if (isObjectString){
                if (!elementPath){
                    Log.d("GetDataUser","(Data error) Error causado por introducción nula de datos. Se solicita usar: setValuePath()");
                }
                else {
                    userDataBase.child("Users").child(id).child(elementPathValue).setValue(data).addOnCompleteListener(task -> {
                        if (isChangeActivity){
                            ChangeActivity.build(context,cls).start();
                        }

                        if (isSetMessage){
                            msgToast.build(context).message(message);
                        }

                        if (useLogIt){
                            Log.d("GetDataUser","Se guardaron exitosamente los cambios");
                        }

                    });
                }
            }
            else if (isChildMap){
                if (!elementPath){
                    Log.d("GetDataUser","(Data error) Error causado por introducción nula de datos. Se solicita usar: setValuePath()");
                }else {
                    userDataBase.child("Users").child(id).child(elementPathValue).setValue(data).addOnCompleteListener(task -> {
                        if (isChangeActivity){
                            ChangeActivity.build(context,cls).start();
                        }

                        if (isSetMessage){
                            msgToast.build(context).message(message);
                        }

                        if (useLogIt){
                            Log.d("GetDataUser","Se guardaron exitosamente los cambios");
                        }
                    });
                }
            }
            else if (isValueString){
                String dataString = valueString;
                if (!elementPath){
                    Log.d("GetDataUser","(Data error) Error causado por introducción nula de datos. Se solicita usar: setValuePath()");
                }else {
                    userDataBase.child("Users").child(id).child(elementPathValue).setValue(dataString).addOnCompleteListener(task -> {
                        if (isChangeActivity){
                            ChangeActivity.build(context,cls).start();
                        }

                        if (isSetMessage){
                            msgToast.build(context).message(message);
                        }

                        if (useLogIt){
                            Log.d("GetDataUser","Se guardaron exitosamente los cambios");
                        }
                    });
                }
            }
            else {
                msgToast.build(context).message("Error causado por introducción nula de datos");
                msgToast.build(context).message("Se solicita usar: setChild()");
            }

        }

        public void uploadImage(int requestCode, int resultCode, final Intent data){
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
                    GetDataUser
                            .DataOnActivity
                            .build(context,activity)
                            .setValuePath("ImageData/imgPerfil")
                            .setChild("ImageMain", String.valueOf(uri))
                            .uploadData();

                    GetDataUser
                            .DataOnActivity
                            .build(context,activity)
                            .setValuePath("ImageData/uploadeds")
                            .setChild("1", String.valueOf(uri))
                            .setChangeActivity(MainActivity.class)
                            .uploadData();

                    progressDialog.dismiss();

                })).addOnFailureListener(e -> {
                    // Error, Image not uploaded
                    progressDialog.dismiss();
                });

            }
        }

        public void signOut(){
            if (!isChangeActivity){
                msgToast.build(context).message("Requiere una clase de retorno");
            }
            else {
                getInstance().signOut();
                ChangeActivity.build(context,cls).start();
            }

            if (isSetMessage){
                msgToast.build(context).message(message);
            }
        }

        public void deleteFile(){
            if (useDeleteFile){
                StorageReference storageReference = getStorageRef().getStorage().getReferenceFromUrl(deleteFile);
                storageReference.delete().addOnSuccessListener(aVoid -> {
                    if (useLogIt) {
                        Log.d("Eliminado", "Archivo eliminado");
                    }
                }).addOnFailureListener(exception -> {
                    if (useLogIt) {
                        Log.d("EliminadoError","Ocurrio un error");
                    }
                });
            }
            else {
                Log.e("GetDataUser","[deleteFile] Es nesesario utilizar una url de referencia mediante .pathFileToDelete(String url);");
                Log.e("GetDataUser","[deleteFile] Es allí donde ubicaras la url de tu archivo almacenado en la base de datos");
            }
        }

        public void deleteUser(){




            /*userDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        msgToast.build(context).message("Estos datos no existen");
                    }else {
                        String mail;
                        String password;
                        mail = Objects.requireNonNull(snapshot.child("CountData").child("userMail").getValue()).toString();
                        password = Objects.requireNonNull(snapshot.child("CountData").child("userPassword").getValue()).toString();
                        final FirebaseUser user = getCurrentUser();
                        AuthCredential credential = EmailAuthProvider.getCredential(credentialEmail, credentialPassword);
                        if (user != null) {
                            if (!isChangeActivity){
                                msgToast.build(context).message("Nesesita agregar una clase de retorno al eliminar la cuenta");
                                msgToast.build(context).message("Se recomienda usar setChangeActivity(@NonNull Class<?> cls)");
                            }
                            else {
                                user.reauthenticate(credential).addOnCompleteListener(task -> user.delete().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Log.d("GetDataUser.DataOnActivity.deleteUser(void)")
                                        ChangeActivity.build(context,cls).start();
                                    }
                                }));
                            }
                        }
                        userDataBase.child("Users").child(id).removeValue();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    msgToast.build(context).message("Error de carga");
                }
            });*/
        }

        public interface MyCallback {
            void onReadData(DataSnapshot value);

            void onChildrenCount(int count);

            void onChildrenTotalCount(int totalCount);

            void onHashMapValue(Map<String, Object> map);

            void onHashMapValue(String key, Object value);
        }

        public interface MyCallback2 {
            void onChildrenTotalCount(int totalCount);
        }

        public void readData(MyCallback myCallback) {
            FirebaseDatabase.getInstance().getReference().child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        Log.e("GetDataUser", "(Database error) Este usuario no existe; Se recomienda revisar su base de datos.");
                    }else {
                        if (!elementPath){
                            Log.e("GetDataUser", "(Error) Falta proporcionar el dato .setValuePath(@NonNull String path); Asegurece de que este correcto el path ingresado");
                        }else{
                            if (!snapshot.child(elementPathValue).exists()){
                                Log.e("GetDataUser", "(Path error) Asegurece de que este correcto el path ingresado en .setValuePath(@NonNull String path)");
                            }
                            else {
                                for (int i = 0;i<snapshot.child(elementPathValue).getChildrenCount(); i++){
                                    if (useLogIt){Log.i("GetDataUser","[onChildrenCount]= Esta ruta seleccionada contiene: " + i + " elementos");}
                                    myCallback.onChildrenCount(i);
                                }
                                for (DataSnapshot child: snapshot.child(elementPathValue).getChildren()) {
                                    Map<String, Object> values = new HashMap<>();
                                    values.put(child.getKey(),child.getValue());
                                    if (useLogIt){Log.i("GetDataUser","[onHashMapValue]= " + values);}
                                    myCallback.onHashMapValue(child.getKey(),child.getValue());
                                }
                                for (DataSnapshot child: snapshot.child(elementPathValue).getChildren()) {
                                    if (useLogIt){Log.i("GetDataUser","[onReadData]= " + child);}
                                    myCallback.onReadData(child);
                                }

                                if (useLogIt){Log.i("GetDataUser","[onChildrenTotalCount]= " + Math.toIntExact(snapshot.child(elementPathValue).getChildrenCount()));}
                                myCallback.onChildrenTotalCount(Math.toIntExact(snapshot.child(elementPathValue).getChildrenCount()));

                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("GetDataUser", "(Error) Obtención de datos cancelada; " + error);
                }
            });
        }


        public void readData2(MyCallback2 myCallback) {
            FirebaseDatabase.getInstance().getReference().child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        Log.e("GetDataUser", "(Database error) Este usuario no existe; Se recomienda revisar su base de datos.");
                    }else {
                        if (!elementPath){
                            Log.e("GetDataUser", "(Error) Falta proporcionar el dato .setValuePath(@NonNull String path); Asegurece de que este correcto el path ingresado");
                        }else{
                            if (!snapshot.child(elementPathValue).exists()){
                                Log.e("GetDataUser", "(Path error) Asegurece de que este correcto el path ingresado en .setValuePath(@NonNull String path)");
                            }
                            else {
                                if (useLogIt){Log.i("GetDataUser","[onChildrenTotalCount]= " + Math.toIntExact(snapshot.child(elementPathValue).getChildrenCount()));}
                                myCallback.onChildrenTotalCount(Math.toIntExact(snapshot.child(elementPathValue).getChildrenCount()));

                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("GetDataUser", "(Error) Obtención de datos cancelada; " + error);
                }
            });
        }

    }

}
