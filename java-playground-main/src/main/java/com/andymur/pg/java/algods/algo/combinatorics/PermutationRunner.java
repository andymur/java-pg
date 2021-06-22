package com.andymur.pg.java.algods.algo.combinatorics;

import java.util.*;

public class PermutationRunner {

    public static void main(String[] args) {
        // (1,2), (2,1)
        System.out.println(permutations(Arrays.asList(1, 2, 3,4, 5, 6, 7, 8, 9)).size());
        System.out.println(permutations2(Arrays.asList(1, 2, 3,4, 5, 6, 7, 8, 9)).size());
    }

    private static <T> Set<List<T>> permutations(final List<T> original) {

        if (original == null || original.isEmpty()) {
            return Collections.emptySet();
        }

        if (original.size() == 1) {
            return Collections.singleton(original);
        }

        Set<List<T>> accum = new HashSet<>();
        accum.add(original);

        permutationStep(accum, new ArrayList<>(), original);
        return accum;
    }

    private static <T> void permutationStep(Set<List<T>> accum,
                                        List<T> left,
                                        List<T> remaining) {
        if (remaining.size() <= 1) {
            return;
        }

        for (int i = 0; i < remaining.size(); i++) {
            List<T> remainingCopy = new ArrayList<>(remaining);
            List<T> newLeft = new ArrayList<>(left);

            Collections.swap(remainingCopy, 0, i);

            if (i > 0) {
                List<T> newPermutation = new ArrayList<>(left);
                newPermutation.addAll(remainingCopy);
                accum.add(newPermutation);
            }

            newLeft.add(remaining.get(i));
            permutationStep(accum, newLeft, copyOf(remainingCopy, 1));
        }
    }

    private static <T> Set<List<T>> permutations2(List<T> original) {

        if (original == null || original.isEmpty()) {
            return Collections.emptySet();
        }

        if (original.size() == 1) {
            return Collections.singleton(original);
        }

        return step(original);
    }

    private static <T> Set<List<T>> step(List<T> original) {
        final Set<List<T>> result = new HashSet<>();

        if (original.size() <= 1) {
            return Collections.singleton(original);
        }

        for (int i = 0; i < original.size(); i++) {
            List<T> copy = new ArrayList<>(original);
            Collections.swap(copy, 0, i);
            final Set<List<T>> partialResult = step(copyOf(copy, 1));
            for (List<T> partialResultRecord: partialResult) {
                result.add(prepend(copy.get(0), partialResultRecord));
            }
        }

        return result;
    }

    private static <T> List<T> prepend(T element, List<T> postfix) {
        postfix.add(0, element);
        return postfix;
    }

    private static <T> List<T> copyOf(List<T> list, int from) {
        return new ArrayList<>(list.subList(from, list.size()));
    }
}
