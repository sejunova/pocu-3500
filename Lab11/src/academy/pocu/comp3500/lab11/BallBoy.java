package academy.pocu.comp3500.lab11;

import academy.pocu.comp3500.lab11.data.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BallBoy {
    public static List<Point> findPath(final Point[] points) {
        List<Point> pointList = new ArrayList<>(points.length + 1);
        pointList.add(new Point(0, 0));

        if (points.length == 0) {
            return pointList;
        }

        for (Point point : points) {
            pointList.add(point);
        }
        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < pointList.size() - 1; i++) {
            for (int j = i + 1; j < pointList.size(); j++) {
                edges.add(new Edge(pointList.get(i), pointList.get(j)));
            }
        }

        Collections.sort(edges);

        ArrayList<Edge> mst = Kruskal
                .run(pointList, edges);

        Map<Point, LinkedList<Point>> mstGraph = new HashMap<>();
        for (Edge e : mst) {
            if (!mstGraph.containsKey(e.getNode1())) {
                mstGraph.put(e.getNode1(), new LinkedList<>());
            }
            if (!mstGraph.containsKey(e.getNode2())) {
                mstGraph.put(e.getNode2(), new LinkedList<>());
            }

            mstGraph.get(e.getNode1()).add(e.getNode2());
            mstGraph.get(e.getNode2()).add(e.getNode1());
        }

        List<Point> tsp = new LinkedList<>();

        Point start = pointList.get(0);
        tsp.add(start);
        LinkedList<Point> connectedNodes = mstGraph.get(start);
        while (!connectedNodes.isEmpty()) {
            Point nextNode = connectedNodes.removeLast();
            dfsRecursive2(nextNode, start, tsp, mstGraph);
        }
        tsp.add(start);
        return tsp;
    }

    private static void dfsRecursive2(Point node, Point prev, List<Point> tsp, Map<Point, LinkedList<Point>> mstGraph) {
        LinkedList<Point> connectedNodes = mstGraph.get(node);
        tsp.add(node);

        while (!connectedNodes.isEmpty()) {
            Point nextNode = connectedNodes.removeLast();
            if (nextNode.equals(prev)) {
                continue;
            }
            dfsRecursive2(nextNode, node, tsp, mstGraph);
        }
    }
}