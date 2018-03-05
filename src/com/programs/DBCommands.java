package com.programs;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DBCommands {

    public static void main(String[] args)  {
        /*
        Scanner in = new Scanner(System.in);
        int fileContents_size = 0;
        fileContents_size = Integer.parseInt(in.nextLine().trim());

        String[] fileContents = new String[fileContents_size];
        for(int i = 0; i < fileContents_size; i++) {
            String fileContents_item;
            try {
                fileContents_item = in.nextLine();
            } catch (Exception e) {
                fileContents_item = null;
            }
            fileContents[i] = fileContents_item;
        }
        */
        Instant instant = Instant.ofEpochMilli(1512779255959L);
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.of("America/Los_Angeles"));
        System.out.print(String.format("%d-%d-%dT%d:%d:%d", ldt.getMonthValue(), ldt.getDayOfMonth(),
                ldt.getYear(), ldt.getHour(), ldt.getMinute(), ldt.getSecond()));


    }
}
