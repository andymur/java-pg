package com.andymur.pg.java.aci.utils;

public final class Year {
    private final int year;

    private Year(final int year) {
        this.year = year;
    }

    public boolean isLeap() {
        // I know this is not correct...yet!
        return year % 4 == 0;
    }

    public static Year of(final int year) {
        return new Year(year);
    }
}
