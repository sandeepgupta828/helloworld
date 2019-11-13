package com.programs.processtream;/*


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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ProcessStream {

    private static final Long WINDOW_MS = 60000L;
    private static final Long TIME_DISTANCE_WINDOW_MS = 5000L;

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
            Thread.sleep((long)Math.random()*5000);
            return new Pair<Long, Integer>(lastTimestamp + (long)Math.random()*10*1000, (int)Math.random()*100);
        }
    }

    synchronized public void cleanUpExpiredEntries(List<List<Pair<Long, Pair<Long, Integer>>>> sourceLists) {
        Long now = System.currentTimeMillis();
        sourceLists.forEach( sourceList -> {
            List<Pair<Long, Pair<Long, Integer>>> expiredEntries = sourceList.stream().filter(pair -> (now - pair.getKey()) > WINDOW_MS).collect(Collectors.toList());
            sourceList.removeAll(expiredEntries);
        });
    }

    // thread run function.
    public void readFromSource(Source s) {
        try {
            Pair<Long, Integer> val = s.get(); // blocking call
            processAsCallback(s, val);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    synchronized public void processAsCallback(Source s, Pair<Long, Integer> curPair) {

        // all the logic to process..
        Long now = System.currentTimeMillis();
        if (s.name == "A") {
            s1List.add(new Pair<>(now, curPair));
            // scan s2 list for matching pair within T.
            s2List.stream().forEach(val -> {
                Pair<Long, Integer> pair = val.getValue();
                if (Math.abs(pair.getKey() - pair.getKey()) <= TIME_DISTANCE_WINDOW_MS) {
                    System.out.println("Matched:"+ pair + ", " + curPair + " "+Math.abs(pair.getValue() - curPair.getValue()));
                }
            });
        }

        //.. same logic for s2
        if (s.name == "B") {
            s2List.add(new Pair<>(now, curPair));
            // scan s2 list for matching pair within T.
            s1List.stream().forEach(val -> {
                Pair<Long, Integer> pair = val.getValue();
                if (Math.abs(pair.getKey() - pair.getKey()) <= TIME_DISTANCE_WINDOW_MS) {
                    System.out.println("Matched:"+ pair + ", " + curPair + " "+Math.abs(pair.getValue() - curPair.getValue()));
                }
            });
        }
    }

    public static void main(String[] args) {

        // S1 & S2.
        Source s1 = new Source("A");
        Source s2 = new Source("B");


        ProcessStream processStream = new ProcessStream();

        Thread readS1 = new Thread( () -> processStream.readFromSource(s1));
        Thread readS2 = new Thread( () -> processStream.readFromSource(s2));
        readS1.start();
        readS2.start();

        // timer thread
        List<List<Pair<Long, Pair<Long, Integer>>>> sourceLists = new ArrayList<>();
        sourceLists.add(processStream.s1List);
        sourceLists.add(processStream.s2List);
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(() -> processStream.cleanUpExpiredEntries(sourceLists), 1, 10, TimeUnit.SECONDS);

    }

}