package com.xforce.bubblepet2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity
        implements
        FragmentUserHome.OnFragmentInteractionListener,
        FragmentMenu.OnFragmentInteractionListener {


    FragmentUserHome fragmentUserHome;
    FragmentMenu fragmentMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*Botones y acciones*/
        fragmentUserHome = new FragmentUserHome();
        fragmentMenu = new FragmentMenu();
        getSupportFragmentManager().beginTransaction().add(R.id.Fragments,fragmentUserHome).commit();//Primera fragment a mostrar




    }




    /*Variable para generar el mensaje Toast*/
    private void msgToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }


    /*Metodo para crear la accion Onclick del los botones del footer.
     * Esto se encuentra iniciado dentro de cada boton en el xml*/
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.editButton:
                Intent inten = new Intent(getApplicationContext(), EditPetProfile.class);
                inten.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inten);
                break;
            case R.id.editPerfilButton:
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.menuButton:
                transaction.replace(R.id.Fragments,fragmentMenu);
                break;
            case R.id.userHomeButton:
                transaction.replace(R.id.Fragments,fragmentUserHome);
                break;
        }
        transaction.commit();
    }


}