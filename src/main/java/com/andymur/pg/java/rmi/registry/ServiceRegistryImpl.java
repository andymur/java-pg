package com.andymur.pg.java.rmi.registry;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by andymur on 10/7/17.
 */
public class ServiceRegistryImpl implements ServiceRegistry {

    private ConcurrentHashMap<Class<?>, Object> servicesByClass = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Object> servicesByName = new ConcurrentHashMap<>();;

    @Override
    public <T> boolean deregister(Class<T> serviceClass) {
        return servicesByClass.remove(serviceClass) != null;
    }

    @Override
    public <T> boolean deregister(String name) {
        return servicesByName.remove(name) != null;
    }

    @Override
    public <T> boolean register(Class<T> serviceClass, T service) {
        return servicesByClass.put(serviceClass, service) == null;
    }

    @Override
    public <T> boolean register(String name, T service) {
        return servicesByName.put(name, service) == null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getService(Class<T> serviceClass) {
        return (T) servicesByClass.get(serviceClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getService(String name) {
        return (T) servicesByName.get(name);
    }
}
