package com.programs.twosg;

import java.util.*;

public class FriendCircles {

    public static class Student {
        public Student(int id) {
            this.id = id;
        }
        int id;
        int circleId = -1;
        List<Student> friends = new ArrayList<>();
    }

    public static int friendCircles(List<String> friends) {
        char[][] friendMatrix = new char[friends.size()][];
        for (int i=0;i<friends.size();i++) {
            if (friends.get(i).length() != friends.size()) {
                throw new RuntimeException("Invalid input (matrix missing data)");
            }
            friendMatrix[i] = friends.get(i).toCharArray();
        }

        // ensure it is valid
        for (int i=0;i<friendMatrix.length;i++) {
            for (int j = 0; j < friendMatrix[i].length; j++) {
                if (friendMatrix[i][j] != friendMatrix[j][i]) {
                    throw new RuntimeException("Invalid input (matrix not symmetrical");
                }
            }
        }

        Map<Integer, Student> idToStudentMap = new HashMap<>();

        // initialize
        for (int i=0;i<friends.size();i++) {
            idToStudentMap.put(i, new Student(i));
        }

        // build friend graph
        for (int i=0;i<friendMatrix.length;i++) {
            for (int j=i+1;j<friendMatrix[i].length;j++) {
                if (friendMatrix[i][j] == 'Y') {
                    idToStudentMap.get(i).friends.add(idToStudentMap.get(j));
                    idToStudentMap.get(j).friends.add(idToStudentMap.get(i));
                }
            }
        }

        // traverse graph
        Set<Set<Integer>> friendCircles = new HashSet<>();
        int nextCircleId = 0;
        for (int i=0;i<friends.size();i++) {
            if (idToStudentMap.get(i).circleId == -1) {
                // if this student is not already part of any existing circle
                Set<Integer> friendIds = getAndMarkFriendsForStudent(idToStudentMap.get(i), nextCircleId);
                friendCircles.add(friendIds);
            }
        }
        return friendCircles.size();
    }

    private static Set<Integer> getAndMarkFriendsForStudent(Student student, Integer circleId) {
        Set<Integer> friendSet = new HashSet<>();
        student.circleId = circleId;
        friendSet.add(student.id);
        for (Student friend: student.friends) {
            if (friend.circleId == -1) {
                friendSet.addAll(getAndMarkFriendsForStudent(friend, circleId));
            }
        }
        return friendSet;
    }

    public static void main(String[] args) throws Exception {
        List<String> friendList = new ArrayList<>();
        friendList.add("YNNY");
        friendList.add("NYNN");
        friendList.add("NNYN");
        friendList.add("YNNY");

        System.out.println(friendCircles(friendList));
    }
}
