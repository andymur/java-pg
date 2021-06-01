package com.andymur.pg.influxdb.model;

import java.util.function.Predicate;

public class CurrencyRateMeterImpl implements RateMeter<PriceUpdate> {

    private final String name;
    private final String currencyCouple;
    private long value = 0;

    public CurrencyRateMeterImpl(String name,
                                 String currencyCouple) {
        this.name = name;
        this.currencyCouple = currencyCouple;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getValue() {
        return value;
    }

    public String getCurrencyCouple() {
        return currencyCouple;
    }

    @Override
    public void reset() {
        value = 0;
    }

    @Override
    public void count() {
        value += 1;
    }

    public void countWithCondition(PriceUpdate priceUpdate) {
        if (priceUpdate.getCurrencyCouple().equals(currencyCouple)) {
            count();
        }
    }
}
