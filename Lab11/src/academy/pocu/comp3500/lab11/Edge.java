package academy.pocu.comp3500.lab11;

import academy.pocu.comp3500.lab11.data.Point;

public final class Edge implements Comparable<Edge> {
    private final Point node1;
    private final Point node2;
    private final double weight;

    public Edge(final Point node1,
                final Point node2) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = Math.sqrt(Math.pow(node1.getX() - node2.getX(), 2) + Math.pow(node1.getY() - node2.getY(), 2));
    }

    public Point getNode1() {
        return this.node1;
    }

    public Point getNode2() {
        return this.node2;
    }

    @Override
    public int compareTo(Edge e) {
        return Double.compare(this.weight, e.weight);
    }
}
