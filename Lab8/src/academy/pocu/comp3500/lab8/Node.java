package academy.pocu.comp3500.lab8;

import academy.pocu.comp3500.lab8.maze.Point;

import java.util.ArrayList;
import java.util.List;

public class Node {
    final Node parent;
    final Point point;
    final List<Node> children = new ArrayList<>();

    Node(Node parent, Point point) {
        this.parent = parent;
        this.point = point;
    }

    Node addChild(Point child) {
        Node childNode = new Node(this, child);
        children.add(childNode);
        return childNode;
    }

    List<Point> getPaths() {
        List<Point> paths = new ArrayList<>();
        Node node = this;
        while (node != null) {
            paths.add(node.point);
            node = node.parent;
        }
        reverseArrayList(paths);
        return paths;
    }

    private static void reverseArrayList(List<Point> alist) {
        for (int i = 0; i < alist.size() / 2; i++) {
            Point temp = alist.get(i);
            alist.set(i, alist.get(alist.size() - i - 1));
            alist.set(alist.size() - i - 1, temp);
        }
    }
}
