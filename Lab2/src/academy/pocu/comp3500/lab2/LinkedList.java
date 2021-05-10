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
        if (rootOrNull == null) {
            return null;
        }
        if (index == 0) {
            return prepend(rootOrNull, data);
        }

        int i = 1;
        Node prev = rootOrNull;
        Node next = rootOrNull.getNextOrNull();
        while (next != null) {
            if (i == index) {
                break;
            }
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
        if (rootOrNull == null) {
            return null;
        }
        if (index == 0) {
            return rootOrNull.getNextOrNull();
        }

        int i = 1;
        Node prev = rootOrNull;
        while (prev.getNextOrNull() != null) {
            if (i == index) {
                break;
            }
            prev = prev.getNextOrNull();
            i++;
        }
        if (prev.getNextOrNull() == null) {
            return rootOrNull;
        }
        prev.setNext(prev.getNextOrNull().getNextOrNull());
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
        if (rootOrNull == null) {
            return null;
        }

        return reverseRecursive(null, rootOrNull);
    }

    private static Node reverseRecursive(final Node prev, final Node next) {
        if (next == null) {
            return prev;
        }
        final Node root = reverseRecursive(next, next.getNextOrNull());
        next.setNext(prev);
        return root;
    }

    public static Node interleaveOrNull(final Node root0OrNull, final Node root1OrNull) {
        if (root0OrNull == null) {
            return root1OrNull;
        }
        interleaveRecursive(root0OrNull, root1OrNull);
        return root0OrNull;
    }

    private static void interleaveRecursive(final Node root0OrNull, final Node root1OrNull) {
        if (root0OrNull.getNextOrNull() == null) {
            root0OrNull.setNext(root1OrNull);
        } else if (root1OrNull.getNextOrNull() == null) {
            root1OrNull.setNext(root0OrNull.getNextOrNull());
            root0OrNull.setNext(root1OrNull);
        } else {
            interleaveRecursive(root0OrNull.getNextOrNull(), root1OrNull.getNextOrNull());
            root1OrNull.setNext(root0OrNull.getNextOrNull());
            root0OrNull.setNext(root1OrNull);
        }
    }
}