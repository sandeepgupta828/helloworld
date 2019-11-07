package com.programs.computestats;

import java.util.HashMap;
import java.util.Map;

public class ComputeStats {
    int windowSize;
    double[] values;
    int lastIndex;

    public ComputeStats(int window) {
        this.windowSize = window;
        this.values = new double[window];
        this.lastIndex = 0;
    }

    public void consume(double value) {
        // code to write to rr array
        values[lastIndex % windowSize] = value;
        lastIndex++;
    }

    public double getMean() {
        // code to calc mean
        double meanSum = 0.0;
        for (int i = 0; i < windowSize && i < lastIndex; i++) {
            meanSum += this.values[i];
        }
        return meanSum / Math.min(windowSize, lastIndex);
    }

    public double getMode() {
        // calculate freq
        Double maxFreqValue = null;
        int maxCount = 0;
        Map<Double, Integer> map = new HashMap();
        for (int i = 0; i < windowSize && i < lastIndex; i++) {
            Integer currentCount = map.get(values[i]);
            if (currentCount == null) {
                map.put(values[i], 1);
            } else {
                map.put(values[i], currentCount + 1);
            }
            if (maxCount < map.get(values[i])) {
                maxCount = map.get(values[i]);
                maxFreqValue = values[i];
            }
        }
        return maxFreqValue;
    }

    // window: 3, values: 1,2,3,4,5
    // window: 4  values : 1,1,2,2,3

    public static void main(String args[]) {

        ComputeStats computeStats = new ComputeStats(4);
        computeStats.consume(1);
        System.out.println(computeStats.getMode());

        computeStats.consume(1);
        System.out.println(computeStats.getMode());

        computeStats.consume(2);
        System.out.println(computeStats.getMode());

        computeStats.consume(2);
        System.out.println(computeStats.getMode());

        computeStats.consume(3);
        System.out.println(computeStats.getMode());

    }

}
