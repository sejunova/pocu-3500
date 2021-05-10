package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class LinkedList {
    private LinkedList() {
    }

    public static Node append(final Node rootOrNull, final int data) {
        if (rootOrNull == null) {
            return new Node(data);
        }

        Node head = rootOrNull;
        while (head.getNextOrNull() != null) {
            head = head.getNextOrNull();
        }
        head.setNext(new Node(data));
        return rootOrNull;
    }

    public static Node prepend(final Node rootOrNull, final int data) {
        final Node newRoot = new Node(data);
        if (rootOrNull != null) {
            newRoot.setNext(rootOrNull);
        }
        return newRoot;
    }

    public static Node insertAt(final Node rootOrNull, final int index, final int data) {
        if (index == 0) {
            return prepend(rootOrNull, data);
        }
        if (index < 0 || rootOrNull == null) {
            return rootOrNull;
        }

        int i = 1;
        Node prev = rootOrNull;
        Node next = rootOrNull.getNextOrNull();
        while (next != null && i < index) {
            prev = next;
            next = next.getNextOrNull();
            i++;
        }
        if (i == index) {
            final Node newNode = new Node(data);
            prev.setNext(newNode);
            newNode.setNext(next);
        }
        return rootOrNull;
    }

    public static Node removeAt(final Node rootOrNull, final int index) {
        if (index < 0 || rootOrNull == null) {
            return rootOrNull;
        }
        Node temp = rootOrNull;
        Node cur;
        if (index == 0) {
            cur = rootOrNull.getNextOrNull();
            rootOrNull.setNext(null);
            return cur;
        }

        for (int i = 0; i < index - 1; i++) {
            temp = temp.getNextOrNull();
            if (temp == null) {
                break;
            }
        }

        if (temp == null || temp.getNextOrNull() == null) {
            return rootOrNull;
        }

        cur = temp.getNextOrNull();
        temp.setNext(cur.getNextOrNull());
        cur.setNext(null);
        return rootOrNull;
    }

    public static int getIndexOf(final Node rootOrNull, final int data) {
        int idx = 0;
        Node cur = rootOrNull;
        while (cur != null) {
            if (cur.getData() == data) {
                return idx;
            }
            cur = cur.getNextOrNull();
            idx++;
        }
        return -1;
    }

    public static Node getOrNull(final Node rootOrNull, final int index) {
        int i = 0;
        Node cur = rootOrNull;
        while (cur != null) {
            if (i == index) {
                return cur;
            }
            cur = cur.getNextOrNull();
            i++;
        }
        return null;
    }

    public static Node reverse(final Node rootOrNull) {
        Node node = rootOrNull;
        Node prev = null;

        while (node != null) {
            Node next = node.getNextOrNull();
            node.setNext(prev);

            prev = node;
            node = next;
        }
        return prev;
    }

    public static Node interleaveOrNull(final Node root0OrNull, final Node root1OrNull) {
        if (root0OrNull == null) {
            return root1OrNull;
        }
        if (root1OrNull == null) {
            return root0OrNull;
        }

        Node head0 = root0OrNull;
        Node head1 = root1OrNull;

        while (true) {
            if (head0.getNextOrNull() == null) {
                head0.setNext(head1);
                break;
            }
            if (head1 == null) {
                break;
            }

            Node head1Next = head1.getNextOrNull();
            Node head0Next = head0.getNextOrNull();
            head1.setNext(head0Next);
            head0.setNext(head1);
            head1 = head1Next;
            head0 = head0Next;
        }
        return root0OrNull;
    }
}