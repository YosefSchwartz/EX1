package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class EX1_WGraph_Algo_Test {
    weighted_graph g;
    final double EPS = 0.0001;

    private static weighted_graph getGraphForAlgo() {
        weighted_graph g1 = new WGraph_DS();

        for (int i = 0; i < 7; i++)
            g1.addNode(i);

        g1.connect(0, 1, 14.3);
        g1.connect(0, 3, 10);
        g1.connect(1, 2, 6.2);
        g1.connect(1, 5, 2.3);
        g1.connect(2, 5, 7.6);
        g1.connect(2, 6, 8.4);
        g1.connect(3, 6, 2.4);
        g1.connect(3, 4, 2.3);

        return g1;
    }
    //////////////////////////////////////////////////////////////
    /**
     * reset the graph to new graph every test
     */
    @BeforeEach
    public void declareGraph() {
        g = new WGraph_DS();
    }

    /**
     * 1.  isConnected-
     * a. check connected graph.
     * b. check don't connected graph.
     */
    @Test
    public void IsConnected() {
        g = getGraphForAlgo();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        Assertions.assertTrue(ga.isConnected(), "1.a FAIL!\n\t should be True");
        g.removeEdge(0, 1);
        g.removeEdge(3, 6);
        Assertions.assertFalse(ga.isConnected(), "1.b FAIL!\n\t should be False");
    }

    /**
     * 2. check shortestPathDist-
     * a. check size of dist
     * b. check src=dist situation
     * c. check src or dest is doesn't exist.
     * d. check path doesn't connected
     */
    @Test
    public void shortestPathDist() {
        g = getGraphForAlgo();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        boolean ans = false;

        if (Math.abs(17 - ga.shortestPathDist(1, 3)) < EPS)
            ans = true;
        Assertions.assertTrue(ans, "2.a FAIL!\n\t should be True");
        Assertions.assertEquals(0, ga.shortestPathDist(2, 2), "2.b FAIL!\n\t should be 0");
        Assertions.assertEquals(-1, ga.shortestPathDist(0, 9), "2.c FAIL!\n\t should be -1");
        g.removeEdge(1, 0);
        g.removeEdge(3, 6);
        Assertions.assertEquals(-1, ga.shortestPathDist(0, 2), "2.d FAIL!\n\t should be -1");
    }

    /**
     * 3. check shortestPath-
     * a. check path size and values.
     * b. check src=dist situation
     * c. check src or dest is doesn't exist.
     * d. check path doesn't connected
     */
    @Test
    public void shortestPath() {
        g = getGraphForAlgo();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);

        List<node_info> pathA = ga.shortestPath(1, 3);
        Assertions.assertEquals(4, pathA.size(), "3.a FAIL!\n\t size should be 4");
        if ((pathA.get(0).getKey() != 1) || (pathA.get(1).getKey() != 2) ||
                (pathA.get(2).getKey() != 6) || (pathA.get(3).getKey() != 3))
            Assertions.fail("3a. FAIL!\n\t Problem with your path!");

        List<node_info> pathB = ga.shortestPath(1, 1);
        if (pathB.size() != 1 || pathB.get(0).getKey() != 1)
            Assertions.fail("3b. FAIL!\n\t Problem with your path! src=dest situation");

        List<node_info> pathC = ga.shortestPath(1, 90);
        Assertions.assertNull(pathC, "3c. FAIL!\n\t should be null");

        g.removeEdge(0, 3);
        g.removeEdge(3, 6);
        ga.init(g);
        List<node_info> pathD = ga.shortestPath(1, 3);
        Assertions.assertNull(pathD, "3d. FAIL!\n\t should be null");
    }

    /**
     * 4. check copy-
     * a. try copy, compare graph
     */
    @Test
    public void copy() {
        g = getGraphForAlgo();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        weighted_graph gCopy = ga.copy();
        Assertions.assertEquals(g,gCopy, "4a. FAIL!\n\t should be True");
    }

    /**
     * 5. check save and load -
     * a. save graph
     * b. load graph
     * c. compare them
     * d. try save at exist file
     * e. try load incorrect path
     */
    @Test
    public void saveAndLoad() throws IOException {
        g = getGraphForAlgo();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);

        String path = System.getProperty("user.home") + "/Desktop/Mygraph.txt";
        Assertions.assertTrue(ga.save(path), "5a. FAIL!\\n\\t should be True");
        weighted_graph gCopy = ga.copy();

        weighted_graph gEvil = new WGraph_DS();
        gEvil.addNode(1);
        ga.init(gEvil);
        Assertions.assertTrue(ga.load(path), "5b. FAIL!\\n\\t should be True");
        weighted_graph g2 = ga.getGraph();
        Assertions.assertEquals(g2,gCopy,"11c. FAIL!\\n\\t should be True");
        ga.init(gEvil);
        ga.save(path);
        ga.load(path);
        weighted_graph g3 = ga.copy();
        Assertions.assertNotEquals(g3,g,"5d. FAIL!\\n\\t should be False");
        path = System.getProperty("user.home") + "/Desktop/NotMygraph.txt";
        System.out.println("\n\n *****\tSHOULD PRINT FILE NOT FOUND EXCEPTION!\t*****");
        Assertions.assertFalse(ga.load(path), "12e. FAIL!\\n\\t should be False");
    }
}
