package com.andymur.pg.java.basics;

import java.math.BigDecimal;

public class BigDecRunner {
    public static void main(String[] args) {

        int scale = 5;

        long midRate = 116560L;
        long spread = 21 / 2;
        System.out.println(midRate + " " + spread);
        BigDecimal bid = scaledValue(midRate - spread, scale);
        BigDecimal ask = scaledValue(midRate + spread, scale);

        System.out.println(bid + " " + ask);
    }

    static BigDecimal scaledValue(long value, int scale) {
        return BigDecimal.valueOf(value, scale);
    }
}
