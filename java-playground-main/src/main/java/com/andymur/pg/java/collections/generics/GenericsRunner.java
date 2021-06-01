package com.andymur.pg.java.collections.generics;

import com.andymur.pg.java.collections.generics.fruits.*;

import java.util.ArrayList;
import java.util.List;

import static com.andymur.pg.java.collections.generics.ClassWithStaticMethods.identity;
import static com.andymur.pg.java.collections.generics.ClassWithStaticMethods.identityNumber;

/**
 * Created by muraand.
 */
public class GenericsRunner {

    public static void main(String[] args) {
        //simpleFruitGenericRunner();
        genericAssignments();
    }

    private static void genericAssignments() {
        List<Fruit> fruits = new ArrayList<>();
        List<Apple> apples = new ArrayList<>();
        List<Orange> oranges = new ArrayList<>();

        List<? super Fruit> superFruits = new ArrayList<>();
        List<? extends Fruit> extendFruits = new ArrayList<>();

        List<? extends Orange> extendOranges = new ArrayList<>();
        List<? extends Fruit> extendFruitOranges = new ArrayList<Orange>();

        List<? extends Apple> extendApples = new ArrayList<>();
        List<? extends Fruit> extendFruitApples = new ArrayList<Apple>();

        List<? super Orange> superFruitOranges = new ArrayList<Fruit>();
        List<? super Orange> superOranges = new ArrayList<>();

        List<? super Apple> superFruitApple = new ArrayList<Fruit>();
        List<? super Apple> superApples = new ArrayList<>();


        List<?> wildCardFruit = new ArrayList<Fruit>();
        List<?> wildCardApples = new ArrayList<Apple>();
        List<?> wildCardOranges = new ArrayList<Orange>();

        // wild card -> anything is ok

        wildCardFruit = wildCardApples;
        wildCardFruit = extendFruitApples;
        wildCardApples = superApples;

        // wild card -> wild card only is possible
        // superApples = wildCardApples; -- incompatible types error

        extendFruitApples = extendFruits;
        //extendFruits.add(new Orange()); impossible to add nothing but null
        extendFruitApples.add(null);

        //covariant types
        extendFruits = extendApples;

        //contravariant
        superOranges = superFruits;
    }

    private static void simpleFruitGenericRunner() {
        List<? extends Apple> apples;
        List<? extends Orange> oranges;

        AppleBox appleBox = new AppleBox();
        OrangeBox orangeBox = new OrangeBox();

        FruitBoxOperator<Orange> operator = new FruitBoxOperator<>();
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
