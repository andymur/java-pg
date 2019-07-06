package com.andymur.pg.java.rmi.simple;

import com.andymur.pg.java.rmi.registry.services.WeatherService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by andymur on 10/7/17.
 */
public class RmiClientRunner {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        WeatherService simpleServer = (WeatherService) registry.lookup("simpleServer");
        System.out.println(simpleServer);
        System.out.println(simpleServer.currentTemperatureMessage("Frankfurt am Main"));
    }
}
