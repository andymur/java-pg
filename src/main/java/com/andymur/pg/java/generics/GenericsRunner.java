package com.andymur.pg.java.generics;

import com.andymur.pg.java.generics.fruits.Apple;
import com.andymur.pg.java.generics.fruits.AppleBox;
import com.andymur.pg.java.generics.fruits.Fruit;
import com.andymur.pg.java.generics.fruits.FruitBoxOperator;
import com.andymur.pg.java.generics.fruits.Orange;
import com.andymur.pg.java.generics.fruits.OrangeBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.andymur.pg.java.generics.ClassWithStaticMethods.identity;
import static com.andymur.pg.java.generics.ClassWithStaticMethods.identityNumber;

/**
 * Created by muraand.
 */
public class GenericsRunner {

    public static void main(String[] args) {
        //simpleFruitGenericRunner();
        genericAssignments();
    }

    private static void genericAssignments() {
        List<Fruit> fruits = new ArrayList<Fruit>();
        List<Apple> apples = new ArrayList<Apple>();
        List<Orange> oranges = new ArrayList<Orange>();

        List<? super Fruit> superFruits = new ArrayList<Fruit>();
        List<? extends Fruit> extendFruits = new ArrayList<Fruit>();

        List<? extends Orange> extendOranges = new ArrayList<Orange>();
        List<? extends Fruit> extendFruitOranges = new ArrayList<Orange>();

        List<? extends Apple> extendApples = new ArrayList<Apple>();
        List<? extends Fruit> extendFruitApples = new ArrayList<Apple>();

        List<? super Orange> superFruitOranges = new ArrayList<Fruit>();
        List<? super Orange> superOranges = new ArrayList<Orange>();

        List<? super Apple> superFruitApple = new ArrayList<Fruit>();
        List<? super Apple> superApples = new ArrayList<Apple>();


        List<?> wildCardFruit = new ArrayList<Fruit>();
        List<?> wildCardApples = new ArrayList<Apple>();
        List<?> wildCardOranges = new ArrayList<Orange>();

        // wild card -> anythins is ok

        wildCardFruit = wildCardApples;
        wildCardFruit = extendFruitApples;
        wildCardApples = superApples;

        // wild card -> wild card only is possible
        // superApples = wildCardApples; -- incompatible types error

        extendFruitApples = extendFruits;
        //extendFruits.add(new Orange()); impossible to add nothing but null
        extendFruitApples.add(null);

    }

    private static void simpleFruitGenericRunner() {
        List<? extends Apple> apples;
        List<? extends Orange> oranges;

        AppleBox appleBox = new AppleBox();
        OrangeBox orangeBox = new OrangeBox();

        FruitBoxOperator<Orange> operator = new FruitBoxOperator();
        operator.addFruit(orangeBox, new Orange("Tom"));
        // compilation error
        //apples = operator.getFruits(orangeBox);
        oranges = operator.getFruits(orangeBox);
        for (Fruit fruit: oranges) {
            System.out.println(fruit);
        }
    }

    private static void staticMethodsCall() {
        String letterA = identity("A");
        identityNumber(12L);
    }
}
