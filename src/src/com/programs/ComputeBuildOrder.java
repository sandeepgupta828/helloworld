package com.programs;

/**
 B -> [C, D]
 A -> [B]
 C -> [E]

 Input => A
 Output => E, C, D, B, A or D, E, C, B, A
 C => E, C
 E => E
 */

/*
Map of build order

B -> [C, D]
A -> [B]
C -> [E]

Input is X

List<String> ComputeBuildOrder(String X) {

}



*/

import java.util.*;
import java.util.stream.Collectors;

public class ComputeBuildOrder {

    private static Map<String, List<String>> moduleDependencyMap = new HashMap();

    static {
        // initialize the map
        moduleDependencyMap.put("A", Arrays.asList("B"));
        moduleDependencyMap.put("B", Arrays.asList("C", "D"));
        moduleDependencyMap.put("C", Arrays.asList("E", "A"));
    }

    public static void main(String[] args) {
        // create obj and call method
        ComputeBuildOrder obj = new ComputeBuildOrder();
        System.out.println(obj.compute("A").toString());
    }

    public List<String> compute(String module) { // A , B, C
        if (moduleDependencyMap.get(module) != null) {
            List<String> dependencies = moduleDependencyMap.get(module); // [B], [C, D]
            List<String> order = new ArrayList(); // [], []
            for (String dependency: dependencies) {
                List<String> subDependencies = compute(dependency);
                List<String> filteredSub = subDependencies.stream().filter(dep -> !order.contains(dep)).collect(Collectors.toList());
                order.addAll(filteredSub);
            }
            order.add(module);
            return order;
        } else {
            return Arrays.asList(module);
        }
    }

}
