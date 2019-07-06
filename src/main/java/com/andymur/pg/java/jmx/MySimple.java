package com.andymur.pg.java.jmx;

/**
 * Created by andymur on 10/30/17.
 */
public class MySimple implements MySimpleMBean {

    @Override
    public String sayHello() {
        return "Hello!";
    }
}
