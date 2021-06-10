package com.andymur.pg.influxdb.repository;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;

public class InfluxRepositoryImpl implements InfluxRepository {
    private final InfluxDB influxInstance;
    private final String dbName;
    private final String retentionPolicy;

    public InfluxRepositoryImpl(String dbName, String retentionPolicy, InfluxDB influxInstance) {
        this.influxInstance = influxInstance;
        this.dbName = dbName;
        this.retentionPolicy = retentionPolicy;
    }

    @Override
    public void writePoint(final Point point) {
        influxInstance.write(dbName, retentionPolicy, point);
    }
}
