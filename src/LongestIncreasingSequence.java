/**
 * Created by sagupta on 11/3/15.
 */
public class LongestIncreasingSequence {

    public void solution(int[] inAr) {

        int[] lisBackIndex = new int[inAr.length];
        int[] lisLength = new int[inAr.length];
        int indexWithMaxLen = -1;
        int maxSeqLen = -1;

        for (int i=0;i<inAr.length;i++) {
            for (int j=0;j<i;j++) {
                if (inAr[i] > inAr[j] && lisLength[i] < (lisLength[j]+1)) {
                    lisBackIndex[i] = j;
                    lisLength[i] = lisLength[j]+1;
                }
            }
            if (lisLength[i] == 0) {
                lisLength[i] = 1;
                lisBackIndex[i] = i;
            }

            if (maxSeqLen < lisLength[i]) {
                maxSeqLen = lisLength[i];
                indexWithMaxLen = i;
            }
        }
        int[] maxSequenceIndexes = new int[inAr.length];

        int m=0;
        for (int k=indexWithMaxLen;k>=0;) {
            maxSequenceIndexes[m++]=k;
            if (k == lisBackIndex[k]) {
                break;
            } else {
                k = lisBackIndex[k];
            }
        }

        for (int i=m-1;i>=0;i--) {
            System.out.println(Integer.toString(inAr[maxSequenceIndexes[i]]));
        }
    }
}
