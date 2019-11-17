package com.andymur.pg.java.aci;

import com.andymur.pg.java.aci.utils.TermPeriodCalculator;
import com.andymur.pg.java.aci.utils.Year;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class Calculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Calculator.class);

    private static final int SCALE = 100;
    private static final int ROUNDING_SCALE = 2;
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
                .setScale(ROUNDING_SCALE, RoundingMode.HALF_EVEN);

        LOGGER.info("calculateEffectiveInterestRate.end; effective interest rate = {}", effectiveInterestRate);
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
        LOGGER.info("calculatePresentValue.start; paRate = {}, daysNumber = {}, basePeriod = {}",
                paRate, daysNumber, basePeriod);
        final BigDecimal presentValue;
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

            presentValue = futureValue.divide(denominator, MATH_CONTEXT)
                    .setScale(ROUNDING_SCALE, RoundingMode.HALF_EVEN);

            LOGGER.info("calculatePresentValue.end; present value = {}", presentValue);
            return presentValue;
        } else {
            throw new IllegalStateException("Not implemented yet!");
        }
    }

    public BigDecimal calculatePresentValue(BigDecimal paRate,
                                           BigDecimal futureValue,
                                           int yearsNumber) {
        LOGGER.info("calculatePresentValue.start; paRate = {}, futureValue = {}, yearsNumber = {}",
                paRate, futureValue, yearsNumber);
        final BigDecimal presentValue = futureValue.divide(
                    paRate.add(BigDecimal.ONE, MATH_CONTEXT)
                .pow(yearsNumber), MATH_CONTEXT).setScale(ROUNDING_SCALE, RoundingMode.HALF_EVEN);

        LOGGER.info("calculatePresentValue.end; present value = {}", presentValue);
        return presentValue;
    }

    public BigDecimal calculateAverageInterestRate(List<Deposit> deposits,
                                                   BasePeriod basePeriod) {
        // avgRate = [r1*d1/b+r2*d2/b+...]*b/(d1+d2+...)
        LOGGER.info("calculateAverageInterestRate.start; deposits = {}, basePeriod = {}",
                deposits, basePeriod);

        final BigDecimal baseDays = BigDecimal.valueOf(getBaseDays(basePeriod, Year.of(2019)));

        BigDecimal sumOfDaysInDepositPeriods = BigDecimal.ZERO;
        BigDecimal sumOfInterests = BigDecimal.ZERO;

        for (final Deposit deposit: deposits) {
            BigDecimal numberOfDaysForDeposit = BigDecimal.valueOf(deposit.getNumberOfDays());

            final BigDecimal currentRate = deposit.getInterestRate().multiply(
                    numberOfDaysForDeposit.divide(baseDays, MATH_CONTEXT),
                    MATH_CONTEXT
            );

            sumOfInterests = sumOfInterests.add(
                    currentRate
            );

            sumOfDaysInDepositPeriods = sumOfDaysInDepositPeriods.add(numberOfDaysForDeposit);
        }

        final BigDecimal averageInterestRate = sumOfInterests.multiply(
                baseDays.divide(sumOfDaysInDepositPeriods, MATH_CONTEXT))/*.setScale(ROUNDING_SCALE,
                RoundingMode.HALF_EVEN)*/;

        LOGGER.info("calculateAverageInterestRate.end; average interest rate = {}", averageInterestRate);
        return averageInterestRate;
    }

    public BigDecimal calculateCompoundInterestRate(List<Deposit> deposits,
                                                    BasePeriod basePeriod) {
        // compoundRate = ([(1+r1*d1/b)*(1+r2*d2/b)*...] - 1)*B/d1+d2+...
        LOGGER.info("calculateCompoundInterestRate.start; deposits = {}, basePeriod = {}",
                deposits, basePeriod);

        final BigDecimal baseDays = BigDecimal.valueOf(getBaseDays(basePeriod, Year.of(2019)));

        BigDecimal sumOfDaysInDepositPeriods = BigDecimal.ZERO;
        BigDecimal multiplicationOfInterests = BigDecimal.ONE;

        for (Deposit deposit: deposits) {
            BigDecimal numberOfDaysForDeposit = BigDecimal.valueOf(deposit.getNumberOfDays());
            multiplicationOfInterests = deposit.getInterestRate().multiply(
                    numberOfDaysForDeposit.divide(baseDays, MATH_CONTEXT),
                    MATH_CONTEXT
            ).add(BigDecimal.ONE).multiply(multiplicationOfInterests, MATH_CONTEXT);
            sumOfDaysInDepositPeriods = sumOfDaysInDepositPeriods.add(numberOfDaysForDeposit);
        }

        final BigDecimal compoundInterestRate = multiplicationOfInterests.subtract(BigDecimal.ONE)
                .multiply(baseDays.divide(sumOfDaysInDepositPeriods, MATH_CONTEXT))
                .setScale(ROUNDING_SCALE, RoundingMode.HALF_EVEN);

        LOGGER.info("calculateCompoundInterestRate.end; compound interest rate = {}", compoundInterestRate);
        return compoundInterestRate;
    }

    public BigDecimal calculateFutureValueWithCompoundInterestRate(BigDecimal presentValue,
                                                                   List<Deposit> deposits,
                                                                   BasePeriod basePeriod) {
        // futureValue = presentValue * [(1+r1*d1/b)*(1+r2*d2/b)*...]
        LOGGER.info(
                "calculateFutureValueWithCompoundInterestRate.start; presentValue = {}, deposits = {}, basePeriod = {}",
                presentValue, deposits, basePeriod);

        final BigDecimal baseDays = BigDecimal.valueOf(getBaseDays(basePeriod, Year.of(2019)));
        BigDecimal multiplicationOfInterests = BigDecimal.ONE;

        for (Deposit deposit: deposits) {
            BigDecimal numberOfDaysForDeposit = BigDecimal.valueOf(deposit.getNumberOfDays());
            multiplicationOfInterests = deposit.getInterestRate().multiply(
                    numberOfDaysForDeposit.divide(baseDays, MATH_CONTEXT),
                    MATH_CONTEXT
            ).add(BigDecimal.ONE).multiply(multiplicationOfInterests, MATH_CONTEXT);
        }

        final BigDecimal futureValue = multiplicationOfInterests
                .multiply(presentValue, MATH_CONTEXT).setScale(ROUNDING_SCALE, RoundingMode.HALF_EVEN);

        LOGGER.info("calculateFutureValueWithCompoundInterestRate.end; future value = {}", futureValue);
        return futureValue;
    }

    private long getBaseDays(final BasePeriod basePeriod,
                            Year year) {
        if (basePeriod == BasePeriod.ACTUAL) {
            return year.isLeap() ? 366 : 365;
        }

        return basePeriod.getNumberOfDays();
    }
}
