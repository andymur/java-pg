package com.andymur.pg.java.algods.ds.graph;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class GraphImpl<E extends Comparable<E>, V> implements Graph<E, V> {

    private final static Graph<?, ?> EMPTY_GRAPH = new GraphImpl<>();

    private final Set<Node<V>> nodes = new HashSet<>();
    private final List<Edge<E, V>> edges = new ArrayList<>();
    private final boolean isOriented;

    public GraphImpl() {
        this(new HashSet<>(), false);
    }

    public GraphImpl(Set<V> vertices) {
        this(vertices, false);
    }

    public GraphImpl(Set<V> vertices, boolean isOriented) {
        nodes.addAll(
                vertices.stream().map(Node::new).collect(Collectors.toSet())
        );
        this.isOriented = isOriented;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public void addEdge(Edge<E, V> edge) {

        if (!nodes.contains(edge.end) || !nodes.contains(edge.start)) {
            throw new IllegalArgumentException("Edge cannot be added, it contains node(s) not part of this graph");
        }

        if (!edges.contains(edge)) {
            edges.add(edge);
        }
    }

    @Override
    public Optional<Edge<E, V>> edgeWithNodes(V firstNodeLabel, V secondNodeLabel) {
        return edgeWithNodes(new Node<>(firstNodeLabel), new Node<>(secondNodeLabel));
    }

    @Override
    public Optional<Edge<E, V>> edgeWithNodes(Node<V> firstNode, Node<V> secondNode) {
        Optional<Edge<E, V>> edgeStartedOnFirst = edges.stream().filter(edge -> edge.start.equals(firstNode) && edge.end.equals(secondNode)).findFirst();
        Optional<Edge<E, V>> edgeStartedOnSecond = edges.stream().filter(edge -> edge.start.equals(secondNode) && edge.end.equals(firstNode)).findFirst();

        if (edgeStartedOnFirst.isPresent()) {
            return edgeStartedOnFirst;
        } else if (isOriented) {
            return Optional.empty();
        }

        return edgeStartedOnSecond;
    }

    @Override
    public boolean hasEdgeWithNodes(V firstNode, V secondNode) {
        return edgeWithNodes(firstNode, secondNode).isPresent();
    }

    @Override
    public Set<Node<V>> getNodes() {
        return nodes;
    }

    @Override
    public AdjacentMatrix<E, V> adjacentMatrix(E equalityValue,
                                               E infinityValue,
                                               BinaryOperator<E> sumOperator) {
        return new AdjacentMatrix<>(equalityValue, infinityValue, sumOperator, this);
    }

    @Override
    public AdjacentMatrix<E, V> floydWarshallAdjacentMatrix(E equalityValue, E infinityValue, BinaryOperator<E> sumOperator) {
        AdjacentMatrix<E, V> adjacentMatrix = new AdjacentMatrix<>(equalityValue, infinityValue, sumOperator, this);
        return adjacentMatrix.floydWarshall();
    }

    private Set<Edge<E, V>> edgesWithNode(Node<V> node) {
        Set<Edge<E, V>> edges = new HashSet<>();
        edges.addAll(edgesEndingOnNode(node));
        edges.addAll(edgesEndingOnNode(node));
        return edges;
    }

    private Set<Edge<E, V>> edgesStartingOnNode(Node<V> node) {
        return edges.stream().filter(edge -> edge.start.equals(node)).collect(Collectors.toSet());
    }

    private Set<Edge<E, V>> edgesEndingOnNode(Node<V> node) {
        return edges.stream().filter(edge -> edge.end.equals(node)).collect(Collectors.toSet());
    }

    static class AdjacentMatrix<E extends Comparable<E>, V> {

        private static final String INFINITY_SYMBOL = "∞";
        private final List<V> nodeLabels = new ArrayList<>();
        private final Map<V, Map<V, E>> matrix = new HashMap<>();
        private final E equalityValue;
        private final E infinityValue;

        private BinaryOperator<E> sumOperator;

        public AdjacentMatrix(E equalityValue,
                              E infinityValue,
                              BinaryOperator<E> sumOperator,
                              Graph<E, V> graph) {
            this.sumOperator = sumOperator;
            this.equalityValue = equalityValue;
            this.infinityValue = infinityValue;

            final Set<Node<V>> nodes = graph.getNodes();
            int nodeNumber = nodes.size();

            for (Node<V> rowNode: nodes) {
                Map<V, E> adjacentMatrixRow = new HashMap<>(nodeNumber);
                nodeLabels.add(rowNode.value);
                for (Node<V> columnNode: nodes) {
                    if (rowNode.equals(columnNode)) {
                        adjacentMatrixRow.put(columnNode.getValue(), this.equalityValue);
                    } else {
                        adjacentMatrixRow.put(columnNode.getValue(), graph.edgeWithNodes(rowNode, columnNode).map(edge -> edge.value).orElse(this.infinityValue));
                    }
                }
                matrix.put(rowNode.value, adjacentMatrixRow);
            }
        }

        public String prettyPrintedAdjacentMatrix() {
            StringBuilder builder = new StringBuilder(matrix.size() * matrix.size() * 2);
            builder.append(String.format("     %s", matrix.keySet().stream().map(Object::toString).collect(Collectors.joining(", ")))).append("\n");
            for (Map.Entry<V, Map<V, E>> adjacentMatrixRow: matrix.entrySet()) {
                builder.append(String.format("(%s): ", adjacentMatrixRow.getKey().toString()));
                List<E> rowWeights = new ArrayList<>();

                for (V nodeLabel: matrix.keySet()) {
                    rowWeights.add(adjacentMatrixRow.getValue().get(nodeLabel));

                }
                builder.append(
                        rowWeights.stream()
                                .map(weight -> weight.equals(infinityValue) ? INFINITY_SYMBOL : weight.toString())
                                .collect(Collectors.joining(", "))
                );
                builder.append("\n");
            }

            return builder.toString();
        }

        //TODO: it must make copy
        public AdjacentMatrix<E, V> floydWarshall() {
            int matrixDim = matrix.size();
            for (int k = 0; k < matrixDim; k++) {
                for (int i = 0; i < matrixDim; i++) {
                    for( int j = 0; j< matrixDim; j++){
                        E currentValue = getValue(i, j);
                        E newValue = sumOperator.apply(getValue(i, k), getValue(k, j));
                        putValue(currentValue.compareTo(newValue) < 0 ? currentValue : newValue , i, j);
                    }
                }
            }
            return this;
        }

        public E getValue(int row, int col) {
            V nodeLabel = nodeLabels.get(row);
            if (nodeLabel == null) {
                throw new IllegalArgumentException("No node for row num: " + row);
            }
            List<E> nodeRow =  new ArrayList<>(matrix.get(nodeLabel).values());
            return nodeRow.get(col);
        }

        private void putValue(E value, int row, int col) {
            V rowNodeLabel = nodeLabels.get(row);
            V colNodeLabel = nodeLabels.get(col);

            if (rowNodeLabel == null || colNodeLabel == null) {
                throw new IllegalArgumentException("No node for row or col num: " + row + " " + col);
            }
            matrix.get(rowNodeLabel).put(colNodeLabel, value);
        }
    }

    static class Edge<E extends Comparable<E>, V> {

        private final E value;
        private final Node<V> start;
        private final Node<V> end;

        public Edge(V start, V end, E value) {
            this(new Node<>(start), new Node<>(end), value);
        }

        public Edge(Node<V> start,
                    Node<V> end,
                    E value) {
            this.value = value;
            this.start = start;
            this.end = end;
        }

        public static <E extends Comparable<E>, V> Edge<E, V> of(V start, V end, E value) {
            return new Edge<>(start, end, value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge<?, ?> edge = (Edge<?, ?>) o;
            return value.equals(edge.value) &&
                    start.equals(edge.start) &&
                    end.equals(edge.end);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, start, end);
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "start=" + start +
                    ", end=" + end +
                    ", value=" + value +
                    '}';
        }
    }

    static class Node<V> {
        private final V value;

        public Node(V value) {
            this.value = value;
        }

        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return value.equals(node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }
}
