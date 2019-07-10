package com.andymur.pg.java.rmi.registry.services;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Created by andymur on 10/7/17.
 */
public interface WeatherService extends Serializable {
    int currentTemperature(String city) throws RemoteException;
    String currentTemperatureMessage(String city) throws RemoteException;
}
