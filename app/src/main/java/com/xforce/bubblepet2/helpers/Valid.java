package com.xforce.bubblepet2.helpers;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Valid {

    public Valid() {
    }



    // Datos de Login

    public static boolean EMAIL(@NonNull String email){
        //    Condiciones:
        //    1: No debe estar vacía.
        //    2: Debe tener una longitud máxima de 256 caracteres.
        //    3: Debe seguir el formato estándar de direcciones de correo electrónico, que consiste
        //       en un nombre de usuario, seguido de un símbolo "@", seguido del nombre del dominio,
        //       seguido de una extensión de dominio (como ".com", ".edu", etc.).
        //    4: El nombre de usuario
        //       puede incluir letras, números y algunos símbolos especiales, como el signo más (+),
        //       el guión (-) y el guión bajo (_). El nombre del dominio y la extensión del dominio
        //       deben ser sólo letras.

        // Expresión regular para validar el formato del correo electrónico
        final String EMAIL_REGEX = "^[a-zA-Z0-9.]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,})$";
        final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        final int MAX_EMAIL_LENGTH = 256;

        if (email.isEmpty() || email.length() > MAX_EMAIL_LENGTH) {
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



    // Finanzas

    public static boolean CREDIT_CARD_NUMBER(@NonNull String creditCardNum) {
        //    Condiciones:
        //    1: No debe estar vacío.
        //    2: Debe tener un formato válido. Esto incluye el número de dígitos y el patrón de
        //       los dígitos.
        //    3: Debe ser un número de tarjeta de crédito de una de las siguientes compañías:
        //       Visa, Mastercard, American Express, Discover, Diners Club o JCB.
        //    4: Debe tener una longitud máxima de 19 dígitos.
        //    5: No puede contener espacios en blanco ni guiones.

        // Eliminar espacios en blanco y guiones del número de tarjeta de crédito
        creditCardNum = creditCardNum.replaceAll("\\s+|-", "");

        // Expresión regular para validar el formato del número de tarjeta de crédito
        final String CREDIT_CARD_REGEX = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|" +
                "5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|" +
                "1800|35\\d{3})\\d{11})$";
        final Pattern CREDIT_CARD_PATTERN = Pattern.compile(CREDIT_CARD_REGEX);

        Matcher matcher = CREDIT_CARD_PATTERN.matcher(creditCardNum);
        return matcher.matches();
    }

    public static boolean BANK_ACCOUNT_NUMBER(@NonNull String bankAccountNumber) {
        //    Condiciones:
        //    1: El número de cuenta bancaria debe estar compuesto únicamente por dígitos (0-9).
        //    2: El número de cuenta bancaria debe tener una longitud de 8 a 16 dígitos.

        // Expresión regular para validar el formato del número de cuenta bancaria
        final String BANK_ACCOUNT_NUMBER_REGEX = "^[0-9]{8,16}$";
        final Pattern BANK_ACCOUNT_NUMBER_PATTERN = Pattern.compile(BANK_ACCOUNT_NUMBER_REGEX);

        Matcher matcher = BANK_ACCOUNT_NUMBER_PATTERN.matcher(bankAccountNumber);
        return matcher.matches();
    }

    public static boolean MONEY_AMOUNT(@NonNull String moneyAmount) {
        //    Condiciones:
        //    1: El monto puede estar precedido o seguido por el símbolo de la moneda, que puede ser el signo de dólar ($), el euro (€), la libra esterlina (£) o el yen japonés (¥).
        //    2: El monto puede estar separado en miles con comas (por ejemplo, 1,000,000).
        //    3: El monto puede incluir un decimal, que debe estar separado del resto del monto con un punto (.) y debe tener solo dos dígitos.
        //    4: El monto debe ser un número entero o decimal válido.

        // Expresión regular para validar el formato del monto de dinero
        final String MONEY_AMOUNT_REGEX = "^[$€£¥]?([0-9]{1,3},([0-9]{3},)*(?:[0-9]{3},)*[0-9]{1,3}|[0-9]+)(.[0-9][0-9])?[$€£¥]?$";
        final Pattern MONEY_AMOUNT_PATTERN = Pattern.compile(MONEY_AMOUNT_REGEX);

        Matcher matcher = MONEY_AMOUNT_PATTERN.matcher(moneyAmount);
        return matcher.matches();
    }

    public static boolean CRYPTO_AMOUNT(@NonNull String cryptoAmount) {
        //    Condiciones:
        //    1: No debe estar vacío.
        //    2: Debe contener sólo dígitos y un punto decimal opcional.
        //    3: Debe seguir el formato de un número decimal, es decir, una parte entera opcional
        //       seguida de un punto decimal opcional y una parte decimal opcional.

        // Expresión regular para validar el formato del monto de criptomoneda
        final String CRYPTO_AMOUNT_REGEX = "^[0-9]*\\.?[0-9]+$";
        final Pattern CRYPTO_AMOUNT_PATTERN = Pattern.compile(CRYPTO_AMOUNT_REGEX);

        Matcher matcher = CRYPTO_AMOUNT_PATTERN.matcher(cryptoAmount);
        return matcher.matches();
    }

    public static boolean CRYPTO_ADDRESS(@NonNull String cryptoAddress) {
        //    Condiciones:
        //    1: La dirección de criptomoneda no puede estar vacía.
        //    2: Debe tener una longitud de 40 caracteres.
        //    3: Debe comenzar con "0x" o no.
        //    4: Debe estar formada únicamente por dígitos hexadecimales (0-9, a-f, A-F).

        // Expresión regular para validar el formato de la dirección de criptomoneda
        final String CRYPTO_ADDRESS_REGEX = "^(0x)?[0-9a-fA-F]{40}$";
        final Pattern CRYPTO_ADDRESS_PATTERN = Pattern.compile(CRYPTO_ADDRESS_REGEX);

        Matcher matcher = CRYPTO_ADDRESS_PATTERN.matcher(cryptoAddress);
        return matcher.matches();
    }



    // Datos Personales

    public static boolean NAME(@NonNull String name) {
        //    Condiciones:
        //    1: La cadena de entrada (name) no debe estar vacía.
        //    2: La longitud de la cadena de entrada (name) debe estar entre 2 y 256 caracteres.
        //    3: La cadena de entrada (name) debe coincidir con el patrón de expresión regular
        //       NAME_REGEX, que es: "^[a-zA-Z]+( [a-zA-Z]+)?$". Esto significa que el nombre debe
        //       comenzar con una o más letras, seguido opcionalmente de un espacio y una o más
        //       letras adicionales.

        // Expresión regular para validar el formato del nombre de persona
        final String NAME_REGEX = "^[a-zA-Z]+( [a-zA-Z]+)?$";
        final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);
        final int MIN_NAME_LENGTH = 2;
        final int MAX_NAME_LENGTH = 256;

        if (name.isEmpty() || name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            return false;
        }

        Matcher matcher = NAME_PATTERN.matcher(name);
        return matcher.matches();
    }

    public static boolean LAST_NAME(@NonNull String lastName) {
        //    Condiciones:
        //    1: La cadena de texto no debe estar vacía.
        //    2: Debe tener una longitud mínima de 2 caracteres y una longitud máxima de 256 caracteres.
        //    3: Debe tener un formato válido, es decir, debe comenzar con una letra y puede tener un
        //       segundo nombre compuesto por una sola letra.

        // Expresión regular para validar el formato del apellido de persona
        final String LAST_NAME_REGEX = "^[a-zA-Z]+( [a-zA-Z]+)?$";
        final Pattern LAST_NAME_PATTERN = Pattern.compile(LAST_NAME_REGEX);
        final int MIN_LAST_NAME_LENGTH = 2;
        final int MAX_LAST_NAME_LENGTH = 256;

        if (lastName.isEmpty() || lastName.length() < MIN_LAST_NAME_LENGTH || lastName.length() > MAX_LAST_NAME_LENGTH) {
            return false;
        }

        Matcher matcher = LAST_NAME_PATTERN.matcher(lastName);
        return matcher.matches();
    }

    public static boolean FULL_NAME(@NonNull String fullName) {
        //    Condiciones:
        //    1: Se está validando que el nombre completo contenga exactamente dos partes
        //       (un nombre y un apellido) y que cada una de estas partes cumpla con el formato de
        //       nombre o apellido válido según las expresiones regulares y condiciones establecidas.
        //       Además, se está validando que el tamaño del nombre completo sea mayor o igual a 2
        //       caracteres y menor o igual a 256 caracteres.


        // Expresión regular para validar el formato del nombre de persona
        final String NAME_REGEX = "^[a-zA-Z]+( [a-zA-Z]+)?$";
        final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

        // Expresión regular para validar el formato del apellido de persona
        final String LAST_NAME_REGEX = "^[a-zA-Z]+( [a-zA-Z]+)?$";
        final Pattern LAST_NAME_PATTERN = Pattern.compile(LAST_NAME_REGEX);

        final int MIN_NAME_LENGTH = 2;
        final int MAX_NAME_LENGTH = 256;

        if (fullName.isEmpty() || fullName.length() < MIN_NAME_LENGTH || fullName.length() > MAX_NAME_LENGTH) {
            return false;
        }

        // Separamos el nombre y el apellido
        String[] nameAndLastNameArray = fullName.split(" ");

        // Si no hay exactamente dos partes (nombre y apellido), entonces es inválido
        if (nameAndLastNameArray.length != 2) {
            return false;
        }

        // Validamos el nombre y el apellido por separado
        Matcher nameMatcher = NAME_PATTERN.matcher(nameAndLastNameArray[0]);
        Matcher lastNameMatcher = LAST_NAME_PATTERN.matcher(nameAndLastNameArray[1]);

        return nameMatcher.matches() && lastNameMatcher.matches();
    }

    public static boolean PHONE_NUMBER(@NonNull String fullPhoneNumber) {
        //    Condiciones:
        //    1: El número de teléfono debe tener una longitud mínima de 8 caracteres y una
        //       longitud máxima de 15 caracteres.
        //    2: El número de teléfono debe coincidir con la expresión regular PHONE_NUMBER_REGEX,
        //       que permite opcionalmente el uso del signo "+" seguido de uno a tres dígitos y un
        //       espacio, seguidos de siete a diez dígitos.

        // Expresión regular para validar el formato del número de teléfono
        final String PHONE_NUMBER_REGEX = "^(\\+[0-9]{1,3}\\s)?[0-9]{7,10}$";
        final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);
        final int MIN_PHONE_NUMBER_LENGTH = 8;
        final int MAX_PHONE_NUMBER_LENGTH = 15;

        if (fullPhoneNumber.isEmpty() || fullPhoneNumber.length() < MIN_PHONE_NUMBER_LENGTH || fullPhoneNumber.length() > MAX_PHONE_NUMBER_LENGTH) {
            return false;
        }

        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(fullPhoneNumber);
        return matcher.matches();
    }

    public static boolean PHONE_NUMBER(@NonNull String phoneNumber, @NonNull String countryCode) {
        //    Condiciones:
        //    1: El número de teléfono debe tener un formato válido según la expresión regular
        //       "^[0-9]{8,12}$". Esto significa que debe contener entre 8 y 12 dígitos numéricos.
        //    2: El código de país debe tener un formato válido según la expresión regular
        //       "^(\+)?[0-9]{1,3}$". Esto significa que debe contener entre 1 y 3 dígitos numéricos
        //       y puede comenzar con un signo "+" opcional.

        // Expresión regular para validar el formato del número de teléfono
        final String PHONE_NUMBER_REGEX = "^[0-9]{8,12}$";
        final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

        // Expresión regular para validar el formato del código de país
        final String COUNTRY_CODE_REGEX = "^(\\+)?[0-9]{1,3}$";
        final Pattern COUNTRY_CODE_PATTERN = Pattern.compile(COUNTRY_CODE_REGEX);

        // Validamos el número de teléfono y código de país
        Matcher phoneNumberMatcher = PHONE_NUMBER_PATTERN.matcher(phoneNumber);
        Matcher countryCodeMatcher = COUNTRY_CODE_PATTERN.matcher(countryCode);
        return phoneNumberMatcher.matches() && countryCodeMatcher.matches();
    }



    // Datos de Ubicación

    public static boolean ZIP_CODE(@NonNull String zipCode) {
        //    Condiciones:
        //    1: Consta de 5 dígitos numéricos.
        //    2: Cada dígito es un número entre 0 y 9.

        // Expresión regular para validar el formato del código postal
        final String ZIP_CODE_REGEX = "^[0-9]{5}$";
        final Pattern ZIP_CODE_PATTERN = Pattern.compile(ZIP_CODE_REGEX);

        Matcher matcher = ZIP_CODE_PATTERN.matcher(zipCode);
        return matcher.matches();
    }

    public static boolean IPs(@NonNull String ip) {
        //    Condiciones:
        //    1: La dirección IP no debe estar vacía.
        //    2: La dirección IP debe tener un formato válido, que consiste en cuatro grupos de
        //       números separados por puntos, cada uno de ellos representando un octeto de la dirección IP.
        //    3: Cada octeto debe estar compuesto por un número entero comprendido entre 0 y 255,
        //       inclusive.

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
        //    Condiciones:
        //    1: La URL no debe estar vacía.
        //    2: La URL debe tener un formato válido, que consiste en un esquema
        //       (opcionalmente "http" o "https"), seguido de dos puntos y dos barras diagonales,
        //       seguido del nombre del servidor (opcionalmente precedido por "www"), seguido de
        //       una barra diagonal, seguido del resto de la dirección de la URL. El nombre del
        //       servidor puede incluir letras, números y algunos símbolos especiales, como el
        //       signo más (+), el guión (-) y el guión bajo (_).
        //    3: La URL debe ser válida según el método toURI() de la clase URL de Java.

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



    // Datos de de tiempo

    public static boolean TIME(@NonNull String time) {
        //    Condiciones:
        //    1: Tener un formato de hora válido, que puede ser:
        //         HH:MM:SS o HH:MM o HH
        //         HHh o HH:MM AM/PM o HH.MM AM/PM o HH AM/PM
        //         HH:MM:SS.SSS o HH:MM.SSS
        //         HH:MM:SS [AM/PM] o HH:MM [AM/PM] o HH [AM/PM]
        //    2: No puede estar vacía.

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

    public static boolean DATEs(@NonNull String date) {
        //    Condiciones:
        //    1: La fecha debe tener un formato válido, que consiste en un día, seguido de una barra
        //       (/), guion (-) o punto (.), seguido de un mes, seguido de una barra (/), guion (-)
        //       o punto (.), seguido de un año.
        //    2: Los días deben ser números del 01 al 31.
        //    3: Los meses deben ser números del 01 al 12 o nombres de meses en inglés
        //       (Jan, Feb, Mar, etc.) con o sin la inicial en mayúsculas.
        //    4: Los años deben tener cuatro dígitos y pueden ser cualquier año válido.
        //    5: Si se especifica el día y el mes como números, el año debe tener cuatro dígitos.
        //       Si se especifica el mes como un nombre de mes en inglés, el año puede tener dos o
        //       cuatro dígitos.
        //    6: La fecha debe ser una fecha válida, es decir, debe existir en el calendario.
        //       Por ejemplo, Febrero 31 no es una fecha válida.

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

    public static boolean BIRTH_DATE(@NonNull String birthDate) {
        //    Condiciones:
        //    1: La cadena birthDate no está vacía. Si está vacía, se devuelve falso.
        //    2: La cadena birthDate cumple con el patrón de fecha especificado en la expresión
        //       regular DATE_REGEX. Si no cumple con el patrón, se devuelve falso.
        //    3: El objeto Date obtenido es anterior a la fecha actual. Si el objeto Date es
        //       posterior a la fecha actual, se devuelve falso.


        final String DATE_REGEX = "^(?:(?:(?:0?[1-9]|1\\\\d|2[0-8])([-.\\\\/])[0?1-9|12]" +
                "([-.\\\\/])\\\\d{4}|(?:29|30)([-.\\\\/])[0?13-9|2]([-.\\\\/])\\\\d{4}" +
                "|31([-.\\\\/])[0?13578|2]([-.\\\\/])\\\\d{4})|29([-.\\\\/])0?2([-" +
                ".\\\\/])\\\\d{2}(?:(?:[02468][048]|[13579][26])00|(?:[0-9]\\\\d(?:[02" +
                "468][048]|[13579][26])|[0-9]00[48]))|(?:0?[1-9]|1[012])([-.\\\\/])" +
                "0?2([-.\\\\/])\\\\d{4})$";

        if (birthDate.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile(DATE_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(birthDate);
        if (!matcher.matches()) {
            return false;
        }

        // Parse the date to check if it's a valid birth date
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(birthDate);
            // Check if the date is in the past
            return Objects.requireNonNull(date).before(new Date());
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean AGE(@NonNull String age) {
        //    Condiciones:
        //    1: Debe tener un formato válido, que es el especificado por la expresión regular
        //       AGE_REGEX. Esta expresión regular valida que la cadena sea un número entero
        //       de 1 a 3 dígitos.

        final String AGE_REGEX = "^[0-9]{1,3}$";

        if (age.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile(AGE_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(age);
        return matcher.matches();
    }




}