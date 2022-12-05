package com.xforce.bubblepet2.dataFromDataBase;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import com.xforce.bubblepet2.R;
import com.xforce.bubblepet2.helpers.ChangeActivity;
import com.xforce.bubblepet2.helpers.URLValidator;
import com.xforce.bubblepet2.helpers.msgToast;
import com.xforce.bubblepet2.interfaces.CallbackDataUser;

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

        private Context context;
        private Class<?> cls;
        Activity activity;
        TextView textView;
        EditText editText;
        Uri imageUri;
        ImageView imageView,contImageUser;
        Map<String, Object> data = new HashMap<>();
        String id,val,elementPathValue,message,objectString,valueString,credentialEmail,
                credentialPassword, deleteFile;
        int elementIdValue,SELECT_PICTURE = 200;
        boolean elementId = false;
        boolean elementPath = false;
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
            if (isUserLogged()){
                this.userAuth = getInstanceFA();
                this.userDataBase = getDataBaseRef();
                this.id = getUserId();
                this.context = contextView;
                this.activity = activity;
            }
            else {
                Log.e("GetDataUser","[build] No se encontro a ningun usuario logeado en la sesión actual;");
                Log.e("GetDataUser","[build] Sin ningun usuario logeado en Firebase no se podra trabajar con esta clase.");
            }
        }

        private DataOnActivity(@NonNull View contextView){
            if (isUserLogged()){
                Activity activityNew = null;
                this.userAuth = getInstanceFA();
                this.userDataBase = getDataBaseRef();
                this.id = getUserId();
                this.context = contextView.getContext();
                if (contextView.getContext() instanceof Activity){
                    activityNew = (Activity) contextView.getContext();
                    this.activity =  activityNew;
                }
            }
            else {
                Log.e("GetDataUser","[build] No se encontro a ningun usuario logeado en la sesión actual;");
                Log.e("GetDataUser","[build] Sin ningun usuario logeado en Firebase no se podra trabajar con esta clase.");
            }
        }

        //Public static----------------------------------------

        public static DataOnActivity build(@NonNull Context context,Activity activity){
            return new DataOnActivity(context,activity);
        }

        public static DataOnActivity build(@NonNull View contextView){
            return new DataOnActivity(contextView);
        }

        public static FirebaseAuth getInstanceFA(){
            return FirebaseAuth.getInstance();

        }

        public static FirebaseDatabase getInstanceFD(){
            return FirebaseDatabase.getInstance();

        }

        public static DatabaseReference getDataBaseRef(){
            return FirebaseDatabase.getInstance().getReference();
        }

        public static FirebaseDatabase getDataBase(){
            return FirebaseDatabase.getInstance().getReference().getDatabase();
        }

        public static StorageReference getStorageRef(){
            return FirebaseStorage.getInstance().getReference();
        }

        public static FirebaseUser getCurrentUser(){
            return getInstanceFA().getCurrentUser();

        }

        public static String getUserId(){
            return getCurrentUser().getUid();

        }

        public static boolean isUserLogged(){
            final FirebaseUser user = getCurrentUser();
            return user != null;
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
            if (URLValidator.isValid(url)){
                this.deleteFile = url;
                this.useDeleteFile = true;
                return this;
            }
            else {
                this.useDeleteFile = false;
                Log.e("GetDataUser","[pathFileToDelete] Asegurece de que la url sea valida");
                return this;
            }
        }

        //Public void------------------------------------------

        public void getData(){
            userDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        Log.e("GetDataUser","[getData] No existen los datos del usuario");
                    }
                    else {
                        if (elementId && elementPath){
                            if (snapshot.child(elementPathValue).exists()){
                                if (activity.findViewById(elementIdValue) instanceof TextView) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    textView = activity.findViewById(elementIdValue);
                                    textView.setText(val);

                                }
                                else if (activity.findViewById(elementIdValue) instanceof EditText) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    editText = activity.findViewById(elementIdValue);
                                    editText.setText(val);

                                }
                                else if (activity.findViewById(elementIdValue) instanceof ImageView) {

                                    val = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                    imageView = activity.findViewById(elementIdValue);
                                    Glide.with(context).load(val).into(imageView);

                                }
                                else {
                                    Log.e("GetDataUser","[getData] La instancia del elemento no es compatible;");
                                    Log.e("GetDataUser","[getData] Instancias compatibles: TextView, EditText, ImageView");
                                }
                            }
                            else {
                                String value = "Datos no encontrados";
                                Log.e("GetDataUser","[getData] Error producido por datos no encontrados");
                                if (activity.findViewById(elementIdValue) instanceof TextView) {

                                    val = value;
                                    textView = activity.findViewById(elementIdValue);
                                    textView.setText(val);
                                    textView.setTextColor(ContextCompat.getColor(context,R.color.rojo10));

                                }
                                else if (activity.findViewById(elementIdValue) instanceof EditText) {

                                    val = value;
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
                                    Log.e("GetDataUser","[getData] La instancia del elemento no es compatible;");
                                    Log.e("GetDataUser","[getData] Instancias compatibles: TextView, EditText, ImageView");
                                }
                            }
                        }
                        else if (elementId){
                            Log.e("GetDataUser","[getData] Es nesesario insertar el dato: .setValuePath(String _path)");
                            Log.e("GetDataUser","[getData] Es mediante ese dato que se podra acceder a la información especifica");
                        }
                        else if (elementPath){
                            Log.e("GetDataUser","[getData] Es nesesario insertar el dato: .setElementbyId(int _id)");
                            Log.e("GetDataUser","[getData] Es mediante ese dato que se podra ingresar la información en el elemento seleccionado");
                        }
                        else{
                            Log.e("GetDataUser","[getData] Es nesesario insertar el dato: .setElementbyId(int _id) y .setValuePath(String _path))");
                            Log.e("GetDataUser","[getData] Estos datos son nesesarios para acceder al elemento al que se insertaran los valores obtenidos");
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("GetDataUser","[getData] Error de carga de datos");
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
                        Log.e("GetDataUser","[getString] No existen los datos del usuario");
                    }else {
                        if (!elementPath){
                            value[0] = "Error";
                            Log.e("GetDataUser","[getString] Es nesesario insertar el dato: .setValuePath(String _path)");
                            Log.e("GetDataUser","[getString] Es mediante ese dato que se podra acceder a la información especifica");
                        }else{
                            if (!snapshot.child(elementPathValue).exists()){
                                value[0] = "Error";
                                Log.e("GetDataUser","[getString] No existen los datos seleccionados");
                                Log.e("GetDataUser","[getString] Verifica que la ruta este correctamente escrita");
                            }else {
                                value[0] = Objects.requireNonNull(snapshot.child(elementPathValue).getValue()).toString();
                                if (useLogIt){
                                    Log.d("GetDataUser","[getString] Datos insertados correctamente");
                                }
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    value[0] = "Carga cancelada";
                    Log.e("GetDataUser","[getString] Error en la carga de los datos");
                }
            });
            return value[0];
        }

        public void uploadData(){

            if (isObjectString || isChildMap){
                if (!elementPath){
                    Log.e("GetDataUser","[uploadData] Es nesesario insertar el dato: .setValuePath(String _path)");
                    Log.e("GetDataUser","[uploadData] Es mediante ese dato que se podra acceder a la información especifica");
                }
                else {
                    //Se requiere: .setValuePath() y .setChild()
                    //Opcionales: .setChangeActivity(), .setMessage(), .logIt()
                    userDataBase.child("Users").child(id).child(elementPathValue).setValue(data).addOnCompleteListener(task -> {
                        if (isChangeActivity){
                            ChangeActivity.build(context,cls).start();
                        }

                        if (isSetMessage){
                            msgToast.build(context).message(message);
                        }

                        if (useLogIt){
                            Log.d("GetDataUser","[uploadData] Se guardaron exitosamente los cambios");
                        }

                    });
                }
            }
            else if (isValueString){
                String dataString = valueString;
                if (!elementPath){
                    Log.e("GetDataUser","[uploadData] Es nesesario insertar el dato: .setValuePath(String _path)");
                    Log.e("GetDataUser","[uploadData] Es mediante ese dato que se podra acceder a la información especifica");
                }else {
                    //Se requiere: .setValuePath() y .setChild()
                    //Opcionales: .setChangeActivity(), .setMessage(), .logIt()
                    userDataBase.child("Users").child(id).child(elementPathValue).setValue(dataString).addOnCompleteListener(task -> {
                        if (isChangeActivity){
                            ChangeActivity.build(context,cls).start();
                        }

                        if (isSetMessage){
                            msgToast.build(context).message(message);
                        }

                        if (useLogIt){
                            Log.d("GetDataUser","[uploadData] Se guardaron exitosamente los cambios");
                        }
                    });
                }
            }
            else {
                Log.e("GetDataUser","[uploadData] Es nesesario insertar el dato: .setChild()");
                Log.e("GetDataUser","[uploadData] Esto permite obtener los datos correctamente a insertar en la base de datos.");
            }

        }

        public void signOut(){
            if (!isChangeActivity){
                Log.e("GetDataUser","[signOut] Requiere una clase de retorno mediante: setChangeActivity(@NonNull Class<?> cls)");
            }
            else {
                getInstanceFA().signOut();
                ChangeActivity.build(context,cls).start();
            }

            if (isSetMessage){
                msgToast.build(context).message(message);
            }

            Log.d("GetDataUser","[deleteAllFiles] Se ha cerrado sesion con exito");

        }

        public void deleteChild(String pathValue){
            getDataBaseRef().child("Users").child(getUserId()).child(pathValue).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        getDataBaseRef().child("Users").child(getUserId()).child(pathValue).removeValue().addOnCompleteListener(task ->
                                Log.d("GetDataUser","[removeChild] Child eliminado correctamente"));
                    }
                    else {
                        Log.d("GetDataUser","[removeChild] Este child no existe");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("GetDataUser","[removeChild] Error al eliminar este child");
                }
            });
        }

        public void copyPasteDataBase(String pathToCopy,String pathToPaste){
            Map<String,Object> map = new HashMap<>();
            GetDataUser
                    .DataOnActivity
                    .build(context,activity)
                    .setValuePath(pathToCopy)
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
                        public void onHashMapValue(Map<String, Object> map) {

                        }

                        @Override
                        public void onHashMapValue(String key, Object value) {
                            map.put(key,value);

                            if ((isObjectString || isChildMap) && !isValueString){
                                data.putAll(map);
                                userDataBase.child("Users").child(id).child(pathToPaste).setValue(data).addOnCompleteListener(task -> {
                                    if (isChangeActivity){
                                        ChangeActivity.build(context,cls).start();
                                    }

                                    if (isSetMessage){
                                        msgToast.build(context).message(message);
                                    }

                                    if (useLogIt){
                                        Log.d("GetDataUser","[copyPasteDataBase] Se copiaron exitosamente los cambios con childs agregados");
                                    }

                                });
                            }
                            else if (isObjectString || isChildMap){
                                Log.e("GetDataUser","[copyPasteDataBase] No es válido introducir los datos ingresados desde: setChild(@NonNull String string)");
                            }
                            else {
                                userDataBase.child("Users").child(id).child(pathToPaste).setValue(map).addOnCompleteListener(task -> {
                                    if (isChangeActivity){
                                        ChangeActivity.build(context,cls).start();
                                    }

                                    if (isSetMessage){
                                        msgToast.build(context).message(message);
                                    }

                                    if (useLogIt){
                                        Log.d("GetDataUser","[copyPasteDataBase] Se copiaron exitosamente los cambios sin childs agregados");
                                    }

                                });
                            }
                        }

                    });
        }

        public void deleteAllFiles(String filesPath){
            GetDataUser
                    .DataOnActivity
                    .build(context,activity)
                    .setValuePath(filesPath)
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
                        public void onHashMapValue(Map<String, Object> map) {

                        }

                        @Override
                        public void onHashMapValue(String key, Object value) {
                            GetDataUser
                                    .DataOnActivity
                                    .build(context,activity)
                                    .pathFileToDelete(String.valueOf(value))
                                    .deleteFile();

                            GetDataUser
                                    .DataOnActivity
                                    .build(context,activity)
                                    .deleteChild(filesPath);

                            if (isChangeActivity){
                                ChangeActivity.build(context,cls).start();
                            }

                            if (isSetMessage){
                                msgToast.build(context).message(message);
                            }

                            Log.d("GetDataUser","[deleteAllFiles] Se eliminaron todos los archivos");

                        }

                    });
        }

        public void deleteFile(){
            if (useDeleteFile){

                StorageReference storageReference = getStorageRef().getStorage().getReferenceFromUrl(deleteFile);
                storageReference.delete().addOnSuccessListener(aVoid -> {
                    if (useLogIt) {
                        Log.d("GetDataUser", "[deleteFile] Archivo eliminado con exito");
                    }

                    if (isChangeActivity){
                        ChangeActivity.build(context,cls).start();
                    }

                    if (isSetMessage){
                        msgToast.build(context).message(message);
                    }
                }).addOnFailureListener(exception -> {
                    Log.e("GetDataUser", "[deleteFile] Error al intentar borrar el archivo");
                });
            }
            else {
                Log.e("GetDataUser","[deleteFile] Es nesesario utilizar una URL de referencia mediante: .pathFileToDelete(String url);");
                Log.e("GetDataUser","[deleteFile] Es allí donde ubicaras la URL de tu archivo almacenado en la base de datos FirebaseStorage");
            }
        }

        public void deleteUserAccount(String filesPath){

            userDataBase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        Log.e("GetDataUser","[deleteUser] No existen los datos del usuario");
                    }
                    else {
                        final FirebaseUser user = getCurrentUser();
                        if ((useCredentialEmail && useCredentialPassword) && (user != null)){
                            AuthCredential credential = EmailAuthProvider.getCredential(credentialEmail, credentialPassword);
                            if (!isChangeActivity){
                                Log.e("GetDataUser","[deleteUser] Requiere una clase de retorno mediante: setChangeActivity(@NonNull Class<?> cls)");
                                Log.e("GetDataUser","[deleteUser] Es recomendable que retorne a los usuarios hasta su pantalla de Login");
                            }
                            else {
                                GetDataUser.DataOnActivity.build(context,activity).deleteAllFiles(filesPath);
                                user.reauthenticate(credential).addOnCompleteListener(task -> user.delete().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Log.d("GetDataUser","[deleteUser] Usuario eliminado con exito)");
                                        ChangeActivity.build(context,cls).start();
                                    }
                                }));
                            }
                        }
                        else if (useCredentialEmail && useCredentialPassword){
                            Log.e("GetDataUser","[deleteUser] Es nesesario que el usuario este loggeado");
                        }
                        else if ((user != null)){
                            Log.e("GetDataUser","[deleteUser] Es nesesario recopilar las credenciales del usuario: .getCredentials(@NonNull String email,@NonNull String password)");
                        }
                        else {
                            Log.e("GetDataUser","[deleteUser] Error en la solicitud de borrado de cuenta");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("GetDataUser","[deleteUser] Error de carga de datos");
                }
            });
        }

        public void readData(CallbackDataUser myCallback) {
            FirebaseDatabase.getInstance().getReference().child("Users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        Log.e("GetDataUser", "(Database error) Este usuario no existe; Se recomienda revisar su base de datos.");
                    }else {
                        if (!elementPath){
                            Log.e("GetDataUser", "(Error) Falta proporcionar el dato .setValuePath(@NonNull String path); Asegurece de que este correcto el path ingresado");
                        }else{
                            if (!snapshot.child(elementPathValue).exists()){
                                Log.e("GetDataUser", "(Path error) Esta ruta no existe; Asegurece de que este correcto el path ingresado en .setValuePath(@NonNull String path)");
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


    }

}
