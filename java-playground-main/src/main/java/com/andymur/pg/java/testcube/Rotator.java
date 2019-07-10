package com.andymur.pg.java.testcube;

import java.util.stream.IntStream;

/**
 * Created by andymur on 6/25/17.
 */
public class Rotator {

    private int [][] combinations;
    private final int stateNumber;
    private final int pieceNumber;
    private int currentStateIndex;

    public Rotator(int pieceNumber, int stateNumber) {
        this.pieceNumber = pieceNumber;
        this.stateNumber = stateNumber;
        this.currentStateIndex = 0;
        init();
    }

    public int[][] getCombinations() {
        return combinations;
    }

    public int[] next() {
        return combinations[currentStateIndex++];
    }

    public boolean hasNext() {
        return currentStateIndex < combinations.length;
    }

    void init() {
        //final int srcLength = stateNumber;
        int permutations = (int) Math.pow(stateNumber, pieceNumber);
        int [] indices = IntStream.range(0, stateNumber).toArray();
        combinations = new int[permutations][pieceNumber];

        for (int i = 0; i < pieceNumber; i++) {
            int t2 = (int) Math.pow(stateNumber, i);
            for (int p1 = 0; p1 < permutations;) {
                for (int al = 0; al < stateNumber; al++) {
                    for (int p2 = 0; p2 < t2; p2++) {
                        combinations[p1][i] = indices[al];
                        p1++;
                    }
                }
            }
        }
    }
}
