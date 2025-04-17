package database;

import data.Example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestisce l'accesso ai dati di una tabella.
 */
public class TableData {

    /** Connessione al database */
    private DbAccess db;

    /**
     * Inizializza l’attributo db.
     *
     * @param db Connessione al database
     */
    public TableData(DbAccess db) {
        this.db = db;
    }

    /**
     * Recupera le transazioni distinte dalla tabella specificata.
     *
     * @param table Nome della tabella
     * @return Lista di Example memorizzata nella tabella
     * @throws SQLException In caso di errore nella interrogazione
     * @throws EmptySetException In caso di tabella vuota
     * @throws MissingNumberException In presenza di attributi non numerici
     * @throws DatabaseConnectionException In caso di errore nella connessione al database
     */
    public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException, MissingNumberException, DatabaseConnectionException {
        List<Example> transazioni = new ArrayList<>();
        Connection con = db.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        TableSchema schema = new TableSchema(db, table);

        stmt = con.createStatement();
        String query = "SELECT DISTINCT * FROM " + table;
        rs = stmt.executeQuery(query);

        if (!rs.isBeforeFirst()) { // Verifica se il ResultSet è vuoto
            throw new EmptySetException("La tabella " + table + " è vuota.\n");
        }

        while (rs.next()) {
            Example example = new Example();
            for (int i = 0; i < schema.getNumberOfAttributes(); i++) {
                TableSchema.Column column = schema.getColumn(i);
                if (!column.isNumber()) {
                    throw new MissingNumberException("Attributo non numerico trovato: " + column.getColumnName() + "\n");
                }
                example.add(rs.getDouble(column.getColumnName()));
            }
            transazioni.add(example);
        }

        rs.close();
        stmt.close();
        db.closeConnection();

        return transazioni;
    }
}

