package ex1.src;

/**
 * capsNode class represent object the contain key and sum of some weights
 * use by dijkstra algorithm at WGraph_Algo
 */
public class capsNode{
    int key;
    double weightDist;

    /**
     * constructor function
     * @param key - key
     * @param weightDist - sum of weight
     */
    public capsNode(int key, double weightDist)
    {
        this.key = key;
        this.weightDist = weightDist;
    }

    /**
     * get this capsNode key
     * @return - key
     */
    public int getKey() {
        return key;
    }

    /**
     * get this capsNode distance
     * @return - distance
     */
    public double getWeightDist() {
        return weightDist;
    }
}

