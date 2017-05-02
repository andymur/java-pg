package com.andymur.pg.java.generics;

/**
 * Created by muraand.
 */
public class ClassWithStaticMethods {

    static <T> T identity(T element) {
        return element;
    }

    static <T extends Number> T identityNumber(T element) {
        return element;
    }


}
