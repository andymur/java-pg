package com.andymur.pg.influxdb.model;

public class PriceUpdate {
    private final String currencyCouple;
    private final long midValue;
    private final long spreadValue;

    public PriceUpdate(String currencyCouple, long midValue, long spreadValue) {
        this.currencyCouple = currencyCouple;
        this.midValue = midValue;
        this.spreadValue = spreadValue;
    }

    public String getCurrencyCouple() {
        return currencyCouple;
    }

    @Override
    public String toString() {
        return "PriceUpdate{" +
                "currencyCouple='" + currencyCouple + '\'' +
                ", midValue=" + midValue +
                ", spreadValue=" + spreadValue +
                '}';
    }
}
