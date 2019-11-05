package com.programs;

import java.util.*;
import java.util.stream.Collectors;

public class CommonSubSequence {

    /**
     * algo
     * <p>
     * brute force: find all subsequences of str1, find all subsequences of str2, filter the ones that are equal.
     * <p>
     * DP solution:
     * <p>
     * maintain a result per (i,j)
     * next iterate over each row in this matrix:
     * if str1(j) matches with str2(i) add the character to result at (i,j)
     * now this match could extend all previous matches that happened for < i, < j
     * check all prev rows and pick the result for match in greatest j for a given i (as a character at i, could match multiple js)
     * (we pick greatest j as it captures all results that are smaller than it)
     * then append matching character at i,j to result (all subsequences) stored at a matching (prev i, prev j)
     *
     * @param str1
     * @param str2
     * @return
     */
    public static Set<String> getCommonSubSequences(String str1, String str2) {
        List<String>[][] result = new List[str2.length()][str1.length()];

        for (int i = 0; i < str2.length(); i++) {
            for (int j = 0; j < str1.length(); j++) {
                if (str1.charAt(j) == str2.charAt(i)) {
                    char matchingChar = str2.charAt(i);
                    result[i][j] = new ArrayList<>();
                    // add this char as subsequence
                    result[i][j].add("" + matchingChar);
                    // go back to previous rows and extend subsequences with this new matching character
                    int prevRow = i-1;
                    while (prevRow >=0) {
                        // we start from the max index in prev row < j and work back to lower indexes
                        // as higher index would capture all subsequences from a lower index as well.
                        for (int k=j-1;k>=0;k--) {
                            if (str1.charAt(k) == str2.charAt(prevRow)) {
                                result[i][j].addAll(result[prevRow][k].stream().map(seq -> seq+matchingChar).collect(Collectors.toList()));
                                // we can break here, because higher index captures subsequences from lower index
                                break;
                            }
                        }
                        prevRow--;
                    }
                    // finally result[i][j] contains all subsequences that end with matching character at str1[j], str2[i]
                }
            }
        }

        Set<String> totalResult = new HashSet<>();
        // we collect all subsequences from max indexes for each matching character row and put them in a set.
        for (int i = 0; i < str2.length(); i++) {
            for (int j = str1.length()-1; j >=0 ; j--) {
                if (str1.charAt(j) == str2.charAt(i)) {
                    totalResult.addAll(result[i][j]);
                    break;
                }
            }
        }
        return totalResult;
    }

    public static void main(String[] args) {
        System.out.println(CommonSubSequence.getCommonSubSequences("ababcad", "badec"));
    }
}
