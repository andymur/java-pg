package com.andymur.pg.java.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class InserterRunner {

    private static final String URL = "jdbc:postgresql://localhost/events_experiments";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    // table structure is pretty dumb
    // no indices, nothing
    // CREATE TABLE measurements(
    //        id SERIAL,
    //        device VARCHAR(50)
    //);
    private static final String INSERT_STATEMENT = "INSERT INTO MEASUREMENTS(ID, DEVICE) VALUES (?, ?)";

    private static Long COUNTER = 0L;

    private static final long WORKING_TIME_IN_MINUTES = TimeUnit.SECONDS.toMillis(60);

    private static final long BATCH_SIZE = 10000L;

    private static final List<Long> MEASUREMENTS = new ArrayList<>();

    public static void main(String[] args) throws SQLException {
        final Connection connection = connect();
        final PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STATEMENT);

        final long started = System.currentTimeMillis();
        long secondThreshold = System.currentTimeMillis();

        long idCounter = 0L;
        final String name = "device123";

        while (System.currentTimeMillis() - started < WORKING_TIME_IN_MINUTES) {
            makeInsert(preparedStatement, idCounter++, name);
            //makeInsertSlowly(connection, idCounter++, name);
            COUNTER += 1;
            final long now = System.currentTimeMillis();
            if (now - secondThreshold > TimeUnit.SECONDS.toMillis(1L)) {
                secondThreshold = now;
                MEASUREMENTS.add(COUNTER);
                COUNTER = 0L;
            }
        }

        System.out.println("MEASUREMENTS: " + MEASUREMENTS);
    }

    private static void makeInsert(PreparedStatement preparedStatement,
                                   long id,
                                   String name) throws SQLException {
        preparedStatement.setLong(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.addBatch();
        // you need to use some black magic to put right number for the batch size
        if (id % BATCH_SIZE == 0) {
            preparedStatement.executeBatch();
        }
    }

    private static void makeInsertSlowly(Connection connection, long id, String name) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STATEMENT);
        preparedStatement.setLong(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.execute();
    }

    // we don't need it anymore
    private static void randomSleep() {
        try {
            Thread.sleep(new Random(System.currentTimeMillis()).nextInt(200) + 50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}
