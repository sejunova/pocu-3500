package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Queue {
    private Node head = null;
    private Node tail = null;
    private int size = 0;

    public void enqueue(final int data) {
        if (size == 0) {
            head = LinkedList.append(head, data);
            tail = head;
        } else {
            tail.setNext(new Node(data));
            tail = tail.getNextOrNull();
        }
        size++;
    }

    public int peek() {
        if (size == 0) {
            return -1;
        }
        return head.getData();
    }

    public int dequeue() {
        if (size == 0) {
            return -1;
        }
        int val = head.getData();
        head = head.getNextOrNull();
        size--;
        return val;
    }

    public int getSize() {
        return size;
    }
}