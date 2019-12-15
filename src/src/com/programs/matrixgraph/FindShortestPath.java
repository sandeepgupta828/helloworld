package com.programs.matrixgraph;

import java.util.*;

/**
 * Find shortest path from given Node A to another Node B in graph
 */
//https://www.baeldung.com/java-dijkstra (similar to it)
public class FindShortestPath {

    public static void main(String[] args) {
        Map<String, Node> mapIdToNode = buildGraph2();
        System.out.println(findShortestPath(mapIdToNode.get("A")));
    }

    public static class Edge {
        public Edge(int weight, Node node) {
            this.weight = weight;
            this.node = node;
        }

        int weight;
        Node node;
    }

    public static class Node {
        public Node(String id) {
            this.id = id;
        }

        String id;
        List<Edge> edgeList = new ArrayList<>();
    }

    /**
     * Maintain an overall map or table of shortest distances between 2 nodes
     * Maintain weight sorted edges per node
     * start with start node
     * iterate over edges of this node (edges are sorted by weight)
     * pick the node with least weight -- this is guaranteed shortest path to it
     * update the table with this shortest path
     * recurse on this node until all nodes are visited and shortest paths are added to table
     * on return from recursion at each level:
     * add transitive shortest paths after a new one is added i.e. if we found a new shortest path(s) from C like CB, CD and we invoked this from node A then
     * we can add transitive paths from A via C (AB, AD) if they do not exist or exist but are of higher weight

     * next pick the node with next least weight -- check if this is shorter than path in table for it
     * (path in the table could be longer or shorter)
     * If shorter, this is the shortest path to it otherwise shortest path is via other nodes
     * recurse on this node again similarly -- visit other nodes through this node
     * do this for all remaining nodes.
     *
     * @param start
     * @return
     */
    public static Map<String, Integer> findShortestPath(Node start) {
        Map<String, Integer> mapNodePairToMinDistance = new HashMap<>();
        Set<String> visitedNodes = new HashSet<>();
        exploreNearestNode(start, mapNodePairToMinDistance, visitedNodes);
        return mapNodePairToMinDistance;
    }

    private static void exploreNearestNode(Node root, Map<String, Integer> mapNodePairToMinDistance, Set<String> visitedNodes) {
        visitedNodes.add(root.id);
        for (Edge edge : root.edgeList) {
            if (!mapNodePairToMinDistance.containsKey(root.id + edge.node.id) || mapNodePairToMinDistance.get(root.id + edge.node.id) > edge.weight) {
                // this node is at shortest distance to root node
                mapNodePairToMinDistance.put(root.id + edge.node.id, edge.weight);
                if (!visitedNodes.contains(edge.node.id)) {
                    exploreNearestNode(edge.node, mapNodePairToMinDistance, visitedNodes);
                }
                Map<String, Integer> transitivePaths = new HashMap<>();
                mapNodePairToMinDistance.keySet().stream().filter(key -> key.startsWith(edge.node.id.substring(0, 1))).filter(key -> !key.endsWith(root.id)).forEach(key -> {
                    if (!mapNodePairToMinDistance.containsKey(root.id + key.substring(1)) || mapNodePairToMinDistance.get(root.id + key.substring(1)) > (edge.weight + mapNodePairToMinDistance.get(key))) {
                        transitivePaths.put(root.id + key.substring(1), edge.weight + mapNodePairToMinDistance.get(key));
                    }
                });
                mapNodePairToMinDistance.putAll(transitivePaths);
            }
        }
    }

    private static Map<String, Node> buildGraph() {
        Map<String, Node> map = new HashMap<>();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");

        map.put("A", nodeA);
        map.put("B", nodeB);
        map.put("C", nodeC);
        map.put("D", nodeD);

        nodeA.edgeList.add(new Edge(2, nodeB));
        nodeA.edgeList.add(new Edge(10, nodeC));
        nodeA.edgeList.add(new Edge(1, nodeD));

        nodeB.edgeList.add(new Edge(3, nodeC));

        nodeC.edgeList.add(new Edge(6, nodeA));
        nodeC.edgeList.add(new Edge(1, nodeB));
        nodeC.edgeList.add(new Edge(1, nodeD));

        nodeD.edgeList.add(new Edge(1, nodeC));
        nodeD.edgeList.add(new Edge(4, nodeA));

        map.values().stream().forEach(node -> Collections.sort(node.edgeList, (e1, e2) -> e1.weight - e2.weight));

        return map;
    }

    private static Map<String, Node> buildGraph2() {
        Map<String, Node> map = new HashMap<>();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        map.put("A", nodeA);
        map.put("B", nodeB);
        map.put("C", nodeC);
        map.put("D", nodeD);
        map.put("E", nodeE);
        map.put("F", nodeF);

        nodeA.edgeList.add(new Edge(10, nodeB));
        nodeA.edgeList.add(new Edge(15, nodeC));

        nodeB.edgeList.add(new Edge(15, nodeF));
        nodeB.edgeList.add(new Edge(12, nodeD));

        nodeC.edgeList.add(new Edge(10, nodeE));

        nodeD.edgeList.add(new Edge(1, nodeF));
        nodeD.edgeList.add(new Edge(2, nodeE));

        nodeF.edgeList.add(new Edge(5, nodeE));

        map.values().stream().forEach(node -> Collections.sort(node.edgeList, (e1, e2) -> e1.weight - e2.weight));

        return map;
    }
}
