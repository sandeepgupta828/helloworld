package com.programs;

/*

You are building an educational website and want to create a simple calculator for students to use. For now, the calculator will only allow addition and subtraction of positive integers.

Given an expression string using the "+" and "-" operators like "5+16-2", write a function to parse the string and evaluate the result.

Sample input/output:
"6+9-12" => 3
"1+2-3+4-5+6-7" => -2
 */

/*
"1+2-3+4-5+6-7" => -2


int r = 1

+2 => r =3
-3 => r = 0
+4 => r = 4
-5 => ...

*/


class ComputeValue {
    public static void main(String[] args) {
        String expression = "123-21-2-101";
        System.out.println(expression + "=" + Integer.toString(computeExpression(expression)));

    }

    private static int computeExpression3(String expression) {
        char[] chars = expression.toCharArray();
        Integer result = 0;
        for (int i=chars.length-1;i >=0;) {
            char ch = chars[i];
            int nextVal = 0;
            int multiplier = 1;
            while (Character.isDigit(ch)) {
                nextVal += Character.getNumericValue(ch)*multiplier;
                multiplier *= 10;
                if (i > 0) {
                    ch = chars[--i];
                } else {
                    break;
                }
            }
            if (!Character.isDigit(ch)) {
                if (ch == '+') {
                    result += nextVal;
                }
                if (ch == '-') {
                    result -= nextVal;
                }
            } else if (i == 0){
                result += nextVal;
            }
            i--;
        }
        return result;
    }

    private static int computeExpression2(String expression) {
        String[] sumParts = expression.split("\\+");
        Integer result = 0;
        if (sumParts.length > 1) {
            for (String sumPart : sumParts) {
                result += getNegSum(sumPart);
            }
        } else {
            return getNegSum(expression);
        }
        return result;
    }

    private static Integer getNegSum(String sumPart) {
        Integer result = 0;
        String[] negParts = sumPart.split("-");
        if (negParts.length > 0) {
            result += Integer.parseInt(negParts[0]);
            for (int i = 1; i<negParts.length;i++) {
                result -= Integer.parseInt(negParts[i]);
            }
        } else {
            result += Integer.parseInt(sumPart);
        }
        return result;
    }

    private static int computeExpression(String expression) {
        char[] expChars = expression.toCharArray();
        // assume string is length > 1
        // validate string length is odd
        int result = 0;
        // "6+9-12" length = 5
        // 123-21
        for (int i=0;i< expression.length();) {
            if (i == 0) {
                String resultVal = computeValue(expChars, i);
                result = Integer.parseInt(resultVal);
                i = resultVal.length();
            } else {
                char op = expChars[i];
                String resultVal = computeValue(expChars, i+1);
                Integer nextVal = Integer.parseInt(resultVal);
                if (op == '+') {
                    result += nextVal;
                } else if (op == '-') {
                    result -= nextVal;
                }
                i += resultVal.length()+1;
            }
        }
        return result;
    }


    private static String computeValue(char[] input, int startIndex) {
        String value = "";
        while((startIndex < input.length) && Character.isDigit(input[startIndex])) {
            value += input[startIndex];
            startIndex++;
        }
        return value;
    }
}

