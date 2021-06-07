package com.andymur.pg.influxdb.repository;

import com.andymur.pg.influxdb.meters.influx.InfluxMeter;
import com.andymur.pg.influxdb.meters.influx.MultiPointSupplier;
import com.andymur.pg.influxdb.meters.influx.SinglePointSupplier;
import com.andymur.pg.influxdb.model.PriceUpdate;
import org.influxdb.dto.Point;

import java.util.*;

public class MeterRepository {
    private final Set<InfluxMeter<PriceUpdate>> meters = new HashSet<>();

    public void addMeter(InfluxMeter<PriceUpdate> meter)  {
        meters.add(meter);
    }

    public void processUpdate(PriceUpdate priceUpdate) {
        meters.forEach(meter -> meter.process(priceUpdate));
    }

    public List<Point> getValues() {
        //TODO: check for emptiness
        List<Point> result = new ArrayList<>();
        for (InfluxMeter<PriceUpdate> meter: meters) {
            if (meter instanceof SinglePointSupplier) {
                result.add(((SinglePointSupplier) meter).getPoint());
            } else if (meter instanceof MultiPointSupplier) {
                result.addAll(((MultiPointSupplier) meter).getPoints());
            }
        }
        return result;
    }

    public void reset() {
        meters.forEach(InfluxMeter::reset);
    }

    public static Map<String, String> defaultTagSet() {
        Map<String, String> result = new HashMap<>();
        result.put("host", "localhost");
        result.put("instance", "edf-monitoring1");
        result.put("application", "engine");

        return result;
    }
}
