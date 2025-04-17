package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestisce l'accesso al DB per la lettura dei dati di training
 * @author Map Tutor
 *
 */
public class DbAccess {

    /**
     * costruttore
     */
    public DbAccess() {
    }

    /** Nome del driver da utilizzare */
	private String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    /** Nome del DBMS da utilizzare */
    private  String DBMS = "jdbc:mysql";
    /** Indirizzo del server da utilizzare */
    private  String SERVER = "localhost";
    /** Nome del database da utilizzare */
    private  String DATABASE = "MapDB";
    /** Porta del server da utilizzare */
    private  int PORT = 3306;
    /** Nome utente per l'accesso al database */
    private  String USER_ID = "MapUser";
    /** Password per l'accesso al database */
    private  String PASSWORD = "map";
    /** Connessione al database */
    private Connection conn;

    /**
     * Inizializza la connessione al database.
     *
     * @throws DatabaseConnectionException Eccezione lanciata se la connessione al database fallisce.
     */
    public void initConnection() throws DatabaseConnectionException
    {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch(ClassNotFoundException e) {
            System.out.println("[!] Driver not found: " + e.getMessage());
            throw new DatabaseConnectionException(e.toString());
        }
        String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
                + "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch(SQLException e) {
            throw new DatabaseConnectionException(e.toString());
        }
    }

    /**
     * Restituisce la connessione al database.
     *
     * @return Connessione al database.
     * @throws DatabaseConnectionException Eccezione lanciata se la connessione al database fallisce.
     */
    public Connection getConnection() throws DatabaseConnectionException{
        this.initConnection();
        return conn;
    }

    /**
     * Chiude la connessione al database.
     *
     * @throws SQLException Eccezione lanciata se si verifica un errore durante la chiusura della connessione.
     */
    public void closeConnection() throws SQLException {
        conn.close();
    }

}
