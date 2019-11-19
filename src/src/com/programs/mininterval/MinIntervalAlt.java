package com.programs.mininterval;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinIntervalAlt {
    public static Pair<Integer, Integer> findMinInterval(List<List<Integer>> listOfArrays) {
        return null;
    }


    public static void main(String[] args) {
        List<List<Integer>> listOfArrays = new ArrayList<>();
        listOfArrays.add(Arrays.asList(new Integer[]{5, 7, 8, 19}));
        listOfArrays.add(Arrays.asList(new Integer[]{8, 17, 18, 30})); // => 8-7
        listOfArrays.add(Arrays.asList(new Integer[]{1, 9, 22}));
        listOfArrays.add(Arrays.asList(new Integer[]{9, 14, 20, 27}));

        System.out.println(findMinInterval(listOfArrays));
    }
}
