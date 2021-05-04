package academy.pocu.comp3500.assignment1.util;

import academy.pocu.comp3500.assignment1.pba.Player;

import java.util.function.Function;

public class PlayerUtil {
    public static int binarySearch(Player[] players, Function<Player, Integer> supplier, int target, int left, int right) {
        if (left >= right) {
            return left;
        }
        int mid = left + (right - 1) / 2;
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

    public static long dfs(final Player[] players, final Player[] outPlayers, int n, int start, int k, final Player[] scratch, int scratchLen, long maxTeamWorkScore) {
        if (k == scratchLen) {
            long teamworkScore = getTeamWorkScore(scratch, scratchLen);
            if (teamworkScore >  maxTeamWorkScore) {
                System.arraycopy(scratch, 0, outPlayers, 0, scratchLen);
            }
            return Math.max(teamworkScore,  maxTeamWorkScore);
        }

        for (int i = start; i < n; i++) {
            scratch[k] = players[i];
            k++;
            maxTeamWorkScore = dfs(players, outPlayers, n, i + 1, k, scratch, scratchLen,  maxTeamWorkScore);
            k--;
        }
        return  maxTeamWorkScore;
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
}
