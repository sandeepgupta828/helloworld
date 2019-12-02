package com.programs.graph;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.*;

public class CopyDirectedGraph {

    public static class Node {
        Integer value;
        List<Node> outGoing;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return value.equals(node.value) &&
                    outGoing.equals(node.outGoing);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, outGoing);
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
        Node copiedNode = new Node();
        copiedNode.value = givenNode.value;
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

    public static void main(String[] args) {

    }
}
