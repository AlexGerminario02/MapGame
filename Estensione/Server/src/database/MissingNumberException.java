package database;

/**
 * Classe MissingNumberException
 * Modella un'eccezione lanciata quando manca un numero
 */
public class MissingNumberException extends Exception {

    /**
     * Costruttore
     * @param message messaggio di errore
     */
    public MissingNumberException(String message) {
        super(message);
    }
}
