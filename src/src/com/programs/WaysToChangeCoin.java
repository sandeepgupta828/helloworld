package com.programs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class WaysToChangeCoin {

    private Map<Integer, Set<List<Integer>>> cache = new HashMap<>();

    public static void main(String[] args) {
        WaysToChangeCoin waysToChangeCoin = new WaysToChangeCoin();
        Set<List<Integer>> set = waysToChangeCoin.change(10, Arrays.asList(new Integer[]{1, 2, 3, 5, 10, 20}));
        System.out.println(set);
    }

    public Set<List<Integer>> change(Integer amount, List<Integer> coins) {
        if (cache.containsKey(amount)) return cache.get(amount);
        Set<List<Integer>> coinSetsForAmount = new HashSet<>();
        for (int coin : coins) {
            if (coin <= amount) {
                Integer subAmount = amount - coin;
                if (subAmount > 0) {
                    Set<List<Integer>> coinListsForSubAmount = change(subAmount, coins);
                    Set<List<Integer>> updatedCoinLists = coinListsForSubAmount.stream().map(coinList -> {
                                List<Integer> newCoinList = new ArrayList();
                                newCoinList.add(coin);
                                newCoinList.addAll(coinList);
                                Collections.sort(newCoinList);
                                return newCoinList;
                            }
                    ).collect(Collectors.toSet());
                    coinSetsForAmount.addAll(updatedCoinLists);
                } else {
                    List<Integer> coinList = new ArrayList<>();
                    coinList.add(coin);
                    coinSetsForAmount.add(coinList);
                }
            }
        }
        cache.put(amount, coinSetsForAmount);
        return coinSetsForAmount;
    }

}
