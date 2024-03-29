package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BinarySearchTree {
    public static TreeNode insertAndReturnRoot(TreeNode root, Player val) {
        if (root == null) {
            return new TreeNode(val);
        }
        TreeNode cur = root;
        while (true) {
            if (val.getRating() < cur.val.getRating()) {
                if (cur.left == null) {
                    cur.left = new TreeNode(val);
                    break;
                } else {
                    cur = cur.left;
                }
            } else {
                if (cur.right == null) {
                    cur.right = new TreeNode(val);
                    break;
                } else {
                    cur = cur.right;
                }
            }
        }
        return root;
    }

    public static TreeNode insert(TreeNode root, Player val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (val.getRating() < root.val.getRating()) {
            return insert(root.left, val);
        } else {
            return insert(root.right, val);
        }
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

    public static void traverse(TreeNode root, List<Player> players) {
        if (root == null) {
            return;
        }
        traverse(root.left, players);
        players.add(root.val);
        traverse(root.right, players);
    }

    public static TreeNode sortedArrayToBST(Player[] players) {

        List<TreeNode> treeNodes = new ArrayList<>(players.length);
        TreeNode parent = new TreeNode(players[0]);
        treeNodes.add(parent);
        for (int i = 1; i < players.length; i++) {
            Player p = players[i];
            if (p.getRating() == parent.val.getRating()) {
                parent.right = new TreeNode(p, null, parent.right);
            } else {
                parent = new TreeNode(p);
                treeNodes.add(parent);
            }
        }

        Stack<MyTreeNode> rootStack = new Stack<>();
        int start = 0;
        int end = treeNodes.size();
        int mid = (start + end) >>> 1;
        TreeNode root = treeNodes.get(mid);
        TreeNode curRoot = root;
        rootStack.push(new MyTreeNode(root, start, end));
        while (end - start > 1 || !rootStack.isEmpty()) {
            while (end - start > 1) {
                mid = (start + end) >>> 1;
                end = mid;
                mid = (start + end) >>> 1;
                curRoot.left = treeNodes.get(mid);
                curRoot = curRoot.left;
                rootStack.push(new MyTreeNode(curRoot, start, end));
            }
            MyTreeNode myNode = rootStack.pop();
            start = myNode.start;
            end = myNode.end;
            mid = (start + end) >>> 1;
            start = mid + 1;
            curRoot = myNode.root;
            if (start < end) {
                mid = (start + end) >>> 1;
                while (curRoot.right != null) {
                    curRoot = curRoot.right;
                }
                curRoot.right = treeNodes.get(mid);
                curRoot = curRoot.right;
                rootStack.push(new MyTreeNode(curRoot, start, end));
            }
        }
        return root;
    }

    private static class MyTreeNode {
        TreeNode root;
        int start;
        int end;

        MyTreeNode(TreeNode r, int s, int e) {
            this.root = r;
            this.start = s;
            this.end = e;
        }
    }
}
