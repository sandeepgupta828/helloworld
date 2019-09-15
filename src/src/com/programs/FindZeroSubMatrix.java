package com.programs;

/*
public class FindZeroSubMatrix {
    /*
import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

    /*
    class Solution {
        public static void main(String[] args) {
            int[][] image = {
                    {1, 1},
                    {1, 1},
                    {1, 0}
            };
            System.out.println("results="+computeWidthHeight(image));
        }


        public static Map<String, Integer> computeWidthHeight(int[][] image) {
            int maxCols = image[0].length;
            Map<String, Integer> results = new HashMap<>();
            int maxRows = image.length;
            for (int i=0; i<maxRows;i++) {
                for (int j=0;j<maxCols;j++) {
                    if (image[i][j] == 0) {
                        // find width and height
                        int width = 1;
                        int height = 1;
                        int startr = i+1; int startc=j+1;
                        while (startr < maxRows && image[startr][j] == 0)                        {
                            height++;
                            startr++;
                        }
                        while (startc < maxCols && image[i][startc] == 0)                        {
                            width++;
                            startc++;
                        }
                        results.put("x:",i);
                        results.put("y:", j);
                        results.put("width:", width);
                        results.put("height:", height);
                        break;
                    }
                }
                if (!results.isEmpty()) {
                    break;
                }
            }
            return results;
        }

    }


    public static Map<String, Integer> computeSubarray(int[][] image, int i, int j) {
        Map<String, Integer> results = new HashMap<>();

        // find width and height
        int width = 1;
        int height = 1;
        int startr = i+1; int startc=j+1;
        while (startr < maxRows && image[startr][j] == 0)                        {
            height++;
            startr++;
        }
        while (startc < maxCols && image[i][startc] == 0)                        {
            width++;
            startc++;
        }
        results.put("x:",i);
        results.put("y:", j);
        results.put("width:", width);
        results.put("height:", height);

        results.put(
        return results;
    }


    public static computeAll(int[][]image) {
        int maxCols = image[0].length;
        Map<String, Integer> allResults = new HashMap<>();
        int maxRows = image.length;
        for (int i=0; i<maxRows;i++) {
            for (int j=0;j<maxCols;j++) {
                // do the work
                if (image[i][j] == 0) {
                    // if already exopled  skip it

                    computeSubarray(image, i, j);
                }
            }
        }

    }

/*
Q1

Imagine we have an image. We’ll represent this image as a simple 2D array where every pixel is a 1 or a 0. The image you get is known to have a single rectangle of 0s on a background of 1s. Write a function that takes in the image and returns the coordinates of the rectangle -- either top-left and bottom-right; or top-left, width, and height.

Sample output:
x: 3, y: 2, width: 3, height: 2
2 3 3 5
3,2 5,3 -- it’s ok to reverse columns/rows as long as you’re consistent

Here are sample images using JavaScript and Java. Feel free to solve the problem in your language of choice:
// JavaScript
var image = [
  [1, 1, 1, 1, 1, 1, 1],
  [1, 1, 1, 1, 1, 1, 1],
  [1, 1, 1, 0, 0, 0, 1],
  [1, 1, 1, 0, 0, 0, 1],
  [1, 1, 1, 1, 1, 1, 1],
];

// Java
int[][] image = {
  {1, 1, 1, 1, 1, 1, 1},
  {1, 1, 1, 1, 1, 1, 1},
  {1, 1, 1, 0, 0, 0, 1},
  {1, 1, 1, 0, 0, 0, 1},
  {1, 1, 1, 1, 1, 1, 1}
};

Q2

Imagine we have an image. We’ll represent this image as a simple 2D array where every pixel is a 1 or a 0.

The image you get is known to have multiple rectangles of 0s on a background of 1s. Write a function that takes in the image and outputs the coordinates of all the 0 rectangles -- top-left and bottom-right; or top-left, width and height.

For example:
// JavaScript
var image = [
  [1, 1, 1, 1, 1, 1, 1],
  [1, 1, 1, 1, 1, 1, 1],
  [1, 1, 1, 0, 0, 0, 1],
  [1, 0, 1, 0, 0, 0, 1],
  [1, 0, 1, 1, 1, 1, 1],
  [1, 0, 1, 0, 0, 1, 1],
  [1, 1, 1, 0, 0, 1, 1],
  [1, 1, 1, 1, 1, 1, 1],
];

// Java
int[][] image = {
  {1, 1, 1, 1, 1, 1, 1},
  {1, 1, 1, 1, 1, 1, 1},
  {1, 1, 1, 0, 0, 0, 1},
  {1, 0, 1, 0, 0, 0, 1},
  {1, 0, 1, 1, 1, 1, 1},
  {1, 0, 1, 0, 0, 1, 1},
  {1, 1, 1, 0, 0, 1, 1},
  {1, 1, 1, 1, 1, 1, 1},
};





}
 */

