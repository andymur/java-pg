package com.andymur.pg.java.collections.generics.fruits;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muraand.
 */
public class FruitBox<T extends Fruit> {
    List<T> fruits;

    public FruitBox() {
        this.fruits = new ArrayList<>();
    }
}
