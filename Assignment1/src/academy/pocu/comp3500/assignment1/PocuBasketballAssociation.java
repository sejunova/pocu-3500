package academy.pocu.comp3500.assignment1;

import academy.pocu.comp3500.assignment1.pba.GameStat;
import academy.pocu.comp3500.assignment1.pba.Player;
import academy.pocu.comp3500.assignment1.util.GameStatUtil;
import academy.pocu.comp3500.assignment1.util.PlayerUtil;

public final class PocuBasketballAssociation {
    private PocuBasketballAssociation() {
    }

    public static void processGameStats(final GameStat[] gameStats, final Player[] outPlayers) {
        GameStatUtil.quickSort(gameStats);
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
        int idx = PlayerUtil.binarySearch(players, Player::getPointsPerGame, targetPoints, 0, players.length - 1);
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
        int idx = PlayerUtil.binarySearch(players, Player::getShootingPercentage, targetShootingPercentage, 0, players.length - 1);
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
        return PlayerUtil.findMaxTeamWorkRecursive(players, outPlayers, players.length, 0, 0, scratch, 3, 0);
    }

    public static long findDreamTeam(final Player[] players, int k, final Player[] outPlayers, final Player[] scratch) {
        return PlayerUtil.findMaxTeamWorkRecursive(players, outPlayers, players.length, 0, 0, scratch, k, 0);
    }

    public static int findDreamTeamSize(final Player[] players, final Player[] scratch) {
        int dreamTeamSize = 0;
        long dreamTeamMaxScore = 0;
        for (int k = 1; k <= players.length; k++) {
            long dreamTeamScore = PlayerUtil.findMaxTeamWorkRecursive(players, scratch, players.length, 0, 0, scratch, k, 0);
            if (dreamTeamScore >= dreamTeamMaxScore) {
                dreamTeamMaxScore = dreamTeamScore;
                dreamTeamSize = k;
            }
        }
        return dreamTeamSize;
    }
}