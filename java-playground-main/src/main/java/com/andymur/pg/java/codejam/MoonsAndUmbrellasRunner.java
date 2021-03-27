package com.andymur.pg.java.codejam;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class MoonsAndUmbrellasRunner {
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(new BufferedInputStream(System.in)
        )) {
            int testNumbers = strToInt(scanner.nextLine());
            for (int testNumber = 0 ; testNumber < testNumbers; testNumber++) {
                String input = scanner.nextLine();
                int x = getX(input);
                int y = getY(input);
                String seq = getSequence(input);
                System.out.println(String.format("Case #%d: %d", testNumber + 1 , solution(x, y, seq)));
            }
        }
    }

    private static int solution(int x, int y, String sequence) {
        final String simplifiedSequence = simplifySequence(sequence);
        final String bestSequence = findBestSequence(simplifiedSequence);
        System.out.println(String.format("x: %d, y: %d, seq: %s, simplified seq: %s, best seq: %s", x, y, sequence, simplifiedSequence, bestSequence));
        return calculateCost(x, y, bestSequence);
    }

    private static String simplifySequence(String sequence) {
        int seqLength = sequence.length();
        StringBuilder simplified = new StringBuilder(sequence.length());

        for (int i = 0; i < seqLength; i++) {
            Character prev = i == 0 ? null : sequence.charAt(i - 1);
            Character current = sequence.charAt(i);
            if (current == '?') {
                if (prev != null && prev != '?') {
                    simplified.append(current);
                } else if (prev == null) {
                    simplified.append(current);
                }
            } else {
                simplified.append(current);
            }
        }
        return simplified.toString();
    }

    private static String findBestSequence(String sequence) {
        int seqLength = sequence.length();
        char[] seqArray = sequence.toCharArray();
        for (int i = 0; i < seqLength; i++) {

            Character prev = i == 0 ? null : seqArray[i - 1];
            Character next = i == seqLength - 1 ? null : seqArray[i + 1];
            char current = seqArray[i];

            if (current == '?') {
                if (prev != null && next != null) {
                    seqArray[i] = prev;
                } else if (prev == null && next != null) {
                    seqArray[i] = next;
                } else if (prev != null && next == null) {
                    seqArray[i] = prev;
                } else if (prev == null && next == null) {
                    seqArray[i] = 'C';
                }
            }
        }
        return new String(seqArray);
    }

    private static int calculateCost(int x, int y, String sequence) {
        int cost = 0;
        int seqLength = sequence.length();

        for (int i = 0; i < seqLength; i++) {
            Character prev = i == 0 ? null : sequence.charAt(i - 1);
            char current = sequence.charAt(i);
            // cj - x, jc - y
            if (prev != null && (prev != current)) {
                cost += (prev == 'C' ? x : y);
            }
        }

        return cost;
    }

    private static int getX(String input) {
        return Integer.parseInt(input.split(" ")[0]);
    }

    private static int getY(String input) {
        return Integer.parseInt(input.split(" ")[1]);
    }

    private static String getSequence(String input) {
        return input.split(" ")[2];
    }

    private static int chrToInt(Character character) {
        return strToInt(character.toString());
    }

    private static int strToInt(String string) {
        return Integer.parseInt(string);
    }
}
