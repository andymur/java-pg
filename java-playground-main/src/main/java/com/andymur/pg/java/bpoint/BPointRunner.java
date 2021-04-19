package com.andymur.pg.java.bpoint;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BPointRunner {

    private static final Integer [] SEQUENCE = new Integer[] {1, 2, 3, 4, 5};
    private static final Integer [] SEQUENCE_WITH_HOLES = new Integer[] {1, 3, 5, 7, 9, 11};
    // binary searh
    // merge sort
    public static void main(String[] args) {
        System.out.println(revertedList(createListFromSequence(SEQUENCE)));
        LnList<Integer> lnList = new LnList<Integer>(SEQUENCE);
        System.out.println(lnList);
        System.out.println(reverse(lnList));
        System.out.println(bSearch(5, new Integer[] {1, 3, 5 ,7}));
    }


    private static int bSearch(int element, Integer[] sequence) {
        int seqSize = sequence.length;

        if (seqSize == 0) {
            return -1;
        }

        if (seqSize == 1) {
            return element == sequence[0] ? 0 : -1;
        }

        if (element < sequence[0] || element > sequence[seqSize -  1]) {
            return -1;
        }

        int low = 0;
        int high = seqSize;
        // 1, 3
        // 1, 3, 5, 7
        // 1, 3, 5, 7, 9
        while (low < high) {
            int mid = low + ((high - low) / 2);
            if (element == sequence[mid]) {
                return mid;
            } else if (element < sequence[mid]) {
                high = low + mid;
            } else if (element > sequence[mid]) {
                low = low + mid;
            }
        }
        return element == sequence[low] ? low : -1;
    }

    /* reverse list */
    private static List<Integer> revertedList(List<Integer> originalList) {
        Collections.reverse(originalList);
        return originalList;
    }

    private static List<Integer> createListFromSequence(Integer[] sequence) {
        return Arrays.stream(sequence).collect(Collectors.toList());
    }

    private static <E> LnList<E> reverse(LnList<E> originalList) {
        Node<E> prev = null;
        Node<E> current = originalList.getHead();

        if (current == null) {
            return originalList;
        }

        Node<E> next = current.getNext();

        while (next != null) {
            current.setNext(prev);
            prev = current;
            current = next;
            next = next.getNext();
        }

        current.setNext(prev);
        originalList.setHead(current);

        return originalList;
    }

    static class LnList<E> {
        Node<E> head;

        @SafeVarargs
        public LnList(E...elements) {
            if (elements.length == 0) {
                head = null;
                return;
            }

            boolean isHead = true;

            Node<E> prev = null;
            for (E element: elements) {
                if (isHead) {
                    head = new Node<>(element);
                    prev = head;
                } else {
                    final Node<E> current = new Node<>(element);
                    prev.setNext(current);
                    prev = current;
                }
                isHead = false;
            }
        }

        public void setHead(Node<E> head) {
            this.head = head;
        }

        public Node<E> getHead() {
            return head;
        }

        @Override
        public String toString() {
            return stringify(head);
        }

        private static <E> String stringify(final Node<E> head) {
            StringBuilder result = new StringBuilder();

            Node<E> element = head;

            while (!element.isTail()) {
                if (element == head) {
                    result.append(element.getValue());
                } else {
                    result.append(" <= ").append(element.getValue());
                }
                element = element.getNext();
            }

            if (element == head) {
                result.append(element.getValue());
            } else {
                result.append(" <= ").append(element.getValue());
            }

            return result.toString();
        }
    }

    static class Node<E> {
        private final E value;
        private Node<E> next;

        public Node(final E value) {
            this(value, null);
        }

        public Node(final E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }

        public E getValue() {
            return value;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public boolean isTail() {
            return next == null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", tail=" + (next == null) +
                    '}';
        }
    }

}
