package com.programs;

import java.util.stream.IntStream;

public class FindAllStringCombinations {

    private static String lastString;

    public static void main(String[] args) {
        FindAllStringCombinations findAllStringCombinations = new FindAllStringCombinations();
        int i = 100;
        while (i--> 0) {
            System.out.println(findAllStringCombinations.produceNextString(new char[] {'a', 'b', 'c'}, 2));
        }
    }

    public String produceNextString(char[] letters, int strLength) {
        /*
        Sort the letters
        Check for existence of last String,
        if yes increment it with next letter (if end of letters is reached, then traverse back and find the first letter which is less next letter and increment it, and reset all subsequent letters to first letter
        if not form the String with all first letter
         */
        char maxChar = letters[letters.length-1];
        char minChar = letters[0];

        if (lastString == null) {
            // initialize with min char
            StringBuffer buffer = new StringBuffer(strLength);
            IntStream.range(0, strLength).forEach(i -> buffer.append(minChar));
            lastString = buffer.toString();
        } else {
            char[] lastStringArray = lastString.toCharArray();
            char lastChar = lastStringArray[strLength-1];
            boolean lastStringUpdated = false;
            if (lastChar < maxChar) {
                // we can increment last char as it is not max char
                int indexOfNextHigherLetter = lastChar - minChar + 1;
                lastStringArray[strLength-1] = letters[indexOfNextHigherLetter];
                lastString = new String(lastStringArray);
                lastStringUpdated = true;
            } else if (lastChar == maxChar && strLength > 1){
                // if max char is reached, then traverse back and find the first letter which is less next letter and increment it, and reset all subsequent letters to first letter
                for (int i=strLength-1;i>0;i--) {
                  char curChar = lastStringArray[i];
                  char prevChar = lastStringArray[i-1];
                  if (prevChar < curChar) {
                      int indexOfNextHigherLetter = prevChar - minChar + 1;
                      lastStringArray[i-1] = letters[indexOfNextHigherLetter];
                      for (int j = i; j < strLength; j++) {
                          lastStringArray[j] = minChar;
                      }
                      lastString = new String(lastStringArray);
                      lastStringUpdated = true;
                      break;
                  }
                }
            }
            if (!lastStringUpdated) {
                lastString = null;
                produceNextString(letters, strLength);
            }
        }
        return lastString;
    }


}



    /*
// This is the text editor interface.
// Anything you type or change here will be seen by the other person in real time.

//
// 3
// a,b
//
// =>
//
// aaa,aab,aba,abb,baa,bab,bba,bbb

// aaa, aab, aba, abb, baa, bab, bba, bbb

L = length = 3

N = Number of letters = 2

Total possibilities = 2 X 2 X 2 = 8 = N X L


Set<String> produceAllPossibleString(int numPlaces, char[] letters) {// 20, 26 chars
    Set<String> currentSet = new HashSet();
    // base case
    if (numPlaces == 1) {
        for (int i=0;i<letters.length;i++) {
            currentSet.add(""+ch);
        }
    } else {
        Set<String> allSuffixStrings = produceAllPossibleString(numPlaces-1, letters);
        for (int i=0; i< letters.length; i++) {
            char ch = letters[i];
            for(String suffixString: allSuffixStrings) {
                currentSet.add(ch + suffixString);
            }
        }
    }
    return currentSet;
}

/*

length 20, 26 letters

26 letters X 26 leters X ....(20 times)

*/

/*

Thing t = new Thing(20, "abcde...");
t.next() => 'aaaaaaaaa...a';
t.next() => 'aaaaaaaaa...b';

*/

/*
'aaaaaaaaa...a'

        'aaaaaaaaa...z'

        'aaaaaaaaa...ba'

        'aaaaaaaaa...bz'

        'aaaaaaaaa...ca'












        */
