import java.util.ArrayList;

/**
 * Created by sagupta on 11/3/15.
 */
public class Heap {

    public Heap(HeapType type) {
        this.heapType = type;
    }

    enum HeapType {
        MAX, MIN
    }
    HeapType heapType;

    ArrayList<Integer> ar = new ArrayList<>();

    void swapValues(int i1, int i2) {
        int temp = ar.get(i1);
        ar.set(i1,ar.get(i2));
        ar.set(i2,temp);
    }

    void restoreHeapOrderOnInsertion() {
        int startIndex = ar.size()-1;
        int parent = startIndex/2;
        while (startIndex > 0){
            if (heapType == HeapType.MAX) {
                if (ar.get(parent) < ar.get(startIndex)) {
                    swapValues(parent, startIndex);
                } else break;
            } else if (heapType == HeapType.MIN) {
                if (ar.get(parent) > ar.get(startIndex)) {
                    swapValues(parent, startIndex);
                } else break;
            }
            startIndex = parent;
            parent = parent/2;

        }
    }

    void restoreHeapOrderOnDeletion() {
        int parent = 0;
        while(true) {
            int leftChildIndex = 2*(parent+1)-1;
            int rightChildIndex = 2*(parent+1);
            if (heapType == HeapType.MAX) {
                if (leftChildIndex < ar.size() && ar.get(parent) < ar.get(leftChildIndex)) {
                    swapValues(parent,leftChildIndex);
                    parent = leftChildIndex;
                } else if (rightChildIndex < ar.size() && ar.get(parent) < ar.get(rightChildIndex)) {
                    swapValues(parent,rightChildIndex);
                    parent = rightChildIndex;
                } else break;
            } else if (heapType == HeapType.MIN) {
                if (leftChildIndex < ar.size() && ar.get(parent) > ar.get(leftChildIndex)) {
                    swapValues(parent,leftChildIndex);
                    parent = leftChildIndex;
                } else if (rightChildIndex < ar.size() && ar.get(parent) > ar.get(rightChildIndex)) {
                    swapValues(parent,rightChildIndex);
                    parent = rightChildIndex;
                } else break;
            }
        }
    }

    void add(Integer element) {
        int emptyIndex = ar.size();
        ar.add(emptyIndex,element);
        restoreHeapOrderOnInsertion();
    }

    Integer peek() {
        if (ar.size() > 0) {
            return ar.get(0);
        }
        return null;
    }

    void remove() {
        int lastElement = ar.get(ar.size()-1);
        ar.set(0, lastElement);
        ar.remove(ar.size()-1);
        restoreHeapOrderOnDeletion();
    }

    int size() {
        return ar.size();
    }


}
