package com.programs.tradedk;

import java.util.HashSet;
import java.util.Set;

/**
 * Solve master mind as player 2 who figures in minium guesses the color sequence
 *
 */
public class MasterMind {
    public static class Result {
        int numRed;
        int numWhite;
    }

    public static Result invokeGuess(int[] input) {
        // TBD...
        return null;
    }

    public static int[] main() {
        int[] guess = new int[4];

        Set<Integer> allColorSet = new HashSet<>();
        for (int i=0;i<6;i++) {
            allColorSet.add(i);
        }

        Set<Integer> colorSet = new HashSet<>();

        for (int color=0;color < 6;color++) {
            // populate the color
            for (int j=0;j<4;j++) {
                guess[j]=color;
            }
            Result result = invokeGuess(guess);
            // check if this color is present
            if (result.numWhite > 0) {
                colorSet.add(color);
            }
            if (colorSet.size() == 4) break;
        }
        // now we know which all colors are there.

        // find a color which is not in the result to use as control

        allColorSet.removeAll(colorSet);
        int controlColor = allColorSet.iterator().next();

        // figure sequence

        // for each column populate one of the colors and examine result
        int[] sqeGuess = new int[4];
        for (int col=0;col<4;col++) {
            int colorFound = -1;
            if (colorSet.size() ==1) {
                // no need to guess
                sqeGuess[col] = colorSet.iterator().next();
                break;
            }
            for (Integer color: colorSet) {
                // populate guess
                for (int k=col;k<sqeGuess.length;k++) {
                    if (k==col) {
                        sqeGuess[k] = color;
                    } else {
                        sqeGuess[k] = controlColor;
                    }
                }
                Result result = invokeGuess(sqeGuess);
                if (result.numRed == (col+1)) {
                    colorFound = color;
                    break; // we have found which color is in this position
                }
            }
            colorSet.remove(colorFound);
        }
        return sqeGuess;
    }

}
