package com.questionsandanswers.questionsandanswers.exceptions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Métodos Static
 * Esta clase contiene procesos lógicos córtos
 */
public class Tools {

    /**
     * Verifica si un email es o no válido
     * @param email
     * @return isEmail
     */
    public static boolean isEmail(String email){
        Pattern pattern = Pattern.compile(
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        return mather.find();
    }

}
