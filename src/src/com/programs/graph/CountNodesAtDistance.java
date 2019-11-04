package com.programs.graph;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Count nodes in a general tree at distance n (beings at 0)
 * Then, count nodes in a directed graph at distance n (and this could have cycles)
 */
public class CountNodesAtDistance {

    public static class Node {
        // any other fields...
        String val;
        boolean visited = false;
        List<Node> children = new ArrayList<>();

        @Override
        public String toString() {
            return "Node{" +
                    "val='" + val + '\'' +
                    ", visited=" + visited +
                    '}';
        }
    }

    // do DFS
    public static List<Node> getAllNodes(Node node, int level) {
        if (level < 0 || node == null) return Collections.emptyList();
        // check recursion end condition
        List list = new ArrayList();
        if (level == 0) {
            list.add(node);
            return list;
        }
        if (node.children != null) {
            for (Node child : node.children) {
                list.addAll(getAllNodes(child, level - 1));
            }
        }
        return list;
    }

    /**
     * algo:
     *
     * @param node
     * @param distance
     * @return
     */
    public static List<Node> getAllNodesFromDirectedGraph(Node node, int distance) {
        List nodesAtDistance = new ArrayList();

        Deque<Pair<Node, Integer>> nodesToExplore = new ArrayDeque<>();
        nodesToExplore.add(new Pair(node, distance));
        node.visited = true;

        while (nodesToExplore.size() != 0) {
            Pair<Node, Integer> nextNode = nodesToExplore.removeFirst();
            if (nextNode.getValue() == 0) {
                nodesAtDistance.add(nextNode);
            } else {
                if (nextNode.getKey().children != null) {
                    nodesToExplore.addAll(nextNode.getKey().children
                            .stream()
                            .filter(child -> !child.visited)
                            .map(child -> {
                                child.visited = true;
                                Pair<Node, Integer> pair = new Pair(child, nextNode.getValue() - 1);
                                return pair;
                            })
                            .collect(Collectors.toList()));
                }
            }
        }
        return nodesAtDistance;
    }

    public static void main(String[] args) {

        /*
        a.children = [b, a]
        b.children = [c, e]
        c.children = [a, d]
         */

        Node root = new Node();
        root.val = "a";
        root.children = new ArrayList<>();

        Node child1 = new Node();
        child1.val = "b";
        child1.children = new ArrayList<>();

        Node child2 = new Node();
        child2.val = "c";
        child2.children = new ArrayList<>();

        Node child3 = new Node();
        child3.val = "d";
        child3.children = new ArrayList<>();

        Node child4 = new Node();
        child4.val = "e";
        child4.children = new ArrayList<>();

        root.children.add(root);
        root.children.add(child1);

        child1.children.add(child2);
        child1.children.add(child4);

        child2.children.add(root);
        child2.children.add(child3);

        System.out.println(CountNodesAtDistance.getAllNodesFromDirectedGraph(root, 2));
    }

}
