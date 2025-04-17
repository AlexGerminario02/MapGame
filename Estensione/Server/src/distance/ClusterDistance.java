package distance;

import clustering.Cluster;
import data.Data;
import data.InvalidSizeException;

/**
 * Interfaccia ClusterDistance
 * contiene metodo per
 * calcolare la distanza tra due cluster
 *
 * @author Team MAP Que Nada
 */
public interface ClusterDistance {
	/**
	 * metodo distance
	 *
	 * @param c1 primo cluster
	 * @param c2 secondo cluster
	 * @param d dataset
	 * @return double
	 * @throws InvalidSizeException eccezzione
	 */
	double distance(Cluster c1, Cluster c2, Data d) throws InvalidSizeException;
}
