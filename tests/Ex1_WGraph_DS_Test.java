package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;


public class Ex1_WGraph_DS_Test {
    weighted_graph g;


    private static weighted_graph getGraph10V20E() {
        weighted_graph g1 = new WGraph_DS();

        for (int i = 0; i < 10; i++)
            g1.addNode(i);

        g1.connect(0, 1, 3.2);
        g1.connect(0, 2, 4.6);
        g1.connect(0, 3, 3);
        g1.connect(1, 6, 3.2);
        g1.connect(1, 7, 0.6);
        g1.connect(1, 2, 5.1);
        g1.connect(2, 8, 4);
        g1.connect(2, 9, 5);
        g1.connect(3, 1, 7);
        g1.connect(3, 4, 3.2);
        g1.connect(3, 5, 3.2);
        g1.connect(4, 7, 3.2);
        g1.connect(4, 0, 3.2);
        g1.connect(5, 1, 3.2);
        g1.connect(6, 2, 3.2);
        g1.connect(6, 3, 3.2);
        g1.connect(7, 0, 3.2);
        g1.connect(8, 5, 3.2);
        g1.connect(8, 6, 3.2);
        g1.connect(9, 8, 3.2);

        return g1;
    }

//////////////////////////////////////////////////////////////////////////

    ////// WGraph_DS

    /**
     * reset the graph to new graph every test
     */
    @BeforeEach
    public void declareGraph() {
        g = new WGraph_DS();
    }

    /**
     * 1. check getNode-
     * a. that doesn't exist in graph
     * b. that exist in graph
     * c. the node that insert equal to node we get (by key)
     */
    @Test
    public void getNode() {
        g.addNode(1);
        node_info tmp = g.getNode(2);
        Assertions.assertNull(tmp, "1a. FAIL!\n\t should be null");
        tmp = g.getNode(1);
        Assertions.assertNotNull(tmp, "1b. FAIL!\n\t should be not null");
        Assertions.assertEquals(1, tmp.getKey(), "1c. FAIL!\n\t should be 1");
    }


    /**
     * 2. check hasEdge-
     * a. between two nodes that one of them doesn't exist
     * b. between two nodes isn't edge
     * c. between two nodes connected
     * d. check from two sides
     */
    @Test
    public void hasEdge() {
        g.addNode(1);
        g.addNode(2);
        g.connect(1, 2, 3.2);
        Assertions.assertFalse(g.hasEdge(1, 3), "2a. FAIL!\n\t should be false");
        g.addNode(3);
        g.connect(1, 3, 2.4);
        Assertions.assertFalse(g.hasEdge(2, 3), "2b. FAIL!\n\t should be false");
        Assertions.assertTrue(g.hasEdge(1, 3), "2c. FAIL!\n\t should be true");
        Assertions.assertTrue(g.hasEdge(3, 1), "2d. FAIL!\n\t should be true");

    }

    /**
     * 3. check to getEdge-
     * a. between two nodes that one of them doesn't exist
     * b. between two nodes isn't edge
     * c. between two nodes connected
     */
    @Test
    public void getEdge() {
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(1, 3, 2.4);
        Assertions.assertEquals(-1, g.getEdge(2, 3), "3.a FAIL!\n\t should be -1");
        Assertions.assertEquals(-1, g.getEdge(2, 4), "3.b FAIL!\n\t should be -1");
        g.connect(2, 3, 5.6);
        Assertions.assertEquals(5.6, g.getEdge(2, 3), "3.c FAIL!\n\t should be 5.6");
    }

    /**
     * 4. check nodeSize (include addNode and removeNode functions)-
     * a. after adding some nodes.
     * b. after adding a node that already exists in the graph.
     * c. after adding some nodes and remove part of them.
     */
    @Test
    public void NodeSize() {
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        Assertions.assertEquals(4, g.nodeSize(), "4.a FAIL!\n\t should be 4");
        g.addNode(3);
        g.addNode(4);
        Assertions.assertEquals(4, g.nodeSize(), "4.b FAIL!\n\t should be 4");
        g.removeNode(3);
        Assertions.assertEquals(3, g.nodeSize(), "4.c FAIL!\n\t should be 3");
    }

    /**
     * 5. check edgeSize (include connect, removeNode and removeEdge functions) -
     * a. build graph with 10 node and 20 edges.
     * b. remove some edges.
     * c. remove node with some edges.
     * d. try to connect nodes that doesn't exist.
     * e. try to connect edges that already exist.
     */
    @Test
    public void edgeSize() {
        g = getGraph10V20E();

        Assertions.assertEquals(20, g.edgeSize(), "5.a FAIL!\n\t should be 20");
        g.removeEdge(0, 1);
        g.removeEdge(1, 6);
        g.removeEdge(8, 6);
        Assertions.assertEquals(17, g.edgeSize(), "5.b FAIL!\n\t should be 17");
        g.removeNode(1);
        Assertions.assertEquals(13, g.edgeSize(), "5.c FAIL!\n\t should be 13");
        g.connect(1, 6, 2);
        Assertions.assertEquals(13, g.edgeSize(), "5.d FAIL!\n\t should be 13");
        g.connect(8, 9, 2);
        Assertions.assertEquals(13, g.edgeSize(), "5.e FAIL!\n\t should be 13");

    }

    /**
     * 6. check getV() (include getV(node_id)) -
     * a. get V from graph with 10 nodes
     * b. get V(i) from i node
     */
    @Test
    public void getV() {
        g = getGraph10V20E();
        Collection<node_info> x = g.getV();
        Assertions.assertEquals(10, x.size(), "6.a FAIL!\n\t should be 10");
        x = g.getV(1);
        Assertions.assertEquals(6, x.size(), "6.b FAIL!\n\t should be 6");
    }

    /**
     * 7. check MC-
     * a. after build graph with 10 nodes and 20 edges
     * b. after remove 1 node with 5 edges
     * c. after remove 1 edge
     */
    @Test
    public void MC() {
        g = getGraph10V20E();
        Assertions.assertEquals(30, g.getMC(), "7.a FAIL!\n\t should be 30");
        g.removeNode(0);
        Assertions.assertEquals(36, g.getMC(), "7.b FAIL!\n\t should be 36");
        g.removeEdge(2, 6);
        Assertions.assertEquals(37, g.getMC(), "7.c FAIL!\n\t should be 37");
    }

    ////// WGraph_Algo

}