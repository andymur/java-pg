package com.andymur.pg.java.aci;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class CalculatorTest {
    private static final int PA_RATE = 1;

    private Calculator calculator;

    @Before
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    public void testCalculateEffectiveInterestRate() {
        BigDecimal rate = calculator.calculateEffectiveInterestRate(PA_RATE, 70, BasePeriod.ACTUAL);
        Assert.assertNotNull(rate);
    }
}
