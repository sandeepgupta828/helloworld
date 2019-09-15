package com.programs;

import java.util.PriorityQueue;

/**
 * Created by sagupta on 11/3/15.
 */
public class ComputePercentile {

    Heap minHeap = new Heap(Heap.HeapType.MIN);
    Heap maxHeap = new Heap(Heap.HeapType.MAX);

    Integer percentile; //1-100
    Integer totalElements = 0;
    Integer percentileValue = null;

    void setPercentile(int percentile) {
        this.percentile = percentile;
    }

    void addElements(int[] elements) {

        for (int i=0;i<elements.length;i++) {
            ++totalElements;
            if (percentileValue == null) {
                percentileValue = elements[i];
            }
            // route the element to right heap bucket
            if (elements[i] <= percentileValue) {
                maxHeap.add(elements[i]);
            } else {
                minHeap.add(elements[i]);
            }

            // now adjust the bucket sizes to be in proportion
            float fractionInMaxHeap = ((float)maxHeap.size()/totalElements);
            float expectedFractionMaxHeap = (float)percentile/100;

            float fractionInMinHeap = ((float)minHeap.size()/totalElements);
            float expectedFractionMinHeap = 1 - (float)percentile/100;

            if (fractionInMaxHeap > expectedFractionMaxHeap) {
                minHeap.add(maxHeap.peek());
                maxHeap.remove();
            } else if (fractionInMinHeap > expectedFractionMinHeap) {
                maxHeap.add(minHeap.peek());
                minHeap.remove();
            }

            if (minHeap.peek() != null && maxHeap.peek() != null) {
                percentileValue = (minHeap.peek() + maxHeap.peek())/2;
            } else if (maxHeap.peek() != null) {
                percentileValue = maxHeap.peek();
            } else if (minHeap.peek() != null) {
                percentileValue = minHeap.peek();
            }
        }
        System.out.println("MaxHeapPeek="+maxHeap.peek());
        System.out.println("MinHeapPeek=" + minHeap.peek());
        System.out.println("PercentileValue="+percentileValue);
    }
}
