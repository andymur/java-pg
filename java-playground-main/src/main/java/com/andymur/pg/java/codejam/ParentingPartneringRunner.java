package com.andymur.pg.java.codejam;

import java.io.BufferedInputStream;
import java.util.*;
import java.util.stream.Collectors;

public class ParentingPartneringRunner {

    private static final String CAMERON = "C";
    private static final String JAMIE = "J";
    private static final String IMPOSSIBLE = "IMPOSSIBLE";

    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(new BufferedInputStream(System.in)
        )) {
            int testNumbers = strToInt(scanner.nextLine());
            for (int testNumber = 0 ; testNumber < testNumbers; testNumber++) {
                int periods = strToInt(scanner.nextLine());
                List<Period> data = new ArrayList<>();
                for (int periodNumber = 0; periodNumber < periods; periodNumber++) {
                    data.add(Period.of(scanner.nextLine()));
                }

                System.out.println(String.format("Case #%d: %s", testNumber + 1 , linkSolution(data)));
            }
        }
    }

    private static String linkSolution(Collection<Period> periods) {
        StringBuilder result = new StringBuilder();

        Map<Period, String> taskToPerson = new HashMap<>(periods.size());
        Map<Period, List<Period>> map = buildMap(periods);
        Set<Period> notVisited = new HashSet<>(periods);
        Set<Period> jamieTasks = new HashSet<>();
        Set<Period> cameronTasks = new HashSet<>();
        Queue<Period> toCheck = new ArrayDeque<>();
        Period firstTask = periods.iterator().next();
        toCheck.add(firstTask);
        boolean jamieKey = true;
        jamieTasks.add(firstTask);
        taskToPerson.put(firstTask, JAMIE);

        while(!notVisited.isEmpty() && !toCheck.isEmpty()) {
            Period currentPeriod = toCheck.poll();

            List<Period> periodsIntersectedWithCurrentPeriod = map.get(currentPeriod).stream()
                    .filter(notVisited::contains).collect(Collectors.toList());
            jamieKey = jamieTasks.contains(currentPeriod);
            toCheck.addAll(periodsIntersectedWithCurrentPeriod);
            notVisited.remove(currentPeriod);

            for (Period intersectedTask: periodsIntersectedWithCurrentPeriod) {
                if (jamieKey) {
                    // check if cameron can do it
                    if (canBeAdded(intersectedTask, cameronTasks)) {
                        cameronTasks.add(intersectedTask);
                        taskToPerson.put(intersectedTask, CAMERON);
                    } else {
                        return IMPOSSIBLE;
                    }
                } else {
                    // check if jamie can do it
                    if (canBeAdded(intersectedTask, jamieTasks)) {
                        jamieTasks.add(intersectedTask);
                        taskToPerson.put(intersectedTask, JAMIE);
                    } else {
                        return IMPOSSIBLE;
                    }
                }
            }
        }

        for (Period notTakenTask: notVisited) {
            if (canBeAdded(notTakenTask, jamieTasks)) {
                jamieTasks.add(notTakenTask);
                taskToPerson.put(notTakenTask, JAMIE);
            } else if (canBeAdded(notTakenTask, cameronTasks)) {
                cameronTasks.add(notTakenTask);
                taskToPerson.put(notTakenTask, CAMERON);
            } else {
                return IMPOSSIBLE;
            }
        }

        for (Period currentPeriod: periods) {
            result.append(taskToPerson.get(currentPeriod));
        }

        return result.toString();
    }

    private static Map<Period, List<Period>> buildMap(Collection<Period> periods) {
        Map<Period, List<Period>> result = new HashMap<>();

        for (Period period: periods) {
            result.put(period, new ArrayList<>());
            for (Period innerPeriod: periods) {
                if (!innerPeriod.equals(period) && innerPeriod.intersect(period)) {
                    result.get(period).add(innerPeriod);
                }
            }
        }

        return result;
    }

    private static String solution(Collection<Period> periods) {
        StringBuilder result = new StringBuilder(periods.size());

        Queue<Period> sortedPeriods = new PriorityQueue<>(Comparator.comparingInt(p -> p.start));
        sortedPeriods.addAll(periods);
        List<Period> jamiePeriods = new ArrayList<>();
        List<Period> cameronPeriods = new ArrayList<>();
        Map<Period, String> periodToPerson = new HashMap<>(periods.size());

        for (Period currentPeriod: sortedPeriods) {
            if (canBeAdded(currentPeriod, cameronPeriods)) {
                cameronPeriods.add(currentPeriod);
                periodToPerson.put(currentPeriod, CAMERON);
            } else if (canBeAdded(currentPeriod, jamiePeriods)) {
                jamiePeriods.add(currentPeriod);
                periodToPerson.put(currentPeriod, JAMIE);
            } else {
                return IMPOSSIBLE;
            }
        }

        for (Period currentPeriod: periods) {
            result.append(periodToPerson.get(currentPeriod));
        }
        return result.toString();
    }

    private static boolean canBeAdded(Period period, Collection<Period> periods) {
        for (Period existedPeriod: periods) {
            if (existedPeriod.intersect(period)) {
                return false;
            }
        }
        return true;
    }

    private static int strToInt(String string) {
        return Integer.parseInt(string);
    }

    private static class Period {
        private final int start;
        private final int end;

        public Period(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public boolean intersect(Period period) {
            if (period.start >= this.start && period.start < this.end) {
                return true;
            }

            return this.start >= period.start && this.start < period.end;
        }

        private static Period of(String period) {
            String[] startAndEnd = period.split(" ");
            return new Period(strToInt(startAndEnd[0]), strToInt(startAndEnd[1]));
        }

        @Override
        public String toString() {
            return "Period{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Period period = (Period) o;
            return start == period.start &&
                    end == period.end;
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }
    }
}
