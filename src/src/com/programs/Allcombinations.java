package com.programs;

import java.util.*;

/**
 * Created by sagupta on 11/17/15.
 */
public class Allcombinations {


    Set<StringBuffer> combineSet = new HashSet<>();

    void gatherAllcombinations(StringBuffer str, int s) {
        if (s == str.length()-1) {
            combineSet.add(new StringBuffer().append(str.charAt(s)));
            combineSet.add(new StringBuffer());
        } else if (s < str.length()) {
            char currentChar = str.charAt(s);
            gatherAllcombinations(str, s+1);
            Set<StringBuffer> newSet = new HashSet<>();
            for (StringBuffer val: combineSet) {
                StringBuffer newVal = new StringBuffer().append(currentChar);
                newVal.append(val);
                newSet.add(newVal);
            }
            combineSet.addAll(newSet);
        }
    }

    void solution(String str) {
        StringBuffer buffer = new StringBuffer(str);
        gatherAllcombinations(buffer, 0);
        List<String> sortedList = new ArrayList();
        for (StringBuffer value : combineSet) {
            sortedList.add(value.toString());
        }
        Collections.sort(sortedList);
        for (String value: sortedList) {
            System.out.println(value);
        }

    }
}
