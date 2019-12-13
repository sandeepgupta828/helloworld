package com.programs.recursion;

//https://leetcode.com/explore/interview/card/google/62/recursion-4/3079/

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 *
 * For example, given n = 3, a solution set is:
 *
 * [
 *   "((()))",
 *   "(()())",
 *   "(())()",
 *   "()(())",
 *   "()()()"
 * ]
 */
public class GenerateParenthesis {
    public static void main(String[] args) {
        System.out.println(generateAllParenthesis(0, 0, 10));
    }

    /**
     * algo:
     * do it recursively
     * maintain a running openCount, closedCount along with totalCount
     * if you can open (openCount < totalCount), add open parenthesis "(" and prefix this to recursively generated parenthesis strings with openCount updated to openCount+1
     * if you can close (openCount > 0 && closedCount < openCount), add close parenthesis ")" and prefix this to recursively generated parenthesis string with closedCount updated to closedCount+1
     *
     */
    public static List<String> generateAllParenthesis(int openCount, int closedCount, int totalCount) {
        List<String> parenthesisList = new ArrayList<>();
        // handle base case:
        if (closedCount == totalCount) {
            parenthesisList.add("");
        }
        if (openCount < totalCount) {
            // can open
            parenthesisList.addAll(generateAllParenthesis(openCount+1, closedCount, totalCount).stream().map(p -> "("+p).collect(Collectors.toList()));
        }
        if (openCount > 0 && closedCount < openCount) {
            // can close
            parenthesisList.addAll(generateAllParenthesis(openCount, closedCount+1, totalCount).stream().map(p -> ")"+p).collect(Collectors.toList()));
        }
        return parenthesisList;

    }

}
