package com.programs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Anagrams {

    public static void main(String[] args) {
        String[] input = {"dog", "cat", "atc", "god", "godess", "good"};
        Anagrams anagrams = new Anagrams();
        Map<String, List<String>> output = anagrams.findAnagrams(Arrays.asList(input));
        System.out.println(output);
    }


    public Map<String, List<String>> findAnagrams(List<String> wordList) {

        Map<String, List<String>> map = new HashMap();

        for (String word : wordList) {
            char[] charArray = word.toCharArray();
            Arrays.sort(charArray);
            String sortedWord = new String(charArray);

            List<String> existingAnagrams = map.get(sortedWord);
            if (existingAnagrams != null) {
                existingAnagrams.add(word);
            } else {
                List<String> list = new ArrayList();
                list.add(word);
                map.put(sortedWord, list);
            }
        }
        return map;
    }
}


