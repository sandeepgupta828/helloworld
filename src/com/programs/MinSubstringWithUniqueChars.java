package com.programs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

public class MinSubstringWithUniqueChars {

    public static void main(String[] args) {
        // create obj and call method
        MinSubstringWithUniqueChars minSubstringWithUniqueChars = new MinSubstringWithUniqueChars();
        System.out.println(minSubstringWithUniqueChars.findMinSubstring("xyza".toCharArray(), "xyyyayzyyzyyxxxxyyzzaxy"));
    }

    Map<Character, Integer> latestIndex = new HashMap<>();

    public String findMinSubstring(char[] chars, String str) {
        IntStream.range(0, chars.length).forEach(i -> latestIndex.put(chars[i], -1));
        Integer minIndexOverall = -1;
        Integer maxIndexOverall = -1;
        Integer minLength = 0;
        for (int i=0; i< str.length();i++) {
            Character ch = str.charAt(i);
            if (latestIndex.containsKey(ch)) {
                latestIndex.put(ch, i);
                if (latestIndex.values().stream().allMatch(v -> v >=0)) {
                    Integer minIndex = latestIndex.values().stream().min(Integer::compare).get();
                    if ((i - minIndex+1) < minLength || minLength == 0) {
                        maxIndexOverall = i;
                        minIndexOverall = minIndex;
                        minLength = maxIndexOverall - minIndexOverall+1;
                    }
                }
            }
        }
        if (minIndexOverall >= 0 && maxIndexOverall >=0) {
            return str.substring(minIndexOverall, maxIndexOverall+1);
        }
        return "";
    }


    public String findMinSubstring2(char[] chars, String str) {
        PriorityQueue<Integer> minIndexQueue = new PriorityQueue<>(chars.length);

        IntStream.range(0, chars.length).forEach(i -> latestIndex.put(chars[i], -1));
        Integer minIndexOverall = -1;
        Integer maxIndexOverall = -1;
        Integer minLength = 0;
        for (int i=0; i< str.length();i++) {
            Character ch = str.charAt(i);
            if (latestIndex.containsKey(ch)) {
                if (latestIndex.get(ch) >=0) {
                    minIndexQueue.remove(latestIndex.get(ch));
                }
                latestIndex.put(ch, i);
                minIndexQueue.add(i);
                if (minIndexQueue.size() == chars.length) {
                    Integer minIndex = minIndexQueue.peek();
                    if ((i - minIndex+1) < minLength || minLength == 0) {
                        maxIndexOverall = i;
                        minIndexOverall = minIndex;
                        minLength = maxIndexOverall - minIndexOverall+1;
                    }
                }
            }
        }
        if (minIndexOverall >= 0 && maxIndexOverall >=0) {
            return str.substring(minIndexOverall, maxIndexOverall+1);
        }
        return "";
    }
}
