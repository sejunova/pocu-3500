package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Queue {
    private Node peekNode = null;
    private int size = 0;

    public void enqueue(final int data) {
        peekNode = LinkedList.append(peekNode, data);
        size++;
    }

    public int peek() {
        if (size == 0) {
            return -1;
        }
        return peekNode.getData();
    }

    public int dequeue() {
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