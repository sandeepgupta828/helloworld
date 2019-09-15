package com.programs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AllSubsets {

    public static void main(String[] argv) {
        AllSubsets allSubsets = new AllSubsets();
        System.out.println(allSubsets.computeAllSubsets("1234"));
    }

    private Collection<String> computeAllSubsets(String str) {
        List<String> allSets = new ArrayList<>();
        // add the empty set always
        allSets.add("");
        char[] strChars = str.toCharArray();
        for (int i=0;i<strChars.length;i++) {
            char ch = strChars[i];
            List<String> newSets = new ArrayList<>();
            for (String set: allSets) {
                newSets.add(set+ch);
            }
            allSets.addAll(newSets);
        }
        Collections.sort(allSets, (o1, o2) -> {
            if (!o1.isEmpty() && o2.isEmpty()) {
                return 1;
            }
            if (o1.isEmpty() && !o2.isEmpty()) {
                return -1;
            }
            return Integer.parseInt(o1) - Integer.parseInt(o2);
        });
        return allSets;
    }

}
