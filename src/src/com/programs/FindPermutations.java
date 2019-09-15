package com.programs;

import java.util.HashMap;
import java.util.Map;

public class FindPermutations {

    public static  void main(String[] argsv) {
        FindPermutations findPermutations = new FindPermutations();
        findPermutations.findPermutations("abbcdeacb", "abc");
    }

    void findPermutations(String s1, String s2) {
        Map<Character, Integer> countMap = new HashMap<>();

        for (int j=0; j < s2.length();j++) {
            Integer oldCount = countMap.putIfAbsent(s2.charAt(j), 1);
            if (oldCount != null) {
                Integer count = countMap.get(s2.charAt(j));
                countMap.put(s2.charAt(j), count + 1);
            }
        }
        Map<Character, Integer> map = new HashMap<>(countMap);
        resetMap(map);
        Integer permutationStart = -1;
        for(int i=0;i<s1.length();i++) {
            Character ch = s1.charAt(i);
            if (map.containsKey(ch)) {
                // potential start of permutation
                if (permutationStart < 0) {
                    permutationStart = i;
                }
                Integer oldCount = map.get(ch);
                map.put(ch, oldCount+1);
            } else {
                // end of permutation or otherwise
                // check if we have same counts in map as in countMap
                boolean allMatch = map.entrySet().stream().allMatch(entry -> countMap.get(entry.getKey()).equals(entry.getValue()));
                if (allMatch) {
                    System.out.println("\n"+ s1.substring(permutationStart, i));
                }
                permutationStart = -1;
                resetMap(map);
            }
        }
        boolean allMatch = map.entrySet().stream().allMatch(entry -> countMap.get(entry.getKey()).equals(entry.getValue()));
        if (allMatch) {
            System.out.println("\n"+ s1.substring(s1.length()-s2.length(), s1.length()));
        }
    }

    void resetMap(Map<Character, Integer> countMap) {
        for (Character character: countMap.keySet()) {
            countMap.put(character, 0);
        }
    }
}
