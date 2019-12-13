package com.programs.list;


import java.util.List;


public class ReverseList {

    public static void main(String[] args) {
        Node listHead = buildList(10);
        System.out.println(listHead);
        Node reversedList = reverseSinglyLinkedList(listHead);
        System.out.println(reversedList);
    }

    public static class Node {
        int val;
        Node next;

        @Override
        public String toString() {
            return Integer.toString(val) + ((next !=null) ? "->" + next.toString() : "");
        }
    }

    /*
    algo:
    curNode = head
    nextNode = curNode.next
    curNode.next = null
    while(nextNode != null) {
         cache what is updated:
              tempNode = nextNode.next
              nextNode.next = curNode
              curNode = nextNode
              nextNode = tempNode
    }

     */
    public static Node reverseSinglyLinkedList(Node head) {
        Node curNode = head;
        Node nextNode = curNode.next;
        curNode.next = null;
        while (nextNode != null) {
            Node tempNode = nextNode.next;
            nextNode.next = curNode;
            curNode = nextNode;
            nextNode= tempNode;
        }
        return curNode;
    }

    private static Node buildList(int listLength) {
        Node nextNode = null;
        Node head = null;
        for (int i = 0; i < listLength; i++) {
            Node node = new Node();
            node.val = listLength-i;
            node.next = nextNode;
            nextNode = node;
            if (i==(listLength-1)) {
                head = node;
            }
        }
        return head;
    }
}
