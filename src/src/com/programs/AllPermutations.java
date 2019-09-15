package com.programs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllPermutations {
    public static void main(String[] argv) {
        AllPermutations allPermutations = new AllPermutations();
        System.out.println(allPermutations.computeAllPermutations("1234"));
    }

    private List<String> computeAllPermutations(String str) {
        List<String> lastPermutations = new ArrayList<>();
        char[] strChars = str.toCharArray();
        for (int i=0;i<strChars.length;i++) {
            char ch = strChars[i];
            List<String> curPermutations = new ArrayList<>();
            if (lastPermutations.size() > 0) {
                for (String permutation: lastPermutations) {
                    curPermutations.addAll(getNewPermutations(ch, permutation));
                }
            } else {
                curPermutations.add(ch+"");
            }
            lastPermutations = curPermutations;
        }
        Collections.sort(lastPermutations, Comparator.comparingInt(Integer::parseInt));
        return lastPermutations;
    }

    private List<String> getNewPermutations(char ch, String permutation) {
        List<String> newPermutations = new ArrayList<>();
        newPermutations.add(ch+permutation);
        if (permutation.length() > 1) {
            for (int i = 0; i < permutation.length(); i++) {
                newPermutations.add(permutation.substring(0, i+1) + ch + permutation.substring(i+1, permutation.length()));
            }
        } else {
            newPermutations.add(permutation+ch);
        }
        return newPermutations;
    }
}
