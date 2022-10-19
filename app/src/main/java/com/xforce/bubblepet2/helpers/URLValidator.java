package com.xforce.bubblepet2.helpers;

import android.annotation.SuppressLint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLValidator {
    //Ejemplo de uso de esta clase URLValidator
    //
    //URLValidator.url("link de tu url");



    //Variable en la que vamos a almacenar el enlace
    @SuppressLint("StaticFieldLeak")
    private static String urlString;



    // Expresión regular que se encargara de filtrar y
    //limpiar el enlace ingresado
    private static final String URL_REGEX =
            "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))" +
                    "(%{2}|[-()_.!~*';/?:@&=+$, A-Za-z0-9])+)" +
                    "([).!';/?:, ][:blank])?$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);



    // Entregara falso en caso de no ser valido el url,
    //de lo contrario nos devolvera limpio el enlace
    private static boolean urlValidator(String url) {
        if (url == null) {
            return false;
        }
        Matcher matcher = URL_PATTERN.matcher(url);
        return matcher.matches();
    }



    // Se ingresara la url y en caso de no cumplir con la
    //condicional de ser valido o no, entonces nos va a
    //retornar "invalido", de lo contrario retornara la misma url
    public static String url(String url) {
        if (urlValidator(url)) {
            t(url);
            return urlString;

        }
        return "invalido";
    }

    public static boolean isValid(String url){
        return urlValidator(url);
    }

    private static void t(String b){
        urlString = b;
    }






}
