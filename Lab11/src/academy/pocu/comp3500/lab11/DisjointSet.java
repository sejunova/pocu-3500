package academy.pocu.comp3500.lab11;

import academy.pocu.comp3500.lab11.data.Point;

import java.util.HashMap;
import java.util.List;

public final class DisjointSet {
    private class SetNode {
        private Point parent;
        private int size;

        public SetNode(final Point parent, final int size) {
            this.parent = parent;
            this.size = size;
        }
    }

    private final HashMap<Point, SetNode> sets = new HashMap<>(64);

    public DisjointSet(final List<Point> nodes) {
        for (Point s : nodes) {
            SetNode setNode = new SetNode(s, 1);
            this.sets.put(s, setNode);
        }
    }

    public Point find(final Point node) {
        assert (this.sets.containsKey(node));

        SetNode n = this.sets.get(node);
        Point parent = n.parent;
        if (parent.equals(node)) {
            return node;
        }

        n.parent = find(n.parent);

        return n.parent;
    }

    public void union(final Point node1, final Point node2) {
        assert (this.sets.containsKey(node1));
        assert (this.sets.containsKey(node2));

        Point root1 = find(node1);
        Point root2 = find(node2);

        if (root1.equals(root2)) {
            return;
        }

        SetNode parent = this.sets.get(root1);
        SetNode child = this.sets.get(root2);

        if (parent.size < child.size) {
            SetNode temp = parent;

            parent = child;
            child = temp;
        }

        child.parent = parent.parent;
        parent.size = child.size + parent.size;
    }
}
