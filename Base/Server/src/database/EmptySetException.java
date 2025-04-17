package database;

/**
 * Classe EmptySetException
 * Modella un'eccezione lanciata quando un insieme è vuoto
 */
public class EmptySetException extends Exception {

    /**
     * Costruttore
     * @param message messaggio di errore
     */
    public EmptySetException(String message) {
        super(message);
    }
}