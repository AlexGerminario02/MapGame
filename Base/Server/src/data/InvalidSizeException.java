package data;

/**
 * Classe InvalidSizeException
 * Modella un'eccezione lanciata quando la dimensione di un oggetto è minore o uguale a 0
 */
public class InvalidSizeException extends Exception{

    /**
     * Costruttore
     * @param message messaggio di errore
     */
    public InvalidSizeException(String message){
        super(message);
    }
}
