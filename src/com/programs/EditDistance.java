package com.programs;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class EditDistance {

    public static void main(String[] argv) {
        EditDistance editDistance = new EditDistance();
        System.out.println(editDistance.findEditDistance("oheysandy", "sandeepop"));
    }

    /*  0123456
        sandeep
       h       1d+7a
-------------------
       e    45 1d+6a
-------------------
       y       2d+6a
-------------------
       s0      3d+6a
-------------------
       a 1     4d+6a/3d+5a
-------------------
       n  2
       d   3
       y       1d

       deletions = 3

     */

    public Integer findEditDistance(String s1, String s2) {

        char[] s1Chars = s1.toCharArray();
        char[] s2Chars = s2.toCharArray();

        Map<Character, List<Integer>> charToIndexes = new HashMap<>();
        IntStream.range(0, s1Chars.length).forEach(i -> {
            List<Integer> indexes = charToIndexes.get(s1Chars[i]);
            if (indexes == null) {
                indexes = new ArrayList<>();
            }
            indexes.add(i);
            charToIndexes.put(s1Chars[i], indexes);
        });

        Integer[] indexOfMatch = new Integer[s2Chars.length]; // init to -1
        IntStream.range(0, indexOfMatch.length).forEach(i -> indexOfMatch[i] = -1);

        Integer[] indexOfPrevMatch = new Integer[s2Chars.length]; // init to -1
        IntStream.range(0, indexOfPrevMatch.length).forEach(i -> indexOfPrevMatch[i] = -1);

        Integer[] lengthOfMatch = new Integer[s2Chars.length];
        IntStream.range(0, lengthOfMatch.length).forEach(i -> lengthOfMatch[i] = -1);
        Integer indexOfMaxMatchS2 = -1;
        Integer maxLengthMatch = -1;

        for (int i = 0; i < s2Chars.length; i++) {
            if (charToIndexes.containsKey(s2Chars[i])) {
                // there is a match
                indexOfMatch[i] = charToIndexes.get(s2Chars[i]).get(0);
                lengthOfMatch[i] = 1;
                Integer maxLengthOfPrevMatch = 0;
                for (int j = i - 1; j >= 0; j--) {
                    if (indexOfMatch[j] < indexOfMatch[i] && lengthOfMatch[j] > maxLengthOfPrevMatch) {
                        indexOfPrevMatch[i] = j;
                        maxLengthOfPrevMatch = lengthOfMatch[j];
                    }
                }
                if (indexOfPrevMatch[i] >= 0) {
                    lengthOfMatch[i] = lengthOfMatch[indexOfPrevMatch[i]] + 1;
                } else {
                    lengthOfMatch[i] = 1;
                }
                if (lengthOfMatch[i] > maxLengthMatch) {
                    maxLengthMatch = lengthOfMatch[i];
                    indexOfMaxMatchS2 = i;
                }
            }
        }

        Integer editDistance = 0;
        if (indexOfMaxMatchS2 > 0) {
            Integer matchIndexS2 = indexOfMaxMatchS2;
            Integer limitIndexS1 = s1Chars.length;
            Integer limitIndexS2 = s2Chars.length;
            Integer matchIndexS1 = indexOfMatch[indexOfMaxMatchS2];
            while (limitIndexS1 >=0 && limitIndexS2 >=0) {
                editDistance += findChars(matchIndexS1, limitIndexS1, matchIndexS2, limitIndexS2);
                limitIndexS1 = matchIndexS1;
                limitIndexS2 = matchIndexS2;
                if (matchIndexS2 >= 0) {
                    matchIndexS2 = indexOfPrevMatch[matchIndexS2];
                    if (matchIndexS2 >= 0) {
                        matchIndexS1 = indexOfMatch[matchIndexS2];
                    } else {
                        matchIndexS1 = -1;
                    }
                }
            }
        } else {
            editDistance = s1Chars.length + s2Chars.length;
        }

        return editDistance;
    }

    Integer findChars(Integer matchIndexS1, Integer limitIndexS1, Integer matchIndexS2, Integer limitIndexS2) {
        Integer excessCharsS1 = limitIndexS1 - matchIndexS1-1;
        Integer excessCharsS2 = limitIndexS2 - matchIndexS2-1;
        return Math.min(excessCharsS1, excessCharsS2) + Math.abs(excessCharsS1-excessCharsS2);
    }
}
