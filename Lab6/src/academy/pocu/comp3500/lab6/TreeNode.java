package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class TreeNode {
    public Player val;
    public TreeNode left;
    public TreeNode right;

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
        String val = this.val.toString();
        String left = (this.left == null) ? "null" : this.left.val.toString();
        String right = (this.right == null) ? "null" : this.right.val.toString();
        return "TreeNode{" +
                "val=" + val +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}

