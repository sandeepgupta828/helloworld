package heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class FindTopOrBottomKNumbers {
    public static void main(String[] args) {
        //System.out.println(findKNumbers(true, 5, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));
        System.out.println(findKNumbers(true, 5, Arrays.asList(6, 7, 8, 9, 10, 1, 2, 3, 4, 5)));

    }

    public static List<Integer> findKNumbers(boolean top, int k, List<Integer> numbers) {

        PriorityQueue<Integer> priorityQueue;
        /*
        algo:
        we iterate over all numbers and put them in a heap
        when the heap exceeds size "k" we'd poll a number from heap to keep its size at k
        in the end we return all the numbers in the heap

        for top k numbers we choose a min heap, as min number is available via heap
        and it helps with the clear decision of whether or not the next number should be considered for top k bucket
        i.e. if number > min we should include the number and kick out min else we ignore the number

        conversely for bottom k numbers we choose a max heap, as max number is available via heap
        i.e. if number < max we should include the number and kick out max else we ignore the number


         */
        if (top) { // top k
            // min heap
            priorityQueue = new PriorityQueue<Integer>((n1, n2) -> n1-n2);
        } else { // bottom k
            // max heap
            priorityQueue = new PriorityQueue<Integer>((n1, n2) -> n2-n1);
        }


        for (Integer number: numbers) {
            if (top) {
                if (priorityQueue.size() < k || number > priorityQueue.peek()) {
                    priorityQueue.offer(number);
                    if (priorityQueue.size() > k) {
                        priorityQueue.poll();
                    }
                }
            } else {
                if (priorityQueue.size() < k || number < priorityQueue.peek()) {
                    priorityQueue.offer(number);
                    if (priorityQueue.size() > k) {
                        priorityQueue.poll();
                    }
                }
            }
        }

        List<Integer> output = new ArrayList<Integer>(priorityQueue);
        output.sort((n1, n2) -> n1-n2);
        return output;
    }
}
