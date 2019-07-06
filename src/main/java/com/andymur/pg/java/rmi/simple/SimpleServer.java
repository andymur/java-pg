package com.andymur.pg.java.rmi.simple;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by andymur on 10/7/17.
 */
public interface SimpleServer extends Remote {
    int currentTemperature(String city) throws RemoteException;
    String currentTemperatureMessage(String city) throws RemoteException;
}
