package com.xforce.bubblepet2.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xforce.bubblepet2.EditProfile;
import com.xforce.bubblepet2.MainActivity;
import com.xforce.bubblepet2.R;

import java.util.Objects;

public class ConfirmationModal {

    //Variables--------------------------------------------

    Context context;
    String string, titleValue, deniedValue, grantedValue;
    boolean titleTx = false, deniedTx = false, grantedTx = false;
    TextView textView;
    ConstraintLayout constraintLayout;

    //Privates---------------------------------------------

    private ConfirmationModal(@NonNull Context _context, ConstraintLayout _constraintLayout){
        this.context = _context;
        this.constraintLayout = _constraintLayout;
    }

    //Public static----------------------------------------

    public static ConfirmationModal build(@NonNull Context context, ConstraintLayout constraintLayout){
        return new ConfirmationModal(context,constraintLayout);
    }

    //Public void------------------------------------------

    public void show(){
        constraintLayout.setVisibility(View.VISIBLE);
        if (titleTx){
            TextView titulo = constraintLayout.findViewById(R.id.confirmationTitle);
            titulo.setText(titleValue);
        }

        TextView deniedButon = constraintLayout.findViewById(R.id.confirmationDenied);
        TextView grantedButon = constraintLayout.findViewById(R.id.confirmationGranted);
        deniedButon.setOnClickListener(v -> {
            constraintLayout.setVisibility(View.GONE);
        });
        grantedButon.setOnClickListener(v -> {
            constraintLayout.setVisibility(View.GONE);
            assert EditProfile.inicial() != null;
            EditProfile.inicial().setDataBase();
            ChangeActivity.build(context, MainActivity.class).start();
        });

    }

    public void hide(){
        constraintLayout.setVisibility(View.GONE);
    }

    public ConfirmationModal setTitle(String title){
        this.titleValue = title;
        this.titleTx = true;
        return this;
    }

    public ConfirmationModal setTextDenied(String denied){
        this.deniedValue = denied;
        this.deniedTx = true;
        return this;
    }

    public ConfirmationModal setTextGranted(String granted){
        this.grantedValue = granted;
        this.grantedTx = true;
        return this;
    }


}
