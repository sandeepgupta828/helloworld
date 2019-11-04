package com.programs.smssplitting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SMSSplitting {
    public static final int MAX_SEG_LEN = 160;
    /*
     * Complete the 'segments' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts STRING message as parameter.
     */

    public static List<String> segments(String message) {
        if (message != null) {
            List<String> result = new ArrayList();
            int messageLen = message.length();
            if (messageLen <= MAX_SEG_LEN) {
                result.add(message);
            } else {
                // estimate number of segments
                int segmentCount = messageLen/(MAX_SEG_LEN-5); // 155 = 160 - 5 as suffix length is atleast 5 characters (n/m) with n=m=1 character
                int countDigitsInSegmentCount = (int)Math.ceil(Math.log10(segmentCount));
                // re-estimate segments now that we know how many digits would be there in n, m
                //suffix character count
                int suffixCount = 3 + 2*countDigitsInSegmentCount;
                if ((MAX_SEG_LEN-suffixCount) < 1) {
                    throw new RuntimeException("Cannot package with given SMS segment length");
                }
                segmentCount = messageLen/(MAX_SEG_LEN-suffixCount);
                if (messageLen%(MAX_SEG_LEN-suffixCount) != 0) {
                    segmentCount++;
                }
                int messageLengthInSeg = MAX_SEG_LEN-suffixCount;

                // account for not breaking words (tbd)


                // now split the message in chunks of length = (160-suffixCount)
                int count = 1;
                for (int index=0;index < messageLen;index+=messageLengthInSeg) {
                    result.add(message.substring(index, Math.min(index+messageLengthInSeg, messageLen)) + "(" + count++ + "/" + segmentCount + ")");
                }
            }
            return result;
        }
        return Collections.emptyList();
    }

    public static void main(String[] args) {
        System.out.println(SMSSplitting.segments("sandeep gupta abc"));
    }
}
