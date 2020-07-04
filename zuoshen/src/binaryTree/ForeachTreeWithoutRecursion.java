package binaryTree;

import java.util.Stack;

public class ForeachTreeWithoutRecursion {
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
