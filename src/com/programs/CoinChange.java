package com.programs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CoinChange {
// http://www.geeksforgeeks.org/dynamic-programming-set-7-coin-change/
/*
Given a value N, if we want to make change for N cents, and we have infinite supply of each of S = { S1, S2, .. , Sm} valued coins, how many ways can we make the change? The order of coins doesn’t matter.

For example, for N = 4 and S = {1,2,3}, there are four solutions: {1,1,1,1},{1,1,2},{2,2},{1,3}. So output should be 4. For N = 10 and S = {2, 5, 3, 6}, there are five solutions: {2,2,2,2,2}, {2,2,3,3}, {2,2,6}, {2,3,5} and {5,5}. So the output should be 5.
 */

private Map<Integer, Set<Integer>> cachedSolutions = new HashMap();

public Integer numberOfSolutions(Integer coinValue, List<Integer> coins) {
    List<Integer> filteredCoins = coins.stream().filter(den ->den <= coinValue).collect(Collectors.toList());
    if (coinValue > 0 && filteredCoins.size() > 0) {
        return filteredCoins.stream().mapToInt(fCoin -> {
            Integer solutionsWithoutThisCoin = numberOfSolutions(coinValue, filteredCoins.stream().filter(d -> !d.equals(fCoin)).collect(Collectors.toList()));
            System.out.println("Value="+Integer.toString(coinValue)+", coin="+fCoin+", solutions="+solutionsWithoutThisCoin);
            Integer solutionsWithThisCoin = 0;
            if (coinValue-fCoin > 0) {
                solutionsWithThisCoin = numberOfSolutions(coinValue - fCoin, filteredCoins);
            } else if (coinValue-fCoin == 0) {
                solutionsWithThisCoin = 1;
            }
            System.out.println("Value="+Integer.toString(coinValue-fCoin)+", coin="+fCoin+", solutions="+solutionsWithThisCoin);
            return solutionsWithoutThisCoin + solutionsWithThisCoin;
        }).sum();
    }
    return 0;
}

    public static void main(String[] args) {

        CoinChange coinChange = new CoinChange();
        Integer[] values = {1, 2, 3};
        System.out.println("Number of solutions: "+ coinChange.numberOfSolutions(4, Arrays.asList(values))) ;
    }
}
