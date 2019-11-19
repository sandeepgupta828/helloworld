package com.programs.mininterval;

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

    public static List<Integer> findMinInterval(List<List<Integer>> listOfArrays) {
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
        Map<String, Integer> lastIndex = new HashMap<>();

        for (int i = 0; i < entryList.size(); i++) {
            Entry entry = entryList.get(i);
            lastIndex.put(entry.arId, i);
            Boolean wasPresent = isArrPresent.get(entry.arId);
            if (wasPresent == null || !wasPresent) {
                isArrPresent.put(entry.arId, true);
            } else {
                // move the window forward if possible by incrementing start
                // any entries at start that also have later last index <= i can be removed and windown advanced
                // shortening the window leads to minimizing the distance between start and i, as entries are sorted
                // the shortest distance is what we want across all window sizes
                while (lastIndex.get(entryList.get(start).arId) > start) start++;
            }
            if (isArrPresent.size() == listOfArrays.size()) { // window is complete
                // entries from all arrays are seen, now we have an interval where all arrays have entries i.e. [start, i]
                // we update min indexes to track minimum such interval
                if ((minStart < 0 && minEnd < 0) || (minStart >= 0 && minEnd >= 0 && (entryList.get(minEnd).val - entryList.get(minStart).val) > (entryList.get(i).val - entryList.get(start).val))) {
                    minStart = start;
                    minEnd = i;
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int i=0;i<entryList.size();i++) {
            if (i >= minStart && i <= minEnd) {
                result.add(entryList.get(i).val);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<List<Integer>> listOfArrays = new ArrayList<>();
        listOfArrays.add(Arrays.asList(new Integer[]{5, 7, 8}));
        listOfArrays.add(Arrays.asList(new Integer[]{6, 17}));
        listOfArrays.add(Arrays.asList(new Integer[]{1, 9}));
        listOfArrays.add(Arrays.asList(new Integer[]{10, 14}));
        System.out.println(findMinInterval(listOfArrays));
    }
}
