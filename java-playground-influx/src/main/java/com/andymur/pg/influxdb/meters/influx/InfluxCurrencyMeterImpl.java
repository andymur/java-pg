package com.andymur.pg.influxdb.meters.influx;

import com.andymur.pg.influxdb.meters.Meter;
import com.andymur.pg.influxdb.model.BandValue;
import com.andymur.pg.influxdb.model.PriceUpdate;
import org.influxdb.dto.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InfluxCurrencyMeterImpl extends InfluxMeterImpl<PriceUpdate> implements MultiPointSupplier {

    private final static String MID_PRICE_VALUE_FIELD_NAME = "midPriceValue";
    private final static String SPREAD_VALUE_FIELD_NAME = "spreadValue";

    private final List<Meter<PriceUpdate, BandValue>> meters;

    public InfluxCurrencyMeterImpl(final String measurement,
        final Map<String, String> tagSet) {
        super(measurement, tagSet);
        meters = new ArrayList<>();
    }

    public void addCurrencyMeter(final Meter<PriceUpdate, BandValue> meter) {
        this.meters.add(meter);
    }

    @Override
    public List<Point> getPoints() {
        return meters.stream().map(this::getPoint).collect(Collectors.toList());
    }

    private Point getPoint(Meter<PriceUpdate, BandValue> meter) {
        final BandValue value = meter.getValue();
        Point.Builder measurementBuilder = prepareBuilder();
        return measurementBuilder
                .addField(MID_PRICE_VALUE_FIELD_NAME, value.getMidValue())
                .addField(SPREAD_VALUE_FIELD_NAME, value.getSpreadValue())
                .build();
    }

    @Override
    public void process(PriceUpdate updateValue) {
        meters.forEach(meter -> meter.process(updateValue));
    }

    @Override
    public void reset() {
        meters.forEach(Meter::reset);
    }
}
