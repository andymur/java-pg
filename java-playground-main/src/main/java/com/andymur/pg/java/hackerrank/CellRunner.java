package com.andymur.pg.java.hackerrank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class CellRunner {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int grid[][] = new int[n][m];

        for(int grid_i=0; grid_i < n; grid_i++){
            for(int grid_j=0; grid_j < m; grid_j++){
                grid[grid_i][grid_j] = in.nextInt();
            }
        }

        Set<Coordinate> filledCells = filled(grid);
        int largestRegionSize = 0;

        while (!filledCells.isEmpty()) {
            Set<Coordinate> region = new HashSet<>();
            Coordinate firstFilledCell = new ArrayList<>(filledCells).get(0);
            regionCells(region, firstFilledCell, grid, n, m);
            filledCells.removeAll(region);

            if (region.size() > largestRegionSize) {
                largestRegionSize = region.size();
            }
        }

        System.out.print(largestRegionSize);
    }

    static Set<Coordinate> regionCells(Set<Coordinate> region,
                                Coordinate startCell, int[][] matrix, int rowNum, int colNum) {
        Set<Coordinate> neighbors = filledNeighbors(matrix, startCell);
        region.add(startCell);

        for (Coordinate neighbor: neighbors) {
            if (!region.contains(neighbor)) {
                regionCells(region, neighbor, matrix, rowNum, colNum);
            }
        }

        return region;
    }

    static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    static Set<Coordinate> filled(int[][] matrix) {
        Set<Coordinate> coordinates = new HashSet<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (1 == matrix[i][j]) {
                    coordinates.add(new Coordinate(j, i));
                }
            }
        }

        return coordinates;
    }

    static Set<Coordinate> neighbors(int[][] matrix, Coordinate coordinate) {
        int rowNum = matrix.length;
        int colNum = matrix[0].length;

        Coordinate northWest = new Coordinate(coordinate.x - 1, coordinate.y - 1);
        Coordinate north = new Coordinate(coordinate.x, coordinate.y - 1);
        Coordinate northEast = new Coordinate(coordinate.x + 1, coordinate.y - 1);
        Coordinate east = new Coordinate(coordinate.x + 1, coordinate.y);
        Coordinate west = new Coordinate(coordinate.x - 1, coordinate.y);
        Coordinate southWest = new Coordinate(coordinate.x - 1, coordinate.y + 1);
        Coordinate south = new Coordinate(coordinate.x, coordinate.y + 1);
        Coordinate southEast = new Coordinate(coordinate.x + 1, coordinate.y + 1);

        Set<Coordinate> result = new HashSet<>();
        putIfCan(result, northWest, rowNum, colNum);
        putIfCan(result, northEast, rowNum, colNum);
        putIfCan(result, north, rowNum, colNum);
        putIfCan(result, west, rowNum, colNum);
        putIfCan(result, east, rowNum, colNum);
        putIfCan(result, southWest, rowNum, colNum);
        putIfCan(result, southEast, rowNum, colNum);
        putIfCan(result, south, rowNum, colNum);

        return result;
    }

    static void putIfCan(Set<Coordinate> neighbors, Coordinate candidate, int rowNum, int colNum) {
        if (cellExists(candidate, rowNum, colNum)) {
            neighbors.add(candidate);
        }
    }

    static boolean cellExists(Coordinate coordinate, int rowNum, int colNum) {
        if (coordinate.x < 0 || coordinate.y < 0) {
            return false;
        }

        if (coordinate.x >= colNum || coordinate.y >= rowNum) {
            return false;
        }

        return true;
    }

    static Set<Coordinate> filledNeighbors(int[][] matrix, Coordinate coordinate) {
        Set<Coordinate> result = neighbors(matrix, coordinate);
        return result.stream().filter(neighbor -> {
            return matrix[neighbor.y][neighbor.x] == 1;
        }).collect(Collectors.toSet());
    }

    static class Coordinate {
        public final int x;
        public final int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Coordinate{");
            sb.append("x=").append(x);
            sb.append(", y=").append(y);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Coordinate that = (Coordinate) o;

            if (x != that.x) return false;
            if (y != that.y) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
