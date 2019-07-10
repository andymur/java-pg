package com.andymur.pg.java.generics.fruits;

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
        final StringBuilder sb = new StringBuilder("Fruit{");
        sb.append("type='").append(type).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
