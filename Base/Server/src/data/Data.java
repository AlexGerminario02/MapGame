package data;

import database.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe Data
 * Avvalora un oggetto data predefinito (fornito dal docente)
 * oppure leggendo i suoi esempi dalla tabella con nome tableName nel database.
 */
public class Data {
    private List<Example> data = new ArrayList<>(); // rappresenta il dataset

    /**
     * Costruttore
     * Crea un'istanza di classe Data leggendo i suoi esempi dalla tabella con nome tableName nel database.
     *
     * @param tableName Nome della tabella nel database
     * @throws NoDataException se la tabella è vuota.
     */
    public Data(String tableName) throws NoDataException {
        DbAccess dbAccess = new DbAccess();
        try {
            TableData tableData = new TableData(dbAccess);
            List<Example> examples = tableData.getDistinctTransazioni(tableName);
            this.data.addAll(examples);
        } catch (DatabaseConnectionException e) {
            throw new NoDataException("Errore di connessione al database: " + e.getMessage() + "\n");
        } catch (EmptySetException e) {
            throw new NoDataException("La tabella " + tableName + " è vuota: " + e.getMessage() + "\n");
        } catch (MissingNumberException e) {
            throw new NoDataException("Eccezione durante l'elaborazione dei dati: " + e.getMessage() + "\n");
        } catch (SQLException e) {
            throw new NoDataException("Errore SQL durante il recupero dei dati dalla tabella: " + e.getMessage() + "\n");
        }
    }

    /**
     * Metodo getNumberOfExample
     * Restituisce il numero degli esempi memorizzati in data.
     *
     * @return numero di esempi nel dataset
     */
    public int getNumberOfExample() {
        return data.size();
    }

    /**
     * Metodo getExample
     * Restituisce l'elemento dell'istanza data in posizione exampleIndex.
     *
     * @param exampleIndex indice dell'elemento da restituire
     * @return elemento in posizione exampleIndex
     */
    public Example getExample(int exampleIndex) {
        return data.get(exampleIndex);
    }

    /**
     * Metodo iterator
     * Restituisce un iteratore per scorrere gli elementi di data.
     *
     * @return iteratore per scorrere gli elementi di data
     */
    public Iterator<Example> iterator() {
        return data.iterator();
    }

    /**
     * Metodo distance
     * Restituisce la matrice triangolare superiore delle distanze Euclidee
     * calcolate tra gli esempi memorizzati in data.
     * @throws InvalidSizeException se le due istanze Example hanno dimensioni diverse
     * @return matrice delle distanze
     */
    public double[][] distance() throws InvalidSizeException {
        double[][] dist = new double[data.size()][data.size()];
        for (int i = 0; i < data.size(); i++) {
            dist[i][i] = 0;
            for (int j = i + 1; j < data.size(); j++) {
                double d = data.get(i).distance(data.get(j));
                dist[i][j] = d;
                dist[j][i] = 0; // Riflessione nella metà inferiore
            }
        }
        return dist;
    }

    /**
     * Metodo toString
     * Crea una stringa in cui memorizza gli esempi memorizzati nell’attributo data, opportunamente enumerati.
     *
     * @return stringa con gli esempi in data
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        Iterator<Example> iterator = iterator();
        int count = 0;

        while (iterator.hasNext()) {
            s.append(count++).append(":[").append(iterator.next().toString()).append("]\n");
        }

        return s.toString();
    }
}

