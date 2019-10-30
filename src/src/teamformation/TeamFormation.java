package teamformation;


import java.util.*;

/**
 * Problem: Give a list of scores for employees, find the employees for a given team_size with max scores either from
 * first k scores or last k scores. If two employees have same score prefer one having lower index.
 */
public class TeamFormation {
    public static List<Pair> teamFormation(List<Integer> score, int team_size, int k) {
        // Write your code here

        List<Pair> selectedFolks = new ArrayList<>();
        Comparator<Pair> comparator = (p1, p2) -> {
            int valueDiff = p2.getKey().compareTo(p1.getKey());
            if (valueDiff !=0) return valueDiff;
            return p1.getValue().compareTo(p2.getValue());
        };

        PriorityQueue<Pair> priorityQueue = new PriorityQueue<Pair>(k, comparator);
        int i = 0;
        int frontIndex = 0;
        int endIndex = 0;
        for (;i<k;i++) {
            frontIndex = i;
            endIndex = score.size() -1 -i;
            priorityQueue.add(new Pair(score.get(frontIndex), frontIndex));
            if (endIndex > frontIndex) {
                priorityQueue.add(new Pair(score.get(endIndex), endIndex));
            }
        }
        int j=0;
        for (;j<team_size;j++) {
            Pair maxScore = priorityQueue.poll();
            selectedFolks.add(maxScore);
            if (maxScore.getValue() <= frontIndex) {
                frontIndex++;
                if (frontIndex < score.size()) {
                    priorityQueue.add(new Pair(score.get(frontIndex), frontIndex));
                }
            }
            if (maxScore.getValue() >= endIndex) {
                endIndex--;
                if (endIndex >= 0) {
                    priorityQueue.add(new Pair(score.get(endIndex), endIndex));
                }
            }
        }
        return selectedFolks;
    }

    public static void main(String[] args) {
        System.out.println(TeamFormation.teamFormation(Arrays.asList(10, 30, 30, 50, 99, 29, 41, 22, 8), 3, 3));
    }
}
