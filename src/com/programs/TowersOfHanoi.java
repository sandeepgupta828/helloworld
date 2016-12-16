package com.programs;

import java.util.Stack;

/**
 * Created by sagupta on 11/2/15.
 */
public class TowersOfHanoi {

    public void solution(int countDisks) {
        Tower t1 = new Tower(1);
        Tower t2 = new Tower(2);
        Tower t3 = new Tower(3);

        for (int i=0;i<countDisks;i++) {
            t1.push(countDisks-i);
        }

        System.out.println("T1=" + t1.getStack().toString());
        System.out.println("T2="+t2.getStack().toString());
        System.out.println("T3="+t3.getStack().toString());


        moveTower(t1, t2, t3, countDisks);

        System.out.println("T1=" + t1.getStack().toString());
        System.out.println("T2="+t2.getStack().toString());
        System.out.println("T3="+t3.getStack().toString());

    }

    private void moveTower(Tower srcTower, Tower tempTower, Tower destTower, int disksToMove) {
        int disksMoved = 0;
        for(;disksToMove > 0;) {
            if (disksToMove %2 != 0) {
                moveNextDisk(srcTower, tempTower, destTower, disksMoved);
            } else {
                moveNextDisk(srcTower, destTower, tempTower, disksMoved);
            }
            disksToMove -=1;
            disksMoved++;
        }
    }
    private void moveNextDisk(Tower srcTower, Tower tempTower, Tower destTower, int disksMovedPreviously) {
        destTower.push(srcTower.pop());
        if (disksMovedPreviously > 0) {
            moveTower(tempTower, srcTower, destTower, disksMovedPreviously);
        }
    }

    public class Tower {
        private Integer id;
        private java.util.Stack<Integer> stack = new java.util.Stack<>();

        public Tower(Integer id) {
            this.id = id;
        }

        public void push(Integer value) {
            if (stack.size() == 0 || stack.peek() > value) {
                stack.push(value);
                System.out.println("Pushing disk "+value+ " on tower "+ id);
            } else {
                System.out.println("ERROR: Pushing greater disk over smaller disk"+value+" over "+stack.peek()+ " on tower "+ id);
            }
        }

        public Integer pop() {
            if (stack.size() > 0) {
                System.out.println("Removing disk "+stack.peek()+ " on tower "+ id);
                return stack.pop();
            } else {
                System.out.println("ERROR: Removing non-existent disk from tower "+ id);
                return null;
            }
        }

        public int size() {
            return stack.size();
        }

        public Stack getStack() {
            return stack;
        }

    }
}
