package academy.pocu.comp3500.assignment1;

import academy.pocu.comp3500.assignment1.pba.GameStat;
import academy.pocu.comp3500.assignment1.pba.Player;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;

public final class PocuBasketballAssociation {
    private PocuBasketballAssociation() {
    }

    public static void processGameStats(final GameStat[] gameStats, final Player[] outPlayers) {
        sort(gameStats, Comparator.comparing(GameStat::getPlayerName));
        int idx = 0;
        int count = 0;
        int goalAttempts = 0;
        String prevPlayer = gameStats[0].getPlayerName();
        for (GameStat gameStat : gameStats) {
            if (gameStat.getPlayerName().equals(prevPlayer)) {
                count++;
            } else {
                Player outPlayer = outPlayers[idx];
                outPlayer.setPointsPerGame(outPlayer.getPointsPerGame() / count);
                outPlayer.setAssistsPerGame(outPlayer.getAssistsPerGame() / count);
                outPlayer.setPassesPerGame(outPlayer.getPassesPerGame() / count);
                outPlayer.setShootingPercentage(100 * outPlayer.getShootingPercentage() / goalAttempts);

                idx++;
                prevPlayer = gameStat.getPlayerName();
                count = 1;
                goalAttempts = 0;
            }
            goalAttempts += gameStat.getGoalAttempts();
            Player outPlayer = outPlayers[idx];
            outPlayer.setName(gameStat.getPlayerName());
            outPlayer.setPointsPerGame(outPlayer.getPointsPerGame() + gameStat.getPoints());
            outPlayer.setAssistsPerGame(outPlayer.getAssistsPerGame() + gameStat.getAssists());
            outPlayer.setPassesPerGame(outPlayer.getPassesPerGame() + gameStat.getNumPasses());
            outPlayer.setShootingPercentage(outPlayer.getShootingPercentage() + gameStat.getGoals());
        }
        Player outPlayer = outPlayers[outPlayers.length - 1];
        outPlayer.setPointsPerGame(outPlayer.getPointsPerGame() / count);
        outPlayer.setAssistsPerGame(outPlayer.getAssistsPerGame() / count);
        outPlayer.setPassesPerGame(outPlayer.getPassesPerGame() / count);
        outPlayer.setShootingPercentage(100 * outPlayer.getShootingPercentage() / goalAttempts);
    }

    public static Player findPlayerPointsPerGame(final Player[] players, int targetPoints) {
        return findPlayer(players, targetPoints, Player::getPointsPerGame);
    }

    public static Player findPlayerShootingPercentage(final Player[] players, int targetShootingPercentage) {
        return findPlayer(players, targetShootingPercentage, Player::getShootingPercentage);
    }

    private static Player findPlayer(final Player[] players, int target, Function<Player, Integer> supplier) {
        if (target < supplier.apply(players[0])) {
            return players[0];
        }
        if (target >= supplier.apply(players[players.length - 1])) {
            return players[players.length - 1];
        }

        int idx = binarySearch(players, supplier, target, 0, players.length - 1);
        if (idx == players.length) {
            return players[idx - 1];
        }
        Player player = (idx != 0) ? players[--idx] : players[idx];
        int diff = Math.abs(supplier.apply(player) - target);
        while (true) {
            idx++;
            if (idx == players.length) {
                break;
            }
            Player curPlayer = players[idx];
            int curDiff = Math.abs(supplier.apply(curPlayer) - target);
            if (curDiff > diff) {
                break;
            }
            player = curPlayer;
            diff = curDiff;
        }
        return player;
    }

    public static long find3ManDreamTeam(final Player[] players, final Player[] outPlayers, final Player[] scratch) {
        return findDreamTeamHelper(players, outPlayers, scratch, 3);
    }

    public static long findDreamTeam(final Player[] players, int k, final Player[] outPlayers, final Player[] scratch) {
        return findDreamTeamHelper(players, outPlayers, scratch, k);
    }


    public static int findDreamTeamSize(final Player[] players, final Player[] scratch) {
        int dreamTeamSize = 0;
        long dreamTeamMaxScore = 0;
        for (int k = 1; k <= players.length; k++) {
            long dreamTeamScore = findDreamTeamHelper(players, null, scratch, k);
            if (dreamTeamScore >= dreamTeamMaxScore) {
                dreamTeamMaxScore = dreamTeamScore;
                dreamTeamSize = k;
            }
        }
        return dreamTeamSize;
    }


    private static long findDreamTeamHelper(final Player[] players, final Player[] outPlayers, final Player[] scratch, int k) {
        sort(players, Comparator.comparing(Player::getAssistsPerGame));
        long maxScore = 0;
        for (int i = 0; i < players.length - (k - 1); i++) {
            Player pivot = players[i];
            Arrays.fill(scratch, null);
            for (int j = i + 1; j < players.length; j++) {
                addPlayerToScratch(scratch, k, players[j]);
            }
            long curScore = pivot.getPassesPerGame();
            for (int l = 0; l < k - 1; l++) {
                curScore += scratch[l].getPassesPerGame();
            }
            curScore *= pivot.getAssistsPerGame();
            if (curScore >= maxScore) {
                maxScore = curScore;
                if (outPlayers != null) {
                    System.arraycopy(scratch, 0, outPlayers, 0, scratch.length);
                    outPlayers[k - 1] = pivot;
                }
            }
        }
        return maxScore;
    }

    private static void addPlayerToScratch(final Player[] scratch, int n, Player player) {
        int minPass = Integer.MAX_VALUE;
        int minIdx = -1;
        for (int i = 0; i < n - 1; i++) {
            if (scratch[i] == null) {
                scratch[i] = player;
                return;
            }
            if (scratch[i].getPassesPerGame() < minPass) {
                minPass = scratch[i].getPassesPerGame();
                minIdx = i;
            }
        }
        if (minPass < player.getPassesPerGame()) {
            scratch[minIdx] = player;
        }
    }

    private static int binarySearch(Player[] players, Function<Player, Integer> supplier, int target, int left, int right) {
        if (left >= right) {
            return left;
        }
        int mid = left + (right - left) / 2;
        int midVal = supplier.apply(players[mid]);
        if (midVal == target) {
            return mid;
        }
        if (midVal > target) {
            return binarySearch(players, supplier, target, left, mid - 1);
        } else {
            return binarySearch(players, supplier, target, mid + 1, right);
        }
    }

    private static long getTeamWorkScore(final Player[] scratch, int len) {
        int passSum = 0;
        int minAssist = Integer.MAX_VALUE;
        for (int i = 0; i < len; i++) {
            passSum += scratch[i].getPassesPerGame();
            minAssist = Math.min(minAssist, scratch[i].getAssistsPerGame());
        }
        return passSum * minAssist;
    }

    private static <T> void sort(T[] array, Comparator<T> comparator) {
        quickSortRecursive(array, 0, array.length - 1, comparator);
    }

    private static <T> void quickSortRecursive(T[] array, int left, int right, Comparator<T> comparator) {
        if (left >= right) {
            return;
        }
        int pivotPos = partition(array, left, right, comparator);

        quickSortRecursive(array, left, pivotPos - 1, comparator);
        quickSortRecursive(array, pivotPos + 1, right, comparator);
    }

    private static <T> int partition(T[] array, int left, int right, Comparator<T> comparator) {
        T pivot = array[right];
        int i = (left - 1);
        for (int j = left; j < right; j++) {
            if (comparator.compare(array[j], pivot) < 0) {
                ++i;
                swap(array, i, j);
            }
        }
        int pivotPos = i + 1;
        swap(array, pivotPos, right);
        return pivotPos;
    }

    private static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}