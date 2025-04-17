package clustering;

import data.Data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


/**
 * Rappresenta un cluster di dati.
 * Un cluster è una raccolta di indici di dati che sono stati raggruppati insieme.
 * <p>
 * Questo è il costruttore di default che inizializza un nuovo cluster senza dati.
 */
public class Cluster implements Iterable<Integer>, Cloneable, Serializable {
	 /**
     * Inizializza un nuovo cluster senza dati.
     */
    public Cluster() {
        // Costruttore di default
    }
    /**
     * Insieme degli indici degli esempi raggruppati nel cluster.
     */
    private Set<Integer> clusteredData = new TreeSet<>();
	/**
	 * metodo addData
	 * aggiunge l'indice di posizione id al cluster
	 *
	 * @param id indice da aggiungere al cluster
	 */
	void addData(int id){
        clusteredData.add(id);
	}

	/**
	 * metodo getSize
	 * restituisce la dimensione del cluster
	 *
	 * @return dimensione del cluster
	 */
	public int getSize() {
		return clusteredData.size();
	}

	/**
	 * metodo iterator
	 * restituisce un iteratore per scorrere gli elementi del cluster
	 *
	 * @return clusteredData.iterator() iteratore per scorrere gli elementi del cluster
	 */
	public Iterator<Integer> iterator() {
		return clusteredData.iterator();
	}

	/**
	 * metodo clone
	 * crea una copia del cluster
	 *
	 * @return copia del cluster
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Cluster clone() throws CloneNotSupportedException {
		Cluster clone = null;
		try {
			clone = (Cluster) super.clone();
			clone.clusteredData = (Set<Integer>) ((TreeSet<Integer>) this.clusteredData).clone();
		} catch (CloneNotSupportedException e) {
			throw new CloneNotSupportedException("Errore nella clonazione!");
		}

		return clone;
	}

	/**
	 * metodo mergeCluster
	 * crea un nuovo cluster che è la fusione del cluster corrente e del cluster c
	 *
	 * @param c cluster da unire al cluster corrente
	 * @return newC cluster che è la fusione del cluster corrente e del cluster c
	 */
	Cluster mergeCluster(Cluster c) {
		Cluster newC = new Cluster();
		Iterator<Integer> it1 = this.iterator();
		Iterator<Integer> it2 = c.iterator();

		while (it1.hasNext()) {
			newC.addData(it1.next());
		}
		while (it2.hasNext()) {
			newC.addData(it2.next());
		}

		return newC;
	}


	/**
	 * metodo toString
	 * restituisce una stringa contenente gli indici degli esempi raggruppati nel cluster
	 *
	 * @return str stringa contenente gli indici degli esempi raggruppati nel cluster
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		Iterator<Integer> it = this.iterator();

		if (it.hasNext())
			str.append(it.next());

		while (it.hasNext())
			str.append(",").append(it.next());

		return str.toString();
	}

	/**
	 * metodo toString
	 * restituisce una stringa contenente gli esempi raggruppati nel cluster
	 *
	 * @param data oggetto di classe Data che modella il dataset su cui il clustering è calcolato
	 * @return str stringa contenente gli esempi raggruppati nel cluster
	 */

	public String toString(Data data) {
		StringBuilder str = new StringBuilder();
		Iterator<Integer> it = clusteredData.iterator();

		while (it.hasNext())
			str.append("<[").append(data.getExample(it.next())).append("]>");

		return str.toString();
	}
}
