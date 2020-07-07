package list;

/**
 * @Author liyang
 * @Date 2020/7/7 5:53 下午
 * @Description 翻转单向链表，要求原地翻转
 **/
public class ReverseList {

    public Node reverse(Node head){
        if (head == null || head.next == null)
            return head;

        Node pre = null;
        Node next = null;

        while (head != null){
            next = head.next;
            head.next = pre;

            pre = head;
            head = next;
        }
        return pre;
    }

    private class Node{
        public int value;
        public Node next;

        public Node(int val){
            this.value = val;
        }
    }
}
