package com.programs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinCoinChange {

    public static void main(String[] args) {
        // create obj and call method
        MinCoinChange obj = new MinCoinChange();
        System.out.println(obj.computeMinCoins(Arrays.asList(1, 7, 17, 18), 52));
    }


    private Map<Integer, List<Integer>> cache = new HashMap();

    public List<Integer> computeMinCoins(List<Integer> coins, Integer sum) {
        if (cache.containsKey(sum)) {
            return cache.get(sum);
        }
        if (sum.equals(0)) {
            return new ArrayList<>();
        }
        List<Integer> minLengthSolution = new ArrayList<>();
        Integer minLength = -1;
        for (Integer coin: coins) {
            if (coin <= sum) {
                List<Integer> sol = computeMinCoins(coins, sum - coin);
                if (minLength < 0 || minLength > sol.size()+1) {
                    minLengthSolution = new ArrayList<>(sol);
                    minLengthSolution.add(coin);
                    minLength = minLengthSolution.size();
                }
            }
        }
        cache.put(sum, minLengthSolution);
        return minLengthSolution;
    }
}
