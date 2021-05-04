package academy.pocu.comp3500.assignment1;

import academy.pocu.comp3500.assignment1.pba.GameStat;
import academy.pocu.comp3500.assignment1.pba.Player;

import java.util.function.Function;

public final class PocuBasketballAssociation {
    private PocuBasketballAssociation() {
    }

    public static void processGameStats(final GameStat[] gameStats, final Player[] outPlayers) {
        sortGameStat(gameStats);
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
        int idx = binarySearch(players, Player::getPointsPerGame, targetPoints, 0, players.length - 1);
        if (idx == players.length) {
            return players[idx - 1];
        }
        Player player = (idx != 0) ? players[--idx] : players[idx];
        int diff = Math.abs(player.getPointsPerGame() - targetPoints);
        while (true) {
            idx++;
            if (idx == players.length) {
                break;
            }
            Player curPlayer = players[idx];
            int curDiff = Math.abs(curPlayer.getPointsPerGame() - targetPoints);
            if (curDiff > diff) {
                break;
            }
            player = curPlayer;
            diff = curDiff;
        }
        return player;
    }

    public static Player findPlayerShootingPercentage(final Player[] players, int targetShootingPercentage) {
        int idx = binarySearch(players, Player::getShootingPercentage, targetShootingPercentage, 0, players.length - 1);
        if (idx == players.length) {
            return players[idx - 1];
        }
        Player player = (idx != 0) ? players[--idx] : players[idx];
        int diff = Math.abs(player.getShootingPercentage() - targetShootingPercentage);
        while (true) {
            idx++;
            if (idx == players.length) {
                break;
            }
            Player curPlayer = players[idx];
            int curDiff = Math.abs(curPlayer.getShootingPercentage() - targetShootingPercentage);
            if (curDiff > diff) {
                break;
            }
            player = curPlayer;
            diff = curDiff;
        }
        return player;
    }

    public static long find3ManDreamTeam(final Player[] players, final Player[] outPlayers, final Player[] scratch) {
        return findMaxTeamWorkRecursive(players, outPlayers, players.length, 0, 0, scratch, 3, 0);
    }

    public static long findDreamTeam(final Player[] players, int k, final Player[] outPlayers, final Player[] scratch) {
        return findMaxTeamWorkRecursive(players, outPlayers, players.length, 0, 0, scratch, k, 0);
    }

    public static int findDreamTeamSize(final Player[] players, final Player[] scratch) {
        int dreamTeamSize = 0;
        long dreamTeamMaxScore = 0;
        for (int k = 1; k <= players.length; k++) {
            long dreamTeamScore = findMaxTeamWorkRecursive(players, scratch, players.length, 0, 0, scratch, k, 0);
            if (dreamTeamScore >= dreamTeamMaxScore) {
                dreamTeamMaxScore = dreamTeamScore;
                dreamTeamSize = k;
            }
        }
        return dreamTeamSize;
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

    private static long findMaxTeamWorkRecursive(final Player[] players, final Player[] outPlayers, int n, int start, int k, final Player[] scratch, int scratchLen, long maxTeamWorkScore) {
        if (k == scratchLen) {
            long teamworkScore = getTeamWorkScore(scratch, scratchLen);
            if (teamworkScore > maxTeamWorkScore) {
                System.arraycopy(scratch, 0, outPlayers, 0, scratchLen);
            }
            return Math.max(teamworkScore, maxTeamWorkScore);
        }

        for (int i = start; i < n; i++) {
            scratch[k] = players[i];
            k++;
            maxTeamWorkScore = findMaxTeamWorkRecursive(players, outPlayers, n, i + 1, k, scratch, scratchLen, maxTeamWorkScore);
            k--;
        }
        return maxTeamWorkScore;
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

    private static void sortGameStat(GameStat[] gameStats) {
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
}