package zuoShen.stackAndQueue;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class GetFirtBiggerNum {
    public Integer[][] getFirstBiggerNums(int[] arr){
        Integer[][] result = new Integer[2][arr.length];
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < arr.length; i++){
            while (!queue.isEmpty() && queue.peekLast() <= arr[i]){
                queue.pollLast();
            }
            result[0][i] = queue.peekLast();
            queue.addLast(arr[i]);
        }

        for (int i = arr.length - 1; i >= 0; i--){
            while (!queue.isEmpty() && queue.peekLast() <= arr[i]){
                queue.pollLast();
            }
            result[1][i] = queue.peekLast();
            queue.addLast(arr[i]);
        }
        return result;
    }

    public Node getMaxTree(int[] arr){
        Integer[][] maxNums = getFirstBiggerNums(arr);

        HashMap<Integer, Node> map = new HashMap<>();
        Node root = new Node(arr[0]);
        for (int i = 0; i < arr.length; i++){
            int curValue = arr[i];
            Node curNode;
            if (!map.containsKey(curValue)) {
                curNode = new Node(curValue);
                map.put(curValue, curNode);
            }
            else curNode = map.get(curValue);

            Integer leftBiggerValue = maxNums[0][i];
            Integer rightBiggerValue = maxNums[1][i];

            Integer parentValue = null;
            if (leftBiggerValue != null && rightBiggerValue != null) {
                parentValue = Math.max(leftBiggerValue, rightBiggerValue);
            }else if (leftBiggerValue != null){
                parentValue = leftBiggerValue;
            }else if (rightBiggerValue != null){
                parentValue = rightBiggerValue;
            }

            Node parentNode = null;
            if (parentValue != null && !map.containsKey(parentValue)) {
                parentNode = new Node(parentValue);
                map.put(parentValue, parentNode);
            }
            else if (parentValue != null)
                parentNode = map.get(parentValue);

            if (parentValue != null && parentValue > root.value){
                root = parentNode;
            }

            if (parentNode != null && parentNode.left == null)
                parentNode.left = curNode;
            else if (parentNode != null)
                parentNode.right = curNode;
        }
        return root;
    }

    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int val){
            this.value = val;
        }
    }
}
