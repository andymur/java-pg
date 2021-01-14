package com.andymur.pg.java.algods.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * We have a schedule (list with arrivals and list with departures).
 * 
 * Question is: what is the minimum number of platforms needed?
 */
public class MinimumPlatformFinder {
    // In this case we have three trains with appropriate schedule:
    // 14.30 -> 15.00,
    // 14.55 -> 15.50,
    // 14.50 -> 15.20
    // We need minimum three platform cause all three are overlapping in interval 14.55-15.00.

    private static List<String> ARRIVALS = Arrays.asList("14:30", "14:55", "14:50");
    private static List<String> DEPARTURES = Arrays.asList("15:00", "15:50", "15:20");

    public static void main(String[] args) {
        final List<Interval> intervals = convert(ARRIVALS, DEPARTURES);
        int maxNumberOfPlatforms = 0;
        for (int i = 0; i < intervals.size(); i++) {
            Interval interval = intervals.get(i);
            int numberOfPlatforms = 0;
            for (final Interval candidate : intervals) {
                if (interval.intersects(candidate)) {
                    numberOfPlatforms += 1;
                    interval = interval.intersect(candidate).get();
                }
            }
            if (numberOfPlatforms > maxNumberOfPlatforms) {
                maxNumberOfPlatforms = numberOfPlatforms;
            }
        }
        System.out.println("Number of platforms needed: " + maxNumberOfPlatforms);
    }

    static List<Interval> convert(final List<String> arrivals,
                                  final List<String> departures) {
        if (arrivals.size() != departures.size()) {
            throw new IllegalArgumentException("number of arrivals doesn't match with number of departures");
        }
        List<Interval> intervals = new ArrayList<>(arrivals.size());

        for (int i = 0; i < arrivals.size(); i++) {
            intervals.add(Interval.of(convertTime(arrivals.get(i)), convertTime(departures.get(i))));
        }
        return intervals;
    }

    static int convertTime(String time) {
        String[] timeParts = time.split(":");
        return Integer.parseInt(timeParts[0]) * 60 + Integer.parseInt(timeParts[1]);
    }

    static class Interval {
        private Pair<Integer, Integer> interval;

        private Interval(int start, int end) {
            interval = Pair.of(start, end);
        }

        public int getStart() {
            return interval.first;
        }

        public int getEnd() {
            return interval.second;
        }

        public boolean intersects(Interval interval) {
            return (getStart() >= interval.getStart() && getStart() <= interval.getEnd())
                    || (interval.getStart() >= getStart() && interval.getStart() <= getEnd());
        }

        public Optional<Interval> intersect(Interval interval) {
            int start = Integer.max(interval.getStart(), getStart());
            int end = Integer.min(interval.getEnd(), getEnd());
            if (end >= start) {
                return Optional.of(Interval.of(start, end));
            }
            return Optional.empty();
        }

        public static Interval of(int start, int end) {
            if (end < start) {
                throw new IllegalArgumentException("start should be less or equal to end");
            }
            return new Interval(start, end);
        }

        @Override
        public String toString() {
            return "Interval{" +
                    "interval=" + interval +
                    '}';
        }
    }

    static class Pair<T, U> {
        private final T first;
        private final U second;

        private Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }

        public static <T, U> Pair<T, U> of(T first, U second) {
            return new Pair<>(first, second);
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "first=" + first +
                    ", second=" + second +
                    '}';
        }
    }
}
