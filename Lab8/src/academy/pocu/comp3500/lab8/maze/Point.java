package academy.pocu.comp3500.lab8.maze;

import java.util.ArrayList;
import java.util.List;

public final class Point {
    private static final int[] dx = new int[]{1, 0, -1, 0};
    private static final int[] dy = new int[]{0, 1, 0, -1};

    private final int x;
    private final int y;

    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public List<Point> getNextAvailablePoints(final char[][] maze) {
        List<Point> nextAvailablePoints = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int nx = this.x + dx[i];
            int ny = this.y + dy[i];
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
}
