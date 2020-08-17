package com.andymur.pg.java.hackerrank;

import java.util.Arrays;

public class MatrixSumRunner {
    //

    public static void main(String[] args) {
        final String solution = new MatrixSumRunner().solution(2, 3, new int[]{0, 0, 1, 1, 2});
        System.out.println(solution);
    }

    // U -- upper sum
    // L -- lower sum
    // C -- sum by column
    // 3, 2, [2, 1, 1, 0, 1]
    // 1 1 0 0 1
    // 1 0 1 0 0
    public String solution(int U, int L, int [] C) {
        int totalSum = Arrays.stream(C).reduce(Integer::sum).orElse(0);

        if (totalSum != U + L) {
            return "IMPOSSIBLE";
        }

        StringBuilder u = new StringBuilder(C.length);
        StringBuilder l = new StringBuilder(C.length);

        int uOccupied = 0;

        for (int i = 0; i < C.length; i++) {
            int colSum = C[i];

            if (colSum == 2) {
                u.append("1");
                l.append("1");
                uOccupied += 1;
            } else if (colSum == 1) {
                if (uOccupied >= U) {
                    l.append("1");
                    u.append("0");
                } else {
                    u.append("1");
                    l.append("0");
                    uOccupied += 1;
                }
            } else {
                u.append("0");
                l.append("0");
            }
        }

        return u.toString().concat(",").concat(l.toString());
    }
}
