package com.programs.findbytearray;

import java.io.*;
import java.util.*;

/**
 * Write a function to find a given byte array in a given File containing bytes
 * Avoid reading the entire file in memory, and minimize on memory overhead.
 * Optimize on buffer memory used to fetch data from file
 *
 * The approach is to keep 2 buffers, with each buffer size of byte array to be searched
 * The drawback is that there could be lot of IO if buffer size is small. This could be prevented by reading a larger chunk (multiple of buffer size)of file
 * and then reading the 2 buffers off those chunk
 *
 *
 * we read the file as sequence of equal sized memory buffers until file ends
 * we need at least 2 buffers to be able to search for byte array we do the comparison in a sliding window fashion
 * we move a sliding window of size of buffer from B1-B2, once done with B1 we swap B2-B1, with fresh bytes read into B1
 */

class FindByteArray {

    public static boolean isByteArrayInFile(String filePath, byte[] byteArray) throws IOException {

        FileInputStream in = null;
        try {
            int arrLen = byteArray.length;
            in = new FileInputStream(filePath);
            byte[] buf1 = new byte[arrLen];
            byte[] buf2 = new byte[arrLen];

            int buf1Count = in.read(buf1);
            int buf2Count = in.read(buf2);
            boolean isBuf1First = true;

            while (true) {
                // compare byteArrray arrLen times
                if (isBuf1First && buf1Count == arrLen) {
                    if (compare(buf1, buf2, buf2Count, byteArray)) return true;
                    buf1Count = in.read(buf1);
                    isBuf1First = false;

                } else if (!isBuf1First && buf2Count == arrLen) {
                    if (compare(buf2, buf1, buf1Count, byteArray)) return true;
                    buf2Count = in.read(buf2);
                    isBuf1First = true;
                } else {
                    break;
                }
            }
        } finally {
            if (in != null) {
                in.close();;
            }
        }
        return false;
    }

    private static boolean compare(byte[] buf1, byte[] buf2, int limitBuf2, byte[] byteArr) {
        // compare byteArr across buf1 and buf2 upto limitBuf2
        int len = byteArr.length;
        for (int i=0;i<byteArr.length;i++) {
            boolean firstPartCompare = comparebytes(buf1, i, byteArr, 0,len-i);
            Boolean secondPartCompare = null;
            if (firstPartCompare && i > 0 && i <= limitBuf2) {
                secondPartCompare = comparebytes(buf2, 0, byteArr, len-i, i);
            }
            if (firstPartCompare && (secondPartCompare == null || secondPartCompare)) return true;
        }
        return false;
    }

    private static boolean comparebytes(byte[] buf, int startBuf, byte[] byteArr, int startBArr, int bytesToCompare) {
        for (int i=startBuf, j=startBArr;i<startBuf+bytesToCompare && j < startBArr+bytesToCompare;i++, j++) {
            if (buf[i] != byteArr[j]) return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/new/tmp/file.txt");
        byte[] byteBuf = new byte[]{2, 1, 1, 1, 2};
        byte[] byteSearch = new byte[] {1, 1};
        fileOutputStream.write(byteBuf);
        fileOutputStream.close();
        System.out.println(FindByteArray.isByteArrayInFile("/Users/new/tmp/file.txt", byteSearch));
    }
}