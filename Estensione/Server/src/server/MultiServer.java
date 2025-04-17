package server;

import java.io.*;
import java.net.*;

/**
 * Classe che gestisce il server
 */
public class MultiServer {
    /** La porta del server */
    private final int PORT;
    /** Singleton */
    private static MultiServer singleton = null;

    /**
     * Costruttore di classe, inizializza la porta e invoca run()
     *
     * @param port che indica la porta alla quale connettersi
     */
    private MultiServer(int port) {
        this.PORT = port;
        run();
    }

    /**
     * Metodo che serve per istanziare un nuovo Server, specificando
     * la porta in modo tale da rendere Singleton la classe Server.
     * Istanzio un nuovo server solo 1 volta, quelle successive non potrò più farlo.
     */
    public static void instanceMultiServer(){
        if(singleton == null)
            singleton = new MultiServer(2025);
    }

    /**
     * Istanzia un oggetto istanza della classe ServerSocket che pone in attesa di
     * richiesta di connessioni da parte del client. A ogni nuova richiesta
     * connessione si istanzia ServerOneClient sfruttando cosi il MultiThreading
     */
    private void run() {
        try {
            ServerSocket s = new ServerSocket(PORT);
            try (s) {
                System.out.println("Started: " + s);
                while (true) {
                    Socket socket = s.accept();
                    System.out.println("Connessione client: " + socket);

                    try {
                        new ServerOneClient(socket);
                    } catch (IOException e) {
                        System.out.println("Errore nell'istanziazione del socket: " + socket);
                        socket.close();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Errore...");
        }
    }
}

