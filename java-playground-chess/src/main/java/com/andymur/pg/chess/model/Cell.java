package com.andymur.pg.chess.model;

import java.util.Arrays;
import java.util.List;

public class Cell {

    private final static List<Character> HORIZONTAL_COORDINATE_UPPER_VALUES = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H');
    private final static List<Character> HORIZONTAL_COORDINATE_LOWER_VALUES = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');
    private final Coordinate coordinates;
    private final Color color;

    private static final Cell NONE = new Cell(new Coordinate(-1, -1));

    public Cell(char horizontalCoordinate,
                int verticalCoordinate) {
        final Coordinate coordinates = new Coordinate(horizontalCoordinateToInt(horizontalCoordinate), verticalCoordinate);
        if (!checkIfValidCoordinates(coordinates)) {
            throw new IllegalArgumentException("Coordinates are not correct for the chess board cell!");
        }
        this.coordinates = coordinates;
        this.color = getColor(coordinates);
    }

    private Cell(final Coordinate coordinates) {
        this.coordinates = coordinates;
        this.color = getColor(coordinates);
    }

    private Cell(final Coordinate coordinates, final Color color) {
        this.coordinates = coordinates;
        this.color = color;
    }

    @Override
    public String toString() {
        return String.valueOf(horizontalCoordinateToChar(coordinates.getFirst())) + coordinates.getSecond();
    }

    public Color getColor() {
        return color;
    }

    private static char horizontalCoordinateToChar(int horizontalCoordinate) {
        return HORIZONTAL_COORDINATE_UPPER_VALUES.get(horizontalCoordinate - 1);
    }

    private static int horizontalCoordinateToInt(char horizontalCoordinate) {
        if (HORIZONTAL_COORDINATE_UPPER_VALUES.contains(horizontalCoordinate)) {
            return Character.getNumericValue(horizontalCoordinate) - Character.getNumericValue('A') + 1;
        } else if (HORIZONTAL_COORDINATE_LOWER_VALUES.contains(horizontalCoordinate)) {
            return Character.getNumericValue(horizontalCoordinate) - Character.getNumericValue('a') + 1;
        }
        throw new IllegalArgumentException("Horizontal coordinate value is not correct!");
    }

    private static boolean checkIfValidCoordinates(final Coordinate coordinate) {
        return checkIfValidCoordinateValue(coordinate.getFirst()) && checkIfValidCoordinateValue(coordinate.getSecond());
    }

    private static boolean checkIfValidCoordinateValue(final int coordinateValue) {
        return coordinateValue >= 1 && coordinateValue <= 8;
    }

    private static Color getColor(final Coordinate coordinate) {
        return (coordinate.getFirst() + coordinate.getSecond()) % 2 == 0 ? Color.WHITE : Color.BLACK;
    }
}
