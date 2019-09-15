package com.programs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.IntStream;

public class MinJumpsDP {

    public static void main(String[] args) {

        MinJumpsDP minJumps = new MinJumpsDP();
        Integer[] array = {1, 1, 1, 5, 1, 1};
        Deque<Integer> jumpArray = new ArrayDeque<>();
        try {
            jumpArray = minJumps.getMinJumSequence(Arrays.asList(array));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Min jumps = " + jumpArray.toString());
    }

    public Deque<Integer> getMinJumSequence(List<Integer> input) throws Exception{
        Deque<Integer> jumpIndexes = new ArrayDeque<>(input.size());
        Integer curJumpIndex = 0;
        Integer curMaxForwardJumpIndex = curJumpIndex + input.get(0);
        Integer nextJumpIndex = -1;
        Integer nextMaxForwardJumpIndex = -1;
        for (int i = 0; i < input.size(); i++) {
            if (i <= curMaxForwardJumpIndex) {
                if ((i + input.get(i)) > nextMaxForwardJumpIndex) {
                    nextJumpIndex = i;
                    nextMaxForwardJumpIndex = i + input.get(i);
                }
                continue;
            } else {
                if (i <= nextMaxForwardJumpIndex) {
                    curJumpIndex = nextJumpIndex;
                    curMaxForwardJumpIndex = nextMaxForwardJumpIndex;
                    jumpIndexes.addLast(curJumpIndex);
                    if ((i + input.get(i)) > nextMaxForwardJumpIndex) {
                        nextJumpIndex = i;
                        nextMaxForwardJumpIndex = i + input.get(i);
                    }
                } else {
                    throw new Exception("Jump not possible");
                }
            }
        }
        return jumpIndexes;
    }
}
