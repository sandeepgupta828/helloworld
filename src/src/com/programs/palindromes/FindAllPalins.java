package com.programs.palindromes;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import static com.programs.palindromes.FindMinSpanningIntervals.*;
import static com.programs.palindromes.FindMinSpanningIntervals.findMinSpanningInterval;

/**
 * Given a string, split it into as few strings as possible
 * such that each string is a palindrome.
 *
 * For example, given the input string racecarannakayak,
 * return ["racecar", "anna", "kayak"].
 *
 * Given the input string abc, return ["a", "b", "c"].
 */


/*
  approach: (1) find all palindromes with size > 1 in the string, add them to result
            (2) subtract them from the string
            (3) remaining string characters are 1 character palindromes, add them to result
 */

public class FindAllPalins {

    public static class Palindrome {
        public Palindrome(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public boolean contains(Palindrome palindrome) {
            return this.start <= palindrome.start && this.end >= palindrome.end;
        }

        int start;
        int end;
    }

    public static List<String> getAllPalindromes(String input) {

        List<String> result = new ArrayList<>();
        PriorityQueue<Palindrome> priorityQueue = new PriorityQueue<>((p1, p2) -> (p2.end-p2.start) - (p1.end -p1.start));

        char[] charArr = input.toCharArray();

        boolean isPalindromeAtLastCh = false;
        int startIndexOfLastPalindrome = -1;
        int endIndexOfLastPalindrome = -1;
        boolean isCharRepeated =false;
        char repeatedChar = 0;
        for (int i=0; i<charArr.length;i++) {
            char ch = charArr[i];
            if (isPalindromeAtLastCh && (startIndexOfLastPalindrome -1) >= 0 && charArr[startIndexOfLastPalindrome-1] == ch) {
                // this ch extends the palindrome that exists at last ch
                startIndexOfLastPalindrome = startIndexOfLastPalindrome -1;
                endIndexOfLastPalindrome = i;
                isPalindromeAtLastCh = true;
                if (isCharRepeated && ch != repeatedChar) {
                    isCharRepeated = false;
                }
            } else if (isPalindromeAtLastCh && isCharRepeated && charArr[startIndexOfLastPalindrome] == ch) {
                endIndexOfLastPalindrome = i;
                isPalindromeAtLastCh = true;
            } else {
                if (isPalindromeAtLastCh) {
                    addPalindrome(priorityQueue, startIndexOfLastPalindrome, endIndexOfLastPalindrome);
                    addPalindrome(priorityQueue, i, i);
                    isPalindromeAtLastCh = false;
                } else {
                    if (i > 0 && charArr[i - 1] == ch) {
                        // this ch starts a new palindrome (even length >= 2)
                        startIndexOfLastPalindrome = i - 1;
                        endIndexOfLastPalindrome = i;
                        isPalindromeAtLastCh = true;
                        isCharRepeated = true;
                        repeatedChar = ch;
                    } else if (i > 1 && charArr[i - 2] == ch) {
                        // this ch starts a new palindrome (odd length >= 3)
                        startIndexOfLastPalindrome = i - 2;
                        endIndexOfLastPalindrome = i;
                        isPalindromeAtLastCh = true;
                        if (charArr[i-1] == ch) {
                            isCharRepeated = true;
                            repeatedChar = ch;
                        }
                    } else {
                        // this ch starts a new palindrome length == 1
                        addPalindrome(priorityQueue, i, i);
                    }
                }
            }
        }
        // handle the last palindrome match
        if (isPalindromeAtLastCh) {
            addPalindrome(priorityQueue, startIndexOfLastPalindrome, endIndexOfLastPalindrome);
        }
        List<Interval> intervalList = new ArrayList<>();

        //priorityQueue.stream().forEach(palindrome -> result.add(input.substring(palindrome.start, palindrome.end+1)));

        priorityQueue.stream().forEach(palindrome -> intervalList.add(new Interval(palindrome.start, palindrome.end)));
        List<Interval> minIntervalList = findMinSpanningInterval(new Interval(0, input.length()-1), intervalList);
        minIntervalList.stream().forEach(minInterval -> result.add(input.substring(minInterval.start, minInterval.end+1)));

        return result;
    }

    private static void addPalindrome(PriorityQueue<Palindrome> priorityQueue, int startIndexOfLastPalindrome, int endIndexOfLastPalindrome) {
        Palindrome pal = new Palindrome(startIndexOfLastPalindrome, endIndexOfLastPalindrome);
        priorityQueue.removeAll(priorityQueue.stream().filter(palindrome -> pal.contains(palindrome)).collect(Collectors.toList()));
        priorityQueue.add(pal);
    }

    public static void main(String[] args) {
        System.out.println(getAllPalindromes("aaaxxxbbbxxxccxxxbbb"));
    }
}

