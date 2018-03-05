package com.programs;// This is the text editor interface.
// Anything you type or change here will be seen by the other person in real time.

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShowMeTheMoney {
    /**
     * Goal is to print all lines on stdin that represent
     * some amount of currency. "Currency" is any number that
     * is prefixed with "$"
     */
    public static void main(String[] args) throws Exception {
        InputStream in = System.in;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = reader.readLine();
        while (line != null) {
            if (line.length() > 0 && line.charAt(0) == '$') {
                Double value = Double.parseDouble(line.substring(1, line.length()));
                System.out.println("money=$"+value);
            }
            line = reader.readLine();
            // check for empty line
        }
    }
}

/*
Hi
500
$500
$500.00
0xdeadbeef
$.72
$0000.01

 */