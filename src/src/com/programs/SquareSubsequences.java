package com.programs;

import java.util.ArrayList;
import java.util.List;

public class SquareSubsequences {
    /**
     * algo:
     *
     * is there brute force?
     *
     * find all subsequences, and test each for square attribute
     * all subsequences = 2^n (where n is length of string)
     * list of all: recursive method
     * square test: even length n%/2=0, check for equality of two substrings i.e. str.substring(0,n/2).equals(str.substring(n/2,n);
     *
     * can DP be applied?
     *
     * b: [b] 0
     * ba: [b,ba,a] 0
     * bab: [b,a,ba] X b = [b,a,ba,bb,ab,bab,b] : bb
     * baba: [b,a,ba,bb,ab,bab,b] X a = [b,a,ba,bb,ab,bab,b,ba,aa,bab,bba,aba,baba,ba] 3 bb,baba,aa
     * ....
     * @param inputStr
     * @return
     */
    public static List<String> countSquareSubSequences(String inputStr) {
        List<String> squareSubSequences = new ArrayList<>();
        List<String> subSequences = new ArrayList<>();
        for (int i=0;i<inputStr.length();i++) {
            List<String> newSubSequences = new ArrayList<>();
            for (String oldSeq: subSequences) {
                String newSubSequence = oldSeq+inputStr.charAt(i);
                newSubSequences.add(newSubSequence);
                if (isSquare(newSubSequence)) {
                    squareSubSequences.add(newSubSequence);
                }
            }
            newSubSequences.add(""+inputStr.charAt(i));
            subSequences.addAll(newSubSequences);
        }
        return squareSubSequences;
    }

    private static boolean isSquare(String str) {
        if (str.length() %2 !=0) return false;
        for (int i=0;i<str.length()/2;i++) {
            if (str.charAt(i) != str.charAt(i+(str.length()/2))) return false;
        }
        return true;
    }
    public static void main(String[] args) {
        System.out.println(SquareSubsequences.countSquareSubSequences("baaba"));
    }
}
