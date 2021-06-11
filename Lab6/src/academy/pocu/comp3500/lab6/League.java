package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class League {
    private TreeNode root;

    public League(final Player[] players, boolean sorted) {
        if (sorted) {
            int mid = players.length / 2;
            int left = mid - 1;
            int right = mid;
            while (0 <= left && right < players.length) {
                root = BinarySearchTree.insertIntoBST(root, players[left--]);
                root = BinarySearchTree.insertIntoBST(root, players[right++]);
            }
        } else {
            for (Player player: players) {
                root = BinarySearchTree.insertIntoBST(root, player);
            }
        }
    }

    public Player findMatchOrNull(final Player player) {
        TreeNode playerFound = BinarySearchTree.searchBST(root, player);
        if (!(playerFound.val.getId() == player.getId())) {
            return playerFound.val;
        }

        TreeNode preOrderSuccessor = playerFound.left;
        TreeNode inOrderSuccessor = playerFound.right;

        if (preOrderSuccessor != null) {
            while (preOrderSuccessor.right != null) {
                preOrderSuccessor = preOrderSuccessor.right;
            }
        }

        if (inOrderSuccessor != null) {
            while (inOrderSuccessor.left != null) {
                inOrderSuccessor = inOrderSuccessor.left;
            }
        }

        if (preOrderSuccessor == null && inOrderSuccessor != null) {
            return inOrderSuccessor.val;
        } else if (preOrderSuccessor != null && inOrderSuccessor == null) {
            return preOrderSuccessor.val;
        } else if (preOrderSuccessor == null && inOrderSuccessor == null) {
            return null;
        } else {
            int preOrderDiff = player.getRating() - preOrderSuccessor.val.getRating();
            int inOrderDiff = inOrderSuccessor.val.getRating() - player.getRating();
            return (preOrderDiff < inOrderDiff) ? preOrderSuccessor.val : inOrderSuccessor.val;
        }
    }

    public Player[] getTop(final int count) {
        Player[] players = new Player[count];

        BinarySearchTree.getTopHelper(root, players, new Count());
        return players;
    }

    public Player[] getBottom(final int count) {
        Player[] players = new Player[count];

        BinarySearchTree.getBottomHelper(root, players, new Count());
        return players;
    }

    public boolean join(final Player player) {
        return false;
    }

    public boolean leave(final Player player) {
        return false;
    }
}
