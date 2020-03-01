package com.andymur.pg.chess.model;

public class Figure {

    private  final FigureType type;
    private final Side side;
    private final Coordinate position;

    public Figure(FigureType type,
                  Side side,
                  Coordinate position) {
        this.type = type;
        this.side = side;
        this.position = position;
    }

    enum Side {
        WHITE, BLACK
    }
}
