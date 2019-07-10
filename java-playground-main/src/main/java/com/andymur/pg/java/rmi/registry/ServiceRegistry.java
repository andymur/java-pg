package com.andymur.pg.java.rmi.registry;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by andymur on 10/7/17.
 */
public interface ServiceRegistry extends Remote {
    <T> boolean deregister(Class<T> serviceClass) throws RemoteException;
    <T> boolean deregister(String name) throws RemoteException;
    <T> boolean register(Class<T> serviceClass, T service) throws RemoteException;
    <T> boolean register(String name, T service) throws RemoteException;
    <T> T getService(String name) throws RemoteException;
    <T> T getService(Class<T> serviceClass) throws RemoteException;
}
