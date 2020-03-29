package com.andymur.pg.java.algods.ds.graph;


import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

public class GraphImplTest {

    @Test
    public void testAdjacentMatrix() {
        Graph<Integer, String> weightedGraph = new GraphImpl<>(new HashSet<>(Arrays.asList("A", "B", "C")));
        weightedGraph.addEdge(GraphImpl.Edge.of("A", "B", 1));
        weightedGraph.addEdge(GraphImpl.Edge.of("A", "C", 1));
        weightedGraph.addEdge(GraphImpl.Edge.of("B", "C", 1));

        GraphImpl.AdjacentMatrix<Integer, String> adjacentMatrix = weightedGraph.adjacentMatrix(0, Integer.MAX_VALUE, (a, b) -> a == Integer.MAX_VALUE || b == Integer.MAX_VALUE ? Integer.MAX_VALUE : a + b);
        System.out.println(adjacentMatrix.prettyPrintedAdjacentMatrix());
        Assert.assertNotNull(adjacentMatrix);

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