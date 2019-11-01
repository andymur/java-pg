package com.andymur.pg.java.aci;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;

public class CalculatorTest {
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
        //1*(70/360)
        BigDecimal newRate = calculator.calculateEffectiveInterestRate(PA_RATE, 70, BasePeriod._360);

        Assert.assertEquals(new BigDecimal("0.194444444", MATH_CONTEXT), newRate);
    }

    @Test
    public void testCalculateEffectiveInterestRateWithTerm() {
        BigDecimal newRate = calculator.calculateEffectiveInterestRate(
                PA_RATE,
                TEST_DATE,
                TEST_DATE,
                TermPeriodType.ACTUAL,
                BasePeriod._360
        );

        Assert.assertEquals(new BigDecimal("0.00", MATH_CONTEXT), newRate);
    }

}
