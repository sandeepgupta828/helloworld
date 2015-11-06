/**
 * Created by sagupta on 11/2/15.
 */
public class MedianOfSortedArrays {


    public int solution(int[] inAr1, int[] inAr2) {
        int len1 = inAr1.length;
        int len2 = inAr2.length;

        Integer subArLen1 = len1;
        Integer subArLen2 = len2;

        Integer b1= 0, b2=0;
        Integer e1 = len1-1, e2 = len2-1;

        for(;subArLen1 > 1 || subArLen2 > 1;) {
            subArLen1 = e1-b1+1;
            Integer midIndex1 = b1 + (subArLen1-1)/2;

            subArLen2 = e2-b2+1;
            Integer midIndex2 = b2 + (subArLen2-1)/2;
            if (inAr1[midIndex1] > inAr2[midIndex2]) {
                System.out.println("Mid1="+inAr1[midIndex1]+", Mid2="+inAr2[midIndex2]);
                if (subArLen1 > 1 || subArLen2 > 1) {
                    if (subArLen1 > 1) {
                        e1 = midIndex1 - 1;
                    }
                    if (subArLen2 > 1) {
                        b2 = midIndex2 + 1;
                    }
                } else {
                    return (inAr1[midIndex1] + inAr2[midIndex2]) / 2;
                }
            } else if (inAr1[midIndex1] < inAr2[midIndex2]) {
                System.out.println("Mid1="+inAr1[midIndex1]+", Mid2="+inAr2[midIndex2]);
                if (subArLen1 > 1 || subArLen2 > 1) {
                    if (subArLen1 > 1) {
                        b1 = midIndex1 + 1;
                    }
                    if (subArLen2 > 1) {
                        e2 = midIndex2 - 1;
                    }
                } else {
                    return (inAr1[midIndex1] + inAr2[midIndex2]) / 2;
                }
            } else {
                return inAr1[midIndex1];
            }
        }
        return 0;
    }
}
