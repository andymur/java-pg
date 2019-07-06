package com.andymur.pg.java.rmi.registry;

import com.andymur.pg.java.rmi.registry.services.WeatherService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by andymur on 10/7/17.
 */
public class ClientVmRunner {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        ServiceRegistry serviceRegistry = (ServiceRegistry) registry.lookup("registry");

        WeatherService weatherService = serviceRegistry.getService(WeatherService.class);

        System.out.println(weatherService.currentTemperatureMessage("Frankfurt am Main"));
    }
}
