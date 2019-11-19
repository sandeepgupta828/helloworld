package com.programs.mininterval;

import javafx.util.Pair;

import java.util.*;

public class MinInterval {

    public static class Entry {
        String arId;
        Integer val;

        public Entry(String arId, Integer val) {
            this.arId = arId;
            this.val = val;
        }
    }

    public static Pair<Integer, Integer> findMinInterval(List<List<Integer>> listOfArrays) {
        List<Entry> entryList = new ArrayList<>();

        // merge all the arrays integers into a single list, and map each integer to an entry obj having an integer and array id
        for (int i = 0; i < listOfArrays.size(); i++) {
            String arId = Integer.valueOf(i).toString();
            for (int k = 0; k < listOfArrays.get(i).size(); k++) {
                entryList.add(new Entry(arId, listOfArrays.get(i).get(k)));
            }
        }
        // sort the entries
        entryList.sort((e1, e2) -> e1.val - e2.val);

        // window is between start, and current index and is defined as completed when window has entries from all arrays
        int start = 0;

        int minStart = -1;
        int minEnd = -1;

        Map<String, Boolean> isArrPresent = new HashMap<>();

        for (int i = 0; i < entryList.size(); i++) {
            Entry entry = entryList.get(i);
            Boolean wasPresent = isArrPresent.get(entry.arId);
            if (wasPresent == null || !wasPresent) {
                isArrPresent.put(entry.arId, true);
            } else {
                while (entryList.get(start).arId == entry.arId) start++;
            }
            if (isArrPresent.size() == listOfArrays.size()) { // window is complete
                // entries from all arrays are seen, now we have an interval where all arrays have entries i.e. [start, i]
                // we update min indexes to track minimum such interval
                if ((minStart < 0 && minEnd < 0) || (minStart > 0 && minEnd > 0 && (minEnd - minStart) > (i - start))) {
                    minStart = start;
                    minEnd = i;
                }
            }
        }

        return new Pair<>(entryList.get(minStart).val, entryList.get(minEnd).val);

    }

    public static void main(String[] args) {
        List<List<Integer>> listOfArrays = new ArrayList<>();
        listOfArrays.add(Arrays.asList(new Integer[]{5, 7, 8, 19}));
        listOfArrays.add(Arrays.asList(new Integer[]{8, 17, 18, 30}));
        listOfArrays.add(Arrays.asList(new Integer[]{1, 9, 22}));
        listOfArrays.add(Arrays.asList(new Integer[]{9, 14, 20, 27}));

        System.out.println(findMinInterval(listOfArrays));
    }
}
