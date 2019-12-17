package com.programs.palindromes;

import java.util.*;

//https://leetcode.com/explore/interview/card/google/64/dynamic-programming-4/451/
public class LongestPalindrome {

    public static void main(String[] args) {
        System.out.println(findLongestPalindrome("abccbaoabccbaxabccbao")); // abccbaoabccbaxabccbao

    }

    public static String findLongestPalindrome(String str) {
        /*
        algo: using DP i.e. process characters incrementally to come up with solution using previous solutions

        record longest palindrome start index, end index so far

        scan the characters from left to right
        on each character:
        --keep a record of index of a prev character(s) where a palindrome starts and ends at this character (length > 1)
        --check if this character extends palindrome ending at last character: record prev character index
          palindrome is extended by 2 if a prev index exists where character is same as this character OR
          it may also extend by 1 if palindrome consists of all same characters
        --check if this character starts a new palindrome (length > 1: 2/3 i.e. even or odd) : record prev character index with longer palindrome

         */


        Map<Integer, Set<Integer>> mapIndexToPrevPIndexes = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (i > 0) {
                // check if any P ends at prev character and this character extends it
                Set<Integer> prevChPIndexes = mapIndexToPrevPIndexes.getOrDefault(i - 1, new HashSet<>());
                if (prevChPIndexes.size() > 0) {
                    for (int prevIndex : prevChPIndexes) {
                        if (prevIndex - 1 >= 0 && str.charAt(prevIndex - 1) == ch) {
                            addPIndex(mapIndexToPrevPIndexes, i, prevIndex - 1);
                        }
                    }
                }
                // check if this character starts a new P (even or odd)
                // odd
                if (i > 1 && str.charAt(i - 2) == ch) {
                    addPIndex(mapIndexToPrevPIndexes, i, i - 2);
                }
                // even
                if (str.charAt(i - 1) == ch) {
                    addPIndex(mapIndexToPrevPIndexes, i, i - 1);
                }
            }
        }

        int start = -1;
        int end = -1;
        int maxLength = 0;

        for (Map.Entry<Integer, Set<Integer>> entry: mapIndexToPrevPIndexes.entrySet()) {
            int minIndex = entry.getValue().stream().min((n1, n2) -> n1-n2).get();
            if ((entry.getKey()-minIndex+1) > maxLength) {
                maxLength = entry.getKey()-minIndex+1;
                start = minIndex;
                end = entry.getKey();
            }
        }
        return str.substring(start, end+1);
    }

    private static void addPIndex(Map<Integer, Set<Integer>> mapIndexToPrevPIndexes, int i, int pos) {
        Set<Integer> indexes = mapIndexToPrevPIndexes.getOrDefault(i, new HashSet<>());
        indexes.add(pos);
        mapIndexToPrevPIndexes.put(i, indexes);
    }
}
