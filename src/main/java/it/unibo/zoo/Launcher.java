package it.unibo.zoo;

/**
 * Classe di avvio alternativa per aggirare il controllo dei moduli JavaFX.
 * Da Java 11 in poi, eseguire direttamente una classe che estende javafx.application.Application
 * senza i moduli nel module-path causa l'errore "JavaFX runtime components are missing".
 * Questa classe non estende Application e avvia semplicemente la classe Main.
 */
public final class Launcher {

    private Launcher() {}

    public static void main(final String[] args) {
        Main.main(args);
    }
}
