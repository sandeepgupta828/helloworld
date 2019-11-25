package com.programs.finddupimages;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class FindDuplicates {


    /**
     * You are an avid photographer and have accumulated many images. You haven’t done a good job of organizing the images and suspect that you have many duplicates. You want to determine a count of how many duplicates you have in your workspace folder. An image is considered a duplicate if it has the exact same binary data as another.
     * Image files: F1, F2,...Fn
     * <p>
     * Read F1 --> compute a hash over image byetes and outputs a value --> HV1
     * <p>
     * F2 --> HV2
     * ....
     * <p>
     * HasMap:
     * <p>
     * Value -> FileName
     **/
    public static void main(String args[]) throws Exception {
        System.out.println("Hello World");
    }

    public static int detectDuplicateImages(List<String> fileLocations) throws FileNotFoundException {

        Map<Integer, List<String>> map = new HashMap();
        int duplicateCount = 0;

        for (int i = 0; i < fileLocations.size(); i++) {
            // read file and compute hash
            int hashValue = computeHash(fileLocations.get(i));
            List<String> fileList = map.getOrDefault(hashValue, new ArrayList<String>());
            fileList.add(fileLocations.get(i));
            map.put(hashValue, fileList);
        }

        // now go over hash map for keys that have values size > 1
        for (List<String> dupFiles : map.values()) {
            if (dupFiles.size() > 1) {
                // we have possible duplicate image here
                // check if there is really a duplicate here
                for (int i = 0; i < dupFiles.size(); i++) {
                    for (int j = i + 1; j < dupFiles.size(); j++) {
                        // compare value.get(i) and value.get(j);
                        if (compareImages(dupFiles.get(i), dupFiles.get(j))) {
                            duplicateCount++;
                        }
                    }
                }
            }
        }

        return duplicateCount;
    }

    private static int computeHash(String fileLocation) {
        // read bytes from fileLocation and compute hash
        // b1, b2, b3, ....bn
        // (int)b1*31^0 + (int)b2*31^
        return 0;
    }

    private static boolean compareImages(String location1, String location2) {
        // read from both locations in a buffer incrementally and compare
        return false;
    }

}

