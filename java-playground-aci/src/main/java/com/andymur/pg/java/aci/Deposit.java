package com.andymur.pg.java.aci;

import com.andymur.pg.java.aci.utils.TermPeriodCalculator;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Deposit {

    private static final String DEFAULT_CCY = "USD";

    // optional
    private LocalDate startPeriod;
    private LocalDate endPeriod;

    // mandatory
    private final long numberOfDays;
    private final String currency;
    private final BigDecimal interestRate;

    public Deposit(final LocalDate periodStart,
                   final LocalDate periodEnd,
                   final BigDecimal interestRate) {
        this(TermPeriodCalculator.of().calculateTermPeriodInDays(periodStart, periodEnd), interestRate, DEFAULT_CCY);
    }

    public Deposit(long numberOfDays, BigDecimal interestRate) {
        this(numberOfDays, interestRate, DEFAULT_CCY);
    }

    public Deposit(long numberOfDays, BigDecimal interestRate, String currency) {
        this.numberOfDays = numberOfDays;
        this.interestRate = interestRate;
        this.currency = currency;
    }


    @Override
    public String toString() {
        return "Deposit{" +
                "startPeriod=" + startPeriod +
                ", endPeriod=" + endPeriod +
                ", numberOfDays=" + numberOfDays +
                ", currency='" + currency + '\'' +
                ", interestRate=" + interestRate +
                '}';
    }
}
