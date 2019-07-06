package com.andymur.pg.java.testcube;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by andymur on 6/24/17.
 */
public class BruteForceRunner {
    static final int SIZE = 4;

    public static void main(String[] args) {
        System.out.println(solve(new int[]{1}, new int[]{3, 5, 4}, 1, ""));
    }

    static int[] solve(int[] combination, int[] remainder, int probe, String tab) {
        //System.out.println(String.format("%s%s | %s p = %d", tab, toString(combination), toString(remainder), probe));

        if (combination == null || combination.length == 0) {
            return null;
        }

        if (combination.length > 0 && !compliant(combination)) {
            return null;
        }

        if (combination.length == SIZE) {
            return combination;
        }

        int reminderLength = remainder.length;

        int next = remainder[0];

        int[] newCombination = Arrays.copyOf(combination, combination.length + 1);
        newCombination[newCombination.length - 1] = next;
        int[] newReminder = Arrays.copyOfRange(remainder, 1, reminderLength);

        // increase combination

        int[] solution = solve(newCombination, newReminder, 1, tab.concat("\t"));

        if (solution != null) {
            System.out.println(String.format("SOLUTION: %s", toString(solution)));
        } else {
            //System.out.println("bad sequence");
        }

        //permute combination

        while (probe <= reminderLength) {
            int[] permutedCombination = Arrays.copyOf(combination, combination.length);
            int[] permutedReminder = Arrays.copyOf(remainder, reminderLength);
            int elementToMove = permutedCombination[permutedCombination.length - 1];
            permutedCombination[permutedCombination.length - 1] = permutedReminder[probe - 1];
            permutedReminder[probe - 1] = elementToMove;
            solve(permutedCombination, permutedReminder, probe + 1, tab.concat("\t"));
            probe += 1;
        }

        return null;
    }

    static String toString(int[] arr) {
        return Arrays.stream(arr).boxed().map(i -> String.valueOf(i)).collect(Collectors.joining(", "));
    }

    /*
    public void dfs(int[] nums, List<List<Integer>> results, List<Integer> result) {
        if (nums.length == result.size()) {
            List<Integer> temp = new ArrayList<>(result);
            results.add(temp);
        }
        for (int i=0; i<nums.length; i++) {
            if (!result.contains(nums[i])) {
                result.add(nums[i]);
                dfs(nums, results, result);
                result.remove(result.size() - 1);
            }
        }
    }*/

    static boolean compliant(int[] numbers) {
        for (int i = 0; i < numbers.length - 1; i++) {

            if (Math.abs(numbers[i] - numbers[i + 1]) > 4) {
                return false;
            }
        }

        return true;
    }

}
