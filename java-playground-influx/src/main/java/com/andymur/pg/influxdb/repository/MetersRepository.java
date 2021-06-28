package com.andymur.pg.influxdb.repository;

import com.andymur.pg.influxdb.meters.BandValueMeter;
import com.andymur.pg.influxdb.meters.CurrencyCoupleRateMeterImpl;
import com.andymur.pg.influxdb.meters.influx.*;
import com.andymur.pg.influxdb.model.PriceUpdate;
import org.influxdb.dto.Point;

import java.util.*;

/**
 * query examples:
 * select spread from "band-values" where band_size = '1000000'
 * select currency_couple, band_size, mid_price from "band-values" where currency_couple = 'EURUSD' and band_size = '5000000'
 * select * from "rate-measurements" where time > now() - 1m
 *
 * select currencyCouple, rateValue from "rate-measurement" where currencyCouple = 'EURUSD'
 */

public class MetersRepository {

    public static final String UPDATE_RATES_MEASUREMENT_NAME = "rate-measurements";
    public static final String BAND_VALUES_MEASUREMENT_NAME = "band-values";
    private final Set<String> currencyCouplesInProcess = new HashSet<>();

    private final Set<InfluxMeter<PriceUpdate>> meters = new HashSet<>();

    public void processUpdate(PriceUpdate priceUpdate) {
        final String currencyCouple = priceUpdate.getCurrencyCouple();
        if (!currencyCouplesInProcess.contains(currencyCouple)) {
            addCurrencyCoupleRelatedMeters(currencyCouple);
            currencyCouplesInProcess.add(currencyCouple);
        }
        meters.forEach(meter -> meter.process(priceUpdate));
    }

    public void addMeter(InfluxMeter<PriceUpdate> meter)  {
        meters.add(meter);
    }

    private synchronized void addCurrencyCoupleRelatedMeters(final String currencyCouple) {
        // rate meter for currency couple
        addMeter(new InfluxRateMeterImpl(UPDATE_RATES_MEASUREMENT_NAME, new CurrencyCoupleRateMeterImpl(currencyCouple),
                defaultTagSetWithCurrencyCouple(currencyCouple)));

        // mid & spread meters per band for currency couple
        InfluxBandValueMeterImpl influxCurrencyCoupleMeter = new InfluxBandValueMeterImpl(BAND_VALUES_MEASUREMENT_NAME,
                defaultTagSetWithCurrencyCouple(currencyCouple));

        influxCurrencyCoupleMeter.addBandValueMeter(new BandValueMeter(currencyCouple, 500_000));
        influxCurrencyCoupleMeter.addBandValueMeter(new BandValueMeter(currencyCouple, 1000_000));
        influxCurrencyCoupleMeter.addBandValueMeter(new BandValueMeter(currencyCouple, 5000_000));

        addMeter(influxCurrencyCoupleMeter);
    }

    public synchronized List<Point> getValues() {
        List<Point> result = new ArrayList<>();
        for (InfluxMeter<PriceUpdate> meter: meters) {
            if (meter.hasUpdates()) {
                if (meter instanceof SinglePointSupplier) {
                    result.add(((SinglePointSupplier) meter).getPoint());
                } else if (meter instanceof MultiPointSupplier) {
                    result.addAll(((MultiPointSupplier) meter).getPoints());
                }
            }
        }
        return result;
    }

    public void reset() {
        meters.forEach(InfluxMeter::reset);
    }

    public static Map<String, String> defaultTagSet() {
        Map<String, String> result = new HashMap<>();
        result.put("monitor_host", "localhost");
        result.put("application_instance", "edf-monitoring1");
        result.put("application", "engine");

        return result;
    }

    public static Map<String, String> defaultTagSetWithCurrencyCouple(final String currencyCouple) {
        Map<String, String> result = new HashMap<>();
        result.put("monitor_host", "localhost");
        result.put("application_instance", "edf-monitoring1");
        result.put("application", "engine");
        result.put("currency_couple", currencyCouple);

        return result;
    }
}
