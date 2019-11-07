package com.programs;

import java.util.*;

public class FindTaskOrder {
    /* Problem: Design a task scheduler to ensure ordering the tasks based on the dependencies.
     *
     * It takes several focused s/w teams (AV) to build an autonomous vehicle software. Tracking, Motion planning, Localization, Prediction etc
     * Each of these teams would define their own software "task" which can be run on the car. If they want to test their software (before deploying
     * it onto the car), they would want to make use of a "Tasks platform" to simulate/run their tasks. These tasks might have dependencies b/w them
     * so we need to help the teams to manage running their tasks on the platform without breaking the dependencies.
     *
     *
     * Input:
     *  - char array (or string) representing "tasks" that the Tasks platform needs to execute. Each char represents one task.
     *  - list of tuples representing the dependencies b/w the tasks - where a tuple (T, M) would mean that task 'T' needs to be scheduled before we can schedule 'M'.
     *    (Note: There won't be any cyclic dependencies)
     *
     * Output:
     * - valid order of scheduled tasks (honoring the dependency constraint)
     * (Note: there might be more than one valid order, returning back any one valid order is fine)
     *
     * Examples:
     *
     *  Input: tasks = "PTLM",  dependencies = [(T, M), (L, P)]
     *  Output: "TMLP" or "LPTM" or "TLPM" etc
     *
     *  Input: tasks = "PTLM",  dependencies = [(T, M), (M, L), (M, P), (L, P)]
     *  Output: "TMLP"
     *
     */
 /*
 algo:

[(T, M), (L, P)]

P,T,L,M

output?

N=1: P

N=2 (PT):  (1)PT / (2)TP

N=3 (PTL): (1)LPT / (2) TLP, LTP

N=4 (PTLM) : (1) LPTM / (2) TMLP, TLMP, TMPM, LTPM


*/

    public Set<String> findOrder(String taskStr, List<String> taskDepList) {

        Set<String> result = new HashSet<>();
        // precompute dep maps

        // mapBeforeTasks: tasks that must be executed before a given task
        Map<Character, List<Character>> preTasks = new HashMap<>();

        // mapAfterTasks: tasks that must be executed after a given task
        Map<Character, List<Character>> postTasks = new HashMap<>();

        taskDepList.forEach( taskDep -> {
            // populate pre tasks
            List<Character> preTasksList = preTasks.getOrDefault(taskDep.charAt(1), new ArrayList<>());
            preTasksList.add(taskDep.charAt(0));
            preTasks.put(taskDep.charAt(1), preTasksList);

            // populate post tasks
            List<Character> postTasksList = postTasks.getOrDefault(taskDep.charAt(0), new ArrayList<>());
            postTasksList.add(taskDep.charAt(1));
            postTasks.put(taskDep.charAt(0), postTasksList);

        });

        for (int i = 0; i < taskStr.length(); i++) {
            char newTask = taskStr.charAt(i);
            if (result.size() == 0) {
                result.add("" + newTask);
            } else {
                // examine where this new task can be added to previous ordered tasks within result
                Set<String> newTaskOrders = new HashSet<>();
                for (String taskOrder: result) {
                    // for this taskOrder go over each task and see if new task can be added

                    // find the sub tasks string within which this new task can be inserted at any position
                    // if this task has post tasks:
                    // find a position (end) in the task string from left to right  until a post task is encountered
                    // before this end we can insert our new task anywhere

                    // if this task has pre tasks:
                    // find a position (start) in the task string from right to left  until a pre task is encountered.
                    // after this start we can insert our new task anywhere

                    // now between (start,end) if start<end this new task can be inserted anywhere to form new task orders
                    // i.e. between pre and post tasks

                    String preTaskStr = "";
                    String postTaskStr = "";

                    int indexPostTask = 0;
                    int indexPreTask = taskOrder.length()-1;

                    // find "end" or index of post task as described before
                    // this leads to finding post task string which remains unchanged
                    if (postTasks.get(newTask) != null) {
                        while (indexPostTask < taskOrder.length()) {
                            if (postTasks.get(newTask).contains(taskOrder.charAt(indexPostTask))) {
                                postTaskStr = taskOrder.substring(indexPostTask);
                                break;
                            }
                           indexPostTask++;
                        }
                    } else {
                        indexPostTask = taskOrder.length();
                    }

                    // find "start" or index of pre task as described before
                    // this leads to finding pre task string which remains unchanged
                    if (preTasks.get(newTask) != null) {
                        while (indexPreTask >=0) {
                            if (preTasks.get(newTask).contains(taskOrder.charAt(indexPreTask))) {
                                preTaskStr = taskOrder.substring(0, indexPreTask+1);
                                break;
                            }
                            indexPreTask--;
                        }
                    } else {
                        indexPreTask = -1;
                    }
                    // we cannot insert this task in this order as pre task index is after post task
                    if (!preTasks.isEmpty() && !postTasks.isEmpty() && indexPreTask > indexPostTask) continue;

                    // next figure all the new strings

                    // first get the sub task string which we can modify to get new task orders compliant with pre and post tasks
                    String subTaskStr = taskOrder.substring(indexPreTask >= 0? indexPreTask+1 : 0, indexPostTask < taskOrder.length() ? indexPostTask : taskOrder.length());
                    if (subTaskStr.isEmpty()) {
                        // this is empty so simply add the new task in the middle
                        newTaskOrders.add(preTaskStr+newTask+postTaskStr);
                    } else {
                        // insert new task at every position of this subtask to form new task order that then can be sandwiched in the middle
                        newTaskOrders.add(preTaskStr + newTask + subTaskStr + postTaskStr);
                        for (int j=1;j<subTaskStr.length();j++) {
                            newTaskOrders.add(preTaskStr+subTaskStr.substring(0,j) + newTask + subTaskStr.substring(j) + postTaskStr);
                        }
                        newTaskOrders.add(preTaskStr + subTaskStr + newTask + postTaskStr);
                    }
                }
                result = newTaskOrders;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        FindTaskOrder findTaskOrder = new FindTaskOrder();
        System.out.println(findTaskOrder.findOrder("PTML", Arrays.asList("TM","ML","MP","LP")));
    }
}
