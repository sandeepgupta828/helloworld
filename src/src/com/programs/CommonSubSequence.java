package com.programs;

import java.util.*;
import java.util.stream.Collectors;

public class CommonSubSequence {

    /**
     * algo
     * <p>
     * brute force: find all subsequences of str1, find all subsequences of str2, filter the ones that are equal.
     * <p>
     * or bottom up?
     * <p>
     * create a matrix of str(i) x str(j) where (i,j) == 1 if str(i) == str(j)
     * maintain a result per (i,j)
     * next iterate over each row in this matrix:
     * if val = 1 then:
     * (1) add the character to result at (i,j)
     * (2) if i > 0
     * check in prev row (which has at least a match), pick the greatest match < j
     * add to result of (i,j) all the results (prev i, prev j), with character at i appended.
     *
     * @param str1
     * @param str2
     * @return
     */
    public static Set<String> getCommonSubSequences(String str1, String str2) {
        int[][] matrix = getMatchingMatrix(str1, str2);
        List<String>[][] result = new List[str2.length()][str1.length()];

        for (int i = 0; i < str2.length(); i++) {
            for (int j = 0; j < str1.length(); j++) {
                if (matrix[i][j] == 1) {
                    char matchingChar = str2.charAt(i);
                    result[i][j] = new ArrayList<>();
                    result[i][j].add("" + str1.charAt(j));
                    // go back to previous rows and extend matching subsequences with this new match
                    int prevRow = i-1;
                    while (prevRow >=0) {
                        for (int k=j-1;k>=0;k--) {
                            if (matrix[prevRow][k] == 1) {
                                result[i][j].addAll(result[prevRow][k].stream().map(seq -> seq+matchingChar).collect(Collectors.toList()));
                                break;
                            }
                        }
                        prevRow--;
                    }
                }
            }
        }

        Set<String> totalResult = new HashSet<>();
        for (int i = 0; i < str2.length(); i++) {
            for (int j = str1.length()-1; j >=0 ; j--) {
                if (matrix[i][j] == 1) {
                    totalResult.addAll(result[i][j]);
                    break;
                }
            }
        }
        return totalResult;
    }

    private static int[][] getMatchingMatrix(String str1, String str2) {
        int[][] matrix = new int[str2.length()][str1.length()];
        for (int i = 0; i < str2.length(); i++) {
            for (int j = 0; j < str1.length(); j++) {
                if (str1.charAt(j) == str2.charAt(i)) {
                    matrix[i][j] = 1;
                } else {
                    matrix[i][j] = 0;
                }
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
        System.out.println(CommonSubSequence.getCommonSubSequences("abfabcad", "fbadec"));
    }
}
