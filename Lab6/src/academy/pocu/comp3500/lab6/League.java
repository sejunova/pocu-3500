package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class League {
    private TreeNode root;
    private int size;

    public League() {
    }

    public League(final Player[] players, boolean sorted) {
        if (players != null && players.length > 0) {
            if (sorted && players.length > 2) {
                int mid = players.length / 2;
                root = BinarySearchTree.insert(root, players[mid]);

                int depth = 1;
                boolean leftEndReach = false;
                boolean rightEndReach = false;

                int left = mid - 1;
                int right = mid + 1;

                while (!leftEndReach || !rightEndReach) {
                    if (!leftEndReach) {
                        for (int i = 0; i < depth; i++) {
                            root = BinarySearchTree.insert(root, players[left--]);
                            if (left == -1) {
                                leftEndReach = true;
                                break;
                            }
                        }
                    }

                    if (!rightEndReach) {
                        for (int i = 0; i < depth; i++) {
                            root = BinarySearchTree.insert(root, players[right++]);
                            if (right == players.length) {
                                rightEndReach = true;
                                break;
                            }
                        }
                    }
                    depth *= 2;
                }
            } else {
                for (Player player : players) {
                    root = BinarySearchTree.insert(root, player);
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
            root = BinarySearchTree.insert(root, player);
            size++;
            return true;
        }

        // 이미 선수가 경기장에 있는 경우
        if (BinarySearchTree.search(root, player) != null) {
            return false;
        }
        root = BinarySearchTree.insert(root, player);
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
