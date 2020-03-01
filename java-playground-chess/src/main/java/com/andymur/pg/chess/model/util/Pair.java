package com.andymur.pg.chess.model.util;

public class Pair<U, V> {
    private final U first;
    private final V second;

    protected Pair(final U first, final V second) {
        this.first = first;
        this.second = second;
    }

    public static <U, V> Pair<U, V> of(final U first, final V second) {
        return new Pair<>(first, second);
    }

    public U first() {
        return first;
    }

    public V second() {
        return second;
    }
}
