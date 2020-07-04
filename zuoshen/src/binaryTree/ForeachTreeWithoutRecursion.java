package binaryTree;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ForeachTreeWithoutRecursion {

    /**
     * BFS层序遍历二叉树，但是二叉树的所有节点作为一个一维数组输出，各层之间没有区分
     * @param head
     */
    public void BFSForTree(Node head){
        if (head == null)
            return;

        LinkedList<Node> queue = new LinkedList<>();
        queue.addLast(head);
        while (!queue.isEmpty()){
            Node cur = queue.pollFirst();
            System.out.println(cur.value);
            if (cur.left != null)
                queue.addLast(cur.left);
            if (cur.right != null)
                queue.addLast(cur.right);
        }
    }

    /**
     * 层序遍历
     * @param head
     * @return 层序遍历结果，每一层单独一个list
     */
    public List<List<Node>> levelOrder(Node head){
        if (head == null)
            return new LinkedList<>();

        LinkedList<Node> queue = new LinkedList<>();
        queue.addLast(head);

        List<List<Node>> result = new LinkedList<>();

        while (!queue.isEmpty()){
            int n = queue.size();
            List<Node> curLevel = new LinkedList<>();

            for (int i = 0; i < n; i++){
                Node cur = queue.pollFirst();
                curLevel.add(cur);
                if (cur.left != null){
                    queue.addLast(cur.left);
                }
                if (cur.right != null){
                    queue.addLast(cur.right);
                }
            }

            result.add(curLevel);
        }
        return result;
    }

    /**
     * 前序遍历
     */
    public void preOrder(Node head){
        if (head == null)
            return;

        Stack<Node> stack = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty()){
            Node cur = stack.pop();
            System.out.println(cur.value);

            if (cur.right != null)
                stack.push(cur.right);
            if (cur.left != null)
                stack.push(cur.left);
        }
    }

    /**
     * 后序遍历
     * @param head
     */
    public void postOrder(Node head){
        if (head == null)
            return;

        Stack<Node> stack = new Stack<>();
        Stack<Node> result = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty()){
            Node cur = stack.pop();
            result.push(cur);
            if (cur.left != null)
                stack.push(cur.left);
            if (cur.right != null)
                stack.push(cur.right);
        }
        while (!result.isEmpty()){
            System.out.println(result.pop().value);
        }
    }

    /**
     * 中序遍历
     * @param head
     */
    public void inOrder(Node head){
        if (head == null)
            return;

        Stack<Node> stack = new Stack<>();

        pushLeftToStack(head, stack);

        while (!stack.isEmpty()){
            Node cur = stack.pop();
            System.out.println(cur.value);
            if (cur.right != null)
                pushLeftToStack(cur.right, stack);
        }
    }

    /**
     * 将head为头节点的树的最左边的节点入栈
     * @param head
     * @param stack
     */
    private void pushLeftToStack(Node head, Stack<Node> stack){
        while (head != null){
            stack.push(head);
            head = head.left;
        }
    }
}
