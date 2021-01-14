package com.andymur.pg.java.temp;

import org.HdrHistogram.Histogram;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HistogramRunner {
    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis());
        Map<Long, Integer> numbers = new HashMap<>();

        final Histogram histogram = new Histogram(0);
        histogram.setAutoResize(true);

        for (int i = 0; i < 5; i++) {
            final long value = (long) random.nextInt(10) + 1;
            histogram.recordValue(value);
            Integer num = numbers.getOrDefault(value, 0);
            numbers.put(value, num + 1);
        }
        System.out.println(histogram.getMean());
        System.out.println(histogram.getMinValue());
        System.out.println(histogram.getMaxValue());
        System.out.println(histogram.getCountAtValue(5));
        System.out.println(histogram.getPercentileAtOrBelowValue(5));
        System.out.println(numbers);
    }
}
