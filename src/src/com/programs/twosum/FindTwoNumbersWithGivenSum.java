package com.programs.twosum;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Great problem!
 */
public class FindTwoNumbersWithGivenSum {

    public static void main(String[] args) {
        System.out.println(find(new int[] {1 , 4, 7, 9, 10}, 16));
    }

    /**
     * algo: assume arr is sorted
     * run 2 pointers from p1: start to end and from p2: end to start
     * case: ar[p1]+ar[p2]
     * == sum : happy case : return ar[p1], ar[p2]
     * > sum : decrement p2
     * < sum : increment p1
     * @param arr
     * @param sum
     * @return
     */
    public static List<Integer> find(int[] arr, int sum) {
        int p1=0;
        int p2=arr.length-1;
        while (p1 < p2) {
            int curSum = arr[p1]+arr[p2];
            if (curSum < sum) {
                p1++;
            } else if (curSum > sum) {
                p2--;
            } else {
                return Arrays.asList(arr[p1], arr[p2]);
            }
        }
        return Collections.emptyList();
    }
}
