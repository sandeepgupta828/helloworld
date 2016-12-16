package com.programs;

/**
 * Created by sagupta on 11/17/15.
 */
public class Permute {

    void swap(StringBuffer str, int i1, int i2) {
        char temp = str.charAt(i2);
        str.setCharAt(i2, str.charAt(i1));
        str.setCharAt(i1,temp);

    }

    void permute(StringBuffer str, int s, int e) {
        if (s == e) {
            System.out.println(str);
            return;
        }

        permute(str,s+1,e);

        for (int i=s+1;i<=e;i++) {
            // move the starting char to next position
            swap(str,s,i);
            permute(str,s+1,e);
            // restore the position (or backtrack)
            swap(str,i,s);
        }

    }

    void solution(String str) {
        StringBuffer buffer = new StringBuffer(str);
        permute(buffer,0,buffer.length()-1);
    }
}
