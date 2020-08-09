package com.andymur.pg.chess.model;

import com.andymur.pg.chess.model.util.Pair;

import java.util.Objects;

public class Coordinate {
    private final Pair<Integer, Integer> coordinate;

    private Coordinate() {
        this.coordinate = Pair.of(0, 0);
    }

    public Coordinate(final int firstValue,
                      final int secondValue) {
        this.coordinate = Pair.of(firstValue, secondValue);
    }

    public int getFirst() {
        return coordinate.first();
    }

    public int getSecond() {
        return coordinate.second();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(coordinate, that.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate);
    }
}
