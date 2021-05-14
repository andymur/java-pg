package com.andymur.pg.java.algods.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleSortsRunner {

    public static void main(String[] args) {
        final List<Integer> sorted = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Integer> shuffled = new ArrayList<>(sorted);

        shuffle(shuffled);
        System.out.println("Preparing...sorted: " + sorted + ", shuffled: " + shuffled);
        bubbleSort(shuffled);
        System.out.println("bubble sort works: " + listsAreEqual(sorted, shuffled));

        shuffle(shuffled);
        System.out.println("Preparing...sorted: " + sorted + ", shuffled: " + shuffled);
        qSort(shuffled);
        System.out.println("quick sort works: " + listsAreEqual(sorted, shuffled));

        shuffle(shuffled);
        System.out.println("Preparing...sorted: " + sorted + ", shuffled: " + shuffled);
        qSortInPlace(shuffled);
        System.out.println("quick sort in place works: " + listsAreEqual(sorted, shuffled));

        shuffle(shuffled);
        System.out.println("Preparing...sorted: " + sorted + ", shuffled: " + shuffled);
        mergeSort(shuffled);
        System.out.println("merge sort works: " + listsAreEqual(sorted, shuffled));
    }

    static <T extends Comparable<T>> void shuffle(List<T> list) {
        Collections.shuffle(list);
    }

    static <T extends Comparable<T>> void bubbleSort(List<T> shuffled) {
        final int size = shuffled.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if (greater(shuffled, i, j)) {
                    Collections.swap(shuffled, i, j);
                }
            }
        }
    }

    /* in place quick sort start */

    static <T extends Comparable<T>> void qSortInPlace(List<T> shuffled) {
        if (shuffled == null || shuffled.size() < 2) {
            return;
        }
        qSortInPlaceStep(0, shuffled.size(), shuffled);
    }

    static <T extends Comparable<T>> void qSortInPlaceStep(int low, int high, List<T> list) {

        if (low >= high || list == null || list.size() < 2) {
            return;
        }

        final int idx = partition(low, high - 1, list);

        qSortInPlaceStep(low, idx - 1, list);
        qSortInPlaceStep(idx + 1, list.size(), list);
    }

    static <T extends Comparable<T>> int partition(int low,
                                                   int high,
                                                   List<T> list) {
        int midIdx = (high - low) / 2;
        T mid = get(list, midIdx + low);

        while (low < high) {
            while (get(list, high).compareTo(mid) > 0) {
                high -= 1;
            }
            while (get(list, low).compareTo(mid) < 0) {
                low += 1;
            }
            Collections.swap(list, low, high);
        }

        return low;
    }

    /* quick sort start */

    static <T extends Comparable<T>> void qSort(List<T> shuffled) {
        if (shuffled == null || shuffled.size() < 2) {
            return;
        }

        List<T> sorted = qSortStep(shuffled);
        shuffled.clear();
        shuffled.addAll(sorted);
    }

    static <T extends Comparable<T>> List<T> qSortStep(List<T> list) {
        if (list == null || list.size() < 2) {
            return list;
        }

        int midIdx = list.size() / 2;
        T mid = get(list, midIdx);

        list.remove(midIdx);

        return concat(qSortStep(list.stream().filter(e -> e.compareTo(mid) <= 0).collect(Collectors.toList())),
                mid,
                qSortStep(list.stream().filter(e -> e.compareTo(mid) > 0).collect(Collectors.toList())));
    }

    static <T extends Comparable<T>> List<T> concat(List<T> left, T mid, List<T> right) {
        List<T> result = new ArrayList<>(left);
        result.add(mid);
        result.addAll(right);
        return result;
    }


    /* merge sort start */

    static <T extends Comparable<T>> void mergeSort(List<T> shuffled) {
        if (shuffled == null || shuffled.size() < 2) {
            return;
        }

        List<T> left = new ArrayList<>();
        List<T> right = new ArrayList<>();

        int idx = shuffled.size() / 2;

        for (int i = 0; i < idx; i++) {
            left.add(get(shuffled, i));
        }

        for (int i = idx; i < shuffled.size(); i++) {
            right.add(get(shuffled, i));
        }

        mergeSort(left);
        mergeSort(right);

        List<T> sorted = merge(left, right);
        Collections.copy(shuffled, sorted);
    }

    static <T extends Comparable<T>> List<T> merge(List<T> left,
                                                   List<T> right) {
        List<T> result = new ArrayList<>();

        while (!left.isEmpty() && !right.isEmpty()) {
            T el;
            if (left.get(0).compareTo(right.get(0)) < 0) {
                el = left.remove(0);
            } else {
                el = right.remove(0);
            }
            result.add(el);
        }

        result.addAll(left);
        result.addAll(right);

        return result;
    }

    static <T extends Comparable<T>> void radixSort(List<T> shuffled) {

    }

    static <T extends Comparable<T>> boolean greater(List<T> list, int idx1, int idx2) {
        return get(list, idx1).compareTo(get(list, idx2)) > 0;
    }

    static <T extends Comparable<T>> T get(List<T> list, int idx) {
        return list.get(idx);
    }

    static <T> boolean listsAreEqual(List<T> expected, List<T> given) {
        if (expected == null && given == null) {
            return true;
        }

        if (expected == null || given == null) {
            return false;
        }

        if (expected.size() != given.size()) {
            return false;
        }

        boolean result = true;
        int idx = 0;

        while (result) {
            result &= expected.get(idx).equals(given.get(idx));
            idx++;
            if (given.size() >= idx) {
                break;
            }
        }

        return result;
    }
}
