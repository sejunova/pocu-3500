package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Stack {
    private Node peekNode = null;
    private int size = 0;

    public void push(final int data) {
        peekNode = LinkedList.prepend(peekNode, data);
        size++;
    }

    public int peek() {
        if (size == 0) {
            return -1;
        }
        return peekNode.getData();
    }

    public int pop() {
        if (size == 0) {
            return -1;
        }
        int val = peekNode.getData();
        peekNode = peekNode.getNextOrNull();
        size--;
        return val;
    }

    public int getSize() {
        return size;
    }
}