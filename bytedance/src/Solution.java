import java.util.Arrays;
import java.util.HashMap;

public class Solution {
    // 1 Two sum
    public int[] twoSumByHash(int[] nums, int target){
        HashMap<Integer, Integer> hash = new HashMap<>();
        for (int i = 0; i < nums.length; i++){
            int num = target - nums[i];
            if (hash.containsKey(num)){
                return new int[]{i, hash.get(num)};
            }
            hash.put(nums[i], i);
        }
        throw  new IllegalArgumentException("不存在");
    }

    public int[] twoSum(int[] nums, int target) {
        int[] returnNums = new int[2];
        int[] newNums = Arrays.copyOfRange(nums, 0, nums.length);
        Arrays.sort(newNums);
        int[] result = new int[2];
        for (int i = 0; i < newNums.length; i++){
            Integer index = binarySearch(newNums, i + 1, nums.length - 1, target);
            if (index != null) {
                result[0] = newNums[i];
                result[1] = newNums[index];
            }
            break;
        }
        int count = 0;
        for (int i = 0; i < nums.length; i++){
            if (count == 2)
                break;
            if (nums[i] == result[0]){
                returnNums[0] = i;
                count++;
            }
            if (nums[i] == result[1]){
                returnNums[1] = i;
                count++;
            }
        }
        return returnNums;
    }
    private Integer binarySearch(int[] nums, int left, int right, int target){
        if (right < left){
            return null;
        }
        while (left <= right){
            int middle = (right - left) / 2 + left;
            if (nums[middle] == target){
                return middle;
            }else if (nums[middle] < target){
                left = middle + 1;
            }else{
                right = middle - 1;
            }
        }
        return null;
    }


    // 3
    public int lengthOfLongestSubstring(String s) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        int maxLength = 0;
        int indexLeft = 0, indexRight = 0;
        while (indexRight < s.length()){
            String cur = s.substring(indexRight, indexRight + 1);
            if (hashMap.containsKey(cur)){
                int temp = hashMap.get(cur);
                for (int i = indexLeft; i <= temp; i++){
                    hashMap.remove(s.substring(i, i+1));
                }
                indexLeft = temp + 1;
            }

            hashMap.put(cur, indexRight);
            maxLength = Math.max(maxLength, indexRight - indexLeft + 1);
            indexRight++;
        }
        return maxLength;
    }
}
