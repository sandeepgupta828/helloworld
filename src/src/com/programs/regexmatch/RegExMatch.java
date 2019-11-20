package com.programs.regexmatch;

/**
 * Write a method that matches regular expression containing only '*' with a word
 * '*' matches with 0 or more characters
 * handle edge cases where regular expression or word could be empty
 */
public class RegExMatch {

    public static boolean checkMatch(String regex, String word) {

        // handle edge cases
        if (regex.isEmpty() && word.isEmpty()) return true;
        if (regex.isEmpty()) return false; // regex is empty but word is not
        if (word.isEmpty()) {
            return regex.replaceAll("\\*","").isEmpty();
        }
        int index = 0;

        while (index < regex.length() && index < word.length()) {
            if (regex.charAt(index) == word.charAt(index)) {
                index++;
            } else if (regex.charAt(index) == '*') {
                // case1: use * exactly once
                boolean case1 = checkMatch(regex.substring(index + 1), word.substring(index + 1));
                if (case1) return true;

                // case2: use * more than once
                boolean case2 = checkMatch(regex.substring(index), word.substring(index + 1));
                if (case2) return true;

                // case3: don't use * at all
                boolean case3 = checkMatch(regex.substring(index + 1), word.substring(index));
                if (case3) return true;
                return false;
            } else {
                return false;
            }
        }
        if (index == word.length() && index == regex.length()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(RegExMatch.checkMatch("Orange*", "Oranges"));
    }
}
