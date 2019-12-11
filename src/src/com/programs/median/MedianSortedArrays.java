package com.programs.median;

//https://leetcode.com/problems/median-of-two-sorted-arrays/
public class MedianSortedArrays {
    public static void main(String[] args) {
        // System.out.println(getMedian(new int[]{1, 2}, 0, 1));
        System.out.println(findMedian(new int[] {1, 2, 3, 4, 5, 6, 7, 8}, 0, 7, new int[] {9, 10, 11, 12, 13, 14}, 0, 5));
    }

    /**
     * Key algorithm: Remove *equal* amounts of numbers from both arrays using the median of arrays
     * This causes equal reduction in the size of arrays on either side of median for which we need to find the combined median
     * Ultimately one or both the arrays are reduced to size 1
     * Then calculate the median as
     * If only 1 of arrays is size 1, merge it into another another (logN) and calculate the median
     * If both are size 1, calculate the average of two numbers
     * @param ar1
     * @param ar2
     * @return
     */
    public static double findMedian(int[] ar1, int s1, int e1, int[] ar2, int s2, int e2) {
        // steps
        /*
        handle base cases:
        if either array is size = 1, merge it with other array and return the median of merged array
        else evaluate medians and recurse:
        m1 = find median of ar1
        m2 = find median of ar2
        l1 = length of ar1
        l2 = length of ar2

        1. m1 < m2 : recurse on subarrays with indexes

            (a) l1 < l2 (we remove l1/2 elements from both arrays)
            ar1: li=ceil(l1/2), hi=l1-1
            ar2: li=0, hi=l2-ceil(l1/2)-1

            (b) l1 > l2 (we remove l2/2 elements from both arrays)
            ar1: li=floor(l2/2), hi=l1-1
            ar2: li=0, hi=floor(l2/2)-1

            (c) l1 == l2 == l
            ar1: li=ceil(l/2), hi=l-1
            ar2: li=0, hi=floor(l/2)-1


        2. do opposite of (1)
        3. m1 == m2 (return m1 as median)
         */

        int l1 = e1-s1+1;
        int l2 = e2-s2+1;

        if (l1 == 1 && l2 == 1) {
            return getMedian(new int[] {ar1[s1], ar2[s2]}, 0, 1);
        }

        if (l1 == 1) {
            return getExtMedian(ar2, s2, e2, ar1[s1]);
        }
        if (l2 == 1) {
            return getExtMedian(ar1, s1, e1, ar2[s2]);
        }

        double m1 = getMedian(ar1, s1, e1);
        double m2 = getMedian(ar2, s2, e2);


        if (m1 < m2) {
            return recurse(ar1, s1, e1, ar2, s2, e2);
        } else {
            return recurse(ar2, s1, e1, ar1, s2, e2);
        }
    }

    private static double recurse(int[] ar1, int s1, int e1, int[] ar2, int s2, int e2) {
        int l1 = e1-s1+1;
        int l2 = e2-s2+1;
        if (l1 < l2) {
            int countRemoveC = (int)Math.ceil((double)l1/2);
            int nS1 = s1+countRemoveC;
            int nE1 = e1;
            int nS2 = 0;
            int nE2 = e2 - countRemoveC;
            return findMedian(ar1, nS1, nE1, ar2, nS2, nE2);
        } else if (l2 < l1){
            int countRemoveC = (int)Math.ceil((double) l2/2);
            int nS1 = s1+countRemoveC;
            int nE1 = e1;
            int nS2 = s2;
            int nE2 = e2 - countRemoveC;
            return findMedian(ar1, nS1, nE1, ar2, nS2, nE2);
        } else {
            int countRemoveC = (int)Math.ceil((double)l1/2);
            int nS1 = s1+countRemoveC;
            int nE1 = e1;
            int nS2 = s2;
            int nE2 = e2-countRemoveC;
            return findMedian(ar1, nS1, nE1, ar2, nS2, nE2);
        }
    }


    public static double getMedian(int[] ar, int s, int e) {
        int arLength = e-s+1;
        if (arLength == 1) {
            return ar[s];
        }
        int midIndex = s+((e-s+1)/2);
        if (arLength %2 == 0) {
            // even length
            try {
                return ((double) (ar[midIndex - 1] + ar[midIndex])) / 2;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw ex;
            }
        } else {
            // odd length
            return (ar[midIndex]);
        }
    }

    /**
     * 123 --> 1234
     * 1234 --> 12345
     *
     * 123 --> 0123
     * 1234 --> 01234
     * @param ar
     * @param number
     * @return
     */
    public static double getExtMedian(int[] ar, int s, int e, int number) {
        double median = getMedian(ar, s, e);
        int arLength = e-s+1;
        int midIndex = s+ (e-s+1)/2;
        if (number > median) {
            if (arLength %2 == 0) { // ar is even length
                return (ar[midIndex]);
            } else { // ar is odd length
                try {
                    return ((double) (ar[midIndex] + ar[midIndex + 1]) / 2);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    throw ex;
                }
            }
        } else if (number < median){
            if (arLength %2 == 0) { // ar is even length
                return (ar[midIndex]);
            } else {
                return ((double)(ar[midIndex-1] + ar[midIndex])/2);
            }
        } else {
            return median;
        }

    }
}
