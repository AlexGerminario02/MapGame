package distance;

import clustering.Cluster;
import data.Data;
import data.Example;
import data.InvalidSizeException;

import java.util.Iterator;

/**
 * classe SingleLinkDistance
 * Implementa il metodo distance dell'interfaccia
 * ClusterDistance per calcolare la distanza tra due cluster
 * @author Team MAP Que Nada
 */
public class SingleLinkDistance implements ClusterDistance {

	/**
	 * costruttore
	 */
	public SingleLinkDistance() {
	}
	/**
	 * metodo distance
	 * restituisce la minima distanza tra due cluster
	 * con la distanza singlelink
	 *
	 * @param c1 primo cluster
	 * @param c2 secondo cluster
	 * @param d dataset
	 * @return min (un double)
	 */
	public double distance(Cluster c1, Cluster c2, Data d) throws InvalidSizeException {
		double min=Double.MAX_VALUE;

		Iterator<Integer> it1 = c1.iterator();
		while(it1.hasNext()) {
			Example e1=d.getExample(it1.next());
			Iterator<Integer> it2 = c2.iterator();
			while(it2.hasNext()) {
                double distance = e1.distance(d.getExample(it2.next()));
                if (distance<min)
					min=distance;
			}
		}
		return min;
	}
}

