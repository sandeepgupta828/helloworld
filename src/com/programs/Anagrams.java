package com.programs;

//public class Anagrams {

/*
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
  */
/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

    //class Solution {
/*
        public static void main(String[] args) {
            String[] input = {"dog", "cat", "atc", "god", "godess", "good"};
            Solution sol = new Solution();
            Map<String, List<String>> output = sol.findAnagrams(Arrays.asList(input));
            //System.out.print(output);
        }


        public Map<String, List<String>> findAnagrams(List<String> wordList) {

            Map<String, List<String>> map = new HashMap();

            for (int i = 0; i < wordList.size(); i++) {
                String word = wordList.get(i);

                CharArray charArray = word.toCharArray();
                String sortedWord = new String(Arrays.sort(charArray));

                List<String> existingAnagrams = map.get(sortedWord);
                if (existingAnagrams != null) {
                    existingAnagrams.add(word);
                } else {
                    List<String> list = new ArrayList();
                    list.add(word);
                    map.put(sortedWord, list);
                }
            }
            return map;
        }
    }
*/

