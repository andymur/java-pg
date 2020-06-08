package com.andymur.pg.java.algods.ds.graph;

import com.andymur.pg.java.algods.ds.graph.GraphImpl.Edge;
import com.andymur.pg.java.algods.ds.graph.GraphImpl.Node;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class GraphReader {

    /**
     * Reads from resource and returns positive weighted non-oriented graph in a maze-format, i.e. each edge has weight of 1
     * @param resourceName
     * @return
     */
    public Graph<Integer, Integer> readFromResource(final String resourceName) throws FileNotFoundException, URISyntaxException {
        try(Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(
                new File(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(resourceName)).toURI()))
        ))) {
            Board board = new Board();

            while (scanner.hasNextLine()) {
                String rowAsString = scanner.nextLine();
                board.addRow(rowAsString);
            }

            board.buildNodes();
            Graph<Integer, Integer> graph = new GraphImpl<>(board.getNodeValues());

            for (Edge<Integer, Integer> edge: board.getEdges()) {
                graph.addEdge(edge);
            }

            return graph;
        }
    }

    public Graph<Integer, Integer> readNonWeightedEdgeListFromResource(final String resourceName) throws FileNotFoundException, URISyntaxException {
        try(Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(
                new File(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(resourceName)).toURI()))
        ))) {

            Graph<Integer, Integer> graph = new GraphImpl<>();

            while (scanner.hasNextLine()) {
                String[] edgeAsString = scanner.nextLine().split(" ");
                graph.addEdge(Edge.of(Integer.parseInt(edgeAsString[0]), Integer.parseInt(edgeAsString[1]), 1));
            }

            return graph;
        }
    }

    static class Board {

        private final List<List<Cell>> board = new ArrayList<>();
        private final Map<Coordinate, Node<Integer>> nodes = new HashMap<>();

        public void addRow(String rowAsString) {
            List<Cell> row = new ArrayList<>();

            StringTokenizer tokenizer = new StringTokenizer(rowAsString, " ");
            while (tokenizer.hasMoreTokens()) {
                row.add(Cell.of(tokenizer.nextToken()));
            }
            addRow(row);
        }

        public void addRow(List<Cell> row) {
            board.add(row);
        }

        public void buildNodes() {
            int nodeLabel = 0;
            int rowNum = 0;
            for (List<Cell> boardRow: board) {
                int colNum = 0;
                for (Cell boardCell: boardRow) {
                    if (boardCell == Cell.EMPTY) {
                        Node<Integer> node = new Node<>(nodeLabel++);
                        nodes.put(Coordinate.of(colNum, rowNum), node);
                    }
                    colNum++;
                }
                rowNum++;
            }
        }

        public Set<Node<Integer>> getNodes() {
            return new HashSet<>(nodes.values());
        }

        public Set<Integer> getNodeValues() {
            return nodes.values().stream().map(Node::getValue).distinct().collect(Collectors.toCollection(TreeSet::new));
        }

        public List<Edge<Integer, Integer>> getEdges() {
            List<Edge<Integer, Integer>> edges = new ArrayList<>();

            for (Coordinate nodeCoordinate: nodes.keySet()) {
                final Node<Integer> currentNode = nodes.get(nodeCoordinate);
                final List<Coordinate> adjacentCoordinates = getAdjacentCoordinates(nodeCoordinate);

                for (Coordinate adjacentCoordinate: adjacentCoordinates) {
                    if (nodes.containsKey(adjacentCoordinate)) {
                        edges.add(Edge.of(currentNode.getValue(), nodes.get(adjacentCoordinate).getValue(), 1));
                    }
                }
            }

            return edges;
        }

        public List<Coordinate> getAdjacentCoordinates(Coordinate coordinate) {
            List<Coordinate> coordinates = new ArrayList<>();
            addIfPossible(coordinates, coordinate.up());
            addIfPossible(coordinates, coordinate.down());
            addIfPossible(coordinates, coordinate.left());
            addIfPossible(coordinates, coordinate.right());
            return coordinates;
        }

        private void addIfPossible(List<Coordinate> coordinates, Coordinate candidate) {
            if (coordinateExist(candidate)) {
                coordinates.add(candidate);
            }
        }

        private boolean coordinateExist(Coordinate coordinate) {
            int rowNum = coordinate.y;
            int colNum = coordinate.x;

            int rows = board.size();
            if (rows > 0) {
                int cols = board.get(0).size();
                return (rowNum >= 0 && rowNum < rows) && (colNum >= 0 && colNum < cols);
            }
            return false;
        }

        public Cell getValue(int rowNum, int colNum) {
            List<Cell> cellsRow =  board.get(rowNum);
            if (cellsRow == null) {
                throw new IllegalArgumentException("no row within index: " + rowNum);
            }
            return cellsRow.get(colNum);
        }

        private void putValue(Cell value, int rowNum, int colNum) {
            List<Cell> cellsRow =  board.get(rowNum);
            if (cellsRow == null) {
                throw new IllegalArgumentException("no row within index: " + rowNum);
            }
            cellsRow.set(colNum, value);
        }

        @Override
        public String toString() {
            return "Board{" +
                    "board=" + board +
                    '}';
        }
    }

    static class Coordinate {

        private final int x;
        private final int y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Coordinate up() {
            return Coordinate.of(this.x, this.y - 1);
        }

        public Coordinate down() {
            return Coordinate.of(this.x, this.y + 1);
        }

        public Coordinate left() {
            return Coordinate.of(this.x - 1, this.y);
        }

        public Coordinate right() {
            return Coordinate.of(this.x + 1, this.y);
        }

        public static Coordinate of(int x, int y) {
            return new Coordinate(x, y);
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x &&
                    y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    enum Cell {
        EMPTY('0'), WALL('X');

        final Character value;

        Cell(Character value) {
            this.value = value;
        }

        static Cell of(String value) {
            switch (value) {
                case "0":
                    return EMPTY;
                case "X":
                    return WALL;
                default:
                    throw new IllegalArgumentException("Value is not supported: " + value);
            }
        }
    }
}
