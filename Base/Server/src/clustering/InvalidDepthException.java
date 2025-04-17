package clustering;

/**
 * Classe InvalidDepthException
 * Modella un'eccezione lanciata quando la profondità del dendrogramma è minore o uguale a 0
 */
public class InvalidDepthException extends Exception{

    /**
     * Costruttore
     * @param msg messaggio di errore
     */
    InvalidDepthException(String msg) {
        super(msg);
    }
}
