package com.programs;

/**
 * Add two arbritrary long numbers
 */
public class AddNumbers {

    public String addNumbers(String num1, String num2) {
        char[] num1Ar = num1.toCharArray();
        char[] num2Ar = num2.toCharArray();

        int length1 = num1Ar.length;
        int length2 = num2Ar.length;

        StringBuilder result = new StringBuilder(); // reverse later on

        int carry = 0;
        int i = 0;
        for (; i < length1 && i < length2; i++) {
            char c1 = num1Ar[length1 - 1 - i];
            char c2 = num2Ar[length2 - 1 - i];
            int v1 = c1 - '0';
            int v2 = c2 - '0';
            carry = updateCarryAndResult(result, carry, v1, v2);
        }
        i = i-1;
        // exit condition either length1 or length is exhausted or both are exhausted
        // figure out which one
        if (length1 - 1 - i == 0) {
            // it is length1, so iterate over length2
            carry = executeRemaining(num2Ar, length2, result, carry, i);
        } else {
            // it is length2, , so iterate over length1
            carry = executeRemaining(num1Ar, length1, result, carry, i);
        }

        if (carry > 0) {
            result.append(carry);
        }
        return result.reverse().toString();
    }

    private int executeRemaining(char[] numAr, int length, StringBuilder result, int carry, int i) {
        for (int j = i+1; j < length; j++) {
            char c = numAr[length - 1 - j];
            int v = c - '0';
            carry = updateCarryAndResult(result, carry, 0, v);
        }
        return carry;
    }

    private int updateCarryAndResult(StringBuilder result, int carry, int icInteger1, int icInteger2) {
        int sum = icInteger1 + icInteger2 + carry;
        if (sum > 9) {
            carry = sum / 10;
            sum = sum % 10;
        }
        // push to result
        result.append(sum);
        return carry;
    }


    public static void main(String args[]) throws Exception {
        AddNumbers addNumbers = new AddNumbers();
        System.out.println(addNumbers.addNumbers("111111", "9999999999999999999999"));
    }

}
