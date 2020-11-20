package ex1.src;

import java.io.*;
import java.util.*;

/**
 * WGraph_Algo class represents an Undirected (positive) Weighted Graph Theory algorithms
 * note: all function work on graph that implements weighted_graph interface.
 */
public class WGraph_Algo implements weighted_graph_algorithms, Comparator<capsNode> {
    weighted_graph ga = new WGraph_DS();
    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g - graph
     */
    @Override
    public void init(weighted_graph g) {
        this.ga = g;
    }
    /**
     * Return the underlying graph of which this class works.
     * @return - pointer to this graph
     */
    @Override
    public weighted_graph getGraph() {
        return ga;
    }
    /**
     * Compute a deep copy of this weighted graph.
     * @return new graph that equal to this.graph.
     */
    @Override
    public weighted_graph copy() {
        WGraph_DS gaCopy = new WGraph_DS();
        for (node_info i : ga.getV()) // run on each node
            gaCopy.addNode(i.getKey());//and add them to new graph
        for (node_info i : ga.getV())
            for (node_info j : ga.getV(i.getKey())) { // for each node connect him to his neighbors
                double w = ga.getEdge(i.getKey(), j.getKey());
                if(!gaCopy.hasEdge(i.getKey(),j.getKey())) // if this edge doesn't exist
                    gaCopy.connect(i.getKey(), j.getKey(), w);
            }
        return gaCopy;
    }

    /**
     * reset tad field to infinity
     */
    private void resetTagToMaxValue() {     //O(v) (v = vertices in graph)
        for (node_info it : ga.getV())
            it.setTag(Integer.MAX_VALUE);
    }
    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node.
     * @return - true iff graph is connected
     */
    @Override
    public boolean isConnected() { //based on dijkstra algorithm
        resetTagToMaxValue();

        if (ga == null) return false;
        if (ga.nodeSize() <= 1) return true;

        node_info tmp = ga.getV().stream().findFirst().get(); //get "random" node and start scan the graph
        final int mark = 1;
        List<node_info> nodes = new ArrayList<>();
        nodes.add(tmp);
        tmp.setTag(mark); // set like "visible"
        int pointer = 0;
        while (pointer < nodes.size()) {
            for (node_info tmpNi : ga.getV(nodes.get(pointer).getKey())) {
                if (tmpNi.getTag() != mark) {
                    nodes.add(tmpNi);
                    tmpNi.setTag(mark);     //each node that we can go to him, mark and add him to 'nodes'
                }
            }
            pointer++;
        }
        return (nodes.size() == ga.nodeSize()); //if their size are equal, graph is connected.
    }

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * @param src - start node
     * @param dest - end (target) node
     * @return - the value of the path
     */
    @Override
    public double shortestPathDist(int src, int dest) { //based on dijkstra algorithm
        if (src == dest) return 0;
        if (!isConnected(src, dest)) //using private function that check if the path between two nodes are possible
            return -1;

        //int shortPath function we return al list that contain the short path, and the
        //last element is dest, and in his tag he save the shortPathDist.
        List<node_info> list = shortestPath(src, dest);
        return list.get(list.size()-1).getTag();
    }

    /**
     * check if this path between src->dest are possible
     * @param src - key of first node
     * @param dest - key of second node
     * @return boolean value if this possible
     */
    private boolean isConnected(int src, int dest) {
        if (ga.getNode(src) != null && ga.getNode(dest) != null) { //check if this node exist in graph
            node_info srcNode = ga.getNode(src);
            node_info destNode = ga.getNode(dest);
            if (src == dest) return true;

            resetTagToMaxValue();
            List<node_info> nodes = new ArrayList<>();
            final int mark = 1;
            srcNode.setTag(mark);
            nodes.add(srcNode);
            int pointer = 0;
            while (pointer < nodes.size()) {
                //scan all graph, and for each node we scan his neighbors until we get dest.
                for (node_info tmpNi : ga.getV(nodes.get(pointer).getKey())) {
                        if (tmpNi.getTag() != mark) {
                        nodes.add(tmpNi);
                        tmpNi.setTag(mark);
                        if (tmpNi == destNode)
                            return true;
                    }
                }
                pointer++;
            }
        }
        // if doesn't found - false.
        return false;
    }

