package com.andymur.pg.java.rmi.simple;

import com.andymur.pg.java.proxy.SimpleService;
import com.andymur.pg.java.rmi.registry.services.WeatherService;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by andymur on 10/7/17.
 */
public class RmiServerRunner {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        SimpleServer simpleServer = new SimpleServerImpl();

        SimpleServer stub = (SimpleServer) UnicastRemoteObject.exportObject(simpleServer, 0);
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("simpleServer", stub);

    }
}
