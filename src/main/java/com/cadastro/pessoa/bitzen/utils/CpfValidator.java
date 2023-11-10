package com.cadastro.pessoa.bitzen.utils;

public class CpfValidator {
    private static final Integer TAMANHO_CPF = 11;

    public static boolean isCPFValido(String cpf) {
        if (!isFormatoValido(cpf)) return false;
        return temDigitosValidos(cpf);
    }

    private static boolean isFormatoValido(String cpf) {
        if (cpf == null || cpf.length() != TAMANHO_CPF || cpf.matches(cpf.charAt(0) + "{" + TAMANHO_CPF + "}")) {
            return false;
        }
        return true;
    }

    private static boolean temDigitosValidos(String cpf) {
        Integer digito1 = calcularDigito(cpf, 10);
        Integer digito2 = calcularDigito(cpf, 11);
        return digito1.equals(Character.getNumericValue(cpf.charAt(9))) && digito2.equals(Character.getNumericValue(cpf.charAt(10)));
    }

    private static Integer calcularDigito(String cpf, Integer fator) {
        Integer soma = 0;
        for (int i = 0; i < fator - 1; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (fator - i);
        }
        Integer resto = (soma * 10) % 11;
        return resto == 10 ? 0 : resto;
    }
}
