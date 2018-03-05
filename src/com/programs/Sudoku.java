package com.programs;

/*
Introduction
        Sudoku is a number placing puzzle based on a 9x9 grid with several given or pre-populated numbers. The object is to place all of the numbers 1 to 9 in the empty squares so that each row, each column and each 3x3 box contains the same number only once. A picture of a Sudoku puzzle can be found here: http://dingo.sbs.arizona.edu/~sandiway/sudoku/examples.html

        A puzzle is presented to a player with some numbers filled in (the "givens"). The givens cannot be changed and the player must fill in the remainder of the grid according the constraints above.

        Problem
        Your goal is to write a Sudoku checker. Pick a programming language, design a data structure to hold the puzzle, and write code to parse a puzzle representation from standard input, check whether the submitted puzzle is correct, and print "valid" or "invalid" to standard output, depending on if the given puzzle satisfies the constraints of Sudoku.

        Note: You aren't trying to solve the puzzle, just see if the submitted solution is correct or not.

        Input
        Write a top level function that receives the input puzzle in a data structure of your choice.  (No need to parse stdin)

        Output
        Your code should print the string "valid" or "invalid" to standard output, along with a newline character.

*/

import java.util.HashMap;
import java.util.Map;

class Sudoku {

    public void main(String[] args) {
// kick off the check
    }


    boolean checkSolution(Integer[][] input) {

        // check if all the rows have unqiue numbers 1-9

        Integer rowCount = input.length;
        //Preconditions.check(rowCount == 9, "Invalid input");
        Map<Integer, Boolean> checkNumbers = new HashMap();
        //
        for(int i=0; i< rowCount; i++) {
            Integer colCount = input[i].length;
            //Preconditions.check(colCount == 9, "Invalid input");
            initMap(checkNumbers);
            for (int j=0; j < input[i].length; j++) {
                checkNumbers.put(input[i][j], true);
            }
            // check if map has values as true
            boolean isSomeNumberAbsent = checkNumbers.entrySet().stream().anyMatch(entry -> !entry.getValue());
            if (isSomeNumberAbsent) return false;
        }

        // check if all the cols have unqiue numbers 1-9
        Integer colCount = input.length; // must be at this point

        for(int j=0; j< colCount; j++) {
            initMap(checkNumbers);
            for (int i=0; i < input[j].length; i++) {
                checkNumbers.put(input[i][j], true);
            }
            // check if map has values as true
            boolean isSomeNumberAbsent = checkNumbers.entrySet().stream().anyMatch(entry -> !entry.getValue());
            if (isSomeNumberAbsent) return false;
        }


        // check if all 3x3 boxes have unqiue numbers 1-9

        for (int i=0; i < input.length; i=i+3) {
            for (int j=0; i < input.length; j=j+3) {
                boolean areAllNumbersPresent = checkAllNumbersPresentInBox(input, i, j);
                if (!areAllNumbersPresent) return false;
            }
        }
        return false;
    }



    boolean checkAllNumbersPresentInBox(Integer[][] input, Integer r, Integer c) {
        Map<Integer, Boolean> checkNumbers = new HashMap();
        initMap(checkNumbers);
        for (int i=r;i<r+3;i++) {
            for(int j=c;j<c+3;j++) {
                checkNumbers.put(input[i][j], true);
            }
        }
        return !checkNumbers.entrySet().stream().anyMatch(entry -> !entry.getValue());
    }

    void initMap(Map<Integer, Boolean> map) {
        for (int i=0;i<10;i++) {
            map.put(i, false);
        }
    }



}