package com.andymur.pg.java.genestack;

/**
 * Created by andymur on 6/17/17.
 */
public class CountHolesRunner {
    public static void main(String[] args) {
        System.out.println(countHoles(630));
    }

    static int countHoles(int num) {
        int remainder = num;
        int result = 0;
        while(remainder > 0) {
            int digit = remainder % 10;
            remainder = remainder / 10;

            if (digit == 0 || digit == 4 || digit == 6 || digit == 9) {
                result += 1;
            } else if (digit == 8) {
                result += 2;
            }
        }
        return result;
    }
}
