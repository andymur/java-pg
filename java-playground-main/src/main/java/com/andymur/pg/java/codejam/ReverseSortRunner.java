package com.andymur.pg.java.codejam;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReverseSortRunner {

    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(new BufferedInputStream(System.in)
        )) {
            int testNumbers = strToInt(scanner.nextLine());

            for (int testNumber = 0 ; testNumber < testNumbers; testNumber++) {
                int seqSize = strToInt(scanner.nextLine());
                String seq = scanner.nextLine();
                System.out.println(String.format("Case #%d: %d", testNumber + 1 , solution(seqSize, seq)));
            }
        }
    }

    private static long solution(int seqSize, String sequence) {
        final int[] array = Arrays.stream(sequence.split(" ")).mapToInt(Integer::valueOf).toArray();
        long sum = 0;
        for (int i = 0; i < seqSize - 1; i++) {
            final int j = minPosition(array, i, seqSize);
            reverse(array, i, j);
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

    private static int chrToInt(Character character) {
        return strToInt(character.toString());
    }

    private static int strToInt(String string) {
        return Integer.parseInt(string);
    }
}
