package data;

/**
 * Classe NoDataException
 * Modella un'eccezione lanciata quando non ci sono dati da leggere
 */
public class NoDataException extends Exception {

    /**
     * Costruttore
     * @param message messaggio di errore
     */
    public NoDataException(String message) {
        super(message);
    }
}
