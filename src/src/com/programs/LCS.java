package com.programs;

/**
 * Created by sagupta on 10/29/15.
 */
public class LCS {

    public int solution(String s1, String s2) {

        int s1L = s1.length();
        int s2L = s2.length();

        int[][] lcsL = new int[s1L][s2L];

        for (int i=0;i<s1L;i++) {
            for (int j=0;j<s2L;j++) {
                if (i==0 && j==0) {
                    lcsL[i][j] += s1.charAt(i) == s2.charAt(j) ? 1 : 0;
                }
                if (i==0 && j > 0) {
                    lcsL[i][j] = lcsL[i][j-1];
                    lcsL[i][j] += s1.charAt(i) == s2.charAt(j) ? 1 : 0;
                }
                if (i > 0 && j==0) {
                    lcsL[i][j] = lcsL[i-1][j];
                    lcsL[i][j] += s1.charAt(i) == s2.charAt(j) ? 1 : 0;
                }
                if (i>0 && j>0) {
                    lcsL[i][j] = max(lcsL[i][j-1],lcsL[i-1][j]);
                    lcsL[i][j] += s1.charAt(i) == s2.charAt(j) ? 1 : 0;

                }
            }
        }

        return lcsL[s1L-1][s2L-1];
    }

    private int max (int x, int y) {
        return x > y ? x : y;
    }
}
