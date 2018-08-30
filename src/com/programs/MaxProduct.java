package com.programs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxProduct {

    public static void main(String[] args) {
        MaxProduct maxProduct = new MaxProduct();
        List<Integer> subArrayWithMaxProduct = maxProduct.findMaxProductSubArray(Arrays.asList(new Integer[]{4, 1, -60, 0, -100, -4, 10, 15, -1}));
        //List<Integer> subArrayWithMaxProduct = maxProduct.findMaxProductSubArray(Arrays.asList(new Integer[]{-4, -1, -60, -100, 0, -4, 10, -15, -1}));
        //List<Integer> subArrayWithMaxProduct = maxProduct.findMaxProductSubArray(Arrays.asList(new Integer[] {2, 26, -1, -53, 1, -54, 2, -60, 0, 1, 1, -2, 0, 5,5}));

        System.out.println(subArrayWithMaxProduct);
    }

    List<Integer> findMaxProductSubArray(List<Integer> input) {
        List<Integer> maxProductSub = new ArrayList<>(input.size());
        List<Integer> maxProductStartIndex = new ArrayList<>(input.size());
        List<Integer> absMaxProductStartIndex = new ArrayList<>(input.size());

        List<Integer> maxProductAtIndex = new ArrayList<>(input.size());

        List<Integer> absMaxProductAtIndex = new ArrayList<>(input.size());

        maxProductStartIndex.add(0, 0);
        maxProductAtIndex.add(0, input.get(0));
        absMaxProductAtIndex.add(0, input.get(0));
        absMaxProductStartIndex.add(0, 0);

        Integer indexWithMaxProductSofar = 0;
        Integer maxProductSofar = maxProductAtIndex.get(0);
        for (int i = 1; i < input.size(); i++) {
            Integer lastIndexMaxProduct = maxProductAtIndex.get(i - 1);
            Integer newProduct = lastIndexMaxProduct * input.get(i);
            if (newProduct != 0 && newProduct > input.get(i)) {
                maxProductAtIndex.add(i, newProduct);
                maxProductStartIndex.add(i, maxProductStartIndex.get(i - 1));
            } else {
                maxProductAtIndex.add(i, input.get(i));
                maxProductStartIndex.add(i, i);
            }
            if (maxProductAtIndex.get(i) > maxProductSofar) {
                maxProductSofar = maxProductAtIndex.get(i);
                indexWithMaxProductSofar = i;
            }

            // let's see if absolute max product (with sign) trumps it

            Integer lastIndexAbsMaxProduct = absMaxProductAtIndex.get(i - 1);
            Integer newAbsProduct = lastIndexAbsMaxProduct * input.get(i);
            if (input.get(i) < 0 && newAbsProduct < 0) {
                //recompute if there is negative index seen in the abs product
                Integer productToRemove = 1;
                Integer indexOfNegativeNumber = -1;
                for (int k = absMaxProductStartIndex.get(i - 1); k < i; k++) {
                    productToRemove *= input.get(k);
                    if (input.get(k) < 0) {
                        indexOfNegativeNumber = k;
                        break;
                    }
                }
                if (indexOfNegativeNumber >= 0 && productToRemove !=0) {
                    Integer altProduct = newAbsProduct / productToRemove;
                    if (altProduct != 0 && altProduct > maxProductAtIndex.get(i)) {
                        maxProductAtIndex.set(i, altProduct);
                        maxProductStartIndex.set(i, indexOfNegativeNumber+1);
                        if (maxProductAtIndex.get(i) > maxProductSofar) {
                            maxProductSofar = maxProductAtIndex.get(i);
                            indexWithMaxProductSofar = i;
                        }
                    }
                }
            }
            if (newAbsProduct != 0 && (Math.abs(newAbsProduct) > Math.abs(input.get(i)))) {
                absMaxProductAtIndex.add(i, newAbsProduct);
                absMaxProductStartIndex.add(i, absMaxProductStartIndex.get(i - 1));
            } else {
                absMaxProductAtIndex.add(i, input.get(i));
                absMaxProductStartIndex.add(i, i);
            }
            if (absMaxProductAtIndex.get(i) > maxProductSofar) {
                maxProductSofar = absMaxProductAtIndex.get(i);
                indexWithMaxProductSofar = i;
            }
        }
        if (absMaxProductAtIndex.get(indexWithMaxProductSofar) > maxProductAtIndex.get(indexWithMaxProductSofar)) {
            for (int i = absMaxProductStartIndex.get(indexWithMaxProductSofar); i <= indexWithMaxProductSofar; i++) {
                maxProductSub.add(input.get(i));
            }
        } else {
            for (int i = maxProductStartIndex.get(indexWithMaxProductSofar); i <= indexWithMaxProductSofar; i++) {
                maxProductSub.add(input.get(i));
            }
        }
        return maxProductSub;
    }
}
