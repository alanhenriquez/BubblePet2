package com.xforce.bubblepet2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.xforce.bubblepet2.helpers.ChangeActivity;

public class MainActivity extends AppCompatActivity
        implements
        FragmentUserHome.OnFragmentInteractionListener,
        FragmentMenu.OnFragmentInteractionListener,
        FragmentFeed.OnFragmentInteractionListener{


    FragmentUserHome fragmentUserHome;
    FragmentMenu fragmentMenu;
    FragmentFeed fragmentFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Botones y acciones*/
        fragmentUserHome = new FragmentUserHome();
        fragmentMenu = new FragmentMenu();
        fragmentFeed = new FragmentFeed();
        getSupportFragmentManager().beginTransaction().add(R.id.Fragments,fragmentUserHome).commit();//Primera fragment a mostrar

    }

    /*Metodo para crear la accion Onclick del los botones del footer.
     * Esto se encuentra iniciado dentro de cada boton en el xml*/
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.editButton:
                ChangeActivity.build(getApplicationContext(),EditPetProfile.class).start();
                break;
            case R.id.editPerfilButton:
                ChangeActivity.build(getApplicationContext(),EditProfile.class).start();
                break;
            case R.id.menuButton:
                transaction.replace(R.id.Fragments,fragmentMenu);
                break;
            case R.id.userHomeButton:
                transaction.replace(R.id.Fragments,fragmentUserHome);
                break;
            case R.id.feedButton:
                transaction.replace(R.id.Fragments,fragmentFeed);
                break;
        }
        transaction.commit();
    }


}