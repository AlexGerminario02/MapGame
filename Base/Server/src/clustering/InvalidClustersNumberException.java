package clustering;

/**
 * Classe InvalidClustersNumberException
 * Modella un'eccezione lanciata quando il numero di cluster è minore della profondità del dendrogramma
 */
public class InvalidClustersNumberException extends Exception{

    /**
     * Costruttore
     * @param msg messaggio di errore
     */
    InvalidClustersNumberException(String msg) {
        super(msg);
    }
}
