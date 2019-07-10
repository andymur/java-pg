package com.andymur.pg.java.rmi.registry;

import com.andymur.pg.java.rmi.registry.services.WeatherService;
import com.andymur.pg.java.rmi.registry.services.WeatherServiceImpl;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by andymur on 10/7/17.
 */
public class BaseServiceVmRunner {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        WeatherService weatherService = new WeatherServiceImpl();
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        ServiceRegistry serviceRegistry = (ServiceRegistry) registry.lookup("registry");

        serviceRegistry.register(WeatherService.class, weatherService);
    }
}
