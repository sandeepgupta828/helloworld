package com.programs.binarytree;

import java.util.Objects;

public class Node {
    Integer value;
    Node left;
    Node right;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(value, node.value) &&
                Objects.equals(left, node.left) &&
                Objects.equals(right, node.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, left, right);
    }

    @Override
    public String toString() {
        return "{" +
                "\"value\":" + value +
                ", \"left\":" + left +
                ", \"right\":" + right +
                '}';
    }

    public static final class NodeBuilder {
        Integer value;
        Node left;
        Node right;

        private NodeBuilder() {
        }

        public static NodeBuilder aNode() {
            return new NodeBuilder();
        }

        public NodeBuilder withValue(Integer value) {
            this.value = value;
            return this;
        }

        public NodeBuilder withLeft(Node left) {
            this.left = left;
            return this;
        }

        public NodeBuilder withRight(Node right) {
            this.right = right;
            return this;
        }

        public Node build() {
            Node node = new Node();
            node.left = this.left;
            node.value = this.value;
            node.right = this.right;
            return node;
        }
    }
}