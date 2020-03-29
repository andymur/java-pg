package com.andymur.pg.java.algods.ds.graph;

import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;

public interface Graph<E extends Comparable<E>, V> {
    void addEdge(GraphImpl.Edge<E, V> edge);
    Set<GraphImpl.Node<V>> getNodes();
    GraphImpl.AdjacentMatrix<E, V> adjacentMatrix(E equalityValue, E infinityValue, BinaryOperator<E> sumOperator);
    GraphImpl.AdjacentMatrix<E, V> floydWarshallAdjacentMatrix(E equalityValue, E infinityValue, BinaryOperator<E> sumOperator);
    Optional<GraphImpl.Edge<E, V>> edgeWithNodes(V firstNode, V secondNode);

    Optional<GraphImpl.Edge<E, V>> edgeWithNodes(GraphImpl.Node<V> firstNode, GraphImpl.Node<V> secondNode);

    boolean hasEdgeWithNodes(V firstNode, V secondNode);
}
