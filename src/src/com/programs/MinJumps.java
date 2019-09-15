package com.programs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sagupta on 11/2/15.
 */
public class MinJumps {

    public static void main(String[] args) {

        MinJumps minJumps = new MinJumps();
        Integer[] array = {5 ,1 ,1, 1};
        System.out.println("Min jumps = " + minJumps.solution(array).toString());
    }

    public List<Integer> solution(Integer[] inAr) {

        int len = inAr.length;
        Integer[] jumpArray = new Integer[inAr.length];
        int k = 0;
        jumpArray[k++] = inAr[0];
        boolean jumpedToEnd = false;
        for (int i=0;i<len-1 && !jumpedToEnd;) {
            int maxReachSIndex = -1;
            int maxReachTIndex = -1;
            for (int j=i+1;j<=i+inAr[i] && j<len;j++) {
                int reach = j + inAr[j];
                if (reach >= (len-1)) {
                    maxReachTIndex = len-1;
                    maxReachSIndex = j;
                    jumpedToEnd = true;
                    break;
                } else if (maxReachTIndex < reach) {
                    maxReachTIndex = reach;
                    maxReachSIndex = j;
                }
            }
            if (maxReachTIndex > 0) {
                i = maxReachSIndex;
                jumpArray[k++] = inAr[maxReachSIndex];
            }
        }
        return Arrays.asList(jumpArray);
    }
}
