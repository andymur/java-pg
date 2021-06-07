package com.andymur.pg.influxdb.workers;

import com.andymur.pg.influxdb.repository.InfluxRepository;
import com.andymur.pg.influxdb.repository.MeterRepository;
import org.influxdb.dto.Point;

public class MeterWorker implements Runnable {

    private final MeterRepository metersRepository;
    private final InfluxRepository influxRepository;


    public MeterWorker(final MeterRepository metersRepository,
                final InfluxRepository influxRepository) {
        this.metersRepository = metersRepository;
        this.influxRepository = influxRepository;
    }

    @Override
    public void run() {
        for (Point value: metersRepository.getValues()) {
            System.out.println(String.format("Got an update for meter: value = %s", value.toString()));
            influxRepository.writePoint(value);
        }
        metersRepository.reset();
    }
}
