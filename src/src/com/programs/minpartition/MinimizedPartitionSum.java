package com.programs.minpartition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//https://www.geeksforgeeks.org/allocate-minimum-number-pages/
public class MinimizedPartitionSum {
    public static void main(String[] args) {
        System.out.println(getMinPartitions(new int[]{15, 20, 30, 34, 50, 55, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70}, 4));
        //System.out.println(getMinPartitions(new int[]{12, 34, 67, 90}, 2));

    }

    /*
     create a partition assignment array
     assign initial partitions to values starting from 1 to number of partitions
     assign such that all partitions are assigned from right to left and anything remaining goes to partition 1
     e.g. 10 20 30 40 50, with 3 paritions
          1  1  2  3  4
     --that is lower numbers are grouped into parition #1
     also compute values sum of all elements

     next iterate over partitions starting from partition #2

     each iteration we evaluate 2 adjacent partitions:
     we find new boundary between 2 partitions which minimizes delta between new sum of two partitions (with new boundary)
     this sum is close to ~totalSum/2
     we repeat this until partitions continue to update to arrive a distribution where sum of each partition is minimized
     */
    public static List<List<Integer>> getMinPartitions(int[] values, int partitionCount) {
        int[] partitionAssignment = new int[values.length];
        int[][] partitionIndexes = new int[partitionCount][2];

        int[] valuesSum = new int[values.length + 1];
        // compute sum array
        valuesSum[0] = 0;
        for (int i = 1; i < valuesSum.length; i++) {
            valuesSum[i] = valuesSum[i - 1] + values[i - 1];
        }
        int curPartitionNum = partitionCount - 1;
        for (int i = 0; i < partitionAssignment.length; i++) {
            partitionAssignment[partitionAssignment.length - 1 - i] = curPartitionNum;
            partitionIndexes[curPartitionNum] = new int[2];
            if (curPartitionNum > 0) {
                partitionIndexes[curPartitionNum][0] = partitionAssignment.length - 1 - i;
                partitionIndexes[curPartitionNum][1] = partitionAssignment.length - 1 - i;
                curPartitionNum--;
            } else {
                partitionIndexes[curPartitionNum][0] = partitionAssignment.length - 1 - i;
                if (partitionCount > 1) {
                    partitionIndexes[curPartitionNum][1] = partitionIndexes[1][1] - 1;
                } else {
                    partitionIndexes[curPartitionNum][1] = partitionAssignment.length - 1;
                }
            }
        }

        // next iterate
        boolean paritionsUpdated = false;
        do {
            paritionsUpdated = false;
            for (int i = 1; i < partitionCount; i++) {
                boolean updated = adjustPartitionBoundayToMinimizeSumDistribution(values, valuesSum, partitionIndexes, i-1, i);
                paritionsUpdated = paritionsUpdated || updated;
            }
        } while (paritionsUpdated);

        List<List<Integer>> partitionLists = new ArrayList<>();
        List<Integer> partitionSums = new ArrayList<>();
        for (int i = 0; i < partitionCount; i++) {
            List<Integer> paritionValues = new ArrayList<>();
            for (int k = partitionIndexes[i][0]; k <= partitionIndexes[i][1]; k++) {
                paritionValues.add(values[k]);
            }
            partitionSums.add(valuesSum[partitionIndexes[i][1] + 1] - valuesSum[partitionIndexes[i][0]]);
            partitionLists.add(paritionValues);
        }
        System.out.println(partitionSums);
        return partitionLists;
    }

    private static boolean adjustPartitionBoundayToMinimizeSumDistribution(int[] values, int[] valuesSum, int[][] partitionIndexes, int p1, int p2) {
        int p1Boundary = partitionIndexes[p1][1];
        int midCount = 0;
        int minAbsDiff = -1;
        for (int i=partitionIndexes[p1][0];i<=partitionIndexes[p2][1];i++) {
            int sum1 = getSumBetweenIndexesInclusive(valuesSum, partitionIndexes[p1][0], i);
            int sum2 = getSumBetweenIndexesInclusive(valuesSum, i+1, partitionIndexes[p2][1]);
            int diff = Math.abs(sum1-sum2);
            if (minAbsDiff < 0) {
                minAbsDiff = Math.abs(sum1-sum2);
                midCount++;
            } else if (diff <= minAbsDiff){
                minAbsDiff = diff;
                midCount++;
            } else {
                break;
            }
        }
        if (p1Boundary != (partitionIndexes[p1][0]+midCount-1)) {
            // set new boundary
            partitionIndexes[p1][1] = partitionIndexes[p1][0]+midCount-1;
            partitionIndexes[p2][0] = partitionIndexes[p1][0]+midCount;
            return true;
        }
        return false;
    }

    private static int getSumBetweenIndexesInclusive(int[] valuesSum, int i1, int i2) {
        return valuesSum[i2+1] - valuesSum[i1];
    }

    private static int getPartitionSum(int[] valuesSum, int[][] partitionIndexes, int partition) {
        return valuesSum[partitionIndexes[partition][1] + 1] - valuesSum[partitionIndexes[partition][0]];
    }
}
