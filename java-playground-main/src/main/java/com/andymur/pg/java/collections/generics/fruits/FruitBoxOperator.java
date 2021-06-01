package com.andymur.pg.java.collections.generics.fruits;

import java.util.List;

/**
 * Created by muraand.
 */
public class FruitBoxOperator<T extends Fruit> {

    public List<? extends T> getFruits(FruitBox<T> box) {
        return box.fruits;
    }

    public void addFruit(FruitBox<? super T> box, T fruit) {
        box.fruits.add(fruit);
    }
}
