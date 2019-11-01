package com.programs.vanitynumermatch;

import java.util.*;
import java.util.stream.Collectors;

public class VanityNumberMatch {
    /*
     * Complete the 'vanity' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts following parameters:
     *  1. STRING_ARRAY codes
     *  2. STRING_ARRAY numbers
     */

    private static Map<Character, Integer> t9Map = new HashMap<>();

    static {
        t9Map.put('A', 2);
        t9Map.put('B', 2);
        t9Map.put('C', 2);
        t9Map.put('D', 3);
        t9Map.put('E', 3);
        t9Map.put('F', 3);
        t9Map.put('G', 4);
        t9Map.put('H', 4);
        t9Map.put('I', 4);
        t9Map.put('J', 5);
        t9Map.put('K', 5);
        t9Map.put('L', 5);
        t9Map.put('M', 6);
        t9Map.put('N', 6);
        t9Map.put('O', 6);
        t9Map.put('P', 7);
        t9Map.put('Q', 7);
        t9Map.put('R', 7);
        t9Map.put('S', 7);
        t9Map.put('T', 8);
        t9Map.put('U', 8);
        t9Map.put('V', 8);
        t9Map.put('W', 9);
        t9Map.put('X', 9);
        t9Map.put('Y', 9);
        t9Map.put('Z', 9);
    }

    public static List<String> vanity(List<String> codes, List<String> numbers) {
        Set<String> numberCodes = codes.stream().map(code -> mapCodeToNumber(code)).collect(Collectors.toSet());
        List<String> matchingNumbers = numbers.stream().filter(number -> hasAtLeastOneCode(numberCodes, number)).collect(Collectors.toList());
        Collections.sort(matchingNumbers);
        return matchingNumbers;
    }

    private static String mapCodeToNumber(String code) {
        StringBuilder builder = new StringBuilder();
        char[] charArray = code.toCharArray();
        for (char ch : charArray) {
            builder.append(t9Map.get(ch));
        }
        return builder.toString();
    }

    private static boolean hasAtLeastOneCode(Set<String> numberCodes, String number) {
        return numberCodes.stream().anyMatch(code -> number.contains(code));
    }

    public static void main(String[] args) {
        System.out.println(VanityNumberMatch.vanity(Arrays.asList("TWLO", "CODE", "HTCH"), Arrays.asList("+17474824380", "+14157088956", "+919810155555", "+15109926333", "+1415123456")));
    }
}
