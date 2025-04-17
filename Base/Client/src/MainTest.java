import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/** MainTest è la classe che si occupa di gestire la comunicazione con il server.
 *  In particolare, si occupa di inviare le richieste al server e di ricevere le risposte.
 *  Inoltre, si occupa di gestire il menu per la scelta dell'operazione da eseguire.
 */
public class MainTest {
    /** out è lo stream di output per inviare le richieste al server */
    private ObjectOutputStream out;
    /** in è lo stream di input per ricevere le risposte dal server */
    private ObjectInputStream in ;

    /** Costruttore della classe MainTest
     * @param ip è l'indirizzo ip del server
     * @param port è la porta del server
     * @throws IOException se ci sono errori di I/O
     */
    public MainTest(String ip, int port) throws IOException {
        InetAddress addr = InetAddress.getByName(ip); //ip
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, port); //Port
        System.out.println(socket);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    /** menu è il metodo che si occupa di stampare il menu e di leggere la scelta dell'utente
     * @return la scelta dell'utente
     */
    private int menu(){
        int answer;
        System.out.println("Scegli una opzione");
        do{
            System.out.println("(1) Carica Dendrogramma da File");
            System.out.println("(2) Apprendi Dendrogramma da Database");
            System.out.print("Risposta:");
            answer= Keyboard.readInt();
        }
        while(answer<=0 || answer>2);
        return answer;
    }

    /** loadDataOnServer è il metodo che si occupa di inviare la richiesta al server per caricare i dati dal database
     * @throws IOException se ci sono errori di I/O
     * @throws ClassNotFoundException se ci sono errori di casting
     */
    private void loadDataOnServer() throws IOException, ClassNotFoundException {
        boolean flag=false;
        do {
            System.out.println("Nome tabella:");
            String tableName=Keyboard.readString();
            out.writeObject(0);
            out.writeObject(tableName);
            String risposta=(String)(in.readObject());
            if(risposta.equals("OK"))
                flag=true;
            else System.out.println(risposta);

        }while(!flag);
    }

    /** loadDedrogramFromFileOnServer è il metodo che si occupa di inviare la richiesta al server per caricare il dendrogramma da file
     * @throws IOException se ci sono errori di I/O
     * @throws ClassNotFoundException se ci sono errori di casting
     */
    private void loadDedrogramFromFileOnServer() throws IOException, ClassNotFoundException {
        System.out.println("Inserire il nome dell'archivio (comprensivo di estensione):");
        String fileName=Keyboard.readString();

        out.writeObject(2);
        out.writeObject(fileName);
        String risposta= (String) (in.readObject());
        if(risposta.equals("OK"))
            System.out.println(in.readObject()); // stampo il dendrogramma che il server mi sta inviando
        else
            System.out.println(risposta); // stampo il messaggio di errore
    }

    /** mineDendrogramOnServer è il metodo che si occupa di inviare la richiesta al server per apprendere il dendrogramma
     * @throws IOException se ci sono errori di I/O
     * @throws ClassNotFoundException se ci sono errori di casting
     */
    private void mineDendrogramOnServer() throws IOException, ClassNotFoundException {
        out.writeObject(1);
        System.out.println("Introdurre la profondita'  del dendrogramma");
        int depth=Keyboard.readInt();
        out.writeObject(depth);
        int dType=-1;
        do {
            System.out.println("Distanza: single-link (1), average-link (2):");
            dType=Keyboard.readInt();
        }while (dType<=0 || dType>2);
        out.writeObject(dType);

        String risposta= (String) (in.readObject());
        if(risposta.equals("OK")) {
            System.out.println(in.readObject()); // stampo il dendrogramma che il server mi sta inviando
            System.out.println("Inserire il nome dell'archivio (comprensivo di estensione):");
            String fileName=Keyboard.readString();
            out.writeObject(fileName);
        }
        else
            System.out.println(risposta); // stampo il messaggio di errore
    }

    /** main è il metodo che si occupa di creare un'istanza della classe MainTest e di chiamare i metodi per gestire la comunicazione con il server
     * @param args contiene l'indirizzo ip e la porta del server
     */
    public static void main(String[] args) {
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        MainTest main = null;
        try {
            main = new MainTest(ip, port);

            main.loadDataOnServer();
            int scelta = main.menu();
            if (scelta == 1)
                main.loadDedrogramFromFileOnServer();
                
            else
                main.mineDendrogramOnServer();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
