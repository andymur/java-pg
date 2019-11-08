package com.andymur.pg.java.aci;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;

public class CalculatorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorTest.class);

    private static final int PA_RATE = 1;
    private static final int DEFAULT_SCALE = 2;
    private static final MathContext MATH_CONTEXT = new MathContext(DEFAULT_SCALE, RoundingMode.HALF_EVEN);
    private Calculator calculator;
    private static LocalDate TEST_DATE = LocalDate.of(2019, 10, 23);

    @Before
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    public void testCalculateEffectiveInterestRate() {
        LOGGER.info("testCalculateEffectiveInterestRate.start;");
        BigDecimal newRate = calculator.calculateEffectiveInterestRate(PA_RATE, 70, BasePeriod._360);

        Assert.assertEquals("Basic test for effective interest rate calculation.", new BigDecimal("0.19", MATH_CONTEXT), newRate);
        LOGGER.info("testCalculateEffectiveInterestRate.end;");
    }

    @Test
    public void testCalculateEffectiveInterestRateWithTerm() {
        LOGGER.info("testCalculateEffectiveInterestRateWithTerm.start;");
        BigDecimal newRate = calculator.calculateEffectiveInterestRate(
                PA_RATE,
                TEST_DATE,
                TEST_DATE,
                TermPeriodType.ACTUAL,
                BasePeriod._360
        );

        Assert.assertEquals(new BigDecimal("0.0"), newRate);
        LOGGER.info("testCalculateEffectiveInterestRateWithTerm.end;");
    }

    @Test
    @Ignore
    public void testRunner() {
        LOGGER.info("testRunner.start;");

        BigDecimal newRate = calculator.calculateEffectiveInterestRate(
                // 100,000,000 x 0.03 x 61/365 = 501,369.86
                new BigDecimal(0.03, MATH_CONTEXT),
                182,
                //TermPeriodType.ACTUAL,
                BasePeriod._360
        );

        Assert.assertEquals(new BigDecimal("501369.86"), newRate);
        LOGGER.info("testRunner.end;");
    }

    //A 3-month (91-day) deposit of EUR 25 million is made at 3.25%.
    //At maturity, it is rolled over (incl. Interest) three times at the given terms.
    //At the end, how much is repaid (principal plus interest)?


    //90 days	3.55%
    //91 days	4.15%
    //89 days	4.19%


    //****//
    @Test
    public void testPresentValueCalculationForPeriodLessThanYear() {
        LOGGER.info("testPresentValueCalculationForPeriodLessThanYear.start;");
        //What is the present value of a claim for Euro 1 m you will receive in 182 days time
        // if the Euro interest rate for this period is 3% ?
        BigDecimal presentValue = calculator.calculatePresentValue(new BigDecimal("0.03"),
                new BigDecimal("1000000"), 182, BasePeriod._360);

        LOGGER.info("testPresentValueCalculationForPeriodLessThanYear.end;");
    }

    @Test
    public void testPresentValueCalculationForPeriodMoreThanYearOrEqual() {
        LOGGER.info("testPresentValueCalculationForPeriodMoreThanYearOrEqual.start;");
        BigDecimal presentValue = calculator.calculatePresentValue(new BigDecimal("0.03"),
                new BigDecimal("1000000"), 2);

        Assert.assertEquals(new BigDecimal("942595.91"), presentValue);
        LOGGER.info("testPresentValueCalculationForPeriodMoreThanYearOrEqual.end;");
    }

    //****//
    //A 90-day sterling bill is discounted at 6.125%. What is the true yield?

}
