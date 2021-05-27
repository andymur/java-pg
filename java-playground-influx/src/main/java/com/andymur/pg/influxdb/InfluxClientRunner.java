package com.andymur.pg.influxdb;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
    set it up with Docker ```docker run -p 8086:8086 -v influxdb:/var/lib/influxdb influxdb:1.8```
 */
public class InfluxClientRunner {

    private static String HOST = "localhost";
    private static String PORT = "8086";

    private static String DB_NAME = "test_measurements";

    private static String MEASUREMENT_NAME = "test_measurement";

    private static Random SOURCE_OF_RANDOMNESS = new Random(System.currentTimeMillis());

    private static List<String> APPLICATIONS = Arrays.asList("API", "MOBILE", "DESKTOP");

    private static List<String> HOSTS = Arrays.asList("localhost", "192.168.1.1", "192.168.1.2", "192.168.1.3");

    public static void main(String[] args) throws InterruptedException {
        try (InfluxDB influxDB = InfluxDBFactory.connect(String.format("http://%s:%s", HOST, PORT))) {

            Pong pong = influxDB.ping();

            if (pong.isGood()) {
                // will be removed
                influxDB.createDatabase(DB_NAME);
                influxDB.enableBatch();
                BatchPoints batchPoints = BatchPoints.database(DB_NAME).build();

                for (int i = 0; i < 50; i++) {
                    batchPoints.point(createRandomPoint());
                    Thread.sleep(TimeUnit.MILLISECONDS.toMillis(100));
                }

                influxDB.write(batchPoints);
            }
        }
    }

    static Point createRandomPoint() {

        // test_measurement (time, application (tag), application_instance (tag), monitor_host (tag), rate)
        // (tstamp, "API", "1", "dmz", 88)
        return Point.measurement(MEASUREMENT_NAME)
                .tag("application", randomFromList(APPLICATIONS))
                .tag("instance", String.valueOf(randomInt(1, 10)))
                .tag("monitor_host", randomFromList(HOSTS))
                .addField("rate", randomInt(100, 1200))
                .build();
    }

    static <E> E randomFromList(List<E> elements) {
        return elements.get(randomInt(0, elements.size() - 1));
    }

    static int randomInt(int from, int to) {
        return from + SOURCE_OF_RANDOMNESS.nextInt(to + 1);
    }
}
