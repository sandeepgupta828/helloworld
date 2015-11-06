// you can also use imports, for example:

import java.util.*;

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");

class MinAbsSum {


    int[] orIndex;
    int[] orSign;
    int[] opSign;

    public int solution(int[] A) {
        // write your code in Java SE 8
        // sort array by abs values
        orIndex = new int[A.length];
        orSign = new int[A.length];
        opSign = new int[A.length];

        for (int i=0;i<A.length;i++) {
            orIndex[i] = i;
            orSign[i] = A[i] < 0 ? -1 : 1;
            A[i] *=  orSign[i];
        }

        sortAbs(A, orIndex, orSign);

        System.out.println(Arrays.toString(A));

        int pt1 = 0;
        int pt2 = A.length-1;
        int sum=A[0];
        if (sum < 0) {
            sum *= -1;
        }

        while (pt1 < pt2) {
            if ((A[pt2] - A[pt1]) > 0) {
                opSign[pt1] = -1;
                opSign[pt2] = 1;
                A[pt2] -= A[pt1];
                pt1++;
                sum = A[pt2];
                continue;
            } else if ((A[pt2] - A[pt1]) == 0) {
                opSign[pt1] = -1;
                opSign[pt2] = 1;
                A[pt2] -= A[pt1];
                pt1++;
                sum = A[pt2];
                pt2--;
                continue;
            } else if ((A[pt2] - A[pt1]) < 0) {
                if (pt1+1 == pt2) {
                    sum = -1*(A[pt2] - A[pt1]);
                    opSign[pt1] = -1;
                    opSign[pt2] = 1;
                    break;
                } else {
                    A[pt2-1] += A[pt1] - A[pt2];
                    opSign[pt2] = 1;
                    pt2--;
                    continue;
                }
            }
        }
        System.out.println("pt1="+Integer.toString(pt1));
        System.out.println("pt2="+Integer.toString(pt2));
        System.out.println(Arrays.toString(orIndex));
        System.out.println(Arrays.toString(opSign));

        for(int k=0;k<A.length;k++) {
            opSign[k] *= orSign[k];
        }

        return sum;
    }


    void sortAbs(int[] A, int[] orIndex, int[] orSign) {
        int len = A.length;
        for (int j=len;j > 0;j--) {
            for (int i=0;i<j;i++) {
                if ((i+1 < len) && A[i] > A[i+1]) {
                    swap(A,i,i+1);
                    swap(orIndex,i,i+1);
                    swap(orSign, i,i+1);
                }
            }
        }

    }

    void swap(int[] ar, int pos1, int pos2) {
        int temp = ar[pos1];
        ar[pos1] = ar[pos2];
        ar[pos2] = temp;
    }
}