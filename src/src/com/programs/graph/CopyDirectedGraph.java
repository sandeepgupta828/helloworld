package com.programs.graph;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.*;

public class CopyDirectedGraph {

    public static class Node {
        public Node(Integer value) {
            this.value = value;
            this.outGoing = new ArrayList<>();
        }

        Integer value;
        List<Node> outGoing;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return value.equals(node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    private Map<Node, Node> mapNodeToCopiedNode = new HashMap<>();

    /**
     * copy the given node
     *   create a new node with value of given node
     * if given node has outGoing nodes:
     *   for each outgoing node:
     *      check if this node is already copied
     *      if yes: get the copy, if not: copy it recursively
     *      add copied node to the OutGoing nodes of this new node
     * @param givenNode
     * @return
     */
    public Node copy(Node givenNode) {
        Node copiedNode = new Node(givenNode.value);
        mapNodeToCopiedNode.put(givenNode, copiedNode);
        if (givenNode.outGoing != null && givenNode.outGoing.size() > 0) {
            copiedNode.outGoing = new ArrayList<>(givenNode.outGoing.size());
            for (Node node: givenNode.outGoing) {
                Node copyOfOutGoingNode;
                if (!mapNodeToCopiedNode.keySet().contains(node)) {
                    copyOfOutGoingNode = copy(node);
                } else {
                    copyOfOutGoingNode = mapNodeToCopiedNode.get(node);
                }
                copiedNode.outGoing.add(copyOfOutGoingNode);
            }
        }
        return copiedNode;
    }

    public Node createGraph(int numberNodes, int numEdges) {
        Map<Integer, Node> valueToNodeMap = new HashMap<>();
        // create nodes
        for (int i=0;i<numberNodes;i++) {
            valueToNodeMap.put(i+1, new Node(i+1));
        }
        Random random = new Random();
        // create edges
        while (numEdges > 0) {
            // pick a random source node
            int srcNode = random.nextInt(numberNodes)+1;
            // pick a random target node
            int targetNode;
            while((targetNode = (random.nextInt(numberNodes)+1)) == srcNode);
            if (!valueToNodeMap.get(srcNode).outGoing.contains(valueToNodeMap.get(targetNode))) {
                valueToNodeMap.get(srcNode).outGoing.add(valueToNodeMap.get(targetNode));
                numEdges--;
            }
        }
        return valueToNodeMap.values().iterator().next();
    }

    void printGraph(Node printNode, Set<Node> printedNodes, String recursionLevel) {
        System.out.println(recursionLevel+"node:"+printNode.value);
        printedNodes.add(printNode);
        System.out.println(recursionLevel+"outGoing:");
        for (Node node: printNode.outGoing) {
            if (!printedNodes.contains(node)) {
                printGraph(node, printedNodes, recursionLevel+">");
            } else {
                System.out.println(recursionLevel+"(node):"+node.value);
            }
        }
    }

    public static void main(String[] args) {
        // create a graph
        CopyDirectedGraph copyDirectedGraph = new CopyDirectedGraph();
        Node node = copyDirectedGraph.createGraph(4, 12);
        copyDirectedGraph.printGraph(node, new HashSet<>(), ">");
        System.out.println("=========================");
        Node copiedGraph = copyDirectedGraph.copy(node);
        copyDirectedGraph.printGraph(copiedGraph, new HashSet<>(), ">");
    }
}
