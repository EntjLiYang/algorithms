package others;

/**
 * Name：LiYang
 * DATE: 2019/12/18
 * TIME: 22:09
 */
public class QuickSort {
    public static void sort(int[] nums){
        quickSort(nums, 0, nums.length - 1);
    }

    private static void quickSort(int[] nums, int left, int right) {
        if (right - left <= 15){
            InsertSort.sort(nums, left, right);
            return;
        }

        swap(nums, left, (int)Math.random() * (right - left + 1) + left);
        int value = nums[left];
        int j = left;
        for (int i = left + 1; i <= right; i++){
            if (nums[i] <= value){
                swap(nums, j + 1, i);
                j++;
            }
        }
        swap(nums, left, j);
        quickSort(nums, left, j - 1);
        quickSort(nums, j + 1, right);
    }

    private static void swap(int[] nums, int left, int right) {

    }
}
