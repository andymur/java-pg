package com.andymur.pg.pocket.model.asset;

import com.andymur.pg.pocket.model.asset.base.Asset;
import com.andymur.pg.pocket.model.asset.base.AssetType;
import com.andymur.pg.pocket.model.asset.base.AssetValue;
import com.andymur.pg.pocket.model.asset.base.FutureValuedAsset;
import com.andymur.pg.pocket.model.label.Currency;
import com.andymur.pg.pocket.model.label.base.MeasureUnit;

import java.math.BigDecimal;
import java.util.Objects;

public class BankDeposit implements FutureValuedAsset {

    private static final String NA = "N/A";

    private Currency currency;
    private String legalEntity;
    private BigDecimal amount;
    private BigDecimal percentPerAnnum;
    private int days;

    private BankDeposit() {
    }

    public BankDeposit(final Currency currency,
                       final BigDecimal amount) {
        this(NA, currency, amount, BigDecimal.ZERO);
    }

    public BankDeposit(final String legalEntity,
                       final Currency currency,
                       final BigDecimal amount,
                       final BigDecimal percentPerAnnum) {
        Objects.requireNonNull(legalEntity);
        Objects.requireNonNull(currency);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(percentPerAnnum);
        this.legalEntity = legalEntity;
        this.currency = currency;
        this.amount = amount;
        this.percentPerAnnum = percentPerAnnum;
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
        return new BankDeposit(legalEntity, currency, calculateFinalAmount(), percentPerAnnum);
    }

    @Override
    public AssetValue getValue() {
        return new AssetValue(currency, amount);
    }

    @Override
    public AssetType getType() {
        return AssetType.BANK_DEPOSIT;
    }

    private BigDecimal calculateFinalAmount() {
        int years = days / 365;
        int remainingDays = days % 365;

        return BigDecimal.ONE;
    }
}
