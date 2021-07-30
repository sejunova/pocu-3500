package academy.pocu.comp3500.lab11;

import academy.pocu.comp3500.lab11.data.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Kruskal {
    private Kruskal() {
    }

    public static ArrayList<Edge> run(final List<Point> nodes, final List<Edge> edges) {
        DisjointSet set = new DisjointSet(nodes);

        ArrayList<Edge> mst = new ArrayList<>(edges.size());

        Collections.sort(edges);

        for (Edge edge : edges) {
            Point n1 = edge.getNode1();
            Point n2 = edge.getNode2();

            Point root1 = set.find(n1);
            Point root2 = set.find(n2);

            if (!root1.equals(root2)) {
                mst.add(edge);
                set.union(n1, n2);
            }
        }

        return mst;
    }
}
