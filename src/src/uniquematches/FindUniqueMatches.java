package uniquematches;

// Given two arrays of sorted, non-unique integers
// Output a list of unique matches
// Limitation: no hashing structures

import java.util.*;

class FindUniqueMatches {
    public static List<Integer> GetMatches(int[] a, int[] b) {

        int pa = 0;
        int pb = 0;

        List<Integer> result = new ArrayList<Integer>();

        while ((pa < a.length) && (pb < b.length)) {
            // System.out.println("Hello Java");
            // happy path
            if (a[pa] == b[pb]) {
                int val = a[pa];
                //System.out.println("equal on"+a[pa]);
                result.add(a[pa]);
                // advance pa and pb to next non equal values
                while (pa < a.length && a[pa] == val) pa++;
                while (pb < b.length && a[pb] == val) pb++;

            } else {
                // advance pointer having lower value until value is equal to other pointer to greater
                if (a[pa] < b[pb]) {
                    // advance pa
                    while (pa < a.length && a[pa] < b[pb]) pa++;
                } else {
                    // advance pb
                    while (pb < b.length && b[pb] < a[pa]) pb++;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(FindUniqueMatches.GetMatches(new int[]{1, 2, 3,4,4}, new int[]{2, 2, 3, 3, 4}));
    }


}
