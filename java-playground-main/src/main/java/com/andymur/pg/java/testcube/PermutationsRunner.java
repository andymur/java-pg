package com.andymur.pg.java.testcube;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by andymur on 6/24/17.
 */
public class PermutationsRunner {

    private static final int SIZE = 3;

    public static void main(String[] args) {
        solve(new int[] {1, 2, 3});
    }

    static void solve(int[] numbers) {

        if (numbers == null || numbers.length == 0) {
            return;
        }


        while(!reversed(numbers)) {

            System.out.println(
                    Arrays.stream(numbers).boxed().map(i -> String.valueOf(i)).collect(Collectors.joining(", "))
            );

            int i = numbers.length - 1;

            for (; i > 0; i--) {
                if (numbers[i] > numbers[i - 1]) {
                    break;
                }
            }

            Arrays.sort(numbers, i, numbers.length);
            int minIndex = findMinimumIndexByIndexWithThreshold(i, numbers[i - 1], numbers);
            swap(numbers, i - 1, minIndex);

        }

        System.out.println(
                Arrays.stream(numbers).boxed().map(i -> String.valueOf(i)).collect(Collectors.joining(", "))
        );
    }

    static void swap(int[] numbers, int i, int j) {
        int t = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = t;
    }

    static int findMinimumIndexByIndexWithThreshold(int startIndex, int threshold, int[] numbers) {
        int minimum = SIZE + 1;
        int minIndex = 0;

        for (int i = startIndex; i < numbers.length; i++) {
            if (numbers[i] < minimum && numbers[i] > threshold) {
                minimum = numbers[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    static boolean reversed(int[] numbers) {
        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] < numbers[i + 1]) {
                return false;
            }
        }

        return true;
    }
}
