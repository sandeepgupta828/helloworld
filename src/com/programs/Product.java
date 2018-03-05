package com.programs;


public class Product {

}

/**
     Write a function which will receive an array of arbitrary size, containing numbers as its elements and return an array of equal length. In the return array, element at given index will be the product of all elements in the input array except the element at that index in the input array.
     Example:
     Input [1,2,3,4]
     Output: [24,12,8,6]
     */
/*
N elements
Scan for product = N steps
For all i: P*A[i]

Then For all i:

    P/A[i]

    Handle special case of A[i] == 0

    Input [1,2,0,4]
    Output [0, 0, P (without 0 multiplied), 0]]

    Brute force:
   [1,2,3,4]

   For all i: Multiply to P and skip A[i]

   For all i;
     For all j:
          compute P, with skip multiplying A[i]

 ON^2
 */

// [1,2,0,4]
    /*
    Integer[] ComputeProduct(Integer[] array) {
        // put null checks on array
        Integer[] output = new Integer[array.length];
        // compute the product
        Long product = 1;
        int countOfZero = 0;
        for (int i=0;i<array.length;i++) {
            if (!array[i].equals(0)) {
                product *= array[i];
            } else {
                ++countOfZero; // countOfZero  = 1
            }
        }
        // product = 8
        if (countOfZero > 1) {
            //handle special case
            for(int i=0;i<array.length;i++) {
                output[i] = 0;
            } else {
                for (int i=0;i<array.length;i++) { // [1,2,0,4] P=8
                    if (!array[i].equals(0)) {
                        if (countOfZero == 1) {
                            output[i] = 0;
                        } else {
                            output[i]=product/array[i];
                        }
                    } else {
                        output[i]=product;
                    }
                }
            }
            return output;
        }

    }
*/