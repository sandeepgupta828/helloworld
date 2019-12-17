package heap;

import java.util.*;

public class MergeSortedLists {
    public static void main(String[] args) {
        System.out.println(mergeLists(Arrays.asList(Arrays.asList(new Integer[] {1, 3, 5, 7}), Arrays.asList(new Integer[] {2, 4, 5, 6, 8}), Arrays.asList(new Integer[]{-2, -1, 0}), Arrays.asList(new Integer[] {9, 10, 11, 12}))));
    }

    public static class Element {
        public Element(Integer val, Integer listId) {
            this.val = val;
            this.listId = listId;
        }

        Integer val;
        Integer listId;
    }

    public static List<Integer> mergeLists(List<List<Integer>> sortedLists) {

        List<Integer> output = new ArrayList<>();
        Map<Integer, List<Integer>> mapOfListIdToList = new HashMap<>();
        Map<Integer, Integer> mapOfListIdToListIndex = new HashMap<>();

        PriorityQueue<Element> heap = new PriorityQueue<>((e1, e2) -> e1.val - e2.val);
        /*
        create a min heap
        Key point: the heap should contain smallest element from all arrays at all times
        we pick the min element from heap and then add the next element from the array from which this min element was picked.

        #1 add elements to heap from all lists with info of it source list
        #3 poll the min element and out this in output list
        #4 figure from which sortedList the element came from.
        #5 remove the next element from that sortedList and put it on the heap
        #6 repeat while heap is not empty
         */
        for (int i=0;i<sortedLists.size();i++) {
            // initialize lookup data structures
            mapOfListIdToList.put(i, sortedLists.get(i));
            mapOfListIdToListIndex.put(i, 0);
            // initialize heap
            if (sortedLists.get(i).size() > 0) {
                heap.add(new Element(sortedLists.get(i).get(0), i));
            }
        }


        // iterate
        while(heap.size() != 0) {
            Element element = heap.poll();
            output.add(element.val);
            int indexOfElement = mapOfListIdToListIndex.get(element.listId);
            if (indexOfElement < mapOfListIdToList.get(element.listId).size() -1) {
                heap.offer(new Element(mapOfListIdToList.get(element.listId).get(indexOfElement+1), element.listId));
                mapOfListIdToListIndex.put(element.listId, indexOfElement+1);
            }
        }
        return output;
    }
}
