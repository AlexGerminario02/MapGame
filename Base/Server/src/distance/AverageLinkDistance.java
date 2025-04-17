package distance;

import clustering.Cluster;
import data.Data;
import data.Example;
import data.InvalidSizeException;

import java.util.Iterator;

/**
 * classe AverageLinkDistance
 * Implementa l'interfaccia ClusterDistance per calcolare
 * la media della distanza tra due cluster
 *
 * @author Team MAP Que Nada
 */

public class AverageLinkDistance implements ClusterDistance {

    /**
     * costruttore
     */
    public AverageLinkDistance() {
    }

    /**
     * metodo distance
     * restituisce la media delle distanze minime tra i cluster
     * con la distanza AverageLink
     *
     * @param c1 primo cluster
     * @param c2 secondo cluster
     * @param d dataset
     * @return media selle distanze tra i cluster
     */
    public double distance(Cluster c1, Cluster c2, Data d) throws InvalidSizeException {
        double sum = 0.0;

        Iterator<Integer> it1 = c1.iterator();
        while (it1.hasNext()) {
            Example e1 = d.getExample(it1.next());
            Iterator<Integer> it2 = c2.iterator();
            while (it2.hasNext())
                sum += e1.distance(d.getExample(it2.next()));
        }

        return sum / (c1.getSize() * c2.getSize());
    }
}
