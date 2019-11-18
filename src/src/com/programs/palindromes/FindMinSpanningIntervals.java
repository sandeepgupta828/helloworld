package com.programs.palindromes;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class FindMinSpanningIntervals {

    public static class Interval {
        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public boolean overlap(Interval interval) {
            return (interval.start >= this.start && interval.start <= this.end) || (interval.end >= this.start && interval.end <= this.end);
        }

        int start;
        int end;


        @Override
        public String toString() {
            return "[" + start +
                    "," + end +
                    "]";
        }
    }

    public static Integer[] findMinSpanningInterval(Interval totalSpan, List<Interval> spans) {
        int totalSpanLength = totalSpan.end - totalSpan.start + 1;
        Integer[][] intervalArr = new Integer[spans.size()][totalSpanLength];
        PriorityQueue<Integer[]> priorityQueue = new PriorityQueue<Integer[]>((ar1, ar2) -> ar1[ar1.length - 1] - ar2[ar2.length - 1]);
        if (spans == null || spans.isEmpty()) {
            Integer[] intervalIndex = new Integer[totalSpan.end - totalSpan.start + 1];
            addIntervalIndexes(totalSpan, null, intervalIndex);
        }
        for (int i = 0; i < spans.size(); i++) {
            Interval thisInterval = spans.get(i);
            if (i == 0) {
                // if this is first span
                addIntervalIndexes(totalSpan, thisInterval, intervalArr[i]);
            } else {
                PriorityQueue<Pair<Integer[], Integer>> priorityQueueMin = new PriorityQueue<Pair<Integer[], Integer>>((p1, p2) -> (p1.getKey()[p1.getValue()] - p1.getKey()[0]) - (p2.getKey()[p2.getValue()] - p2.getKey()[0]));
                PriorityQueue<Pair<Integer[], Integer>> priorityQueueMax = new PriorityQueue<Pair<Integer[], Integer>>((p1, p2) -> (p1.getKey()[p1.getKey().length - 1] - p1.getKey()[p1.getValue()]) - (p2.getKey()[p2.getKey().length - 1] - p2.getKey()[p2.getValue()]));
                for (int k = 0; k < i; k++) {
                    Integer[] prevIntervalIndex = intervalArr[k];
                    if (thisInterval.start > totalSpan.start && prevIntervalIndex[thisInterval.start] > prevIntervalIndex[thisInterval.start - 1]) {
                        priorityQueueMin.add(new Pair<Integer[], Integer>(prevIntervalIndex, thisInterval.start - 1 - totalSpan.start));
                    }
                    if (thisInterval.end < totalSpan.end && prevIntervalIndex[thisInterval.end + 1] > prevIntervalIndex[thisInterval.end]) {
                        priorityQueueMax.add(new Pair<Integer[], Integer>(prevIntervalIndex, thisInterval.end + 1 - totalSpan.start));
                    }
                }
                int startInterval = 0;
                if (priorityQueueMin.size() > 0) {
                    Pair<Integer[], Integer> pair = priorityQueueMin.peek();
                    Integer[] minInterval = pair.getKey();
                    System.arraycopy(minInterval, 0, intervalArr[i], 0, pair.getValue() + 1);
                    startInterval = intervalArr[i][pair.getValue()];
                } else {
                    for (int k = 0; k < (thisInterval.start - totalSpan.start); k++) {
                        intervalArr[i][k] = ++startInterval;
                    }
                }
                ++startInterval;
                for (int k = (thisInterval.start - totalSpan.start); k <= (thisInterval.end - totalSpan.start); k++) {
                    intervalArr[i][k] = startInterval;
                }
                if (priorityQueueMax.size() > 0) {
                    Integer[] maxInterval = priorityQueueMax.peek().getKey();
                    Pair<Integer[], Integer> pair = priorityQueueMax.peek();
                    System.arraycopy(maxInterval, pair.getValue(), intervalArr[i], pair.getValue(), totalSpanLength - pair.getValue());
                    int lastVal = intervalArr[i][pair.getValue()];
                    intervalArr[i][pair.getValue()] = ++startInterval;
                    for (int j = pair.getValue() + 1; j < totalSpanLength; j++) {
                        // compare it to the last one
                        if (intervalArr[i][j] == lastVal) {
                            lastVal = intervalArr[i][j];
                            intervalArr[i][j] = startInterval;
                        } else {
                            lastVal = intervalArr[i][j];
                            intervalArr[i][j] = ++startInterval;
                        }
                    }
                } else {
                    for (int k = (thisInterval.end + 1 - totalSpan.start); k < totalSpanLength; k++) {
                        intervalArr[i][k] = ++startInterval;
                    }
                }
            }
            priorityQueue.add(intervalArr[i]);
        }
        return priorityQueue.peek();
    }

    private static void addIntervalIndexes(Interval totalSpan, Interval interval, Integer[] intervalIndex) {
        int startInterval = 0;
        if (interval != null) {
            for (int k = 0; k < (interval.start - totalSpan.start); k++) {
                intervalIndex[k] = ++startInterval;
            }
            ++startInterval;
            for (int k = (interval.start - totalSpan.start); k <= (interval.end - totalSpan.start); k++) {
                intervalIndex[k] = startInterval;
            }
            for (int k = (interval.end + 1 - totalSpan.start); k <= (totalSpan.end - totalSpan.start); k++) {
                intervalIndex[k] = ++startInterval;
            }
        } else {
            for (int k = 0; k <= (totalSpan.end - totalSpan.start); k++) {
                intervalIndex[k] = ++startInterval;
            }
        }
    }

    public static void main(String[] args) {
        List<Interval> spanList = new ArrayList<>();
        /*
        spanList.add(new Interval(1, 10));
        spanList.add(new Interval(2, 5));
        spanList.add(new Interval(1, 3));
        spanList.add(new Interval(1, 5));
        spanList.add(new Interval(4, 9));
        spanList.add(new Interval(7, 10));
        spanList.add(new Interval(6, 10));

         */
        spanList.add(new Interval(6, 19));
        spanList.add(new Interval(1, 17));
        spanList.add(new Interval(0, 2));
        spanList.add(new Interval(0, 19));

        System.out.println(Arrays.asList(findMinSpanningInterval(new Interval(0, 19), spanList)));
    }
}
