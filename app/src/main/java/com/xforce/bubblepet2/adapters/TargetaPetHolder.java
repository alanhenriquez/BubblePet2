package com.xforce.bubblepet2.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xforce.bubblepet2.R;
import com.xforce.bubblepet2.dataFromDataBase.GetDataUser;


public class TargetaPetHolder extends RecyclerView.ViewHolder {

    private ImageView imagen;
    private ImageView imagenUser;
    private TextView nombreUser;
    private TextView nombre;
    private TextView edad;
    private TextView color;
    private TextView raza;
    private TextView salud;

    public TargetaPetHolder(@NonNull View itemView) {
        super(itemView);

        nombreUser = (TextView) itemView.findViewById(R.id.userName);
        nombre = (TextView) itemView.findViewById(R.id.text_targeta_pet_content_info_1);
        edad = (TextView) itemView.findViewById(R.id.text_targeta_pet_content_info_2);
        color = (TextView) itemView.findViewById(R.id.text_targeta_pet_content_info_3);
        raza = (TextView) itemView.findViewById(R.id.text_targeta_pet_content_info_4);
        salud = (TextView) itemView.findViewById(R.id.text_targeta_pet_content_info_5);
        imagen = (ImageView) itemView.findViewById(R.id.imagePet);
        imagenUser = (ImageView) itemView.findViewById(R.id.imgPhoto);
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }

    public ImageView getImagenUser() {
        return imagenUser;
    }

    public void setImagenUser(ImageView imagenUser) {
        this.imagenUser = imagenUser;
    }

    public TextView getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(TextView nombreUser) {
        this.nombreUser = nombreUser;
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getEdad() {
        return edad;
    }

    public void setEdad(TextView edad) {
        this.edad = edad;
    }

    public TextView getColor() {
        return color;
    }

    public void setColor(TextView color) {
        this.color = color;
    }

    public TextView getRaza() {
        return raza;
    }

    public void setRaza(TextView raza) {
        this.raza = raza;
    }

    public TextView getSalud() {
        return salud;
    }

    public void setSalud(TextView salud) {
        this.salud = salud;
    }
}
