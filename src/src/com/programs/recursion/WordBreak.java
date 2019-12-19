package com.programs.recursion;

import java.util.*;

/**
 * Use recursion with memoization
 */
//https://leetcode.com/problems/word-break-ii/
public class WordBreak {
    public static void main(String[] args) {
        Map<String, List<String>> cache = new HashMap<>();
        System.out.println(breakIntoSentences("catsanddog", new HashSet<>(Arrays.asList("cat", "cats", "and", "sand", "dog")), cache));
        System.out.println(breakIntoSentences("pineapplepenapple", new HashSet<>(Arrays.asList("apple", "pen", "applepen", "pine", "pineapple")), cache));

    }

    public static List<String> breakIntoSentences(String str, Set<String> dictionary, Map<String, List<String>> cache) {
        if (cache.containsKey(str)) {
            return cache.get(str);
        }
        List<String> sentences = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            String possibleWord = null;
            possibleWord = str.substring(0, i+1);
            if (dictionary.contains(possibleWord)) {
                String remainingStr = str.substring(i+1);
                //recurse on the remaining part of string
                if (remainingStr.length() > 0) {
                    List<String> possibleSentences = breakIntoSentences(remainingStr, dictionary, cache);
                    if (possibleSentences.size() > 0) {
                        for (String sentence : possibleSentences) {
                            sentences.add(possibleWord + " " + sentence);
                        }
                    }
                } else {
                    sentences.add(possibleWord);
                }
            }
        }
        cache.put(str, sentences);
        return sentences;
    }
}
