package com.andymur.pg.java.algods.ds.graph;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GraphReaderTest {

    private GraphReader graphReader;

    @Test
    public void testGraphSimpleReader() throws FileNotFoundException, URISyntaxException {
        graphReader = new GraphReader();
        Graph<Integer, Integer> graph = graphReader.readFromResource("./ds/board.txt");

        Assert.assertNotNull(graph);
        GraphImpl.AdjacentMatrix<Integer, Integer> adjacentMatrix = graph.adjacentMatrix(0, Integer.MAX_VALUE, (a, b) -> a == Integer.MAX_VALUE || b == Integer.MAX_VALUE ? Integer.MAX_VALUE : a + b);
        System.out.println(adjacentMatrix.prettyPrintedAdjacentMatrix());
        System.out.println(adjacentMatrix.floydWarshall().prettyPrintedAdjacentMatrix());
    }

    @Ignore
    @Test
    public void testGraphNonWeightedEdgeListReader() throws FileNotFoundException, URISyntaxException {
        graphReader = new GraphReader();
        Graph<Integer, Integer> graph = graphReader.readNonWeightedEdgeListFromResource("./ds/edges-list.txt");
        Assert.assertNotNull(graph);
    }
}