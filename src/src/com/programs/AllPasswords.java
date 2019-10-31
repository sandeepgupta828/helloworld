package com.programs;
import javafx.util.Pair;

import java.util.*;

public class AllPasswords {
    /**
     * algo:
     * Do DFS starting from (0,0) to (n,n)
     * when one cannot move to a next node because all of them have already been part of path -- save it as a path
     *
     * @param matrix
     */
    private Map<Integer, List<String>> allPaths = new HashMap<>();

    public  Map<Integer, List<String>> getAllPasswords(int[][] matrix) {
        int length = matrix.length;
        int breadth = matrix[0].length;

        for (int i=0; i < length; i++) {
            for (int j=0; j < breadth; j++) {
                allPaths.put(matrix[i][j], new ArrayList<>());
                populateAllPaths(matrix, new Loc(i, j), new Loc(i, j), new StringBuilder());
            }
        }

        return allPaths;
    }

    private  void populateAllPaths(int[][] matrix, Loc baseLoc, Loc loc, StringBuilder path) {
        int length = matrix.length;
        int breadth = matrix[0].length;
        // add location val to path
        path.append(matrix[loc.i][loc.j]);

        // explore 8 possible directions
        List<Loc> nextLocs = loc.getNextLocs(length, breadth);
        for (Loc nextLoc: nextLocs) {
            Integer val = matrix[nextLoc.i][nextLoc.j];
            if (path.indexOf(val.toString()) >= 0) {
                // skip locations that are already on the path
                continue;
            }
            populateAllPaths(matrix, baseLoc, nextLoc, path);
        }
        // after exploring all next locations add the current path
        String pathWithThisLoc = path.toString();
        allPaths.get(matrix[baseLoc.i][baseLoc.j]).add(pathWithThisLoc);
        // remove location val from path when returning from recursion
        path.deleteCharAt(pathWithThisLoc.length()-1);
    }

    public class Loc {
        private int i;
        private int j;

        public Loc(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public List<Loc> getNextLocs(int length, int breadth) {
            List<Loc> list = new ArrayList<>();
            // same row
            if (j+1 < breadth) {
                list.add(new Loc(i, j+1));
            }
            if (j-1 >=0) {
                list.add(new Loc(i, j-1));
            }
            if (i-1 >=0) { // upper row
                list.add(new Loc(i-1,j));
                if (j+1 < breadth) {
                    list.add(new Loc(i-1, j+1));
                }
                if (j-1 >=0) {
                    list.add(new Loc(i-1, j-1));
                }
            }
            if (i+1 < length) { //lower row
                list.add(new Loc(i+1,j));
                if (j+1 < breadth) {
                    list.add(new Loc(i+1, j+1));
                }
                if(j-1 >=0) {
                    list.add(new Loc(i+1, j-1));
                }
            }


            return list;
        }

    }
    public static void main(String[] args) {
        AllPasswords allPasswords = new AllPasswords();
        Map<Integer, List<String>> allP = allPasswords.getAllPasswords(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        allP.entrySet().stream().forEach(entry -> System.out.println(entry.getKey() + ": "+ "("+entry.getValue().size()+")" + " " + entry.getValue()));

    }
}
