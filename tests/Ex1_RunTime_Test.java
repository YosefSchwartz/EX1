package ex1.tests;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.weighted_graph;
import ex1.src.weighted_graph_algorithms;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTimeout;

public class Ex1_RunTime_Test {

    /**
     * 1. runTime test-
     * a. check build graph with 1,000,000 nodes and 1,000,000 edges
     * b. check save this graph.
     * c. check load this graph.
     */
    @Test
    public void runTime() {
        assertTimeout(Duration.ofSeconds(15) , () -> {
            int v = 1000000;
            int e = 1000000;
            boolean ans = false;
            long start = System.currentTimeMillis();
            Random rnd = new Random(1);
            weighted_graph graph = new WGraph_DS();
            for (int i = 0; i < v; i++) {
                graph.addNode(i);
            }
            int n1,n2;
            for (int i = 0; i < e; i++) {
                double w = Math.random() * 20;
                n1 = rnd.nextInt((v - 1));
                n2 = rnd.nextInt((v - 1));
                graph.connect(n1, n2, w);
            }
            weighted_graph_algorithms ga1 = new WGraph_Algo();
            weighted_graph_algorithms ga2 = new WGraph_Algo();
            ga1.init(graph);
            long end0 = System.currentTimeMillis();
            System.out.println("time to build graph with " + v + " vertices and " + e + " edges: " + (end0 - start) / 1000.0);
            if ((end0 - start) / 1000.0 > 10) ans = true;
            Assertions.assertFalse(ans, "1a. FAIL!\\n\\t should be False");
            ans = false;
            ga1.save("ppp");
            long end1 = System.currentTimeMillis();
            System.out.println("time to save: " + (end1 - end0) / 1000.0);
            if ((end1 - end0) / 1000.0 > 10) ans = true;
            Assertions.assertFalse(ans, "1b. FAIL!\\n\\t should be False");
            ans = false;
            ga2.load("ppp");
            long end2 = System.currentTimeMillis();
            System.out.println("time to load: " + (end2 - end1) / 1000.0);
            if ((end2 - end1) / 1000.0 > 10) ans = true;
            Assertions.assertFalse(ans, "1c. FAIL!\\n\\t should be False");
        });
    }
}
