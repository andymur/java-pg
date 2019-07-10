package com.andymur.pg.java.genestack;

import java.util.*;

/**
 * Created by andymur on 6/18/17.
 */
public class GemStonesRunner {

    public static void main(String[] args) {
        List<Set<Character>> uniqueElementsOfRocks = new ArrayList<>();

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        in.nextLine();
        for (int i = 0; i < n; i++) {
            String nextRock = in.nextLine();
            uniqueElementsOfRocks.add(uniqueElements(nextRock));
        }
        in.close();

        System.out.println(gemNum(uniqueElementsOfRocks));
    }

    static int gemNum(List<Set<Character>> uniqueElementsOfRocks) {

        if (uniqueElementsOfRocks.size() == 1) {
            return uniqueElementsOfRocks.get(0).size();
        }

        Set<Character> finalSet = new HashSet<>(uniqueElementsOfRocks.get(0));

        for (Set<Character> next: uniqueElementsOfRocks.subList(1, uniqueElementsOfRocks.size())) {
            finalSet.retainAll(next);

            if (finalSet.isEmpty()) {
                return 0;
            }
        }

        return finalSet.size();
    }

    static Set<Character> uniqueElements(String rock) {
        Set<Character> result = new HashSet<>();
        for (Character character: rock.toCharArray()) {
            result.add(character);
        }

        return result;
    }
}
