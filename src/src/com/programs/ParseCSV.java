package com.programs;

import java.io.*;
import java.util.*;

public class ParseCSV {

    /**
     * Name, LastName, Location, Company, Address
     * Sandeep, Gupta, Santa Clara, XYZ, ABC\, XYZ,
     * Key problem to solve is to deal with values that have comma within them.
     */
    public static List<Map<String, String>> parseData(String fileLocation) throws IOException {

        FileReader fileReader = new FileReader(fileLocation);
        BufferedReader reader = new BufferedReader(fileReader);
        // header of the data
        String line = reader.readLine();
        // check for null
        Map<Integer, String> mapOfKeys = processHeader(line);

        //0,Name
        //1, LastName
        //...

        // CSV
        // To list of events
        List<Map<String, String>> output = new ArrayList<>();
        char chVal = 0x01; //some byte value that doesn't display;

        do {
            line = reader.readLine();
            if (line != null) {
                // replace \, with some ch value
                line.replaceAll("\\,",chVal+"");
                String[] words = line.split(",");

                Map<String, String> map = new HashMap<>();
                // "keyless" key
                for (int i = 0; i < words.length; i++) {
                    String fixedWord = words[i].replaceAll(chVal+"", ",").trim();
                    map.put(mapOfKeys.get(i), fixedWord);
                }
                output.add(map);
            }
        } while (line != null);

        return output;
    }

    private static Map<Integer, String> processHeader(String line) {

        return null;
    }

    public static void main(String[] args) {
        //System.out.println(""+(char)0x0);
        String sample = "sandeep\\, gupta, california";
        String newSample = sample.replaceAll("\\\\,",""+(char)0x1);
        String[] wordArr = newSample.split(",");
        wordArr[0] = wordArr[0].replaceAll(""+(char)0x1,",");
        System.out.println(Arrays.asList(wordArr));
    }
}
