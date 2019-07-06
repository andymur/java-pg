package com.andymur.pg.java.proxy;

/**
 * Created by andymur on 10/7/17.
 */
public class SimpleServiceImpl implements SimpleService{

    @Override
    public String sayHello() {
        System.out.println("Hi All");
        return "Hi All";
    }
}
