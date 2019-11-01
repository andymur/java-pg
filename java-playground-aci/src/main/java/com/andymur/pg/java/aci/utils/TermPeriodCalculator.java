package com.andymur.pg.java.aci.utils;

import com.andymur.pg.java.aci.TermPeriodType;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import static com.andymur.pg.java.aci.TermPeriodType._30E;

public class TermPeriodCalculator {

	//TODO: now it should work correctly only within one month
    public long calculateTermPeriodInDays(LocalDate periodStart,
                                         LocalDate periodEnd,
                                         TermPeriodType termPeriodType) {

		long differenceInDays = ChronoUnit.DAYS.between(periodStart, adjustPeriodEnd(periodEnd, termPeriodType));

    	switch (termPeriodType) {
			case ACTUAL:
				return differenceInDays;
			case _30:
			case _30E:
				return differenceInDays == 31 ? 30 : differenceInDays;
		}

		throw new IllegalArgumentException("Unsupported term period type: " + termPeriodType);
    }

    private LocalDate adjustPeriodEnd(LocalDate periodEnd,
									  TermPeriodType termPeriodType) {

    	if (termPeriodType == _30E && periodEnd.getDayOfMonth() == 31) {
			return LocalDate.of(periodEnd.getYear(), periodEnd.getMonthValue(), 30);
		}

    	return periodEnd;
	}

    public static TermPeriodCalculator of() {
        return new TermPeriodCalculator();
    }
}
