package others;

/**
 * Name：LiYang
 * DATE: 2019/12/18
 * TIME: 22:12
 */
public class InsertSort {
    public static void sort(int[] nums, int left, int right) {
        if (left >= right)
            return;

        for (int i = left + 1; i <= right; i++){
            int temp = nums[i];
            for (int j = i - 1; j >= 0; j--){
                if (nums[j] > temp){
                    nums[j+1] = nums[j];
                }else {
                    nums[j+1] = temp;
                    break;
                }
            }
        }
    }
}
