package com.andymur.pg.influxdb.meters;

import com.andymur.pg.influxdb.model.BandValue;
import com.andymur.pg.influxdb.model.PriceUpdate;

public class CurrencyBandValueMeter implements Meter<PriceUpdate, BandValue> {

    private long midValueAccum;
    private long spreadValueAccum;
    private int processedUpdates;

    private final String currency;
    private final int bandAmount;

    public CurrencyBandValueMeter(final String currency,
                                  final int bandAmount) {
        this.currency = currency;
        this.bandAmount = bandAmount;
    }

    @Override
    public synchronized BandValue getValue() {
        final long midValue = midValueAccum / processedUpdates;
        final long spreadValue = spreadValueAccum / processedUpdates;
        return new BandValue(bandAmount, midValue, spreadValue);
    }

    @Override
    public synchronized void reset() {
        midValueAccum = 0L;
        spreadValueAccum = 0L;
        processedUpdates += 1;
    }

    @Override
    public void process(PriceUpdate updateValue) {
        if (updateValue.getCurrencyCouple().equals(currency)
                && updateValue.getBandValue().getAmount() == bandAmount) {
            internalProcess(updateValue);
        }
    }

    private synchronized void internalProcess(PriceUpdate updateValue) {
        midValueAccum += updateValue.getBandValue().getMidValue();

    }

    @Override
    public boolean hasUpdates() {
        return processedUpdates > 0;
    }
}
