package com.andymur.pg.java.testcube;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by andymur on 6/25/17.
 */
public class Permutator {
    final int size;
    final int[] state;
    private final boolean notReturned;

    public Permutator(int size) {
        this.size = size;
        notReturned = true;
        state = IntStream.range(0, size).toArray();
    }

    private Permutator(int[] state) {
        size = state.length;
        notReturned = true;
        this.state = Arrays.copyOf(state, state.length);
    }

    public int[] state() {
        return Arrays.copyOf(state, size);
    }

    private Permutator(int[] state, boolean notReturned) {
        size = state.length;
        this.notReturned = notReturned;
        this.state = Arrays.copyOf(state, state.length);
    }

    @Override
    public String toString() {
        return stateToString(this.state);
    }

    public Permutator next() {

        if (!hasNext()) {
            return null;
        }

        if (reversed(state)) {
            return new Permutator(state, false);
        }

        int i = state.length - 1;

        for (; i > 0; i--) {
            if (state[i] > state[i - 1]) {
                break;
            }
        }

        Arrays.sort(state, i, state.length);
        int minIndex = findMinimumIndexByIndexWithThreshold(i, state[i - 1], state);
        swap(state, i - 1, minIndex);

        return new Permutator(state);
    }

    public static String stateToString(int[] state) {
        return Arrays.stream(state).boxed().map(i -> String.valueOf(i)).collect(Collectors.joining(", "));
    }

    public boolean hasNext() {
        return notReturned;
    }

    private void swap(int[] numbers, int i, int j) {
        int t = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = t;
    }

    private int findMinimumIndexByIndexWithThreshold(int startIndex, int threshold, int[] numbers) {
        int minimum = size + 1;
        int minIndex = 0;

        for (int i = startIndex; i < numbers.length; i++) {
            if (numbers[i] < minimum && numbers[i] > threshold) {
                minimum = numbers[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    private boolean reversed(int[] numbers) {
        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] < numbers[i + 1]) {
                return false;
            }
        }

        return true;
    }
}
