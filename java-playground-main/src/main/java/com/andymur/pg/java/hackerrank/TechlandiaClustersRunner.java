package com.andymur.pg.java.hackerrank;

import java.util.*;

public class TechlandiaClustersRunner {
    public static void main(String[] args) {
        TechlandiaClustersRunner runner = new TechlandiaClustersRunner();

        List<List<Integer>> grid = new ArrayList<>();

        grid.add(Arrays.asList(1, 1, 0, 0));
        grid.add(Arrays.asList(0, 0, 1, 0));
        grid.add(Arrays.asList(0, 0, 0, 0));
        grid.add(Arrays.asList(1, 0, 1, 1));
        grid.add(Arrays.asList(1, 1, 0, 1));

        System.out.println(runner.numberAmazonGoStores(5, 4, grid));

    }

    private int numberAmazonGoStores(int rows, int column, List<List<Integer>> grid) {
        int numberOfClusters = 0;

        Grid board = new Grid(rows, column, grid);
        List<Coordinate> buildingCoordinates = board.getBuildingCoordinates();

        Set<Coordinate> visitedBuildings = new HashSet<>();

        while (!buildingCoordinates.isEmpty()) {
            Coordinate nextBuildingCoordinate = buildingCoordinates.get(0);
            List<Coordinate> clusterCoordinates = board.getClusterCoordinates(nextBuildingCoordinate);
            buildingCoordinates.removeAll(clusterCoordinates);
            numberOfClusters++;
        }

        return numberOfClusters;
    }

    static class Grid {
        private final int rows;
        private final int cols;
        private final List<List<Cell>> board = new ArrayList<>();
        private List<Coordinate> buildings = new ArrayList<>();

        public Grid(int rows, int cols, List<List<Integer>> gridData) {
            this.rows = rows;
            this.cols = cols;
            int rowNum = 0;
            for (List<Integer> dataRow: gridData) {
                int colNum = 0;
                //board.add(dataRow.stream().map(Cell::of).collect(Collectors.toList()));
                List<Cell> boardRow = new ArrayList<>();
                for (Integer symbol: dataRow) {
                    Cell cell = Cell.of(symbol);
                    boardRow.add(cell);
                    if (Cell.BUILDING == cell) {
                        buildings.add(Coordinate.of(colNum, rowNum));
                    }
                    colNum++;
                }
                board.add(boardRow);
                rowNum++;
            }
        }

        public List<Coordinate> getBuildingCoordinates() {
            return buildings;
        }

        public List<Coordinate> getClusterCoordinates(Coordinate buildingCoordinate) {
            List<Coordinate> clusterCoordinates = new ArrayList<>();

            if (getValue(buildingCoordinate.y, buildingCoordinate.x) != Cell.BUILDING) {
                throw new IllegalArgumentException("Coordinate doesn't correspond to the buildingCoordinate: " + buildingCoordinate);
            }

            clusterCoordinates.add(buildingCoordinate);

            Queue<Coordinate> toVisit = getClusterCoordinates(buildingCoordinate, clusterCoordinates);
            while (!toVisit.isEmpty()) {
                Coordinate nextBuilding = toVisit.poll();
                toVisit.addAll(getClusterCoordinates(nextBuilding, clusterCoordinates));
            }
            return clusterCoordinates;
        }

        public Queue<Coordinate> getClusterCoordinates(Coordinate buildingCoordinate, List<Coordinate> visited) {
            Queue<Coordinate> toVisit = new ArrayDeque<>();

            List<Coordinate> adjacentCoordinates = getAdjacentCoordinates(buildingCoordinate);
            for (Coordinate adjacentCoordinate: adjacentCoordinates) {
                Cell cell = getValue(adjacentCoordinate.y, adjacentCoordinate.x);
                if (cell == Cell.BUILDING && !visited.contains(adjacentCoordinate)) {
                    visited.add(adjacentCoordinate);
                    toVisit.add(adjacentCoordinate);
                }
            }
            return toVisit;
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
        EMPTY(0), BUILDING(1);

        final Integer value;

        Cell(Integer value) {
            this.value = value;
        }

        static Cell of(Integer value) {
            switch (value) {
                case 0:
                    return EMPTY;
                case 1:
                    return BUILDING;
                default:
                    throw new IllegalArgumentException("Value is not supported: " + value);
            }
        }
    }
}
