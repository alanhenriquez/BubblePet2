package com.xforce.bubblepet2.helpers;

import androidx.annotation.NonNull;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Valid {

    public Valid() {
    }

    //Public static------------------------------------

    public static boolean EMAIL(@NonNull String email){
        final String EMAIL_REGEX = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})u";
        final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        final int MAX_EMAIL_LENGTH = 256;

        if (email.isEmpty()|| email.length() > MAX_EMAIL_LENGTH) {
            return false;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public static boolean PASSWORD_EASY(@NonNull String password) {
        //    Condiciones:
        //    1: Debe tener una longitud de al menos 6 caracteres.

        final int MIN_LENGTH = 6;
        return !password.isEmpty() && password.length() >= MIN_LENGTH;
    }

    public static boolean PASSWORD_NORMAL(@NonNull String password) {
        //    Condiciones:
        //    1: Debe tener una longitud de al menos 6 caracteres y máximo 128.
        //    2: Debe contener al menos una letra mayúscula o minúscula.
        //    3: Debe contener al menos un número.

        final int MIN_LENGTH = 6;
        final int MAX_LENGTH = 128;
        final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";

        if (password.isEmpty() || password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            return false;
        }

        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean PASSWORD_HARD(@NonNull String password) {
        //    Condiciones:
        //    1: Debe tener una longitud de al menos 6 caracteres y máximo 128.
        //    2: Debe contener al menos una letra mayúscula.
        //    3: Debe contener al menos una letra minúscula.
        //    4: Debe contener al menos un número.
        //    5: Debe contener al menos uno de los siguientes caracteres especiales: @ $ ! % * ? &.

        final int MIN_LENGTH = 6;
        final int MAX_LENGTH = 128;
        final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";

        if (password.isEmpty() || password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            return false;
        }

        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean PASSWORD_STRONG(@NonNull String password) {
        //    Condiciones:
        //    1: Tener una longitud de al menos 10 caracteres y no exceder la longitud máxima de 128 caracteres.
        //    2: No contener ninguna de las palabras prohibidas especificadas en la expresión regular "PROHIBITED_WORDS_REGEX".
        //    3: Contener al menos una letra mayúscula, una letra minúscula, un número y un carácter especial.

        final int MIN_LENGTH = 10;
        final int MAX_LENGTH = 128;
        final String PROHIBITED_WORDS_REGEX = "^(?!password|123456|qwerty|abc123).*$";
        final String VARIED_CHARACTERS_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).*$";

        if (password.isEmpty() || password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            return false;
        }

        Pattern prohibitedWordsPattern = Pattern.compile(PROHIBITED_WORDS_REGEX);
        Matcher prohibitedWordsMatcher = prohibitedWordsPattern.matcher(password);
        if (!prohibitedWordsMatcher.matches()) {
            return false;
        }

        Pattern variedCharactersPattern = Pattern.compile(VARIED_CHARACTERS_REGEX);
        Matcher variedCharactersMatcher = variedCharactersPattern.matcher(password);
        return variedCharactersMatcher.matches();
    }

    public static boolean IPs(@NonNull String ip) {
        final String IP_REGEX = "^(?:[0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\." +
                "(?:[0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\." +
                "(?:[0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\." +
                "(?:[0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])$";

        if (ip.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile(IP_REGEX);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    public static boolean URLs(@NonNull String url){
        final String URL_REGEX = "^(https?://)?(www\\.)?[-a-zA-Z0-9@:%._+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_+.~#?&/=]*)$";

        try {
            // Comprobamos si la URL es válida utilizando el método toURI()
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }

        // Comprobamos si la URL cumple con el patrón de la expresión regular
        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    public static boolean DATEs(@NonNull String date) {
        final String DATE_REGEX = "^(((0?[1-9]|[12]\\d|3[01])([/.-])(0?[13578]|1[02])(\\4)" +
                "((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|[12]\\d|30)([/.-])(0?[13456789]|1[012])" +
                "(\\6)((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|1\\d|2[0-8])([/.-])0?2([/.-])" +
                "((1[6-9]|[2-9]\\d)?\\d{2}))|(29([/.-])0?2([/.-])((1[6-9]|[2-9]\\d)" +
                "?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))" +
                "$|^(0?2([/.-])29([/.-])([02468][048]00|[13579][26]00|[0-9][0-9][0][48]|" +
                "[0-9][0-9][2468][048]|[0-9][0-9][13579][26]))$";

        if (date.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile(DATE_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public static boolean TIME(@NonNull String time) {
        final String TIME_REGEX = "^(([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])(:[0-9][0-9][0-9])" +
                "?$|^(([01]?[0-9]|2[0-3]):[0-5][0-9])(:[0-5][0-9])?$|^([01]?[0-9]|2[0-3])$|^" +
                "([01]?[0-9]|2[0-3])[hH]$|^([01]?[0-9]|2[0-3]):[0-5][0-9] [APap][mM]$|^" +
                "([01]?[0-9]|2[0-3])[:.]([0-5][0-9])?[ ]?[APap][mM]$|^([01]?[0-9]|2[0-3]):" +
                "[0-5][0-9]$|^([01]?[0-9]|2[0-3])[:.]([0-5][0-9])?$|^((1[0-2]|0?[1-9]):([0-5][0-9])" +
                "(:[0-5][0-9])? ([APap][mM]))$";

        if (time.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile(TIME_REGEX);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }

    public static boolean CONTAINS_BAD_WORDS(String message) {
        final String[] BAD_WORDS = { "pendejo", "cerote", "imbesil", "hijo de puta", "marica", "maricon", "culeron", "chupa huevos" };
        String badWordsRegex = String.join("|", BAD_WORDS);
        String regex = String.format(".*\\b(%s)\\b.*", badWordsRegex);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        return matcher.matches();
    }

}