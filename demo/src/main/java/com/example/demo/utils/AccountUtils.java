package com.example.demo.utils;

import java.time.Year;

// una clase auxiliar que centraliza ciertos valores y operaciones comunes.
public class AccountUtils {
    // static: un método static se puede invocar como Clase.metodo(), sin necesidad
    // de crear un objeto de esa clase.

    // final: Indica que el valor de la variable o el método no puede ser
    // modificado.

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "This user has already an account";
    public static final String ACCOUNT_CREATION_SUCCES = "002";
    public static final String ACCOUNT_CREATION_MESSAGE = "ACCOUNT CREATED SUCCESFULLY";
    public static final String ACCOUNT_NOT_EXIST = "003";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "USER WITH THE PROVIDED ACCOUNT DOES NOT EXIST";
    public static final String ACCOUNT_FOUND = "004";
    public static final String ACCOUNT_FOUND_MESSAGE = "USER ACCOUNT FOUND";
    public static final String ACCOUNT_CREDITED_SUCCESS = "005";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "USER ACCOUNT CREDITED SUCCESSFULLY";
    public static final String INSUFFICIENT_BALANCE_CODE = "006";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "INSUFFICIENT BALANCE";
    public static final String ACCOUNT_DEBITED_SUCCESS = "007";
    public static final String ACCOUNT_DEBITED_MESSAGE = "Account has been succesfully debited";
    public static final String TRANSFER_SUCCESSFUL_CODE = "008";
    public static final String TRANSFER_SUCCESFUL_MESSAGE = "TRANSFER SUCCESFUL";

    public static String generateAccountNumber() {
        /*
         * 2024 + randomSeisDigitos
         */
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

        // generate a random number between min and max
        // larger than 99 minus 100000
        int randNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);
        // ahora quiero convertir currentYear a string
        // y randNumber a string
        // y despues concatenar los 2 juntos

        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);
        StringBuilder accountNumber = new StringBuilder();

        return accountNumber.append(year).append(randomNumber).toString();

    }

}
