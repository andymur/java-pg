package com.andymur.pg.java.algods.ds;

/**
 * Created by andymur on 10/4/17.
 */
public class LinkedListRunner {

    public static void main(String[] args) {
        MyLinkedList<String> cities = new MyLinkedList<>();

        cities.put("Moscow");
        cities.put("Paris");

        System.out.println(cities.peek());
        System.out.println(cities.size());
        System.out.println(cities.get());
        System.out.println(cities.size());
        System.out.println(cities.get());
        System.out.println(cities.size());
    }

    static class MyLinkedList<T> {

        private MyListNode<T> head;

        public int size() {
            int i = 0;

            if (head == null) {
                return i;
            }

            MyListNode node = head;
            while (node != null) {
                i++;
                node = node.next;
            }

            return i;
        }

        public T get() {

            if (head == null) {
                return null;
            }

            T headValue = head.value;
            this.head = head.next;
            return headValue;
        }

        public T peek() {

            if (head == null) {
                return null;
            }

            return head.value;
        }

        public void put(T element) {
            if (head == null) {
                head = constructNode(element);
                return;
            }

            MyListNode last = head;

            while (last.next != null) {
                last = last.next;
            }

            last.next = constructNode(element);
        }

        private MyListNode constructNode(T element) {
            MyListNode node = new MyListNode<>();
            node.value = element;
            return node;
        }
    }

    static class MyListNode<T> {
        MyListNode next;
        T value;
    }
}
