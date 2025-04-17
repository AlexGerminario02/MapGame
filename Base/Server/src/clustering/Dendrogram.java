package clustering;

import data.Data;

import java.io.Serializable;
/**
 * classe Dendrogram
 * Modella un dendrogramma
 *
 * @author Team MAP Que Nada
 */
class Dendrogram implements Serializable {
    private ClusterSet[] tree; //modella il dendrogramma

    /**
     * Costruttore
     * @param depth profondità del dendrogramma
     */
    Dendrogram(int depth) throws InvalidDepthException {
        if (depth <= 0) {
            throw new InvalidDepthException("Profondità non valida!\n");
        }
        tree=new ClusterSet[depth];
    }

    /**
     * metodo getClusterSet
     * Inserisce il cluster set c al livello level di tree
     * @param level livello del dendrogramma in cui inserire il cluster set
     */
    void setClusterSet(ClusterSet c, int level){
        tree[level]=c;
    }

    /**
     * metodo getClusterSet
     * Restituisce il cluster set al livello level di tree
     * @param level livello del dendrogramma da restituire
     * @return il cluster set al livello level di tree
     */
    ClusterSet getClusterSet(int level) {
        return tree[level];
    }

    /**
     * metodo getDepth
     * Restituisce la profondità del dendrogramma
     * @return la profondità del dendrogramma
     */
    int getDepth() {
        return tree.length;
    }

    /**
     * metodo toString
     * Restituisce una rappresentazione testuale del dendrogramma
     * @return una rappresentazione testuale del dendrogramma
     */
    public String toString(){
        String v="";
        for (int i=0;i<tree.length;i++)
            v+=("level"+i+":\n"+tree[i]+ "\n");
        return v;
    }

    /**
     * metodo toString
     * Restituisce una rappresentazione testuale del dendrogramma
     * @param data dataset di esempi
     * @return una rappresentazione testuale del dendrogramma
     */
    public String toString(Data data) throws InvalidDepthException {
        String v="";
        for (int i=0;i<tree.length;i++)
            v+=("level"+i+":\n"+tree[i].toString(data)+"\n");
        return v;
    }
}
