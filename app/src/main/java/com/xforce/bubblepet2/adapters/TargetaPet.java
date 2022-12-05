package com.xforce.bubblepet2.adapters;

import android.widget.ImageView;
import android.widget.TextView;

public class TargetaPet {

    private String imagen;
    private String imagenUser;
    private String nombreUser;
    private String nombre;
    private String edad;
    private String color;
    private String raza;
    private String salud;


    public TargetaPet() {
    }

    public TargetaPet(String imagen, String nombre, String edad, String color, String raza, String salud) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.edad = edad;
        this.color = color;
        this.raza = raza;
        this.salud = salud;
    }

    public TargetaPet(String imagen, String imagenUser, String nombreUser, String nombre, String edad, String color, String raza, String salud) {
        this.imagen = imagen;
        this.imagenUser = imagenUser;
        this.nombreUser = nombreUser;
        this.nombre = nombre;
        this.edad = edad;
        this.color = color;
        this.raza = raza;
        this.salud = salud;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getImagenUser() {
        return imagenUser;
    }

    public void setImagenUser(String imagenUser) {
        this.imagenUser = imagenUser;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSalud() {
        return salud;
    }

    public void setSalud(String salud) {
        this.salud = salud;
    }
}
