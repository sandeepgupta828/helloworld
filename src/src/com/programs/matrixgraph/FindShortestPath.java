package com.programs.matrixgraph;

import java.util.*;

/**
 * Find shortest path from given Node A to another Node B in graph
 */
public class FindShortestPath {

    public static void main(String[] args) {
        Map<String, Node> mapIdToNode = buildGraph();
        System.out.println(findShortestPath(mapIdToNode.get("B")));
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
     * pick the node with least weight -- this is guaranteed shortest path to it
     * update the table with this shortest path
     * recurse on this node until all nodes are visited and shortest paths are added to table
     * add transitive shortest paths once a new one is added
     * <p>
     * on return from recursion:
     * <p>
     * pick the node with next weight -- check if this is shorter than path in table for it
     * If shorter, this is the shortest path to it.
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
        Collections.sort(nodeA.edgeList, (e1, e2) -> e1.weight - e2.weight);

        nodeB.edgeList.add(new Edge(3, nodeC));

        nodeC.edgeList.add(new Edge(6, nodeA));
        nodeC.edgeList.add(new Edge(1, nodeB));
        nodeC.edgeList.add(new Edge(1, nodeD));

        nodeD.edgeList.add(new Edge(1, nodeC));
        nodeD.edgeList.add(new Edge(4, nodeA));

        map.values().stream().forEach(node -> Collections.sort(node.edgeList, (e1, e2) -> e1.weight - e2.weight));

        return map;
    }
}
