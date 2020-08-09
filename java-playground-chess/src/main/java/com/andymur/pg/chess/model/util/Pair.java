package com.andymur.pg.chess.model.util;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
