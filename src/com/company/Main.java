package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
    }
}

class Solution {

    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null)
            return res;
        Set<TreeNode> visited = new HashSet<>();
        travelToLeftBound(root, visited, res);
        travelToAllLeaves(root, visited, res);
        travelToRightBound(root, visited, res);
        return res;
    }

    private void travelToLeftBound(TreeNode root, Set<TreeNode> visited, List<Integer> res) {
        TreeNode curr = root;
        if (curr.left == null) {
            if (!visited.contains(curr))
                res.add(curr.val);
            visited.add(curr);
            return;
        }
        while (!isLeaf(curr)) {
            if (!visited.contains(curr))
                res.add(curr.val);
            visited.add(curr);
            curr = curr.left != null ? curr.left : curr.right;
        }
    }

    private void travelToRightBound(TreeNode root, Set<TreeNode> visited, List<Integer> res) {
        TreeNode curr = root;
        if (curr.right == null) {
            if (!visited.contains(curr))
                res.add(curr.val);
            visited.add(curr);
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        while (!isLeaf(curr)) {
            stack.push(curr);
            curr = curr.right != null ? curr.right : curr.left;
        }

        while (!stack.isEmpty()) {
            curr = stack.pop();
            if (!visited.contains(curr))
                res.add(curr.val);
            visited.add(curr);
        }
    }

    private void travelToAllLeaves(TreeNode root, Set<TreeNode> visited, List<Integer> res) {
        TreeNode curr = root;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(curr);
        while (!stack.isEmpty() || curr != null) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            if (isLeaf(curr) && !visited.contains(curr)) {
                res.add(curr.val);
                visited.add(curr);
            }
            curr = curr.right;
        }

    }

    private boolean isLeaf(TreeNode node) {
        return node != null && node.left == null && node.right == null;
    }
}

// One Pass
class Solution {
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        if (root != null) {
            res.add(root.val);
            getBounds(root.left, res, true, false);
            getBounds(root.right, res, false, true);
        }
        return res;
    }

    private void getBounds(TreeNode node, List<Integer> res, boolean lb, boolean rb) {
        if (node == null) return;
        if (lb) res.add(node.val);
        if (!lb && !rb && node.left == null && node.right == null) res.add(node.val);
        getBounds(node.left, res, lb, rb && node.right == null);
        getBounds(node.right, res, lb && node.left == null, rb);
        if (rb) res.add(node.val);
    }
}