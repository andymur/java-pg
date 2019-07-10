package com.andymur.pg.java.genestack;

import java.util.Arrays;

/**
 * Created by andymur on 6/17/17.
 */
public class OddNumbers {

    public static void main(String[] args) {
        int ar[] = oddNumbers(2, 5);

        for (int i = 0; i < ar.length; i++) {
            System.out.print(i);
            System.out.print(" ");
        }
    }

    static int[] oddNumbers(int l, int r) {
        int[] oddNumbers = new int[r - l];
        int j = 0;
        for (int i = l; i <= r; i++) {
            if (i % 2 != 0) {
                oddNumbers[j] = i;
                j++;
            }
        }

        int[] result = new int[j];
        System.arraycopy(oddNumbers, 0, result, 0, j);
        /*for (int i = 0; i< result.length; i++) {
            result[i] = oddNumbers[i];
        }*/

        return result;
    }

}
