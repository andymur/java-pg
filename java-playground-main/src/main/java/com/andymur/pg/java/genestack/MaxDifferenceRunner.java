package com.andymur.pg.java.genestack;

/**
 * Created by andymur on 6/17/17.
 */
public class MaxDifferenceRunner {

    public static void main(String[] args) {
        int[] a = new int[] {7, 9, 5, 6, 3, 2};
        //int [] a = new int[] {3, 2, 6};
        System.out.println(maxDifference(a));
    }

    static int maxDifference(int[] a) {
        int len = a.length;
        // check single element case
        if (len == 1 ) {
            return -1;
        }

        // check reversed case
        if (isReversed(a)) {
            return -1;
        }

        //check case when min index < max index
        int minIdx = findMinIndex(0, a);
        int maxIdx = findMaxIndex(0, a);

        if (minIdx < maxIdx) {
            return a[maxIdx] - a[minIdx];
        }

        int currentMaxDif = findLocalMaxDifference(a, maxIdx);

        // general solution
        while (maxIdx < len - 1) {
            int newMaxIdx = findMaxIndex(maxIdx + 1, a);
            int newMaxDif = findLocalMaxDifference(a, newMaxIdx);

            if (newMaxDif > currentMaxDif) {
                currentMaxDif = newMaxDif;
            }
            maxIdx = newMaxIdx;
        }

        return currentMaxDif;
    }

    static int findLocalMaxDifference(int[] a, int maxIdx) {
        int maxDif = -2_000_000;

        for (int i = 0; i <= maxIdx; i++) {
            if (a[maxIdx] - a[i] > maxDif) {
                maxDif = a[maxIdx] - a[i];
            }
        }

        return maxDif;
    }

    static boolean isReversed(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] < a[i+1]) {
                return false;
            }
        }

        return true;
    }

    static int findMinIndex(int startIndex, int[] a) {
        int min = 1_000_000;
        int index = -1;

        for (int i = startIndex; i < a.length; i++) {
            if (a[i] < min) {
                min = a[i];
                index = i;
            }
        }
        return index;
    }

    static int findMaxIndex(int startIndex, int[] a) {
        int max = -1_000_000;
        int index = -1;

        for (int i = startIndex; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
                index = i;
            }
        }
        return index;
    }
}
