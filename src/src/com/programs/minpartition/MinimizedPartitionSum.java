package com.programs.minpartition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//https://www.geeksforgeeks.org/allocate-minimum-number-pages/
public class MinimizedPartitionSum {
    public static void main(String[] args) {
        System.out.println(getMinPartitions(new int[]{15, 20, 30, 34, 50, 55, 60}, 7));
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

     each iteration see if
     adjacent left partition has higher sum
     if not: we are good, our initial partition assignment is good! with the min sum value as max value.
     if yes:
     we can move the left adjacent partition boundary such that new sum of two partitions is smaller than previous sums
     essentially shift elements to partitions on right
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
                int sumPrevPartition = getPartitionSum(valuesSum, partitionIndexes, i - 1);
                int sumCurPartition = getPartitionSum(valuesSum, partitionIndexes, i);
                int maxSum;
                if (sumPrevPartition > sumCurPartition) {
                    maxSum = sumPrevPartition;
                    // see if partition boundary can be moved that lowers the "max" sum these 2 adjacent partitions
                    for (int k = partitionIndexes[i - 1][1]; k >= partitionIndexes[i - 1][0]; k--) {
                        if ((sumCurPartition + values[k]) < maxSum) {
                            sumCurPartition += values[k];
                            sumPrevPartition -= values[k];
                            // acquire values
                            partitionIndexes[i - 1][1] -= 1;
                            partitionIndexes[i][0] -= 1;
                            paritionsUpdated = true;
                        } else {
                            break;
                        }
                    }
                }
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

    private static int getPartitionSum(int[] valuesSum, int[][] partitionIndexes, int partition) {
        return valuesSum[partitionIndexes[partition][1] + 1] - valuesSum[partitionIndexes[partition][0]];
    }
}
