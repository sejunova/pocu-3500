package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class BinarySearchTree {
    public static TreeNode insert(TreeNode root, Player val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (val.getRating() < root.val.getRating()) {
            root.left = insert(root.left, val);
        } else {
            root.right = insert(root.right, val);
        }
        return root;
    }

    public static TreeNode delete(TreeNode root, Player key) {
        if (root == null)
            return root;

        if (root.val.getId() == key.getId()) {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            //in-order successor
            TreeNode s = root.right;
            while (s.left != null) {
                s = s.left;
            }
            root.val = s.val;
            root.right = delete(root.right, root.val);
        } else if (key.getRating() < root.val.getRating()) {
            root.left = delete(root.left, key);
        } else {
            root.right = delete(root.right, key);
        }
        return root;
    }

    public static TreeNode search(TreeNode root, Player val) {
        if (root == null || root.val.getId() == val.getId()) {
            return root;
        }
        if (val.getRating() < root.val.getRating()) {
            return search(root.left, val);
        } else {
            return search(root.right, val);
        }
    }

    public static void findClosest(TreeNode root, Player player, ClosestPlayer closestPlayer) {
        if (root == null) {
            return;
        }

        if (root.val.getId() == player.getId()) {
            if (root.left != null) {
                //pre-order successor
                TreeNode s = root.left;
                while (s.right != null) {
                    s = s.right;
                }
                closestPlayer.updateNodes(s.val);
            }
        } else {
            closestPlayer.updateNodes(root.val);
        }

        if (closestPlayer.cp != null && closestPlayer.cp.getRating() == player.getRating()) {
            return;
        }
        if (player.getRating() < root.val.getRating()) {
            findClosest(root.left, player, closestPlayer);
        } else {
            findClosest(root.right, player, closestPlayer);
        }
    }

    public static void getTopHelper(TreeNode root, Player[] players, Count c) {
        if (root == null || c.c == players.length) {
            return;
        }
        getTopHelper(root.right, players, c);
        if (c.c < players.length) {
            players[c.c++] = root.val;
            getTopHelper(root.left, players, c);
        }
    }

    public static void getBottomHelper(TreeNode root, Player[] players, Count c) {
        if (root == null || c.c == players.length) {
            return;
        }
        getBottomHelper(root.left, players, c);
        if (c.c < players.length) {
            players[c.c++] = root.val;
            getBottomHelper(root.right, players, c);
        }
    }
}
