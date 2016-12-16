package com.programs;

/**
 * Created by sagupta on 11/1/15.
 */
public class MaxProductSub {

    public int[] solution(int[] inAr) {
        if (inAr != null && inAr.length > 0) {

            int maxP = 0;
            int runP = 0;
            int lastRunP = 0;
            int lastRunL = -1;
            int maxL = -1;
            int maxR = -1;
            int runL = -1;
            int runR = -1;
            int indexLastNegative = -1;

            for(int i=0;i<inAr.length;i++) {
                if (inAr[i] != 0) {
                    if (inAr[i] > 0) {
                        if (runP >= 0) {
                            if (runP == 0) {
                                runP = 1;
                                runL = i;
                            }
                            runP *= inAr[i];
                            lastRunP = runP;
                            lastRunL = runL;
                        } else {
                            runP *= inAr[i];
                        }
                        runR = i;
                    } else {
                        if (runP >= 0) {
                            runP = inAr[i];
                            runL = i;
                            indexLastNegative = i;
                        } else {
                            runP *= inAr[i];
                            if (lastRunP > 0) {
                                runP *= lastRunP;
                                runL = lastRunL;
                                indexLastNegative = -1;
                            }
                            lastRunP = runP;
                            lastRunL = runL;
                        }
                        runR = i;
                    }
                } else if (inAr[i] == 0) {
                    // reset
                    runP = 0;
                    runL = runR = -1;
                    lastRunP = 0;
                    lastRunL = -1;
                    indexLastNegative = -1;
                    continue;
                }

                if (runP >= maxP) {
                    maxP = runP;
                    maxL = runL;
                    maxR = runR;
                }
            }

            if (runP < 0 && indexLastNegative >=0 && (indexLastNegative+1) < inAr.length) {
                runP /= inAr[indexLastNegative];
                runL++;
            }

            if (runP > maxP) {
                maxP = runP;
                maxL = runL;
                maxR = runR;
            }

            if (maxL >= 0) {
                int[] subAr = new int[maxR - maxL + 1];
                for (int i=0;i<(maxR - maxL + 1);i++) {
                    subAr[i] = inAr[maxL+i];
                }
                return subAr;
            }
        }
        return null;

    }
}
