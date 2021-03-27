package com.andymur.pg.java.codejam;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class ReverseSortEngineeringRunner {

    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(new BufferedInputStream(System.in)
        )) {
            int testNumbers = strToInt(scanner.nextLine());

            for (int testNumber = 0 ; testNumber < testNumbers; testNumber++) {
                int[] testArguments = parseTest(scanner.nextLine());
                final int seqSize = testArguments[0];
                final int cost = testArguments[1];

                System.out.println(String.format("Case #%d: %s", testNumber + 1 , solution(seqSize, cost)));
            }
        }
    }

    private static String solution(int sequenceSize, long cost) {
        final Set<int[]> perms = permutationsForSize(sequenceSize);
        for (int[] perm: perms) {
            int[] permCopy = Arrays.copyOf(perm, perm.length);
            long permCost = permCost(sequenceSize, perm);
            if (cost == permCost) {
                return arrayToString(permCopy);
            }
        }
        return "IMPOSSIBLE";
    }

    private static long permCost(int seqSize, int[] sequence) {
        long sum = 0;
        for (int i = 0; i < seqSize - 1; i++) {
            final int j = minPosition(sequence, i, seqSize);
            reverse(sequence, i, j);
            sum += (j - (i + 1)) + 1;
        }

        return sum;
    }

    private static int minPosition(int[] array, int start, int end) {
        int min = array[start];
        int minPosition = start;
        for (int i = start; i < end; i++) {
            if (array[i] < min) {
                min = array[i];
                minPosition = i;
            }
        }
        return minPosition + 1;
    }

    private static void reverse(int[] array, int start, int end) {
        int j = start;
        for (int i = end - 1; i > start; i--) {
            if (i <= j) {
                break;
            }
            final int t = array[i];
            array[i] = array[j];
            array[j++] = t;
        }
    }

    private static Set<int[]> permutationsForSize(int seqSize) {
        // 123456789 - 987654321
        Set<int[]> permutations = new HashSet<>(4_00_000); // enough for 9!
        int lowerRange = lowerRange(seqSize);
        int upperRange = upperRange(seqSize);
        for (int i = lowerRange; i <= upperRange; i++) {
            if (isRealPermutation(seqSize, i)) {
                permutations.add(intToDigits(seqSize, i));
            }
        }
        return permutations;
    }

    private static int[] intToDigits(int seqSize, int number) {
        int[] result = new int[seqSize];
        for (int i = seqSize - 1; i >= 0; i--) {
            int digit = number % 10;
            number /= 10;
            result[i] = digit;
        }
        return result;
    }

    private static boolean isRealPermutation(int seqSize, int candidate) {
        boolean [] digits = new boolean[seqSize];
        while (candidate > 0) {
            int digit = candidate % 10;
            if (digit > 0 && digit <= seqSize) {
                digits[digit - 1] = true;
            }
            candidate = candidate / 10;
        }
        boolean isPermutation = true;
        for (boolean digitSet: digits) {
            isPermutation &= digitSet;
        }
        return isPermutation;
    }

    private static int lowerRange(int seqSize) {
        int range = 0;
        for (int i = 1; i <= seqSize; i++) {
            range = range * 10 + i;
        }
        return range;
    }

    private static int upperRange(int seqSize) {
        int range = 0;
        for (int i = seqSize; i > 0; i--) {
            range = range * 10 + i;
        }
        return range;
    }

    private static String arrayToString(int[] array) {
        return Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.joining(" "));
    }

    private static int[] parseTest(String testString) {
        final String[] args = testString.split(" ");
        return new int[] {strToInt(args[0]), strToInt(args[1])};
    }

    private static int strToInt(String string) {
        return Integer.parseInt(string);
    }
}
