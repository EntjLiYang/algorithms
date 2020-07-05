package binaryTree;

public class ForeachTreeByMorris {

    /**
     * @param head
     */
    public void inOrder(Node head){
        Node cur = head;
        while (cur != null){
            if (cur.left == null){
                System.out.println(cur.value);
                cur = cur.right;
            }else {
                Node pre = getPreNode(cur);
                if (pre.right == cur){
                    pre.right = null;
                    System.out.println(cur.value);
                    cur = cur.right;
                }else if (pre.right == null){
                    pre.right = cur;
                    cur = cur.left;
                }
            }
        }
    }

    private Node getPreNode(Node head){
        if (head == null)
            return null;
        Node pre = head;
        if (pre.left != null){
            while (pre.right != null && pre.right != head){
                pre = pre.right;
            }
        }
        return pre;
    }
}
