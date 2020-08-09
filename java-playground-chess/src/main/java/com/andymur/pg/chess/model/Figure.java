package com.andymur.pg.chess.model;

public class Figure {

    private final FigureType type;
    private final Color color;
    private final Cell position;

    public Figure(FigureType type,
                  Color color,
                  Cell position) {
        this.type = type;
        this.color = color;
        this.position = position;
    }
}
