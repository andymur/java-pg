package com.andymur.pg.influxdb;

import com.andymur.pg.influxdb.model.CurrencyRateMeterImpl;
import com.andymur.pg.influxdb.model.RateMeterImpl;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MeterRunner {

    private MeterRepository repository;
    private PriceUpdateGenerator generator;

    public static void main(String[] args) {
        MeterRunner meterRunner = new MeterRunner();
        meterRunner.fillRepository();

        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        executorService.submit(new GeneratorWorker(meterRunner.generator, meterRunner.repository));
        scheduledExecutorService.scheduleWithFixedDelay(new MeterWorker(meterRunner.repository), 0, 1, TimeUnit.SECONDS);
    }

    public MeterRunner() {
        this.repository = new MeterRepository();
        this.generator = new PriceUpdateGenerator();
    }

    static class MeterWorker implements Runnable {

        private final MeterRepository repository;

        MeterWorker(MeterRepository repository) {
            this.repository = repository;
        }

        @Override
        public void run() {
            final Map<String, Long> values = repository.getValues();
            for (Map.Entry<String, Long> meterValue: values.entrySet()) {
                System.out.println(String.format("Got an update for meter: %s, value = %d", meterValue.getKey(), meterValue.getValue()));
            }
            repository.reset();
        }
    }

    static class GeneratorWorker implements Runnable {

        private final PriceUpdateGenerator priceUpdateGenerator;
        private final MeterRepository repository;

        public GeneratorWorker(final PriceUpdateGenerator priceUpdateGenerator,
                               final MeterRepository repository) {
            this.priceUpdateGenerator = priceUpdateGenerator;
             this.repository = repository;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repository.processUpdate(priceUpdateGenerator.generate());
            }
        }
    }

    private void fillRepository() {
        repository.addMeter(new RateMeterImpl("general"));
        repository.addMeter(new CurrencyRateMeterImpl("eurusd", "EURUSD"));
    }
}
