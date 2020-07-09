package binaryTree;

public class GetMaxLength {
    public int getMaxLength(Node head, int sum){
        if (head == null)
            return sum == 0 ? 0 : Integer.MIN_VALUE;

        int left = Math.max(getMaxLength(head.left, sum - head.value) + 1, getMaxLength(head.left, sum));
        int right = Math.max(getMaxLength(head.right, sum - head.value) + 1, getMaxLength(head.right, sum));

        return Math.max(left, right);
    }
}
