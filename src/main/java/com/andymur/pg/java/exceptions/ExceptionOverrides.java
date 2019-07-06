package com.andymur.pg.java.exceptions;

import java.util.Objects;

/**
 * Created by andymur on 6/20/17.
 */
public class ExceptionOverrides {

    public static void main(String[] args) {
        A b= new  B();
        b.method();
    }

    static class A {
        int a;
        String s;
        double b;

        public void method() throws IllegalArgumentException {
            System.out.println("A");
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            A a1 = (A) o;

            if (a != a1.a) return false;
            if (Double.compare(a1.b, b) != 0) return false;
            if (s != null ? !s.equals(a1.s) : a1.s != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = a;
            result = 31 * result + (s != null ? s.hashCode() : 0);
            temp = Double.doubleToLongBits(b);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }

    static class B extends A {
        @Override
        public void method() throws RuntimeException {
            System.out.println("B");
        }
    }
}
