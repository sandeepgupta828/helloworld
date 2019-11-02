package com.programs.ksubsequences;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

// https://leetcode.com/problems/subarray-sums-divisible-by-k/solution/

/**
 * This is much better algorithm
 */
public class KSubArray {

    public static List<List<Integer>> findAllKSubArrays(int divisor, List<Integer> numberList) {
        List<Integer> prefixSumList = new ArrayList<>();
        //compute the prefix sum array
        prefixSumList.add(numberList.get(0));
        for (int i = 1; i < numberList.size(); i++) {
            prefixSumList.add(numberList.get(i) + prefixSumList.get(i - 1));
        }
        // any sub array sum (i,j) (both inclusive) can be computed by prefixSum[j] - prefixSum[i-1] (if i-1 >=0 or 0) with j > i;
        // also PS[j]-PS[i-1] modulo k == 0 => subarray is divisible by k
        // => PS[j] modulo k == PS[i-1] modulo k is the condition for subarray to be qualified

        // so let's computer prefix sum list modulo k

        List<Integer> prefixSumListModulo = prefixSumList.stream().map(s -> s % divisor).collect(Collectors.toList());

        // now prefix sum with same values from 0 to K-1 => arrays between them are eligible
        // gather count of prefix sums which are same

        int[] count = new int[divisor - 1];
        for (int i = 0; i < prefixSumListModulo.size(); i++) {
            count[prefixSumListModulo.get(i)]++;
        }

        // if count = n it => in nC2 ways we can choose 2 arrays
        // to calculate number of such array we do summation of nC2 which is n(n-1)/2
        int countOfSubArrays = 0;
        for (int i = 0; i < count.length; i++) {
            countOfSubArrays += (count[i] * count[i]) / 2;
        }
        //return countOfSubArrays;

        //====

        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < prefixSumListModulo.size(); i++) {
            map.putIfAbsent(prefixSumListModulo.get(i), new ArrayList<>());
            map.get(prefixSumListModulo.get(i)).add(i);
        }

        List<List<Integer>> allSubArrays = new ArrayList<>();
        map.values().forEach(indexList -> {
            if (indexList.size() > 1) {
                List<List<Integer>> twoCombinations = getCombinations(indexList, 2);
                twoCombinations.stream().forEach(combPair -> allSubArrays.add(numberList.subList(combPair.get(0), combPair.get(1))));
            }
        });
        return allSubArrays;
    }

    /**
     * 0,5,7 ==> 0,5 : 0,7, 5,7
     * @return
     */
    private static List<List<Integer>> getCombinations(List<Integer> list, int combSize) {
        List<List<Integer>> listOfCombinations = new ArrayList<>();
        for (int i = 0;i<list.size();i++) {
            int currentNum = list.get(i);
            List<Integer> singleNumList = new ArrayList<>();
            singleNumList.add(currentNum);
            List<List<Integer>> listOfNewCombinations = new ArrayList<>();
            listOfNewCombinations.add(singleNumList);
            for (List combination: listOfCombinations) {
                List<Integer> newCombList = new ArrayList<>();
                newCombList.addAll(combination);
                newCombList.add(currentNum);
                listOfNewCombinations.add(newCombList);
            }
            listOfCombinations.addAll(listOfNewCombinations);
        }
        return listOfCombinations.stream().filter(comb -> comb.size() == combSize).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(KSubArray.findAllKSubArrays(5, Arrays.asList(5, 10, 11, 9, 5)));
    }
}
