package academy.pocu.comp3500.lab8;

import academy.pocu.comp3500.lab8.maze.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class MazeSolver {
    private static final int[] dx = new int[]{1, 0, -1, 0};
    private static final int[] dy = new int[]{0, 1, 0, -1};

    public static List<Point> findPath(final char[][] maze, final Point start) {
        if (maze[start.getY()][start.getX()] == 'E') {
            List<Point> result = new ArrayList<>();
            result.add(start);
            return result;
        }

        LinkedList<Node> queue = new LinkedList<>();
        queue.addLast(new Node(null, start));
        maze[start.getY()][start.getX()] = '_';

        Node exitPoint = null;
        outer:
        while (!queue.isEmpty()) {
            Node cur = queue.removeFirst();
            List<Point> nextAvailablePoints = getNextAvailablePoints(maze, cur.point);
            for (Point nextPoint : nextAvailablePoints) {
                if (maze[nextPoint.getY()][nextPoint.getX()] == 'E') {
                    exitPoint = cur.addChild(nextPoint);
                    break outer;
                }
                maze[nextPoint.getY()][nextPoint.getX()] = '_';
                Node nextNode = cur.addChild(nextPoint);
                queue.addLast(nextNode);
            }
        }

        if (exitPoint == null) {
            return new ArrayList<>();
        }
        return exitPoint.getPaths();
    }

    private static List<Point> getNextAvailablePoints(final char[][] maze, Point point) {
        List<Point> nextAvailablePoints = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int nx = point.getX() + dx[i];
            int ny = point.getY() + dy[i];
            if (nx < 0 || nx >= maze[0].length || ny < 0 || ny >= maze.length) {
                continue;
            }
            if (maze[ny][nx] == '_' || maze[ny][nx] == 'x') {
                continue;
            }
            nextAvailablePoints.add(new Point(nx, ny));
        }
        return nextAvailablePoints;
    }

    private static class Node {
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
}
