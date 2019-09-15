package com.programs;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/*

sample input
0000000000
0011100000
0011111000
0010001000
0011111000
0000101000
0000101000
0000111000
0000000000
0000000000

output:
leftEdges=[{1,2}, {2,2}, {3,2}, {4,2}, {5,4}, {6,4}, {7,4}]
rightEdges=[{1,4}, {2,6}, {3,6}, {4,6}, {5,6}, {6,6}, {7,6}]
cellReads=62
 */

public class BlobBoundry {
    private int cellReads = 0;
    private Deque<Loc> rightEdges = new ArrayDeque<>();
    private Deque<Loc> leftEdges = new ArrayDeque<>();

    public static void main(String[] args) throws IOException {
        int blobSizeX = 10, blobSizeY = 10;
        Byte[][] blob = new Byte[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        BlobBoundry blobBoundry = new BlobBoundry();
        blobBoundry.print(blob, blobSizeX, blobSizeY);
        blobBoundry.computeBoundaries(blob, 10, 10);
    }

    /*
    Highlevel:
    Each co-ordinate (row, col) is represented by a Loc object defined below
    First we identify a top left location which meets the criteria of a blob cell i.e. it is occupied and has at least 1 adjacent cell occupied
    Next we if we found a top left location, then assuming we may have a blob we walk circumference of blob 2 ways (1) right periphery and (2) left periphery
    We keep on storing the edges of blob in leftEdges and rightEdges list.
    At last we check if the possible blob is valid i.e. left walk and right walk meet or connect
     */

    public void computeBoundaries(Byte[][] blob, Integer blobMaxRow, Integer blobMaxCol) {
        Loc topLeft = null;
        int row = 0;
        // Identify top left
        while (row < blobMaxRow) {
            for (int col = 0; col < blobMaxCol; col++) {
                Loc cell = new Loc(blob, row, col, blobMaxRow, blobMaxCol);
                if (cell.isOccupied()) {
                    Loc bottomCell = cell.getBottom();
                    Loc rightCell = cell.getRight();
                    if ((bottomCell != null && bottomCell.isOccupied()) || (rightCell != null && rightCell.isOccupied())) {
                        // either right or bottom is occupied (no need to check for top or left because they are already examined by iteration order)
                        // this is top left of a possible blob
                        topLeft = cell;
                        break;
                    }
                }
            }
            if (topLeft == null) {
                row++;
            } else {
                break;
            }
        }

        // if we found a top left corner of a possible blob we walk circumference of blob both ways (1) right periphery and (2) left periphery
        if (topLeft != null) {
            exploreRightPeriphery(topLeft);
            exploreLeftPeriphery(topLeft);
            // make sure the blob is complete
            boolean bottomEdgesMeet = leftEdges.getLast().equals(rightEdges.getLast());
            boolean bottomEdgesConnect = false;
            if (!bottomEdgesMeet) {
                Loc bottomLeftLoc = leftEdges.getLast();
                Loc bottomRightLoc = rightEdges.getLast();
                // see if we can get from bottom left to bottom right
                Loc pointer = bottomLeftLoc.getRight();
                while (pointer != null && pointer.isOccupied()) {
                    if (pointer.equals(bottomRightLoc)) {
                        bottomEdgesConnect = true;
                        break;
                    }
                    pointer = pointer.getRight();
                }
            }
            if (!bottomEdgesMeet && !bottomEdgesConnect) {
                System.out.println("Blob is not complete");
            }
            System.out.println("leftEdges=" + leftEdges);
            System.out.println("rightEdges=" + rightEdges);
            System.out.println("cellReads=" + cellReads);
        }
    }

    /*
    go right until right is null
    then go bottom
       if bottom is null, go left until bottom is not null
       then go bottom, then recurse
     */
    private void exploreRightPeriphery(Loc startLoc) {
        Loc pointer = startLoc;
        Loc right = pointer.getRight();

        while (right != null && right.isOccupied()) {
            pointer = right;
            right = right.getRight();
        }
        rightEdges.addLast(pointer);
        Loc bottom = pointer.getBottom();
        if (bottom != null && bottom.isOccupied()) {
            exploreRightPeriphery(bottom);
        } else {
            Loc left = pointer.getLeft();
            boolean descended = false;
            while (left != null && left.isOccupied()) {
                pointer = left;
                Loc leftBottom = left.getBottom();
                if (leftBottom != null && leftBottom.isOccupied()) {
                    descended = true;
                    pointer = leftBottom;
                    break;
                } else {
                    left = left.getLeft();
                }
            }
            if (descended) {
                // we were able to descend down
                exploreRightPeriphery(pointer);
            }
        }
    }

    /*
    Exact opposite of previous method:
    go left until left is null
    then go bottom
       if bottom is null, go right until bottom is not null
       then go bottom, then recurse
     */
    private void exploreLeftPeriphery(Loc startLoc) {
        Loc pointer = startLoc;
        Loc left = pointer.getLeft();

        while (left != null && left.isOccupied()) {
            pointer = left;
            left = left.getLeft();
        }
        leftEdges.addLast(pointer);
        Loc bottom = pointer.getBottom();
        if (bottom != null && bottom.isOccupied()) {
            exploreLeftPeriphery(bottom);
        } else {
            Loc right = pointer.getRight();
            boolean descended = false;
            while (right != null && right.isOccupied()) {
                pointer = right;
                Loc rightBottom = right.getBottom();
                if (rightBottom != null && rightBottom.isOccupied()) {
                    descended = true;
                    pointer = rightBottom;
                    break;
                } else {
                    right = right.getRight();
                }
            }
            if (descended) {
                // we were able to descend down
                exploreLeftPeriphery(pointer);
            }
        }
    }

    public void print(Byte[][] blob, int maxRow, int maxCol) {
        for (int i=0;i<maxRow;i++) {
            for (int j=0;j<maxCol;j++) {
                if (j > 0) {
                    System.out.print(" ");
                }
                System.out.print(blob[i][j]);
            }
            System.out.println();
        }
    }

    private class Loc {
        Integer row = -1, col = -1, maxRow = -1, maxCol = -1;
        Byte[][] blob;

        public Loc() {
        }

        public Loc(Byte[][] blob, Integer row, Integer col, Integer maxRow, Integer maxCol) {
            this.blob = blob;
            this.row = row;
            this.col = col;
            this.maxRow = maxRow;
            this.maxCol = maxCol;
        }

        public Loc getRight() {
            if (col + 1 < maxCol) {
                return new Loc(blob, row, col + 1, maxRow, maxCol);
            }
            return null;
        }

        public Loc getLeft() {
            if (col - 1 >= 0) {
                return new Loc(blob, row, col - 1, maxRow, maxCol);
            }
            return null;
        }

        public Loc getBottom() {
            if (row + 1 < maxRow) {
                return new Loc(blob, row + 1, col, maxRow, maxCol);
            }
            return null;
        }

        public Loc getTop() {
            if (row - 1 >= 0) {
                return new Loc(blob, row - 1, col, maxRow, maxCol);
            }
            return null;
        }

        public Integer getRow() {
            return row;
        }

        public void setRow(Integer row) {
            this.row = row;
        }

        public Integer getCol() {
            return col;
        }

        public void setCol(Integer col) {
            this.col = col;
        }

        public boolean isOccupied() {
            ++cellReads;
            return this.blob[row][col] == 1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Loc loc = (Loc) o;
            return Objects.equals(row, loc.row) &&
                    Objects.equals(col, loc.col);
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append(row+","+col);
            sb.append('}');
            return sb.toString();
        }
    }
}
