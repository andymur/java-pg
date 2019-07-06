package com.andymur.pg.java.rmi.registry.services;

import org.apache.commons.lang.math.RandomUtils;

import java.rmi.RemoteException;

/**
 * Created by andymur on 10/7/17.
 */
public class WeatherServiceImpl implements WeatherService {

    private static final long serialVersionUID = 7088458129115969539L;

    @Override
    public int currentTemperature(String city) throws RemoteException {
        return RandomUtils.nextInt(10) + 10;
    }

    @Override
    public String currentTemperatureMessage(String city) throws RemoteException {
        return String.format("It is %d in %s", currentTemperature(city), city);
    }
}
