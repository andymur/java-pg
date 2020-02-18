package com.andymur.pg.pocket.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Deposit implements FutureValuedAsset {

    private static final String NA = "N/A";

    private Currency currency;
    private String legalEntity;
    private BigDecimal amount;
    private BigDecimal percentPerAnnum;
    private int days;

    private Deposit() {
    }

    public Deposit(final Currency currency,
                   final BigDecimal amount) {
        this(null, currency, amount, null);
    }

    public Deposit(final String legalEntity,
                   final Currency currency,
                   final BigDecimal amount,
                   final BigDecimal percentPerAnnum) {
        Objects.requireNonNull(currency);
        Objects.requireNonNull(amount);
        this.legalEntity = legalEntity != null ? legalEntity : NA;
        this.currency = currency;
        this.amount = amount;
        this.percentPerAnnum = percentPerAnnum != null ? percentPerAnnum : BigDecimal.ZERO;
    }

    @Override
    public String getSymbol() {
        return currency.getSymbol();
    }

    @Override
    public MeasureUnit getUnit() {
        return currency.getUnit();
    }

    @Override
    public Asset futureAsset() {
        return new Deposit(legalEntity, currency, calculateFinalAmount(), percentPerAnnum);
    }

    @Override
    public AssetValue getValue() {
        return new AssetValue(currency, amount);
    }

    private BigDecimal calculateFinalAmount() {
        int years = days / 365;
        int remainingDays = days % 365;

        return BigDecimal.ONE;
    }
}
