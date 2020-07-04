package stackAndQueue;

import java.util.LinkedList;

public class GetNum {
    public int getNum(int[] arr, int num){
        LinkedList<Integer> highQueue = new LinkedList<>(), smallQueue = new LinkedList<>();

        if (arr == null || num < 0 || arr.length == 0){
            return 0;
        }

        int count = 0;

        int i = 0, j = 0; // i表示所有以i为起点的子数组，共有 j - i + 1 个
        while (i < arr.length && j < arr.length){
            while (!highQueue.isEmpty() && highQueue.peekLast() > arr[j])
                highQueue.pollLast();
            while (!smallQueue.isEmpty() && smallQueue.peekLast() < arr[j])
                smallQueue.pollLast();

            highQueue.addLast(arr[j]);
            smallQueue.addLast(arr[j]);

            if (smallQueue.peekFirst() - highQueue.peekFirst() > num) {
                count += j - i;
                if (highQueue.peekFirst() == arr[i])
                    highQueue.pollFirst();
                if (smallQueue.peekFirst() == arr[i])
                    smallQueue.pollFirst();
                i++;
            }
            j++;
        }

        return count;
    }
}
