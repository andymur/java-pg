package com.andymur.pg.java.testcube;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by andymur on 6/25/17.
 */
public class DominoRunner {

    public static void main(String[] args) {
        /*Dice[] dices = new Dice[] {Dice.of(1, 3), Dice.of(4, 6), Dice.of(1, 7)};

        Permutator permutator = new Permutator(3);
        int[] order;

        while(permutator.hasNext()) {
            Dice[] diceBoard = dicePermutation(dices, permutator.state());
            if (complete(diceBoard)) {
                System.out.println("COMPLETE: " + printBoard(diceBoard));
            } else {
                //System.out.println("INCOMPLETE: " + printBoard(diceBoard));
            }
            permutator = permutator.next();
        }*/

        Rotator rotator = new Rotator(3, 2);
        while(rotator.hasNext()) {
            System.out.println(Permutator.stateToString(rotator.next()));
        }
    }

    static Dice[] dicePermutation(Dice[] original, int[] indices) {
        Dice[] result = new Dice[original.length];
        IntStream.range(0, indices.length).forEach(i -> result[i] = original[indices[i]]);
        return result;
    }

    static String printBoard(Dice[] dices) {
        return Arrays.stream(dices).map(Dice::toString).collect(Collectors.joining(" "));
    }

    static boolean complete(Dice[] dices) {

        for (int i = 0; i < dices.length - 1; i++) {
            if (!dices[i].compatible(dices[i + 1])) {
                return false;
            }
        }

        return dices[dices.length - 1].compatible(dices[0]);
    }

    static class Dice {

        private final int left;
        private final int right;

        public Dice(int left, int right) {
            this.left = left;
            this.right = right;
        }

        static Dice of(int left, int right) {
            return new Dice(left, right);
        }

        Dice flip() {
            return new Dice(right, left);
        }

        @Override
        public String toString() {
            return String.format("[%d|%d]", left, right);
        }

        boolean compatible(Dice dice) {
            return Math.abs(this.right - dice.left) <= 1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Dice dice = (Dice) o;

            if (left != dice.left) return false;
            if (right != dice.right) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = left;
            result = 31 * result + right;
            return result;
        }
    }
}
