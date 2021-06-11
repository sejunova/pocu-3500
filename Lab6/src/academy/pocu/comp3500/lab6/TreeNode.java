package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class TreeNode {
    Player val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(Player val) {
        this.val = val;
    }

    TreeNode(Player val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        String val = this.val.getName();
        String left = (this.left == null) ? "null" : this.left.val.getName();
        String right = (this.right == null) ? "null" : this.right.val.getName();
        return "TreeNode{" +
                "val=" + val +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}

