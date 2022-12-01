package com.xforce.bubblepet2.adapters;

public class TargetaPet {

    private String imagen;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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
