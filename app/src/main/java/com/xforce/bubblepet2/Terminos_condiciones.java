package com.xforce.bubblepet2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.xforce.bubblepet2.helpers.ChangeActivity;

public class Terminos_condiciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos_condiciones);
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        ChangeActivity.build(getApplicationContext(),MainActivity.class).start();

    }

}