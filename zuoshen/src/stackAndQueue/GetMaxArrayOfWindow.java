package zuoShen.stackAndQueue;

import java.util.LinkedList;

public class GetMaxArrayOfWindow {
    /**
     * 窗口大小为w，窗口从左到右每次滑动一个位置，直到右边界，每次得到窗口中的最大值
     * 返回所有的窗口中的最大值，共n-w+1个
     * 时间复杂度O(n)
     * 空间为O(w)
     * @param arr
     * @param w
     * @return
     */
    public int[] getMaxArray(int[] arr, int w){
        int n = arr.length;
        int[] result = new int[n - w + 1];

        LinkedList<Integer> deQueue = new LinkedList<>();

        int index = 1;
        for (int i = 0; i < n; i++){
            while (!deQueue.isEmpty() && deQueue.peekLast() <= arr[i]){
                deQueue.pollLast();
            }
            deQueue.addLast(i);
            if (deQueue.peekFirst() == i - w){
                deQueue.pollFirst();
            }
            if (i >= w - 1){
                result[index++] = deQueue.peekFirst();
            }
        }
        return result;
    }

    public int[] getMaxOfWindows(int[] arr, int w){
        int n = arr.length;
        int count = n - w + 1;

        int[] result = new int[count];
        int index = 0;

        LinkedList<Integer> queue = new LinkedList<>();

        for (int i = 0; i < arr.length; i++){
            int curValue = arr[i];
            if (queue.isEmpty())
                queue.addLast(curValue);
            else if (queue.peekLast() >= curValue)
                queue.addLast(curValue);
            else if (queue.peekLast() < curValue){
                queue.clear();
                queue.addLast(curValue);
            }
            if (queue.size() > w)
                queue.pollFirst();
            if (i >= w - 1){
                result[index++] = queue.peekFirst();
            }
        }
        return result;
    }
}
