package database;

/**
 * Classe DatabaseConnectionException
 * Modella un'eccezione lanciata quando non è possibile stabilire una connessione al database
 */
public class DatabaseConnectionException extends Exception {

    /**
     * Costruttore
     * @param msg messaggio di errore
     */
    DatabaseConnectionException(String msg) {
        super(msg);
    }
}
