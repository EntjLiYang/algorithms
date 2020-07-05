package binaryTree;

import org.omg.CORBA.INTERNAL;

import java.util.LinkedList;

/**
 * 序列化二叉树要保证能够无歧义地重建该二叉树
 * 每个字符结束需要结束标志位；每个null节点需要一个特殊字符来表示
 */
public class SerializeBinaryTree {

    /**
     * 按照层序遍历
     * 每个节点结束标志位 ！
     * null节点表示 #
     * @param head
     * @return
     */
    public String serialLevelOrder(Node head){
        if (head == null)
            return "";

        String str = head.value + "!";
        LinkedList<Node> queue = new LinkedList<>();
        queue.addLast(head);

        while (!queue.isEmpty()){
            head = queue.pollFirst();
            if (head.left != null){
                str += head.value + "!";
                queue.addLast(head.left);
            }else {
                str += "#!";
            }
            if (head.right != null){
                str += head.value + "!";
                queue.addLast(head.right);
            }else {
                str += "#!";
            }
        }
        return str;
    }

    public Node deserialLevelOrder(String string){
        if (string == null || string.length() == 0)
            return null;

        String[] strs = string.split("!");

        int index = 0;

        Node head = generateNode(strs[index]);
        LinkedList<Node> queue = new LinkedList<>();
        if (head == null)
            return null;
        queue.addLast(head);

        while (!queue.isEmpty()){
            Node parent = queue.pollFirst();
            Node left = generateNode(strs[index++]);
            Node right = generateNode(strs[index++]);

            if (left != null){
                parent.left = left;
                queue.addLast(left);
            }
            if (right != null){
                parent.right = right;
                queue.addLast(right);
            }
        }
        return head;
    }

    private Node generateNode(String str){
        if (str.equals("#"))
            return null;
        return new Node(Integer.valueOf(str));
    }



    /**
     * 先序遍历
     * 结束标志位 ！
     * null节点表示 #
     * @param head
     * @return
     */
    public String serialInOrder(Node head){
        if (head == null){
            return "#!";
        }

        String str = head.value + "!";
        str += serialInOrder(head.left);
        str += serialInOrder(head.right);

        return str;
    }

    /**
     * 先序遍历
     * @param str
     * @return
     */
    public Node deserialInOrder(String str){
        if (str == null || str.length() == 0){
            return null;
        }

        String[] strs = str.split("!");

        return deserial(strs, 0);
    }

    private Node deserial(String[] strs, int index){
        String cur = strs[index];
        Node curNode;

        if (cur.equals("#")){
            curNode = null;
            return curNode;
        }
        curNode = new Node(Integer.valueOf(cur));

        if (index + 1 < strs.length)
            curNode.left = deserial(strs, index+1);
        if (index + 2 < strs.length)
            curNode.right = deserial(strs, index+2);

        return curNode;
    }
}
