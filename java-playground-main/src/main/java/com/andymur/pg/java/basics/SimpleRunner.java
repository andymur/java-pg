package com.andymur.pg.java.basics;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class SimpleRunner {

    public static void main(String[] args) {
        /*int result = getResult();
        System.out.println(result);*/
        //System.out.println(stringifyMs(9362003L));
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        System.out.println(now);

        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now();
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);

        System.out.println(todayMidnight);
        System.out.println(tomorrowMidnight);
        System.out.println(ChronoUnit.HOURS.between(now, tomorrowMidnight));

        System.out.println(tomorrowMidnight.atZone(Clock.systemDefaultZone().getZone()).toInstant());
    }

    static String stringifyMs(long intervalMs) {
        long ms = intervalMs % 1000;
        long remaining = intervalMs / 1000;
        if (remaining > 0) {
            long seconds = remaining % 60;
            remaining = remaining / 60;
            if (remaining > 0) {
                long minutes = remaining % 60;
                remaining = remaining / 60;
                if (remaining > 0) {
                    return remaining + "h " + minutes + "minutes " + seconds + "seconds " + ms + "ms";
                } else {
                    return minutes + "minutes " + seconds + "seconds " + ms + "ms";
                }
            } else {
                return seconds + "seconds " + ms + "ms";
            }
        } else {
            return ms + "ms";
        }
    }

    static int getResult() {
        try {
            System.out.println("About to return...");
            return 5;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Cleaning up");
        }
        return 0;
    }
}
