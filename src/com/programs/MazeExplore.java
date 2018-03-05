package com.programs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MazeExplore {

    public static  void main(String[] argsv) {
        MazeExplore mazExplore = new MazeExplore();
        // sample maze data: 0 => open, 1 => blocked, 2 => treasure
        Integer[][] mazeData = new Integer[][] {
                {0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 0, 0, 2}
        };

        mazExplore.solveMaze(new MazeImpl(1, 3, mazeData));
    }

    public static abstract class Maze {
        abstract boolean moveLeft();

        abstract boolean moveRight();

        abstract boolean moveTop();

        abstract boolean moveBottom();

        abstract boolean foundTreasure();
    }

    /*
    Sample implementation of Maze
     */
    public static class MazeImpl extends   Maze {
        private Integer curX;
        private Integer curY;
        Integer[][] maze;
        private Integer maxX;
        private Integer maxY;

        public MazeImpl(Integer curX, Integer curY, Integer[][] maze) {
            this.curX = curX;
            this.curY = curY;
            this.maze = maze;
            this.maxX = maze.length-1;
            this.maxY = maze[0].length-1;
        }

        boolean moveLeft() {
            if (curY-1 >= 0 && maze[curX][curY-1] != 1) {
                curY-=1;
                return true;
            }
            return false;
        }

        boolean moveRight() {
            if (curY+1 <= maxY && maze[curX][curY+1] != 1) {
                curY+=1;
                return true;
            }
            return false;
        }

        boolean moveTop() {
            if (curX-1 >= 0 && maze[curX-1][curY] != 1) {
                curX-=1;
                return true;
            }
            return false;
        }

        boolean moveBottom() {
            if (curX+1 <= maxX && maze[curX+1][curY] != 1) {
                curX+=1;
                return true;
            }
            return false;        }

        boolean foundTreasure() {
            return maze[curX][curY] == 2;
        }
    }

    private Set<XY> visitedSet = new HashSet<>();

    /*
    Key things:
    We solve by projecting a 2D exploration space (abstracted as XY) that starts at (0,0) onto Maze's 2D space
    As we explore we construct potential paths (series of L, R, T, B) in BFS manner
    To avoid cycles, we skip what is already visited by maintaining a visited set on XY.
    Only valid paths (if maze can follow the path) are eligible for further exploration around them. Invalid paths are ignored.
     */
    public boolean solveMaze(Maze maze) {
        // BFS is more suited here. We use BFS over DFS as we don't know exploration space, and it could lead into a unwanted depth
        Deque<XY> explore = new ArrayDeque<>();
        explore.add(new XY());

        while (explore.size() != 0) {
            XY curPosition = explore.removeFirst();
            String pathMoved = moveToPath(maze, curPosition.pathFromStart, false);
            if (pathMoved.length() != curPosition.pathFromStart.length()) {
                // we couldn't move the path: we hit blockers or maze walls, thus go back to original location
                moveToPath(maze, new StringBuilder(pathMoved).reverse().toString(), true);
                continue;
            }
            visitedSet.add(curPosition);
            // moved to current position in maze successfully
            if (maze.foundTreasure()) {
                System.out.println(curPosition);
                return true;
            }
            // go back to starting location in maze (essentially reset)
            moveToPath(maze, new StringBuilder(curPosition.pathFromStart).reverse().toString(), true);

            // add new potential explorations
            XY leftOfCur = new XY(curPosition.getLeft(), curPosition.pathFromStart+"L");
            if (!visitedSet.contains(leftOfCur)) {
                explore.addLast(leftOfCur);
            }
            XY rightOfCur = new XY(curPosition.getRight(), curPosition.pathFromStart+"R");
            if (!visitedSet.contains(rightOfCur)) {
                explore.addLast(rightOfCur);
            }
            XY topOfCur = new XY(curPosition.getTop(), curPosition.pathFromStart+"T");
            if (!visitedSet.contains(topOfCur)) {
                explore.addLast(topOfCur);
            }
            XY bottomOfCur = new XY(curPosition.getBottom(), curPosition.pathFromStart+"B");
            if (!visitedSet.contains(bottomOfCur)) {
                explore.addLast(bottomOfCur);
            }

        }
        return false;
    }

    private String moveToPath(Maze maze, String path, boolean reverse) {
        String pathMoved = "";
        boolean moved = false;
        for (int i = 0; i < path.length(); i++) {
            switch (path.charAt(i)) {
                case 'L':
                    moved = reverse ? maze.moveRight() : maze.moveLeft();
                    break;
                case 'R':
                    moved = reverse ? maze.moveLeft() : maze.moveRight();
                    break;
                case 'T':
                    moved = reverse ? maze.moveBottom() : maze.moveTop();
                    break;
                case 'B':
                    moved = reverse ? maze.moveTop() : maze.moveBottom();
                    break;
            }
            if (moved) {
                pathMoved += path.charAt(i);
            } else  {
                // this is invalid path
                break;
            }
        }
        return pathMoved;
    }

    public class XY {
        Integer x = 0;
        Integer y = 0;
        String pathFromStart = "";

        public XY() {
        }

        public XY(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }

        public XY(XY xy, String path) {
            x = xy.x;
            y = xy.y;
            this.pathFromStart = path;
        }

        public XY getLeft() {
            return new XY(x , y-1);
        }

        public XY getRight() {
            return new XY(x, y+1);
        }

        public XY getTop() {
            return new XY(x-1, y );
        }

        public XY getBottom() {
            return new XY(x+1, y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            XY xy = (XY) o;
            return Objects.equals(x, xy.x) &&
                    Objects.equals(y, xy.y);
        }

        @Override
        public int hashCode() {

            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "XY{" +
                    "x=" + x +
                    ", y=" + y +
                    ", pathFromStart='" + pathFromStart + '\'' +
                    '}';
        }
    }

}