package com.andymur.pg.java.aci.utils;

import com.andymur.pg.java.aci.TermPeriodType;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import static com.andymur.pg.java.aci.TermPeriodType._30E;

public class TermPeriodCalculator {

	public long calculateTermPeriodInDays(LocalDate periodStart,
										  LocalDate periodEnd) {
		return calculateTermPeriodInDays(periodStart, periodEnd, TermPeriodType.ACTUAL);
	}

    public long calculateTermPeriodInDays(LocalDate periodStart,
                                         LocalDate periodEnd,
                                         TermPeriodType termPeriodType) {


    	switch (termPeriodType) {
			case ACTUAL:
				return ChronoUnit.DAYS.between(periodStart, periodEnd);
			case _30:
			case _30E: {
				Period period = Period.between(periodStart, adjustPeriodEnd(periodEnd, termPeriodType));
				return period.getYears() * 360 + period.getMonths() * 30 + period.getDays();
			}
		}

		throw new IllegalArgumentException("Unsupported term period type: " + termPeriodType);
    }

    public long calculateDaysSinceBeginningOfTheYear(LocalDate date) {
		LocalDate yearBeginning = LocalDate.of(date.getYear(), 1, 1);
		return ChronoUnit.DAYS.between(yearBeginning, date);
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
