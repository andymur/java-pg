package com.andymur.pg.java.concurrency;

import java.util.Arrays;

/**
 * Created by muraand.
 */
public class Queue<E> {
    private E[] elements;
    private int head;
    private int tail;
    private final int capacity;

    @SuppressWarnings(value = "unchecked")
    public Queue(int capacity) {

        if (capacity <= 0) {
            throw new IllegalStateException("Capacity should be greater than zero");
        }

        elements = (E[]) new Object[capacity];
        this.capacity = capacity;
        this.head = capacity - 1;
        this.tail = capacity - 1;
    }

    public void add(E element) {

        if (element == null) {
            throw new IllegalStateException("null value is prohibited");
        }

        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }

        if (tail < 0) {
            tail = capacity - 1;
        }

        elements[tail--] = element;
    }

    public E take() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        E element = elements[head];
        elements[head] = null;
        head -= 1;
        return element;
    }

    public E peek() {
        return elements[head];
    }


    public void print() {
        System.out.println(Arrays.asList(elements));
    }

    public int capacity() {
        return elements.length;
    }

    public int size() {
        if (isFull()) {
            return capacity;
        }

        if (head <= tail) {
            return head - tail;
        } else {
            return -1;
        }
    }

    private boolean isFull() {
        return head == tail && elements[head] != null;
    }

    private boolean isEmpty() {
        return head == tail && elements[head] == null;
    }
}
