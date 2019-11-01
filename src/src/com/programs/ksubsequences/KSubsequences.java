package com.programs.ksubsequences;

import java.util.*;
import java.util.stream.Collectors;

public class KSubsequences {
    /**
     * Problem: find all subarrays whose sum is divisible by divisor "k"
     * Algorithm
     * evaluate next number
     * sum numbers starting from this number (i) and going back in array until we hit a sum at number (j) which is divisible by divisor k
     * if found such a sum, add the list of numbers from j to i as a qualifying sequence ending at this number.
     * also check if previous number (previous to j) had qualifying sequences ending on it, if yes those can be suffixed with the new sequence
     * the last part is basically DP (build on previous solution state)
     *
     * @param divisor
     * @param numberList
     * @return
     */
    public static int kSub(int divisor, List<Integer> numberList) {
        // Write your code here
        Map<Integer, List<List<Integer>>> listOfSequencesEndingAtIndex = new HashMap<>();
        for (int i = 0; i < numberList.size(); i++) {
            listOfSequencesEndingAtIndex.put(i, new ArrayList<>());
        }
        for (int i = 0; i < numberList.size(); i++) {
            int sum = 0;
            // go back and see if a sequence of previous numbers + this number is divisible by this divisor
            for (int j = i; j >= 0; j--) {
                sum += numberList.get(j);
                if (sum % divisor == 0) {
                    List<Integer> seq = numberList.subList(j, i+1);
                    listOfSequencesEndingAtIndex.get(i).add(seq);
                    if (j - 1 >= 0 && listOfSequencesEndingAtIndex.get(j - 1).size() > 0) {
                        // we can stop here, and append sequences ending at j-1 to this new sequence
                        List<List<Integer>> suffixedSequences = listOfSequencesEndingAtIndex.get(j - 1).stream().map(sequence -> {
                            List<Integer> suffixedSeq = new ArrayList<>();
                            suffixedSeq.addAll(sequence);
                            suffixedSeq.addAll(seq);
                            return suffixedSeq;
                        }).collect(Collectors.toList());
                        listOfSequencesEndingAtIndex.get(i).addAll(suffixedSequences);
                        break;
                    }
                }
            }
        }
        //return listOfSequencesEndingAtIndex;
        return listOfSequencesEndingAtIndex.values().stream().map(val -> val.size()).reduce((a, b) -> a+b).get();
    }


    public static void main(String[] args) {
        System.out.println(KSubsequences.kSub(5, Arrays.asList(5, 10, 11, 9, 5)));
        //System.out.println(KSubsequences.kSub(5, Arrays.asList(2, 5, 3, 10)));
        //System.out.println(KSubsequences.kSub(5, Arrays.asList(10, 10, 10, 1, 1, 1, 1, 1)));

    }
}
