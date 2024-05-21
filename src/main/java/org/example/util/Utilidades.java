package org.example.util;

public final class Utilidades {
    private Utilidades() {}

    public static String obterGeneroPorCodigo(int codigoGenero) {
        return switch (codigoGenero) {
            case 1 -> "Masculino";
            case 2 -> "Feminino";
            case 3 -> "Homem (trans)";
            case 4 -> "Mulher (trans)";
            case 5 -> "Mtf (Homem em transição para mulher)";
            case 6 -> "Ftm (Mulher em transição para homem)";
            case 7 -> "Gênero fluído";
            case 8 -> "Gênero não binário";
            case 9 -> "Outro";
            default -> "Não definido";
        };
    }
}
