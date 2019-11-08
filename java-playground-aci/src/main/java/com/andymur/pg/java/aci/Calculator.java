package com.andymur.pg.java.aci;

import com.andymur.pg.java.aci.utils.TermPeriodCalculator;
import com.andymur.pg.java.aci.utils.Year;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Calculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Calculator.class);

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
        return calculateEffectiveInterestRate(paRate,
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
        LOGGER.info("calculateEffectiveInterestRate.start; paRate = {}, daysNumber = {}, basePeriod = {}",
                paRate, daysNumber, basePeriod);

        final BigDecimal baseDays = BigDecimal.valueOf(getBaseDays(basePeriod, Year.of(2019)));
        final BigDecimal numberOfDays = BigDecimal.valueOf(daysNumber);
        final BigDecimal daysDivision = numberOfDays.divide(baseDays, MATH_CONTEXT);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("effectiveRate = paRate * daysNumber / baseDays");
            LOGGER.debug("effectiveRate = {} * {} / {}", paRate, numberOfDays, baseDays);
            LOGGER.debug("effectiveRate = {} * {}", paRate, daysDivision);
        }

        BigDecimal effectiveInterestRate = paRate.multiply(daysDivision)
                .round(new MathContext(2, RoundingMode.HALF_EVEN));

        LOGGER.info("calculateEffectiveInterestRate.end; effectiveInterestRate = {}", effectiveInterestRate);
        return effectiveInterestRate;
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
        //TODO: add debug logging
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

            return futureValue.divide(denominator, MATH_CONTEXT)
                    .setScale(2, RoundingMode.HALF_EVEN);
        } else {
            throw new IllegalStateException("Not implemented yet!");
        }
    }

    public BigDecimal calculatePresentValue(BigDecimal paRate,
                                           BigDecimal futureValue,
                                           int yearsNumber) {
        //TODO: add debug logging
        return futureValue.divide(
                    paRate.add(BigDecimal.ONE, MATH_CONTEXT)
                .pow(yearsNumber), MATH_CONTEXT).setScale(2, RoundingMode.HALF_EVEN);
    }

    private long getBaseDays(final BasePeriod basePeriod,
                            Year year) {
        if (basePeriod == BasePeriod.ACTUAL) {
            return year.isLeap() ? 366 : 365;
        }

        return basePeriod.getNumberOfDays();
    }
}
