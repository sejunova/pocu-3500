package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class League {
    public TreeNode root;
    private int size;

    public League() {
    }

    public League(final Player[] players, boolean sorted) {
        if (players != null) {
            if (sorted && players.length > 1000) {
                root = BinarySearchTree.sortedArrayToBST(null, players);
            } else {
                for (Player player : players) {
                    root = BinarySearchTree.insertAndReturnRoot(root, player);
                }
            }
            size += players.length;
        }
    }

    public Player findMatchOrNull(final Player player) {
        ClosestPlayer closestPlayer = new ClosestPlayer(player.getRating());
        BinarySearchTree.findClosest(root, player, closestPlayer);
        return closestPlayer.cp;
    }

    public Player[] getTop(final int count) {
        Player[] players = new Player[Math.min(size, Math.max(count, 0))];

        BinarySearchTree.getTopHelper(root, players, new Count());
        return players;
    }

    public Player[] getBottom(final int count) {
        Player[] players = new Player[Math.min(size, Math.max(count, 0))];

        BinarySearchTree.getBottomHelper(root, players, new Count());
        return players;
    }

    public boolean join(final Player player) {
        if (player == null) {
            return false;
        }
        if (size == 0) {
            root = BinarySearchTree.insertAndReturnRoot(root, player);
            size++;
            return true;
        }

        // 이미 선수가 경기장에 있는 경우
        if (BinarySearchTree.search(root, player) != null) {
            return false;
        }
        root = BinarySearchTree.insertAndReturnRoot(root, player);
        size++;
        return true;
    }

    public boolean leave(final Player player) {
        if (player == null || size == 0) {
            return false;
        }

        // 이미 선수가 경기장에 없는 경우
        if (BinarySearchTree.search(root, player) == null) {
            return false;
        }
        root = BinarySearchTree.delete(root, player);
        size--;
        return true;
    }
}
