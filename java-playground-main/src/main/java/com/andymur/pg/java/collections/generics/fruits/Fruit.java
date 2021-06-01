package com.andymur.pg.java.collections.generics.fruits;

/**
 * Created by muraand.
 */
public class Fruit {

    protected String type = "fruit";
    protected String name = "unnamed";

    public Fruit() {
    }

    public Fruit(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String sb = "Fruit{" + "type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
        return sb;
    }
}
