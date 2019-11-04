package com.programs.missingwords;

import java.util.*;

public class MissingWords {
    /*
     * Complete the 'missingWords' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts following parameters:
     *  1. STRING s
     *  2. STRING t
     */

    public static List<String> missingWords(String s, String t) {
        // Write your code here
        if (Objects.nonNull(s) && Objects.nonNull(t)) {
            List<String> sWords = Arrays.asList(s.split(" "));
            List<String> tWords = Arrays.asList(t.split(" "));
            List<String> missingWords = new ArrayList<>();

            int sWordCount = 0;
            for(int twordCount=0;twordCount<tWords.size();twordCount++) {
                String tWord = tWords.get(twordCount);
                // gather all the words in s until we hit this word
                while(sWordCount < sWords.size()) {
                    if (!tWord.equals(sWords.get(sWordCount))) {
                        missingWords.add(sWords.get(sWordCount));
                        sWordCount++;
                    } else {
                        sWordCount++;
                        break;
                    }
                }
            }
            while(sWordCount < sWords.size()) {
                missingWords.add(sWords.get(sWordCount));
                sWordCount++;
            }
            return missingWords;
        }
        return Collections.emptyList();
    }

    public static void main(String[] args) {
        System.out.println(MissingWords.missingWords("I am using HackerRank to improve programming", "am HackerRank to improve"));
    }

}