    /**
     * reset the info field to empty string ("")
     */
    private void resetInfo() {
        for (node_info it : ga.getV())
            it.setInfo("");
    }
    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * Note if no such path --> returns null;
     * @param src - start node
     * @param dest - end (target) node
     * @return List of path
     */
    // Based on dijkstra algorithm
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (!isConnected(src, dest))
            return null;

        List<node_info> path = new LinkedList<>();
        if (src == dest) {
            path.add(ga.getNode(src));
            return path;
        }

        resetTagToMaxValue();
        resetInfo();
        final String beHere = "Be here!";

        //build Hashmap Which contains within the key all the vertices in the graph,
        // and within the value the vertex to which it is best accessed
        HashMap<Integer, Integer> bestAccess = new HashMap<>();

        //(-1) stop condition for the end
        bestAccess.put(src,-1);
        ga.getNode(src).setTag(0);
        PriorityQueue<capsNode> pathPQ = new PriorityQueue<>(this::compare);

        capsNode srcN = new capsNode(src, 0);
        pathPQ.add(srcN);

        capsNode tmp;
        while (pathPQ.size() != 0) {
            tmp = pathPQ.poll();

            if (tmp.getKey() == dest) {
               break;
            }

            for (node_info it : ga.getV(tmp.getKey())) {
                if (!it.getInfo().equals(beHere)) {
                    int tmpKey = it.getKey();
                    double tmpWeight = ga.getEdge(tmpKey,tmp.getKey());
                    if((tmp.getWeightDist()+tmpWeight)<it.getTag()) { //dijkstra condition
                        it.setTag((tmp.getWeightDist() + tmpWeight)); //update tag
                        bestAccess.put(it.getKey(),tmp.getKey()); //update best access
                        capsNode node = new capsNode(tmpKey, tmp.getWeightDist() + tmpWeight);
                        pathPQ.add(node);
                    }
                }
            }
            ga.getNode(tmp.getKey()).setInfo(beHere);
        }
        // while loop to restore the path and add it to stack
        //(because it store at opposite way)
        Stack<node_info> oppoPath = new Stack<>();
        oppoPath.push(ga.getNode(dest));

        int need = bestAccess.get(dest);
        while(need!=-1) {
            oppoPath.push(ga.getNode(need));
            need = bestAccess.get(need);
        }
        // the path
        while(!oppoPath.isEmpty())
            path.add(oppoPath.pop());

        return path;
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try {
            PrintWriter pw = new PrintWriter(new File(file+".txt"));

            StringBuilder sb = new StringBuilder();
            final String splitter = ",";

            // each node store- "key,info,tag"
            for (node_info node : ga.getV()) {
                sb.append(node.toString());
                sb.append("\n");
                //each neighbors of node stored serially
                //"key1,w1,key2,w2..."
                for (node_info nei : ga.getV(node.getKey())) {
                    sb.append(nei.getKey());
                    sb.append(splitter);
                    sb.append(ga.getEdge(node.getKey(), nei.getKey()));
                    sb.append(splitter);
                }
                sb.append("\n");
            }
            pw.write(sb.toString());
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) throws IOException {
        String line = "";
        final String splitter = ",";
        WGraph_DS g = new WGraph_DS();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {
                String[] node = line.split(splitter);
                // get the data as we write them
                int key = Integer.parseInt(node[0]);
                String info = node[1];
                double tag = Double.parseDouble(node[2]);
                g.addNode(key);
                g.getNode(key).setTag(tag);
                g.getNode(key).setInfo(info);
                line = br.readLine();
                node = line.split(splitter);
                if (node.length > 1) { //avoid case that don't have any neighbor
                    for (int i = 0; i < node.length; i = i + 2) {
                        int dest = Integer.parseInt(node[i]);
                        double w = Double.parseDouble(node[i + 1]);
                        if (g.getNode(dest) != null)
                            g.connect(key, dest, w);
                    }
                }
            }
            ga = g;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * cooperator to priority queue
     * check they summed up weight, the min is first.
     * @param o1 - caps node 1
     * @param o2 - caps node 2
     * @return equalization
     */
    @Override
    public int compare(capsNode o1, capsNode o2) {
        if (o1.getWeightDist() > o2.getWeightDist())
            return 1;
        else if (o1.getWeightDist() < o2.getWeightDist())
            return -1;
        else
            return 0;
    }
}
