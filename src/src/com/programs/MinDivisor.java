package com.programs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Find minimum divisor that divides a list of numbers such that result of summing all divisions after rounding off
 * is <= a given threshold
 */
public class MinDivisor {

    public static int minimumDivisor(List<Integer> arr, int threshold) {
        int minDivisor = 1;
        // find minimum number to start division by
        Optional<Integer> sumArr = arr.stream().reduce((a, b) -> a+b);
        if (sumArr.isPresent()) {
            int sum = sumArr.get();
            minDivisor = Math.round((float) sum/threshold);
        }

        // now minDivisor should be greater than or equal to above

        // we try minDivisor calculated as above first
        int totalSum;
        //int totalSum = threshold+1; // set to some value greater than threshold
        do {
            totalSum = getTotalSum(arr, minDivisor);
            if (totalSum > threshold) {
                minDivisor += 1;
            }
        } while(totalSum > threshold);

        return minDivisor;
    }

    private static int getTotalSum(List<Integer> arr, int minDivisor) {
        int totalSum = 0;
        for (Integer element: arr) {
            totalSum += Math.round((float)element/minDivisor);
        }
        return totalSum;
    }

    public static void main(String[] args) {
        MinDivisor minDivisor = new MinDivisor();
        System.out.println(minDivisor.minimumDivisor(Arrays.asList(2, 4,5 ), 10));
    }
}
