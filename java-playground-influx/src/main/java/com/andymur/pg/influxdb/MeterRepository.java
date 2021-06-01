package com.andymur.pg.influxdb;

import com.andymur.pg.influxdb.model.PriceUpdate;
import com.andymur.pg.influxdb.model.RateMeter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MeterRepository {
    private Set<RateMeter<PriceUpdate>> rateMeters = new HashSet<>();

    public void addMeter(RateMeter<PriceUpdate> meter)  {
        rateMeters.add(meter);
    }

    public void processUpdate(PriceUpdate priceUpdate) {
        rateMeters.forEach(meter -> meter.countWithCondition(priceUpdate));
    }

    public Map<String, Long> getValues() {
        Map<String, Long> result = new HashMap<>();
        for (RateMeter<PriceUpdate> meter: rateMeters) {
            result.put(meter.getName(), meter.getValue());
        }
        return result;
    }

    public void reset() {
        rateMeters.forEach(RateMeter::reset);
    }
}
