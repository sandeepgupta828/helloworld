package com.programs.binarytree;

import java.util.List;
import java.util.Objects;

public class BinaryTree {

    private Node root = null;

    public BinaryTree() {
    }

    /**
     * find the parent of the value if it exists
     * if it does:
     * if the value is in a leaf node (including root), delete it
     * if the value is non-leaf node then:
     * if the value has only 1 child, delete it by connecting it's child to it's parent
     * if the value has 2 children:
     * replace the value with either:
     * immediate successor node if it exists
     * or immediate predecessor node if it exists
     * if successor/predecessor is not a leaf node i.e. successor has RST, predecessor has LST
     * delete the successor node recursively.
     * then replace the given node with successor node value
     *
     * @param value
     * @return
     */
    public BinaryTree delete(Integer value) {
        Node parent = findParent(value);
        Node node = null;
        boolean isLeftNode = isLeftNode(parent, value);

        if (parent == null) {
            // parent = null ==> either value is the root, or it does not exist
            if (!isValueEqual(root, value)) {
                throw new RuntimeException("Value to delete not found: "+ value);
            }
            node = root;
        } else {
            node = isLeftNode ? parent.left : parent.right;
        }
        // handle leaf node
        if (isLeafNode(node)) {
            if (parent != null) {
                if (isLeftNode) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            } else {
                root = null;
            }
            return this;
        }
        // handle non leaf node with 1 child
        if (node.left != null && node.right == null) {
            if (parent != null) {
                if (isLeftNode) {
                    parent.left = node.left;
                } else {
                    parent.right = node.left;
                }
            } else {
                root = node.left;
            }
            return this;
        }
        if (node.left == null && node.right != null) {
            if (parent != null) {
                if (isLeftNode) {
                    parent.left = node.right;
                } else {
                    parent.right = node.right;
                }
            } else {
                root = node.right;
            }
            return this;
        }

        // handle non leaf node with 2 children
        if (node.left != null && node.right != null) {
            Node successorNode = findMinNode(node.right);
            delete(successorNode.value);
            node.value = successorNode.value;
            return this;
        }
        return this;
    }

    private Node findMinNode(Node node) {
        if (node == null) return null;
        Node leftNodeParent = node;
        while (leftNodeParent.left != null) {
            leftNodeParent = leftNodeParent.left;
        }
        return leftNodeParent;
    }


    private boolean isLeafNode(Node node) {
        return node != null && node.left == null && node.right == null;
    }


    public Node findParent(Integer value) {
        return findParent(root, value);
    }

    /**
     * parent of node hosting the value
     *
     * @param value
     * @return
     */
    private Node findParent(Node node, Integer value) {
        if (isValueBigger(node, value)) {
            if (isValueEqual(node.right, value)) {
                return node;
            }
            return findParent(node.right, value);
        }
        if (isValueSmaller(node, value)) {
            if (isValueEqual(node.left, value)) {
                return node;
            }
            return findParent(node.left, value);
        }
        return null;
    }


    public BinaryTree insert(Integer value) {
        if (root == null) {
            root = Node.NodeBuilder.aNode().withValue(value).build();
        } else {
            Node parentNode = findPotentialParent(root, value);
            if (parentNode != null) {
                if (parentNode.left != null && parentNode.right != null) {
                    throw new RuntimeException("Unexpected");
                }
                if (isValueSmaller(parentNode, value) && parentNode.left == null) {
                    parentNode.left = Node.NodeBuilder.aNode().withValue(value).build();
                }
                if (isValueBigger(parentNode, value) && parentNode.right == null) {
                    parentNode.right = Node.NodeBuilder.aNode().withValue(value).build();
                }
            } else {
                System.out.println("Value cannot be inserted (already exists)");
            }
        }
        return this;
    }

    public boolean exists(Integer value) {
        return false;
    }

    public String serialize() {
        return null;
    }

    @Override
    public String toString() {
        return Objects.nonNull(root) ? root.toString() : "null";
    }

    /**
     * a parent node is one which has either left or right child null
     *
     * @param rootNode
     * @param value
     * @return
     */
    private Node findPotentialParent(Node rootNode, Integer value) {
        if (rootNode == null) return null;
        if ((isValueBigger(rootNode, value) && rootNode.right == null) || (isValueSmaller(rootNode, value) && rootNode.left == null))
            return rootNode;
        if (isValueBigger(rootNode, value)) return findPotentialParent(rootNode.right, value);
        if (isValueSmaller(rootNode, value)) return findPotentialParent(rootNode.left, value);
        return null;
    }

    private boolean isValueSmaller(Node node, Integer value) {
        return node != null && node.value > value;
    }

    private boolean isValueBigger(Node node, Integer value) {
        return node != null && node.value < value;
    }

    private boolean isValueEqual(Node node, Integer value) {
        return node != null && node.value.equals(value);
    }

    private boolean isLeftNode(Node node, Integer value) {
        return node != null && node.left != null && node.left.value.equals(value);
    }

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree = binaryTree.insert(5).insert(2).insert(12).insert(-4).insert(3).insert(9).insert(21).insert(19).insert(25);
        System.out.println(binaryTree);
        //System.out.println(binaryTree.findParent(3));
        binaryTree.delete(5).delete(9).delete(12).delete(19).delete(21).delete(25).delete(2).delete(3).delete(-4);
        System.out.println(binaryTree);

    }
}
