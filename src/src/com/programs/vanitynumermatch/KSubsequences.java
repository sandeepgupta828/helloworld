package com.programs.vanitynumermatch;

import java.util.*;

public class KSubsequences {
    /** Code incomplete
     * Algorithm
     * This is DP problem
     * And can be solved by keeping a list of k sequences that end at each number in the array.
     * The next number (n+1) checks itself for a subsequence, and then looks back at the sequences
     * ending at previous indexes to build the complete list of subsequences.
     * @param k
     * @param nums
     * @return
     */
    public static long kSub(int k, List<Integer> nums) {
        // Write your code here
        Map<Integer, List<List<Integer>>> listOfSequencesEndingAtIndex = new HashMap<>();
        for (int i=0;i<nums.size();i++) {
            List<List<Integer>> listOfSequences = new ArrayList<>();
            listOfSequencesEndingAtIndex.put(i, listOfSequences);
            // look at the current number
            if (nums.get(i) % k == 0) {
                List<Integer> list = new ArrayList();
                list.add(nums.get(i));
                listOfSequences.add(list);
            }
            int sum = nums.get(i);
            // look at past sequences
            for (int j = i-1; j >=0;j--) {
                if(sum + nums.get(j) % k == 0) {

                }
                if (listOfSequences.get(nums.get(j)).size() > 0) {

                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(KSubsequences.kSub(5, Arrays.asList(5, 10, 11, 9, 5)));
    }
}
