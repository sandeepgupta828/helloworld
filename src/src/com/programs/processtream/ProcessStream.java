package com.programs.processtream;/*

Given two sources that you read data from

Reading data from sources is blocking call.

Write a processor that processes events from these 2 sources to compute difference in values and print it out
if events match the criteria of being within a certain time distance

Events are produced as:

Source1: (t1, v1), (t2, v2) ....events are ordered by monotonically increasing time t
Source2: (t1, v1), (t2, v2) ....events are ordered by monotonically increasing time t

class Stream {
  public Tuple<double, int> get();
}


A: (1, 4), (4, 2), ...
B: (2, 4), (6, 1), ...


T: 5


output abs(v1 - v2), if abs(t1 - t2) < T

block on S1: get() = (t1, v1)...

on getting the value: compare to S2 list to see if there are matches, and add to output.
then: add to S1 list at the end.

block on S2: get() = (t2, v2)...

on getting the value: compare to S1 list to see if there are matches, and add to output.
then: add to S2 list at the end.


compare step:

if (dist(t1 ~ t2) <= T) {
// add to result/print
}
else {
preseve the value until some window of time W

expire after the values after W window

keep them in a list S1 list, S2 list

}


*/


import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Read data from 2 sources in parallel
 * As data is available process it
 * processing is synchronized
 * Data events are not kept forever, but within a configurable sliding window within which events enter at an IN time, and are removed when the time had advanced to IN+WindowLen
 */
public class ProcessStream {

    private static final Long SLIDING_WINDOW_MS = 4000L;
    private static final Long TIME_DISTANCE_MS = 2000L;

    List<Pair<Long, Pair<Long, Integer>>> s1List = new ArrayList<>();
    List<Pair<Long, Pair<Long, Integer>>> s2List = new ArrayList<>();

    public static class Source {
        private String name;
        private Long lastTimestamp = System.currentTimeMillis();

        public Source(String name) {
            this.name = name;
        }

        public Pair<Long, Integer> get() throws InterruptedException {
            // some way to get the pair
            lastTimestamp += (long) (Math.random() * 10) * 1000;
            return new Pair(lastTimestamp, (int) (Math.random() * 100));
        }
    }

    synchronized public void cleanUpExpiredEntries(List<List<Pair<Long, Pair<Long, Integer>>>> sourceLists) {
        Long now = System.currentTimeMillis();
        sourceLists.forEach(sourceList -> {
            List<Pair<Long, Pair<Long, Integer>>> expiredEntries = sourceList.stream().filter(pair -> (now - pair.getKey()) > SLIDING_WINDOW_MS).collect(Collectors.toList());
            System.out.println("Removing from source: "+expiredEntries);
            sourceList.removeAll(expiredEntries);
        });
    }

    // thread run function.
    public void readFromSource(Source source) {
        try {
            Pair<Long, Integer> val = source.get(); // blocking call
            //System.out.println("Source:"+source.name+":"+val.toString());
            processAsCallback(source, val);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    synchronized public void processAsCallback(Source source, Pair<Long, Integer> curPair) {

        // all the logic to process..
        Long now = System.currentTimeMillis();
        List<Pair<Long, Pair<Long, Integer>>> sourceList = null;
        List<Pair<Long, Pair<Long, Integer>>> otherSourceList = null;

        switch (source.name) {
            case "A":
                sourceList = s1List;
                otherSourceList = s2List;
                break;
            case "B":
                sourceList = s2List;
                otherSourceList = s1List;
                break;
        }

        sourceList.add(new Pair<>(now, curPair));
        otherSourceList.stream().forEach(val -> {
            Pair<Long, Integer> pair = val.getValue();
            if (Math.abs(pair.getKey() - curPair.getKey()) <= TIME_DISTANCE_MS) {
                System.out.println("Matched:"+source.name+":" + curPair.toString() + ", " + pair.toString() + " " + Math.abs(pair.getValue() - curPair.getValue()));
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {

        // create S1 & S2.
        Source s1 = new Source("A");
        Source s2 = new Source("B");


        ProcessStream processStream = new ProcessStream();

        // source reader 1 (reading from source1 every sec)
        ScheduledThreadPoolExecutor sourceReader1Executor = new ScheduledThreadPoolExecutor(1);
        sourceReader1Executor.scheduleAtFixedRate(() -> processStream.readFromSource(s1), 0, 1, TimeUnit.SECONDS);

        // source reader 2 (reading from source2 every sec)
        ScheduledThreadPoolExecutor sourceReader2Executor = new ScheduledThreadPoolExecutor(1);
        sourceReader2Executor.scheduleAtFixedRate(() -> processStream.readFromSource(s2), 0, 1, TimeUnit.SECONDS);

        // timer thread to expire entries every sec
        List<List<Pair<Long, Pair<Long, Integer>>>> sourceLists = new ArrayList<>();
        sourceLists.add(processStream.s1List);
        sourceLists.add(processStream.s2List);
        ScheduledThreadPoolExecutor timerExecutor = new ScheduledThreadPoolExecutor(1);
        timerExecutor.scheduleAtFixedRate(() -> processStream.cleanUpExpiredEntries(sourceLists), 0, 1, TimeUnit.SECONDS);

    }

}