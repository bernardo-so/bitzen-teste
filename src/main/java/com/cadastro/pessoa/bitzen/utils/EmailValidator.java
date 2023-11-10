package com.cadastro.pessoa.bitzen.utils;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public static boolean isEmailValido(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
