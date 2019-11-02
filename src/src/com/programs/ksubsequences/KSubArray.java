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
        prefixSumList.add(0);
        for (Integer number: numberList) {
            prefixSumList.add(number + prefixSumList.get(prefixSumList.size()-1));
        }
        // prefix sum array has 1 additional element at the start = 0
        // now prefixSum[j] - prefixSum[i] with j > i represents a subarray sum in original array for (i, j) j exclusive
        // with prefixSum[length] representing the sum all the array
        // also PS[j]-PS[i] modulo k == 0 => subarray (i, j) is divisible by k
        // => PS[j] modulo k == PS[i-1] modulo k is the condition for subarray to be qualified

        // so let's compute prefix sum list modulo k
        List<Integer> prefixSumListModulo = prefixSumList.stream().map(s -> s % divisor).collect(Collectors.toList());

        // now prefix sum (modulo k) with same values from 0 to K-1 => arrays between them are eligible
        // gather count of prefix sums which are same

        int[] count = new int[divisor];
        for (int i = 0; i < prefixSumListModulo.size(); i++) {
            count[prefixSumListModulo.get(i)]++;
        }

        Map<Integer, List<Integer>> mapModuloValueToIndexList = new HashMap<>();
        for (int i = 0; i < prefixSumListModulo.size(); i++) {
            mapModuloValueToIndexList.putIfAbsent(prefixSumListModulo.get(i), new ArrayList<>());
            mapModuloValueToIndexList.get(prefixSumListModulo.get(i)).add(i);
        }

        List<List<Integer>> allSubArrays = new ArrayList<>();
        mapModuloValueToIndexList.forEach((key, value) -> {
            List<List<Integer>> combinations = Collections.emptyList();
            if (value.size() > 1) {
                // we'd need index pairs
                combinations = getCombinations(value, Arrays.asList(2));
            }
            // the indexes in comb list are from prefix sum/modulo list, but are applied to original list to get the sublist
            // as it nicely captures the sub lists(arrays).
            // e.g. index pair (1,3) in prefix sum/modulo k list = (sum up to 0, sum up to 2) and difference maps to sum of sublist [1,2] or [1,3) with 3 excluded t
            combinations.stream().forEach(combList -> allSubArrays.add(numberList.subList(combList.get(0), combList.get(combList.size() - 1))));
        });
        return allSubArrays;
    }

    /**
     * 0,5,7 ==> 0,5 : 0,7, 5,7
     * @return
     */
    private static List<List<Integer>> getCombinations(List<Integer> list, List<Integer> combSizeNeeded) {
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
        return listOfCombinations.stream().filter(comb -> combSizeNeeded.contains(comb.size())).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(KSubArray.findAllKSubArrays(5, Arrays.asList(5, 3, 2, 10, 11, 9, 5)));
    }
}
