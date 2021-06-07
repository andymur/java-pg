package com.andymur.pg.influxdb;

import com.andymur.pg.influxdb.meters.RateMeterImpl;
import com.andymur.pg.influxdb.meters.influx.InfluxRateMeterImpl;
import com.andymur.pg.influxdb.model.PriceUpdate;
import com.andymur.pg.influxdb.repository.InfluxRepository;
import com.andymur.pg.influxdb.repository.MeterRepository;
import com.andymur.pg.influxdb.workers.MeterWorker;
import com.andymur.pg.influxdb.workers.UpdatesConsumer;
import com.andymur.pg.influxdb.workers.UpdatesSupplier;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;

import java.util.concurrent.*;

public class MeterSystemRunner {

    private static String HOST = "localhost";
    private static String PORT = "8086";

    private static final String DB_NAME = "test_measurements";
    private static final String MEASUREMENT_NAME = "rate-measurement";
    private static final String RETENTION_POLICY = "autogen";

    public static void main(String[] args) {

        final PriceUpdateGenerator updateGenerator = new PriceUpdateGenerator();
        final BlockingQueue<PriceUpdate> updatesQ = new ArrayBlockingQueue<>(100_000);

        final UpdatesSupplier updatesSupplier = new UpdatesSupplier(updateGenerator, updatesQ);

        final MeterRepository metersRepository = new MeterRepository();
        metersRepository.addMeter(new InfluxRateMeterImpl(MEASUREMENT_NAME, new RateMeterImpl(),
                MeterRepository.defaultTagSet()));

        final UpdatesConsumer updatesConsumer = new UpdatesConsumer(updatesQ, metersRepository);

        final InfluxRepository influxRepository = new InfluxRepository(DB_NAME, RETENTION_POLICY,
                getInfluxInstance(HOST, PORT));

        final MeterWorker meterWorker = new MeterWorker(metersRepository, influxRepository);

        final ExecutorService priceUpdateSupplierExecutor = Executors.newSingleThreadExecutor();
        final ScheduledExecutorService priceUpdateConsumerExecutor = Executors.newSingleThreadScheduledExecutor();
        final ScheduledExecutorService meterRepositoryExecutor = Executors.newSingleThreadScheduledExecutor();

        priceUpdateSupplierExecutor.submit(updatesSupplier);
        priceUpdateConsumerExecutor.scheduleWithFixedDelay(updatesConsumer, 0L, 10, TimeUnit.MILLISECONDS);
        meterRepositoryExecutor.scheduleWithFixedDelay(meterWorker, 0L, 1, TimeUnit.SECONDS);
    }

    private static InfluxDB getInfluxInstance(String host, String port) {
        InfluxDB influxDB = InfluxDBFactory.connect(String.format("http://%s:%s", host, port));
        Pong pong = influxDB.ping();
        if (pong.isGood()) {
            return influxDB;
        }
        throw new IllegalStateException("Failed to establish connection to influx db");
    }
}
