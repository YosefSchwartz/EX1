package ex1.src;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 * WGraph_DS class represents an un-directional weighted graph.
 * It implemented by 2 main hashMaps that represents the nodes of graph and neighbors of each node.
 */
public class WGraph_DS implements weighted_graph {
    HashMap<Integer, HashMap<node_info, Double>> nei;
    HashMap<Integer, node_info> myGraph;
    int nodeCounter;
    int edgeCounter;
    public int modeCounter;

    /**
     * constructor function
     */
    public WGraph_DS() {
        myGraph = new HashMap<>();
        nei = new HashMap<>();
        this.nodeCounter = this.modeCounter = this.edgeCounter = 0;
    }

    /**
     * inner class NodeInfo represents node in graph,
     * each node contain unique key and metadata (tag and info)
     */
    private static class NodeInfo implements node_info {
        int key;
        double tag = 0;
        String info = "";

        /**
         * constructor function
         */
        private NodeInfo(int id) {
            this.key = id;
        }
        /**
         * Return the key (id) associated with this node.
         * Note: each node_data should have a unique key.
         * @return - key
         */
        @Override
        public int getKey() {
            return key;
        }
        /**
         * return the remark (meta data) associated with this node.
         * @return - info
         */
        @Override
        public String getInfo() {
            return info;
        }
        /**
         * Allows changing the remark (meta data) associated with this node.
         * @param s - new value of the info
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }
        /**
         * Temporal data (aka distance, color, or state)
         * which can be used be algorithms
         * @return - tag
         */
        @Override
        public double getTag() {
            return tag;
        }
        /**
         * Allow setting the "tag" value for temporal marking an node - common
         * practice for marking by algorithms.
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        /**
         * override default equal function, the equalization made by key
         * @param other - other node_info
         * @return - boolean value of equalisation.
         */
        public boolean equals(Object other) {
            if (other instanceof node_info) {
                node_info node = (node_info) other;
                return node.getKey() == this.getKey();
            }
            return false;
        }
        /***
         * toString function
         * @return - String that contain the data of this node
         */
        public String toString() {
            if(info == null) info="";
            return ""+ key+","+info+","+tag;
        }
    }

    /**
     * return the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        if (myGraph != null)
            if (isInGraph(key))
                return myGraph.get(key);
        return null;
    }
    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method should run in O(1) time.
     * @param node1 - src
     * @param node2 - dest
     * @return true iff there is an edge
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (isInGraph(node1, node2)) {
            return nei.get(node1).containsKey(getNode(node2));
        }
        return false;
    }

    /**
     * return the weight if the edge (node1, node2).
     * In case there is no such edge - should return -1
     * @param node1 - src
     * @param node2 - dest
     * @return - value of edge or -1 if no such edge
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (!hasEdge(node1, node2))
            return -1;
        else {
            return nei.get(node1).get(getNode(node2));
        }
    }

    /**
     * add a new node to the graph with the given key.
     * Note: if there is already a node with such a key -> no action should be performed.
     * @param key - unique key of new node.
     */
    @Override
    public void addNode(int key) {
        if (!isInGraph(key)) {
            node_info tmp = new NodeInfo(key);
            myGraph.put(key, tmp);
            nei.put(key, new HashMap<>());
            nodeCounter++;
            modeCounter++;
        }
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (isInGraph(node1, node2)) {
            if(node1==node2) return; // because connect one node to himself is meaningless.
            if (!hasEdge(node1, node2)) { //check if this edge already exist
                nei.get(node1).put(getNode(node2), w);
                nei.get(node2).put(getNode(node1), w);
                edgeCounter++;
                modeCounter++;
            } else if (w != nei.get(node1).get(getNode(node2))) { //check if weight need to updates weight
                nei.get(node1).replace(getNode(node2), w);
                nei.get(node2).replace(getNode(node1), w);
                modeCounter++;
            }
        }
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return myGraph.values();
    }
    /**
     *
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if (isInGraph(node_id)) {
            return nei.get(node_id).keySet();
        }
        return null;
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     * @param key the node that should be removed
     */
    @Override
    public node_info removeNode(int key) {
        if (isInGraph(key)) {
            for (node_info it : getV(key)) { //remove all edges are this node connected
                nei.get(it.getKey()).remove(getNode(key));
                edgeCounter--;
                modeCounter++;
            }
            nodeCounter--;
            modeCounter++;
            nei.remove(key);    //remove from neighbors data structure
            return myGraph.remove(key); // remove from graph data structure and return it
        }
        return null;
    }
    /**
     * Delete the edge from the graph,.
     * @param node1 - one point of edge
     * @param node2 - second point of edge
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) { // check if this edge exist
            nei.get(node1).remove(getNode(node2));
            nei.get(node2).remove(getNode(node1));
            edgeCounter--;
            modeCounter++;
        }
    }
    /** return the number of vertices (nodes) in the graph.
     * @return size of node in graph.
     */
    @Override
    public int nodeSize() {
        return nodeCounter;
    }
    /**
     * return the number of edges (un-directional graph).
     * @return size of edge in graph
     */
    @Override
    public int edgeSize() {
        return edgeCounter;
    }
    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @return sum of act that performed in graph
     */
    @Override
    public int getMC() {
        return modeCounter;
    }

    /**
     * override equal function, check if two graph are same (check bi directional inclusion)
     * @param obj - other graph
     * @return - boolean value of equalization
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof weighted_graph) {
            weighted_graph g1 = this;
            weighted_graph g2 = (weighted_graph) obj;
            int[] g1Nodes = nodes(g1);
            int[] g2Nodes = nodes(g2);
            if(g1Nodes.length != g2Nodes.length) //check if two graph had same node size
                return false;
            for (int i = 0; i < g1Nodes.length; i++) {
                if (g1Nodes[i] != g2Nodes[i]) //check equalization of nodes
                    return false;

                int[] g1NiNodes = new int[g1.getV(i).size()];
                int[] g2NiNodes = new int[g2.getV(i).size()];

                if(g1NiNodes.length!=g2NiNodes.length) //check for each node if he had same neighbors size
                    return false;
                int p = 0;
                for (node_info it : g1.getV(i)) {
                    g1NiNodes[p] = it.getKey();
                    p++;
                }
                p = 0;
                for (node_info it : g2.getV(i)) {
                    g2NiNodes[p] = it.getKey();
                    p++;
                }
                Arrays.sort(g2NiNodes);
                Arrays.sort(g1NiNodes);
                for (int j = 0; j < g1NiNodes.length; j++) //check equalization of the neighbors.
                    if (g1NiNodes[j] != g2NiNodes[j])
                        return false;

            }
            return true;
        } else
            return false;

    }

    /////////////////Private function///////////////////////////

    /**
     * check if node1 is in graph
     * @param node1 - the node that need to check
     * @return - true if yes, otherwise no.
     */
    private boolean isInGraph(int node1) { //O(1)
        return myGraph.containsKey(node1);
    }
    /**
     * check if node1 and node2 is in graph
     * @param node1 - first node
     * @param node2 - second node
     * @return - true iff both are in graph
     */
    private boolean isInGraph(int node1, int node2) { //O(1)
        return (myGraph.containsKey(node1) && myGraph.containsKey(node2));
    }
    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for (int i = 0; i < size; i++) {
            ans[i] = nodes[i].getKey();
        }
        Arrays.sort(ans);
        return ans;
    }
}
