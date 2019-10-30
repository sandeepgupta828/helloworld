package com.programs;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Method to process events in sorted order by multiple attributes
 */
public class EventOrder {

    private static final String SPACE = " ";

    public static List<String> getEventsOrder(String team1, String team2, List<String> events1, List<String> events2) {
        // process events

        List<Event> eventList1 = events1.stream().map(eventData -> new Event(team1, eventData)).collect(Collectors.toList());
        List<Event> eventList2 = events2.stream().map(eventData -> new Event(team2, eventData)).collect(Collectors.toList());

        List<Event> mergedList = new ArrayList<>();
        mergedList.addAll(eventList1);
        mergedList.addAll(eventList2);

        Collections.sort(mergedList, (e1, e2) -> {
            int timeDiff = e1.time - e2.time;
            if (timeDiff != 0) return timeDiff;

            int eventDiff = e1.eventName.ordinal() - e2.eventName.ordinal();
            if (eventDiff != 0) return eventDiff;

            int playerNameDiff = e1.playerName.compareTo(e2.playerName);
            if (playerNameDiff != 0) return playerNameDiff;

            int teamNameDiff = e1.teamName.compareTo(e2.teamName);
            if (teamNameDiff != 0) return teamNameDiff;
            return 0;
        });

        return mergedList.stream().map(event -> event.toString()).collect(Collectors.toList());
    }

    public enum EventName {
        G,
        Y,
        R,
        S
    }

    public static class Event {
        String teamName;
        String playerName;
        Integer time;
        Integer additionalTime;
        EventName eventName;
        String subPlayerName;

        public Event(String teamName, String data) {
            this.teamName = teamName;
            String[] dataArr = data.split(" ");
            StringBuilder playerNameBuilder = new StringBuilder();
            int i = 0;
            while (!Character.isDigit(dataArr[i].charAt(0))) {
                playerNameBuilder.append(dataArr[i] + " ");
                i++;
            }

            playerName = playerNameBuilder.toString().trim();

            String[] timeArr = dataArr[i].split("\\+");
            time = Integer.parseInt(timeArr[0]);

            if (timeArr.length > 1) {
                additionalTime = Integer.parseInt(timeArr[1]);
            }

            i++;

            eventName = EventName.valueOf(dataArr[i]);

            i++;

            if (dataArr.length == i + 1) {
                subPlayerName = dataArr[i];
            }
        }

        @Override
        public String toString() {
            return teamName + SPACE + playerName + SPACE + time.toString() + (additionalTime != null ? "+" + additionalTime.toString() : "") + SPACE + eventName + (subPlayerName != null ? SPACE + subPlayerName : "");
        }
    }

    public static void main(String[] args) {
        System.out.println(EventOrder.getEventsOrder("team1", "team2", Arrays.asList("sandeep gupta 10 Y", "sandeep gupta 11 S shyam", "sam 19 Y", "andy 16+2 R"), Arrays.asList("brad 10 G", "doba 10 Y", "wilson 25 G", "wilson 14 Y", "tola 9 Y")));
    }

}
