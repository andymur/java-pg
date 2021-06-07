package com.andymur.pg.influxdb.meters.influx;

import java.util.Map;

public interface InfluxMeter<T> {
    void addTag(String tagName, String tagValue);
    void addTags(Map<String, String> tagSet);
    Map<String, String> getTags();
    void process(T updateValue);
    void reset();
}
