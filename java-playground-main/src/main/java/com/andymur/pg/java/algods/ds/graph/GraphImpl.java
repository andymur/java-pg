package com.andymur.pg.java.algods.ds.graph;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class GraphImpl<E extends Comparable<E>, V> implements Graph<E, V> {

    private final static Graph<?, ?> EMPTY_GRAPH = new GraphImpl<>();

    private final Set<Node<V>> nodes = new HashSet<>();
    private final Map<V, Set<Edge<E, V>>> edges = new HashMap<>();
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

        if (!nodes.contains(edge.start)) {
            nodes.add(edge.start);
        }

        if (!nodes.contains(edge.end)) {
            nodes.add(edge.end);
        }

        if (!edges.containsKey(edge.start.getValue())) {
            edges.put(edge.start.getValue(), new HashSet<>());
        }

        if (!edges.containsKey(edge.end.getValue())) {
            edges.put(edge.end.getValue(), new HashSet<>());
        }

        Set<Edge<E, V>> outgoingEdges = edges.get(edge.start.getValue());
        outgoingEdges.add(edge);

        if (!isOriented) {
            Set<Edge<E, V>> incomingEdges = this.edges.get(edge.end.getValue());
            incomingEdges.add(edge.reversed());
        }
    }

    @Override
    public Optional<Edge<E, V>> edgeWithNodes(V firstNodeLabel, V secondNodeLabel) {
        return edgeWithNodes(new Node<>(firstNodeLabel), new Node<>(secondNodeLabel));
    }

    @Override
    public Optional<Edge<E, V>> edgeWithNodes(Node<V> firstNode, Node<V> secondNode) {
        Optional<Edge<E, V>> edgeStartedOnFirst = this.edges.get(firstNode.getValue()).stream().filter(e -> e.end.equals(secondNode)).findFirst();
        Optional<Edge<E, V>> edgeStartedOnSecond = this.edges.get(secondNode.getValue()).stream().filter(e -> e.end.equals(firstNode)).findFirst();

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
    public Set<V> getNodeValues() {
        return nodes.stream().map(Node::getValue).collect(Collectors.toSet());
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

    private Set<Edge<E, V>> edgesStartingOnNode(Node<V> node) {
        return edges.get(node.getValue());
    }

    @Override
    public Set<Set<V>> getConnectedComponents() {
        //TODO: might be different for oriented and non-oriented graphs
        return Collections.emptySet();
    }

    @Override
    public Set<V> breadthFirstSearch(V initialNodeValue) {
        Set<V> visitedNodes = new HashSet<>(nodes.size());
        Queue<V> queue = new ArrayDeque<>(nodes.size());

        visitedNodes.add(initialNodeValue);
        queue.add(initialNodeValue);
        while (!queue.isEmpty()) {
            V nextNode = queue.poll();
            Set<V> notYetVisitedNodes = edgesStartingOnNode(Node.of(nextNode)).stream().map(e -> e.end.getValue())
                    .filter(node -> !visitedNodes.contains(node))
                    .collect(Collectors.toSet());
            visitedNodes.addAll(notYetVisitedNodes);
            queue.addAll(notYetVisitedNodes);
        }

        return visitedNodes;
    }

    @Override
    public Set<V> depthFirstSearch(V initialNodeValue) {
        Set<V> visitedNodes = new HashSet<>(nodes.size());
        Deque<V> stack = new ArrayDeque<>(nodes.size());

        stack.push(initialNodeValue);
        visitedNodes.add(initialNodeValue);
        while (!stack.isEmpty()) {
            V nextNode = stack.pop();
            Set<V> notYetVisitedNodes = edgesStartingOnNode(Node.of(nextNode)).stream().map(e -> e.end.getValue())
                    .filter(node -> !visitedNodes.contains(node))
                    .collect(Collectors.toSet());
            visitedNodes.addAll(notYetVisitedNodes);
            notYetVisitedNodes.forEach(stack::push);
        }

        return visitedNodes;
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

        public Edge<E, V> reversed() {
            return new Edge<>(end, start, value);
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

        public static <V> Node<V> of(V value) {
            return new Node<>(value);
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
