package com.andymur.pg.java.algods.ds.graph;


import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GraphImplTest {

    @Test
    public void testAdjacentMatrix() {
        Graph<Integer, String> weightedGraph = new GraphImpl<>(new HashSet<>(Arrays.asList("A", "B", "C")));
        weightedGraph.addEdge(GraphImpl.Edge.of("A", "B", 1));
        weightedGraph.addEdge(GraphImpl.Edge.of("A", "C", 1));
        weightedGraph.addEdge(GraphImpl.Edge.of("B", "C", 1));

        Assert.assertThat(
                weightedGraph.getNodeValues().stream().sorted().collect(Collectors.toList()),
                CoreMatchers.equalTo(
                        Arrays.asList("A", "B", "C")
                )
        );

        Assert.assertThat(
                weightedGraph.breadthFirstSearch("A").stream().sorted().collect(Collectors.toList()),
                CoreMatchers.equalTo(
                        Arrays.asList("A", "B", "C")
                )
        );

        GraphImpl.AdjacentMatrix<Integer, String> adjacentMatrix = weightedGraph.adjacentMatrix(0, Integer.MAX_VALUE, (a, b) -> a == Integer.MAX_VALUE || b == Integer.MAX_VALUE ? Integer.MAX_VALUE : a + b);
        System.out.println(adjacentMatrix.prettyPrintedAdjacentMatrix());
        Assert.assertNotNull(adjacentMatrix);
        //TODO replace actual with matcher
        Assert.assertThat(0, CoreMatchers.equalTo(adjacentMatrix.getValue(0, 0)));
        Assert.assertThat(1, CoreMatchers.equalTo(adjacentMatrix.getValue(0, 1)));
        Assert.assertThat(1, CoreMatchers.equalTo(adjacentMatrix.getValue(0, 2)));

        Assert.assertThat(1, CoreMatchers.equalTo(adjacentMatrix.getValue(1, 0)));
        Assert.assertThat(0, CoreMatchers.equalTo(adjacentMatrix.getValue(1, 1)));
        Assert.assertThat(1, CoreMatchers.equalTo(adjacentMatrix.getValue(1, 2)));

        Assert.assertThat(1, CoreMatchers.equalTo(adjacentMatrix.getValue(2, 0)));
        Assert.assertThat(1, CoreMatchers.equalTo(adjacentMatrix.getValue(2, 1)));
        Assert.assertThat(0, CoreMatchers.equalTo(adjacentMatrix.getValue(2, 2)));
    }

    @Test
    public void testDepthFirstSearch() {
        Graph<Integer, Integer> graph = new GraphImpl<>();

        graph.addEdge(GraphImpl.Edge.of(1, 2, 1));
        graph.addEdge(GraphImpl.Edge.of(1, 3, 1));
        graph.addEdge(GraphImpl.Edge.of(3, 4, 1));
        graph.addEdge(GraphImpl.Edge.of(3, 5, 1));
        graph.addEdge(GraphImpl.Edge.of(4, 6, 1));
        graph.addEdge(GraphImpl.Edge.of(5, 6, 1));
        graph.addEdge(GraphImpl.Edge.of(6, 7, 1));
        graph.addEdge(GraphImpl.Edge.of(8, 9, 1));
        graph.addEdge(GraphImpl.Edge.of(8, 10, 1));
        graph.addEdge(GraphImpl.Edge.of(9, 10, 1));

        Set<Integer> dfsStartingFromFirst = graph.depthFirstSearch(1);
        Assert.assertThat(
                dfsStartingFromFirst.stream().sorted().collect(Collectors.toList()),
                CoreMatchers.equalTo(
                        Arrays.asList(1, 2, 3, 4, 5, 6, 7)
                )
        );

        Set<Integer> dfsStartingFromNeun = graph.depthFirstSearch(9);

        Assert.assertThat(
                dfsStartingFromNeun.stream().sorted().collect(Collectors.toList()),
                CoreMatchers.equalTo(
                        Arrays.asList(8, 9, 10)
                )
        );
    }

    @Test
    public void testBreadthFirstSearch() throws FileNotFoundException, URISyntaxException {
        Graph<Integer, Integer> graph = new GraphImpl<>();

        graph.addEdge(GraphImpl.Edge.of(1, 2, 1));
        graph.addEdge(GraphImpl.Edge.of(1, 3, 1));
        graph.addEdge(GraphImpl.Edge.of(3, 4, 1));
        graph.addEdge(GraphImpl.Edge.of(3, 5, 1));
        graph.addEdge(GraphImpl.Edge.of(4, 6, 1));
        graph.addEdge(GraphImpl.Edge.of(5, 6, 1));
        graph.addEdge(GraphImpl.Edge.of(6, 7, 1));
        graph.addEdge(GraphImpl.Edge.of(8, 9, 1));
        graph.addEdge(GraphImpl.Edge.of(8, 10, 1));
        graph.addEdge(GraphImpl.Edge.of(9, 10, 1));

        Set<Integer> bfsStartingFromFirst = graph.breadthFirstSearch(1);
        Assert.assertThat(
                bfsStartingFromFirst.stream().sorted().collect(Collectors.toList()),
                CoreMatchers.equalTo(
                        Arrays.asList(1, 2, 3, 4, 5, 6, 7)
                )
        );

        Set<Integer> bfsStartingFromNeun = graph.breadthFirstSearch(9);

        Assert.assertThat(
                bfsStartingFromNeun.stream().sorted().collect(Collectors.toList()),
                CoreMatchers.equalTo(
                        Arrays.asList(8, 9, 10)
                )
        );
    }

    @Test
    public void testHasEdgeWithNodes() {
        Graph<Integer, String> nonOrientedWeightedGraph = new GraphImpl<>(new HashSet<>(Arrays.asList("A", "B", "C")));

        nonOrientedWeightedGraph.addEdge(GraphImpl.Edge.of("A", "B", 1));
        nonOrientedWeightedGraph.addEdge(GraphImpl.Edge.of("A", "C", 1));
        nonOrientedWeightedGraph.addEdge(GraphImpl.Edge.of("B", "C", 1));

        Assert.assertTrue(nonOrientedWeightedGraph.hasEdgeWithNodes("A", "B"));
        Assert.assertTrue(nonOrientedWeightedGraph.hasEdgeWithNodes("B", "A"));

        Assert.assertTrue(nonOrientedWeightedGraph.hasEdgeWithNodes("A", "C"));
        Assert.assertTrue(nonOrientedWeightedGraph.hasEdgeWithNodes("C", "A"));

        Assert.assertTrue(nonOrientedWeightedGraph.hasEdgeWithNodes("B", "C"));
        Assert.assertTrue(nonOrientedWeightedGraph.hasEdgeWithNodes("C", "B"));

        Graph<Integer, String> orientedWeightedGraph = new GraphImpl<>(new HashSet<>(Arrays.asList("A", "B", "C")), true);

        orientedWeightedGraph.addEdge(GraphImpl.Edge.of("A", "B", 1));
        orientedWeightedGraph.addEdge(GraphImpl.Edge.of("A", "C", 10));
        orientedWeightedGraph.addEdge(GraphImpl.Edge.of("B", "C", 1));

        Assert.assertTrue(orientedWeightedGraph.hasEdgeWithNodes("A", "B"));
        Assert.assertFalse(orientedWeightedGraph.hasEdgeWithNodes("B", "A"));

        Assert.assertTrue(orientedWeightedGraph.hasEdgeWithNodes("A", "C"));
        Assert.assertFalse(orientedWeightedGraph.hasEdgeWithNodes("C", "B"));

        Assert.assertTrue(orientedWeightedGraph.hasEdgeWithNodes("B", "C"));
        Assert.assertFalse(orientedWeightedGraph.hasEdgeWithNodes("C", "B"));
    }

    @Test
    public void testFloyd() {
        Graph<Integer, String> weightedGraph = new GraphImpl<>(new HashSet<>(Arrays.asList("A", "B", "C", "D", "E", "F")), true);

        weightedGraph.addEdge(GraphImpl.Edge.of("A", "B", 1));
        weightedGraph.addEdge(GraphImpl.Edge.of("B", "C", 2));
        weightedGraph.addEdge(GraphImpl.Edge.of("B", "D", 3));
        weightedGraph.addEdge(GraphImpl.Edge.of("D", "E", 1));
        weightedGraph.addEdge(GraphImpl.Edge.of("C", "E", 4));
        weightedGraph.addEdge(GraphImpl.Edge.of("C", "F", 3));

        GraphImpl.AdjacentMatrix<Integer, String> adjacentMatrix = weightedGraph.adjacentMatrix(0, Integer.MAX_VALUE, (a, b) -> a == Integer.MAX_VALUE || b == Integer.MAX_VALUE ? Integer.MAX_VALUE : a + b);
        System.out.println(adjacentMatrix.prettyPrintedAdjacentMatrix());

        Assert.assertThat(0, CoreMatchers.equalTo(adjacentMatrix.getValue(0, 0)));
        Assert.assertThat(1, CoreMatchers.equalTo(adjacentMatrix.getValue(0, 1)));
        Assert.assertThat(Integer.MAX_VALUE, CoreMatchers.equalTo(adjacentMatrix.getValue(0, 2)));
        Assert.assertThat(Integer.MAX_VALUE, CoreMatchers.equalTo(adjacentMatrix.getValue(0, 3)));
        Assert.assertThat(Integer.MAX_VALUE, CoreMatchers.equalTo(adjacentMatrix.getValue(0, 4)));
        Assert.assertThat(Integer.MAX_VALUE, CoreMatchers.equalTo(adjacentMatrix.getValue(0, 5)));


        GraphImpl.AdjacentMatrix<Integer, String> shortestPathAdjacentMatrix = adjacentMatrix.floydWarshall();
        System.out.println(shortestPathAdjacentMatrix.prettyPrintedAdjacentMatrix());

        // TODO: redo! not very correct assert statements
        Assert.assertThat(0, CoreMatchers.equalTo(adjacentMatrix.getValue(0, 0)));
        Assert.assertThat(1, CoreMatchers.equalTo(adjacentMatrix.getValue(0, 1)));
        Assert.assertThat(3, CoreMatchers.equalTo(adjacentMatrix.getValue(0, 2)));
        Assert.assertThat(4, CoreMatchers.equalTo(adjacentMatrix.getValue(0, 3)));
        Assert.assertThat(5, CoreMatchers.equalTo(adjacentMatrix.getValue(0, 4)));
        Assert.assertThat(6, CoreMatchers.equalTo(adjacentMatrix.getValue(0, 5)));
    }
}