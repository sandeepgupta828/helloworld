package com.programs.palindromes;

import java.util.ArrayList;
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

    public static List<Interval> findMinSpanningInterval(Interval totalSpan, List<Interval> spans) {
        List<Interval>[] intervalListArr = new List[spans.size()];
        PriorityQueue<List<Interval>> priorityQueue = new PriorityQueue<List<Interval>>(((list1, list2) -> list1.size() - list2.size()));
        if (spans == null || spans.isEmpty()) {
            List<Interval> intervalList = new ArrayList<Interval>();
            addIntervals(totalSpan, null, intervalList);
            priorityQueue.add(intervalList);
        }
        for (int i = 0; i < spans.size(); i++) {
            intervalListArr[i] = new ArrayList<Interval>();
            if (i == 0) {
                // if this is first span
                addIntervals(totalSpan, spans.get(i), intervalListArr[i]);
            } else {
                // look at intervals of a previous span that doesn't overlap with this span
                int scanIntervalsIndex = i - 1;
                while (scanIntervalsIndex >= 0) {
                    if (!spans.get(i).overlap(spans.get(scanIntervalsIndex))) {
                        break;
                    }
                    scanIntervalsIndex--;
                }
                if (scanIntervalsIndex >= 0) {
                    List<Interval> intervalList = intervalListArr[scanIntervalsIndex];
                    boolean added = false;

                    List<Interval> newList = new ArrayList<>();

                    for (int k = 0; k < intervalList.size(); k++) {
                        if (!spans.get(i).overlap(intervalList.get(k))) {
                            newList.add(intervalList.get(k));
                        } else if (!added) {
                            newList.add(spans.get(i));
                            added = true;
                        } else {
                            continue;
                        }
                    }
                    //
                    List<Interval> finalList = new ArrayList<>();
                    Interval firstInterval = newList.get(0);
                    if (firstInterval.start > totalSpan.start) {
                        addIntervals(new Interval(totalSpan.start, firstInterval.start-1), null, finalList);
                    }
                    for (int h=0;h<newList.size();h++) {
                        Interval interval = newList.get(h);
                        finalList.add(interval);
                        if ((h+1) <newList.size()) {
                            Interval nextInterval = newList.get(h + 1);
                            if ((nextInterval.start - interval.end) > 1) {
                                addIntervals(new Interval(interval.end+1, nextInterval.start-1), null, finalList);
                            }
                        }
                    }
                    if (newList.size() > 0) {
                        Interval lastInterval = newList.get(newList.size()-1);
                        if (lastInterval.end < totalSpan.end) {
                            addIntervals(new Interval(lastInterval.end+1, totalSpan.end), null, finalList);
                        }
                    }
                    intervalListArr[i] = finalList;
                } else {
                    addIntervals(totalSpan, spans.get(i), intervalListArr[i]);
                }
            }
            priorityQueue.add(intervalListArr[i]);
        }
        return priorityQueue.peek();
    }

    private static void addIntervals(Interval totalSpan, Interval interval, List<Interval> intervals) {
        if (interval != null) {
            for (int k = totalSpan.start; k < interval.start; k++) {
                intervals.add(new Interval(k, k));
            }
            intervals.add(interval);
            for (int k = (interval.end + 1); k <= totalSpan.end; k++) {
                intervals.add(new Interval(k, k));
            }
        } else {
            for (int k = totalSpan.start; k <= totalSpan.end; k++) {
                intervals.add(new Interval(k, k));
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
        spanList.add(new Interval(0, 2));
        spanList.add(new Interval(3, 11));
        System.out.println(findMinSpanningInterval(new Interval(0, 19), spanList));
    }
}
