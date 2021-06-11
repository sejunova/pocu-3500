package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class BinarySearchTree {
    public static TreeNode insertIntoBST(TreeNode root, Player val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (val.getRating() < root.val.getRating()) {
            root.left = insertIntoBST(root.left, val);
        } else {
            root.right = insertIntoBST(root.right ,val);
        }
        return root;
    }

    public static TreeNode deleteNodeFromBST(TreeNode root, Player key) {
        if (root == null)
            return root;

        if (key.getRating() < root.val.getRating())
            root.left = deleteNodeFromBST(root.left, key);
        else if (key.getRating() > root.val.getRating())
            root.right = deleteNodeFromBST(root.right, key);

        else {
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
            root.right = deleteNodeFromBST(root.right, root.val);
        }

        return root;
    }

    public static TreeNode searchBST(TreeNode root, Player val) {
        if (root == null || root.val.getId() == val.getId()) {
            return root;
        }
        if (val.getRating() < root.val.getRating()) {
            return searchBST(root.left, val);
        } else {
            return searchBST(root.right ,val);
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
