package com.andymur.pg.influxdb.meters;

import com.andymur.pg.influxdb.model.PriceUpdate;

import java.util.concurrent.atomic.AtomicLong;

public class CurrencyRateMeterImpl implements Meter<PriceUpdate, Long> {

    private final String currencyCouple;
    private AtomicLong value = new AtomicLong();

    public CurrencyRateMeterImpl(String currencyCouple) {
        this.currencyCouple = currencyCouple;
    }

    @Override
    public Long getValue() {
        return value.get();
    }

    public String getCurrencyCouple() {
        return currencyCouple;
    }

    @Override
    public void reset() {
        value.set(0L);
    }

    public void process(PriceUpdate priceUpdate) {
        if (priceUpdate.getCurrencyCouple().equals(currencyCouple)) {
            count();
        }
    }

    private void count() {
        value.incrementAndGet();
    }

    @Override
    public boolean hasUpdates() {
        return value.get() > 0L;
    }
}
