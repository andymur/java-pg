package com.andymur.pg.influxdb.model;

public interface RateMeter<T> {
    String getName();
    long getValue();
    void reset();
    void count();
    void countWithCondition(T updateValue);
}
