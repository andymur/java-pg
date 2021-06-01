package com.andymur.pg.influxdb;

import com.andymur.pg.influxdb.model.PriceUpdate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PriceUpdateGenerator {

    private static final Random SOURCE_OF_RANDOMNESS = new Random(System.currentTimeMillis());

    private static List<String> CURRENCY_COUPLES_CACHE = Arrays.asList("EURUSD", "EURRUB", "USDRUB", "EURGBP");

    public PriceUpdate generate() {
        return new PriceUpdate(randomFromList(CURRENCY_COUPLES_CACHE), randomLong(10000, 110000), randomLong(100, 300));
    }

    static <E> E randomFromList(List<E> elements) {
        return elements.get(randomInt(0, elements.size() - 1));
    }

    static int randomInt(int from, int to) {
        return from + SOURCE_OF_RANDOMNESS.nextInt(to + 1);
    }

    static long randomLong(long from, long to) {
        long val = from + SOURCE_OF_RANDOMNESS.nextLong();
        return Math.min(val, to);
    }
}
