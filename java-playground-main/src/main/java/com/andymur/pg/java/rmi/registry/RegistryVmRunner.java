package com.andymur.pg.java.rmi.registry;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by andymur on 10/7/17.
 */
public class RegistryVmRunner {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        ServiceRegistry registryService = new ServiceRegistryImpl();

        ServiceRegistry stub = (ServiceRegistry) UnicastRemoteObject.exportObject(registryService, 0);
        Registry rmiRegistry = LocateRegistry.createRegistry(1099);
        rmiRegistry.bind("registry", stub);
    }
}
