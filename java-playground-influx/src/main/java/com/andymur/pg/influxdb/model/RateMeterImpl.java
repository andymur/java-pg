package com.andymur.pg.influxdb.model;

import java.util.function.Predicate;

public class RateMeterImpl implements RateMeter<PriceUpdate> {

    private long value = 0;
    private final String name;

    public RateMeterImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public void reset() {
        value = 0;
    }

    @Override
    public void count() {
        value += 1;
    }

    @Override
    public void countWithCondition(PriceUpdate updateValue) {
        count();
    }
}
