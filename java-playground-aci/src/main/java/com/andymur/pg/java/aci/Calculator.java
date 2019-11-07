package com.andymur.pg.java.aci;

import com.andymur.pg.java.aci.utils.TermPeriodCalculator;
import com.andymur.pg.java.aci.utils.Year;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Calculator {

    private static final int SCALE = 100;
    private static final MathContext MATH_CONTEXT = new MathContext(SCALE, RoundingMode.HALF_EVEN);
    private static final TermPeriodCalculator TERM_PERIOD_CALCULATOR = TermPeriodCalculator.of();


    public BigDecimal calculateEffectiveInterestRate(double paRate,
                                                     LocalDate periodStart,
                                                     LocalDate periodEnd,
                                                     TermPeriodType termPeriodType,
                                                     BasePeriod basePeriod) {
        return calculateEffectiveInterestRate(BigDecimal.valueOf(paRate),
                periodStart, periodEnd, termPeriodType, basePeriod);
    }

    public BigDecimal calculateEffectiveInterestRate(BigDecimal paRate,
                                                     LocalDate periodStart,
                                                     LocalDate periodEnd,
                                                     TermPeriodType termPeriodType,
                                                     BasePeriod basePeriod) {
        return calculateEffectiveInterestRate(paRate.setScale(SCALE, RoundingMode.CEILING),
                getTermDays(periodStart, periodEnd, termPeriodType), basePeriod);
    }

    /**
     * Effective rate calculated as paRate x (period / basePeriod)
     *
     * @param paRate     per annum rate
     * @param daysNumber number of actual days
     * @param basePeriod type of base per annum period
     * @return effective rate
     */
    public BigDecimal calculateEffectiveInterestRate(double paRate, long daysNumber, BasePeriod basePeriod) {
        return calculateEffectiveInterestRate(BigDecimal.valueOf(paRate), daysNumber, basePeriod);
    }

    /**
     * @param paRate     per annum rate
     * @param daysNumber number of actual days
     * @param basePeriod type of base per annum period
     * @return effective rate
     */
    public BigDecimal calculateEffectiveInterestRate(BigDecimal paRate, long daysNumber, BasePeriod basePeriod) {
        BigDecimal baseDays = BigDecimal.valueOf(getBaseDays(basePeriod, Year.of(2019)), SCALE);
        return paRate.multiply(
                BigDecimal.valueOf(daysNumber, SCALE)
                        .divide(baseDays, MATH_CONTEXT)).round(new MathContext(2, RoundingMode.HALF_EVEN));
    }

    private long getTermDays(LocalDate periodStart,
                            LocalDate periodEnd,
                            TermPeriodType termPeriodType) {
        return TERM_PERIOD_CALCULATOR.calculateTermPeriodInDays(periodStart, periodEnd, termPeriodType);
    }

    /**
     *
     * @return present value based on future value and other parameters
     */
    public BigDecimal calculatePresentValue(BigDecimal paRate,
                                            BigDecimal futureValue,
                                            long daysNumber,
                                            BasePeriod basePeriod) {
        if (daysNumber < 365) {
            // futureValue / (1 + rate * daysNumber/basePeriod)
            BigDecimal denominator = BigDecimal.ONE.add(
                    paRate.multiply(
                            BigDecimal.valueOf(daysNumber).divide(
                                    BigDecimal.valueOf(getBaseDays(basePeriod, Year.of(2019))),
                                    MATH_CONTEXT
                            ), MATH_CONTEXT
                    ), MATH_CONTEXT
            );

            return futureValue.divide(denominator, 2, RoundingMode.HALF_EVEN);
        } else {
            return BigDecimal.ONE;
        }
    }

    private long getBaseDays(final BasePeriod basePeriod,
                            Year year) {
        if (basePeriod == BasePeriod.ACTUAL) {
            return year.isLeap() ? 366 : 365;
        }

        return basePeriod.getNumberOfDays();
    }
}
