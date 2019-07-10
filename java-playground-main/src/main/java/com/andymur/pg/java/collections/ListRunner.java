package com.andymur.pg.java.collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by andymur on 6/7/17.
 */
public class ListRunner {
    public static void main(String[] args) throws InterruptedException {
        final int size = 1000000;

        createArrayList(size);

        createArrayList(size);

        createArrayList(size);

        long before = System.currentTimeMillis();
        List<Integer> list = createArrayList(size);
        System.out.println(TimeUnit.MILLISECONDS.convert(System.currentTimeMillis() - before, TimeUnit.MILLISECONDS));

        before = System.currentTimeMillis();
        insertIntoArrayList(list);
        System.out.println(TimeUnit.MILLISECONDS.convert(System.currentTimeMillis() - before, TimeUnit.MILLISECONDS));


        before = System.currentTimeMillis();
        list = createLinkedList(size);
        System.out.println(TimeUnit.MILLISECONDS.convert(System.currentTimeMillis() - before, TimeUnit.MILLISECONDS));

        before = System.currentTimeMillis();
        insertIntoLinkedList(list);
        System.out.println(TimeUnit.MILLISECONDS.convert(System.currentTimeMillis() - before, TimeUnit.MILLISECONDS));
    }

    private static List<Integer> createArrayList(int size) throws InterruptedException {
        List<Integer> list = new ArrayList<>(size);
        IntStream.range(0, size).forEach(number -> list.add(number));
        return list;
    }

    private static void insertIntoArrayList(List<Integer> list) {
        IntStream.range(0, 3000).forEach(number -> list.add(900000 + number, number));
    }

    private static List<Integer> createLinkedList(int size) throws InterruptedException {
        final List<Integer> list = new LinkedList<>();
        ListIterator<Integer> iterator = list.listIterator();
        for (int i = 0; i < size; i++) {
            iterator.add(i);
        }
        return list;
    }

    private static void insertIntoLinkedList(List<Integer> list) {
        IntStream.range(0, 3000).forEach(number -> list.add(900000 + number, number));
    }
}
