package com.andymur.pg.java.hackerrank;

import java.util.Arrays;

public class FairIndexRunner {

    public static void main(String[] args) {
        final FairIndexRunner fairIndexRunner = new FairIndexRunner();
        int result = fairIndexRunner.solution(new int[] {2, -2, -3, 3}, new int[] {0, 0, 4, -4});
        System.out.println(result);
    }

    public int solution(int[] a, int[] b) {
        int result = 0;
        int n = a.length;

        int sumA = Arrays.stream(a).sum();
        int sumB = Arrays.stream(b).sum();

        int leftSumA = 0;
        int leftSumB = 0;

        int rightSumA = sumA;
        int rightSumB = sumB;

        for (int i = 0; i < n - 1; i++) {
            leftSumA += a[i];
            leftSumB += b[i];

            rightSumA -= a[i];
            rightSumB -= b[i];

            if (isFair(leftSumA, leftSumB, rightSumA, rightSumB)) {
                result += 1;
            }
        }

        return result;
    }

    private static boolean isFair(int leftSumA, int leftSumB, int rightSumA, int rightSumB) {
        return leftSumA == leftSumB && rightSumA == rightSumB;
    }
}
