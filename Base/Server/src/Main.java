import server.MultiServer;
/**
 * Classe main del Server.
 * Punto di partenza dell'applicazione lato server.
 */
public class Main {

    /**
     * Metodo main del server.
     * @param args argomenti passati da terminale (non vengono gestiti)
     */
    public static void main(String[] args) {
        MultiServer.instanceMultiServer();
    }
}
