package com.programs.wildcardtype;

import java.util.Arrays;
import java.util.List;

public class WildCardType {

    public static double sum1(List<? extends Number> list) {
        double sum = 0;
        for (Number number: list) {
            sum += number.doubleValue();
        }
        return sum;
    }

    public static double sum2(List<? super Integer> list) {
        double sum = 0;
        for (int i=0;i<list.size();i++) {
            sum += ((Number)list.get(i)).doubleValue();
        }
        return sum;
    }

    public static void main(String[] args) {
        List<Integer> integerList = Arrays.asList(1 , 2);
        List<Double> doubleList = Arrays.asList(1.1 , 2.2);
        List<Number> numbersList = Arrays.asList(1.1 , 2.2);
        System.out.println(sum1(integerList));
        System.out.println(sum1(doubleList));
        System.out.println(sum2(integerList));
        //System.out.println(sum2(doubleList)); // not possible
        System.out.println(sum2(numbersList));
    }
}
