package com.programs.trees;

import java.util.*;

//https://leetcode.com/explore/interview/card/apple/350/trees-and-graphs/3121/
public class LowestCommonAncestor {
    public static void main(String[] args) {
        Node root = buildTree();
        System.out.println(findLowestCommonNode(root, 6, 15));
    }

    public static Node findLowestCommonNode(Node root, int v1, int v2) {
        /*
        algo:
        find finds nodes in LST, and find nodes in RST
        possibilities:
        both of them return null
        One of them returns both
        each of them return one each
        --in this last possibility this node is the lowest common ancestor

         */
        Set<Node> commonAncestor = new HashSet<>(1);
        findNodes(root, v1, v2, commonAncestor);
        if (commonAncestor.size() > 0) return commonAncestor.iterator().next();
        return null;
    }

    private static List<Node> findNodes(Node root, int v1, int v2, Set<Node> commonAncestor) {
        Node foundSelf = null;
        List<Node> foundNodesFromLST = null;
        List<Node> foundNodesFromRST = null;

        if (root.value == v1 || root.value == v2) {
            foundSelf = root;
        }

        if (root.left != null) {
            foundNodesFromLST = findNodes(root.left, v1, v2, commonAncestor);
        }
        if (root.right != null) {
            foundNodesFromRST = findNodes(root.right, v1, v2, commonAncestor);
        }

        // check
        boolean isCommonAncestor = false;
        if (foundSelf != null) {
            isCommonAncestor = foundNodesFromLST != null ? foundNodesFromLST.get(0).value != foundSelf.value : false;
            if (!isCommonAncestor) {
                isCommonAncestor = foundNodesFromRST != null ? foundNodesFromRST.get(0).value != foundSelf.value : false;
            }
        }
        if (!isCommonAncestor) {
            isCommonAncestor = foundNodesFromLST != null && foundNodesFromRST != null ? foundNodesFromLST.get(0).value != foundNodesFromRST.get(0).value : false;
        }
        if (isCommonAncestor) {
            commonAncestor.add(root);
        }
        List<Node> foundNodes = new ArrayList<>();
        if (foundNodesFromLST != null) {
            foundNodes.addAll(foundNodesFromLST);
        }
        if (foundNodesFromRST != null) {
            foundNodes.addAll(foundNodesFromRST);
        }
        if (foundSelf != null) {
            foundNodes.add(foundSelf);
        }

        if (foundNodes.size() > 0) {
            return foundNodes;
        }
        return null;
    }

    public static Node buildTree() {
        Node root = new Node(5);
        root.left = new Node(1);
        root.right = new Node(10);
        root.left.left = new Node(0);
        root.left.right = new Node(3);
        root.right.left = new Node(6);
        root.right.right = new Node(15);
        return root;
    }
}
