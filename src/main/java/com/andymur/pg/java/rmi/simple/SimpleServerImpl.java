package com.andymur.pg.java.rmi.simple;

import com.andymur.pg.java.proxy.SimpleService;
import com.andymur.pg.java.rmi.registry.services.WeatherService;
import org.apache.commons.lang.math.RandomUtils;

/**
 * Created by andymur on 10/7/17.
 */
public class SimpleServerImpl implements SimpleServer {

    @Override
    public int currentTemperature(String city) {
        return RandomUtils.nextInt(10) + 10;
    }

    @Override
    public String currentTemperatureMessage(String city) {
        return String.format("It is %d in %s", currentTemperature(city), city);
    }

}
