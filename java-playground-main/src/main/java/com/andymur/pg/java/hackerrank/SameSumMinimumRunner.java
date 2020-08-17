package com.andymur.pg.java.hackerrank;

import java.util.ArrayList;
import java.util.List;

public class SameSumMinimumRunner {
    private static final int NUMBER = 734;

    public static void main(String[] args) {
        final SameSumMinimumRunner sameSumMinimumRunner = new SameSumMinimumRunner();
        System.out.println(sameSumMinimumRunner.solution(NUMBER));
    }

    public int solution(int n) {
        final int originalSum = digitsSum(n);
        int i = n + 1;
        for (; i <= 50000; i++) {
            final int sum = digitsSum(i);
            if (sum == originalSum) {
                break;
            }
        }
        return i;
    }

    private static int digitsSum(int number) {
        return digits(number).stream().reduce(Integer::sum).orElse(0);
    }

    private static List<Integer> digits(int number) {
        List<Integer> digits = new ArrayList<>();
        while (number > 0) {
            digits.add(number % 10);
            number /= 10;
        }
        return digits;
    }
}
