package com.andymur.pg.chess.model;

import java.util.Map;
import java.util.Set;

public class Board {
    private final Set<Figure> figures;
    private final Map<Coordinate, Cell> cells;

    public Board(final Set<Figure> figures,
                 final Map<Coordinate, Cell> cells) {
        this.figures = figures;
        this.cells = cells;
    }
}
