package com.andymur.pg.java.aci;

import com.andymur.pg.java.aci.utils.Year;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Bidi;

public class Calculator {

    /**
     *
     * @param paRate per annum rate
     * @param daysNumber number of actual days
     * @param basePeriod type of base per annum period
     * @return effective rate
     */
    public BigDecimal calculateEffectiveInterestRate(double paRate, int daysNumber, BasePeriod basePeriod) {
        return calculateEffectiveInterestRate(BigDecimal.valueOf(paRate), daysNumber, basePeriod);
    }

    /**
     *
     * @param paRate per annum rate
     * @param daysNumber number of actual days
     * @param basePeriod type of base per annum period
     * @return effective rate
     */
    public BigDecimal calculateEffectiveInterestRate(BigDecimal paRate, int daysNumber, BasePeriod basePeriod) {
        BigDecimal baseDays = BigDecimal.valueOf(getBaseDays(basePeriod, Year.of(2019)));
        return paRate.multiply(BigDecimal.valueOf(daysNumber).divide(baseDays, RoundingMode.HALF_EVEN));
    }


    private int getBaseDays(final BasePeriod basePeriod,
                            Year year) {
        if (basePeriod == BasePeriod.ACTUAL) {
            return year.isLeap() ? 366 : 365;
        }

        return basePeriod.getNumberOfDays();
    }
}
