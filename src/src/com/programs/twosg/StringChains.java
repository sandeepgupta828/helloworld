package com.programs.twosg;

import java.util.*;

public class StringChains {
    public static  Map<String, Integer> longestChain(List<String> stringWords) {
        // create set
        Set<String> wordSet = new HashSet<>(stringWords);
        // create a list without any duplicates
        List<String> wordList = new ArrayList<>(wordSet);

        Set<Integer> stringSizes = new HashSet<>();
        Map<Integer, List<String>> mapSizeToStrings = new HashMap<>();

        // group strings by size
        for (int i=0;i<wordList.size();i++) {
            List<String> strings = mapSizeToStrings.getOrDefault(wordList.get(i).length(), new ArrayList<>());
            strings.add(wordList.get(i));
            stringSizes.add(wordList.get(i).length());
            mapSizeToStrings.put(wordList.get(i).length(), strings);
        }

        // map to record chain length for a given string
        Map<String, Integer> stringToChainLength = new HashMap<>();

        // sort on sizes
        List<Integer> sortedStringSizes = new ArrayList(stringSizes);
        sortedStringSizes.sort((a,b) -> a-b);
        // process strings with smaller size first
        // build bottom up, DP pattern
        for (Integer size: sortedStringSizes) {
            // go over all strings for this size
            for (String str: mapSizeToStrings.get(size)) {
                // remove every character from string and see if the resulting string is present in the wordSet
                // if yes check for character deleted string's chain length and add +1 to it to compute a possible chain for this string
                // if this chain length is greater than we have seen so far record it.

                // initialize chain length to 1 for this str
                stringToChainLength.put(str, 1);

                // go over all character deleted variations
                for (int index=0;index < str.length();index++) {
                    String characterDeletedStr = getCharacterDeletedString(str, index);
                    if (wordSet.contains(characterDeletedStr)) {
                        // set chain length if we are seeing a higher chain length
                        // since we are building bottom up we would always find chain length of character deleted str
                        stringToChainLength.put(str, Math.max(stringToChainLength.get(characterDeletedStr)+1, stringToChainLength.get(str)));
                    }
                }
            }
        }
        if (stringToChainLength.size() > 0) {
            //return stringToChainLength.values().stream().max((a, b) -> a - b).get();
        }
        //return -1;
        return stringToChainLength;
    }

    /**
     * delete a character at given index from given str
     * @param str
     * @param index
     * @return
     */
    private static String getCharacterDeletedString(String str, int index) {
        String characterDeletedStr;
        if (index >= str.length()) {
            throw new RuntimeException("Invalid index");
        }
        if (index == 0) {
            characterDeletedStr = str.substring(1);
        } else if (index == str.length()-1) {
            characterDeletedStr = str.substring(0, index);
        } else {
            characterDeletedStr = str.substring(0, index) + str.substring(index+1);
        }
        return characterDeletedStr;
    }

    public static void main(String[] args) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("a");
        arrayList.add("b");
        arrayList.add("ba");
        arrayList.add("bca");
        arrayList.add("bda");
        arrayList.add("bdca");
        arrayList.add("bdcax");
        arrayList.add("bdcax");
        System.out.println(longestChain(arrayList));
    }
}
