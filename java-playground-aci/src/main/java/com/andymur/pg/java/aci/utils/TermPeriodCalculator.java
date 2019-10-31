package com.andymur.pg.java.aci.utils;

import com.andymur.pg.java.aci.TermPeriodType;

import java.time.LocalDate;
import java.time.Period;

public class TermPeriodCalculator {


    public int calculateTermPeriodInDays(LocalDate periodStart,
                                         LocalDate periodEnd,
                                         TermPeriodType termPeriodType) {
        return Period.between(periodStart, periodEnd).getDays();
    }

    public static TermPeriodCalculator of() {
        return new TermPeriodCalculator();
    }
}
