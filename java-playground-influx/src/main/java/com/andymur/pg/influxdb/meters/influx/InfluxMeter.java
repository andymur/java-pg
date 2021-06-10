package com.andymur.pg.influxdb.meters.influx;

import java.util.Map;

public interface InfluxMeter<T> {
    void process(T updateValue);
    void reset();
    boolean hasUpdates();
}
