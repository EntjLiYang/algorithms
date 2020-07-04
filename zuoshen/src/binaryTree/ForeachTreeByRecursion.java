package binaryTree;

import java.lang.annotation.Documented;

public class ForeachTreeByRecursion {
    /**
     * 中序遍历
     * @param head
     */
    public void inOrder(Node head){
        if (head == null){
            return;
        }

        inOrder(head.left);
        System.out.println(head.value);
        inOrder(head.right);
    }

    public void postOrder(Node head){
        if (head == null){
            return;
        }

        postOrder(head.left);
        postOrder(head.right);
        System.out.println(head.value);
    }

    public void preOrder(Node head){
        if (head == null){
            return;
        }

        System.out.println(head.value);
        preOrder(head.left);
        preOrder(head.right);
    }
}
