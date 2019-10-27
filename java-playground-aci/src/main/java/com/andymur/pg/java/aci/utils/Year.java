package com.andymur.pg.java.aci.utils;

public final class Year {
    private final int year;

    private Year(final int year) {
        this.year = year;
    }

    public boolean isLeap() {
        if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        } else {
            return year % 4 == 0;
        }
    }

    public static Year of(final int year) {
        return new Year(year);
    }
}
