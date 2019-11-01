package com.programs.missingwords;

import java.util.*;

public class MissingWords {
    /*
     * Complete the 'missingWords' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts following parameters:
     *  1. STRING s
     *  2. STRING t
     */

    public static List<String> missingWords(String s, String t) {
        // Write your code here
        if (Objects.nonNull(s) && Objects.nonNull(t)) {
            Set<String> primaryWordSet = new LinkedHashSet<>();
            for (String word : s.split(" ")) {
                primaryWordSet.add(word);
            }
            Set<String> secondaryWordSet = new LinkedHashSet<>();
            for (String word : t.split(" ")) {
                secondaryWordSet.add(word);
            }
            if (!primaryWordSet.containsAll(secondaryWordSet)) {
                throw new RuntimeException("t is not a subsequence of s");
            }
            // compute the difference
            primaryWordSet.removeAll(secondaryWordSet);
            return new ArrayList<>(primaryWordSet);
        }
        return Collections.emptyList();
    }

    public static void main(String[] args) {
        System.out.println(MissingWords.missingWords("I am using HackerRank to improve programming", "am HackerRank to improve"));
    }

}
