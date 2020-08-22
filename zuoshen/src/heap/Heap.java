package heap;

import java.util.Arrays;

/**
 * 堆数据结构，常见用途为优先队列，排序实际中并不常用；作为优先队列使用其目的是维护动态变化的数字序列，并保持其优先级顺序
 * 堆的要求：1.完全二叉树（只有最后一层可能不满，且最后一层节点从左到右依次排列，只能右边缺失）2.每个节点的数值都不大于其父节点的数值（大顶堆）
 * 实现堆的数据结构：不需要树节点，而是通过数组存储。原因在于完全二叉树具有的性质决定，i节点的子节点为2i和2i+1，i节点的父节点为i/2。因此可以将堆从上往下，从左往右依次存储到数组中（从1开始）
 * 为了保证堆的要求，每次插入元素插到数组末尾，然后通过向上比较并交换保证其不会大于父节点；每次弹出元素（只会弹出堆顶元素）将数组尾部元素交换到堆顶，然后通过向下比较并交换保证其不会小于子节点
 */
public class Heap {
    private int count;
    private int capacity;
    private int[] nums;

    /**
     * 给定一个堆的容量，然后将大小n的数组每个元素依次插入堆中，构造堆的时间复杂度是O(nlogn)
     *
     * @param n 堆的容量
     */
    public Heap(int n) {
        this.count = 0;
        this.capacity = n;
        this.nums = new int[capacity + 1];
    }

    /**
     * 给定一个大小为n的无序数组，然后自底向上构建堆，时间复杂度是O(n)，没错，就是n
     * 因为从下往上构建，对当前节点采用shiftDown满足堆定义，只有最上层一个节点需要logn来处理
     *
     * @param arr 无序数组
     */
    public Heap(int[] arr) {
        this.nums = Arrays.copyOf(arr, arr.length);

        int index = nums.length / 2; // 完全二叉树第一个非叶子节点索引是节点总数的一半，存储数组从1开始
        while (index >= 1) {
            shiftDown(index);
            index--;
        }
    }

    /**
     * @return 当前堆中元素数量
     */
    public int size() {
        return count;
    }

    /**
     * @return 当前堆是否为空
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * @return 当前堆是否满了
     */
    public boolean isFull() {
        return count == capacity;
    }

    /**
     * @param num 待插入堆中的元素
     * @return 是否插入成功
     */
    public boolean insert(int num) {
        if (isFull()) {
            return false;
        }

        nums[++count] = num;
        shiftUp(count); // 元素插入到数组尾部，通过向上比较并交换满足堆定义

        return true;
    }

    /**
     * @return 弹出一个元素
     */
    public int poll() {
        int res = nums[1];
        swap(nums, 1, count);
        count--;
        shiftDown(1); // 弹出一个元素后，将原尾部元素置顶，通过向下比较并交换满足堆定义

        return res;
    }

    /**
     * @param k 起点，从该位置开始向下不断比较并交换，从而满足堆定义
     */
    private void shiftDown(int k) {
        while (k < count) {
            int j = 2 * k;
            if (j > count) {
                break;
            }
            if (j + 1 <= count && nums[j] < nums[j + 1]) {
                j = j + 1;
            }

            swap(nums, k, j);
            k = j;
        }
    }


    /**
     * @param k 起点，从该位置开始不断向上比较并交换，从而满足堆定义
     */
    private void shiftUp(int k) {
        while (k > 1 && nums[k / 2] < nums[k]) {
            swap(nums, k / 2, k);
            k /= 2;
        }
    }

    /**
     * @param nums   交换数组两位置数值
     * @param index1
     * @param index2
     */
    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }
}
