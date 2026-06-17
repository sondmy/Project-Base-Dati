package it.unibo.zoo.utils;

public final class InputValidator {

    private InputValidator() {}

    public static void notBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " non può essere vuoto");
        }
    }

    public static void checkSesso(char sesso) {
        if (sesso != 'M' && sesso != 'F') {
            throw new IllegalArgumentException("Il sesso deve essere M o F");
        }
    }

    public static void notNegative(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " non può essere negativo");
        }
    }
}
