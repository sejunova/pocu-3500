package academy.pocu.comp3500.assignment1.util;

import academy.pocu.comp3500.assignment1.pba.GameStat;

public class GameStatUtil {
    public static void quickSort(GameStat[] gameStats) {
        quickSortRecursive(gameStats, 0, gameStats.length - 1);
    }

    private static void quickSortRecursive(GameStat[] gameStats, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivotPos = partition(gameStats, left, right);

        quickSortRecursive(gameStats, left, pivotPos - 1);
        quickSortRecursive(gameStats, pivotPos + 1, right);
    }

    private static int partition(GameStat[] gameStats, int left, int right) {
        GameStat pivot = gameStats[right];
        int i = (left - 1);
        for (int j = left; j < right; j++) {
            if (gameStats[j].getPlayerName().compareTo(pivot.getPlayerName()) < 0) {
                ++i;
                swap(gameStats, i, j);
            }
        }
        int pivotPos = i + 1;
        swap(gameStats, pivotPos, right);
        return pivotPos;
    }

    private static void swap(GameStat[] gameStats, int i, int j) {
        GameStat temp = gameStats[i];
        gameStats[i] = gameStats[j];
        gameStats[j] = temp;
    }

    public static void main(String[] args) {
        GameStat[] gameStats = new GameStat[]{
                new GameStat("Player 1", 1, 13, 5, 6, 10, 1),
                new GameStat("Player 2", 2, 5, 2, 5, 0, 10),
                new GameStat("Player 1", 3, 12, 6, 9, 8, 5),
                new GameStat("Player 3", 1, 31, 15, 40, 5, 3),
                new GameStat("Player 2", 1, 3, 1, 3, 12, 2),
                new GameStat("Player 1", 2, 11, 6, 11, 9, 3),
                new GameStat("Player 2", 3, 9, 3, 3, 1, 11),
                new GameStat("Player 3", 4, 32, 15, 51, 4, 2),
                new GameStat("Player 4", 3, 44, 24, 50, 1, 1),
                new GameStat("Player 1", 4, 11, 5, 14, 8, 3),
                new GameStat("Player 2", 4, 5, 1, 3, 1, 9),
        };
        quickSort(gameStats);
        int x = 0;
    }
}
