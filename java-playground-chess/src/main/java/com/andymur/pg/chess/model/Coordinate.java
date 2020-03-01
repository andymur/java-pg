package com.andymur.pg.chess.model;

import com.andymur.pg.chess.model.util.Pair;

public class Coordinate {
    private final Pair<Integer, Integer> coordinate;

    private Coordinate() {
        this.coordinate = Pair.of(0, 0);
    }

}
