package academy.pocu.comp3500.assignment1;

import academy.pocu.comp3500.assignment1.pba.GameStat;
import academy.pocu.comp3500.assignment1.pba.Player;

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
        sort(players, Comparator.comparing(Player::getAssistsPerGame).reversed());
        return findDreamTeamHelper(players, outPlayers, scratch, 3);
    }

    public static long findDreamTeam(final Player[] players, int k, final Player[] outPlayers, final Player[] scratch) {
        sort(players, Comparator.comparing(Player::getAssistsPerGame).reversed());
        return findDreamTeamHelper(players, outPlayers, scratch, k);
    }


    public static int findDreamTeamSize(final Player[] players, final Player[] scratch) {
        sort(players, Comparator.comparing(Player::getAssistsPerGame).reversed());
        int dreamTeamSize = 0;
        long dreamTeamMaxScore = 0;
        long dreamTeamPassSum = 0;
        for (int i = 0; i < players.length; i++) {
            dreamTeamPassSum += players[i].getPassesPerGame();
            long curDreamTeamScore = dreamTeamPassSum * players[i].getAssistsPerGame();
            if (curDreamTeamScore > dreamTeamMaxScore) {
                dreamTeamMaxScore = curDreamTeamScore;
                dreamTeamSize = i + 1;
            }
        }
        return dreamTeamSize;
    }


    private static long findDreamTeamHelper(final Player[] players, final Player[] outPlayers, final Player[] scratch, int k) {
        long maxScore = 0;
        if (k == 1) {
            for (Player player : players) {
                long teamScore = player.getPassesPerGame() * player.getAssistsPerGame();
                if (teamScore > maxScore) {
                    maxScore = teamScore;
                    if (outPlayers != null) {
                        outPlayers[0] = player;
                    }
                }
            }
            return maxScore;
        }

        long passSum = 0;
        for (int i = 0; i < k; i++) {
            scratch[i] = players[i];
            passSum += players[i].getPassesPerGame();
        }
        maxScore = passSum * players[k - 1].getAssistsPerGame();
        System.arraycopy(scratch, 0, outPlayers, 0, k);
        buildHeap(scratch);

        for (int pivotIdx = k; pivotIdx < players.length; pivotIdx++) {
            Player pivot = players[pivotIdx];
            if (pivot.getPassesPerGame() > scratch[0].getPassesPerGame()) {
                passSum += (pivot.getPassesPerGame() - scratch[0].getPassesPerGame());
                scratch[0] = pivot;
                long curScore = passSum * pivot.getAssistsPerGame();
                if (curScore > maxScore) {
                    maxScore = curScore;
                    System.arraycopy(scratch, 0, outPlayers, 0, k);
                }
                heapify(scratch, 0);
            }
        }
        return maxScore;
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

    private static <T> void sort(T[] array, Comparator<T> comparator) {
        quickSortRecursive(array, 0, array.length - 1, comparator);
    }

    private static <T> void quickSortRecursive(T[] array, int left, int right, Comparator<T> comparator) {
        if (left >= right) {
            return;
        }
        int pivotPos = partition(array, left, right, comparator);

        quickSortRecursive(array, left, pivotPos - 1, comparator);
        quickSortRecursive(array, pivotPos, right, comparator);
    }

    private static <T> int partition(T[] array, int left, int right, Comparator<T> comparator) {
        T pivot = array[left + (right - left) / 2];
        while (left <= right) {
            while (comparator.compare(array[left], pivot) < 0) {
                left++;
            }
            while (comparator.compare(array[right], pivot) > 0) {
                right--;
            }
            if (left <= right) {
                swap(array, left++, right--);
            }
        }
        return left;
    }

    private static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void heapify(Player[] array, int i) {
        int size = array.length;
        int smallest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < size && array[left].getPassesPerGame() < array[smallest].getPassesPerGame())
            smallest = left;

        if (right < size && array[right].getPassesPerGame() < array[smallest].getPassesPerGame())
            smallest = right;

        if (smallest != i) {
            swap(array, i, smallest);
            heapify(array, smallest);
        }
    }

    private static void buildHeap(Player[] arr) {
        int startIdx = (arr.length / 2) - 1;
        for (int i = startIdx; i >= 0; i--) {
            heapify(arr, i);
        }
    }
}