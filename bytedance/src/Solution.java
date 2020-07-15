import sun.text.normalizer.CharacterIteratorWrapper;

import javax.swing.*;
import java.util.*;

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

    // 19
    public ListNode removeNthFromEnd(ListNode head, int n){
        ListNode prv = new ListNode(0);
        prv.next = head;

        ListNode left = prv;
        ListNode cur = head;
        int count = 1;
        while (cur != null){
            if (count == n + 1){
                left = left.next;
                count--;
            }
            cur = cur.next;
            count++;
        }
        ListNode next = left.next;
        left.next = next.next;
        next.next = null;

        head = prv.next;
        prv.next = null;

        return head;
    }

//    public ListNode removeNthFromEnd(ListNode head, int n) {
//        head = reverse(head);
//        ListNode prv = new ListNode(0);
//        prv.next = head;
//
//        ListNode pre = prv;
//        for (int i = 1; i <= n; i++){
//            if (i == n){
//                pre.next = head.next;
//                head.next = null;
//                break;
//            }
//            head = head.next;
//            pre = pre.next;
//        }
//
//        head = prv.next;
//        prv.next = null;
//        return reverse(head);
//    }
//
    private ListNode reverse(ListNode head){
        ListNode pre = null, next = null;

        while (head != null){
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }

        return pre;
    }

    // 22
    public List<String> generateParenthesis(int n) {
        if (n == 0)
            return new ArrayList<>();
        generate("(", n - 1, n);
        return result;
    }
    private List<String> result = new ArrayList<>();
    private void generate(String cur, int leftNumbers, int rightNumbers){
        if (leftNumbers == 0 && rightNumbers == 0){
            result.add(cur + "");
        }

        if (leftNumbers > 0){
            generate(cur + "(", leftNumbers - 1, rightNumbers);
        }
        // 剩下的括号中，右括号一定要比左括号多
        if (rightNumbers > 0 && rightNumbers - 1 >= leftNumbers){
            generate(cur + ")", leftNumbers, rightNumbers - 1);
        }

    }

    // 25
    public ListNode reverseKGroup(ListNode head, int k) {
        // 反转前k个作为整个链表的头
        ListNode cur = head;
        int count = 1;
        while (count < k){
            cur = cur.next;
            count++;
        }
        ListNode next = cur.next;
        cur.next = null;
        ListNode preTail = head;
        head = reverse(head);
        cur = next;
        count = 1;

        // 遍历链表剩余部分，不断寻找k个为一组，切断与后续链表链接后反转
        // 维护前置k个的tail，进行拼接
        ListNode midHead = cur, midTail = null;
        while (cur != null){
            if (count == k){
                next = cur.next;
                cur.next = null;

                midTail = midHead;
                midHead = reverse(midHead);

                preTail.next = midHead;
                preTail = midTail;

                cur = next;
                midHead = cur;
                count = (cur == null) ? 0 : 1;
            }
            cur = cur.next;
            count++;
        }
        // 可能最后不足k个，也要拼接尾首
        preTail.next = midHead;

        return head;
    }

    // 31
    public void nextPermutation(int[] nums) {
        int index = nums.length - 2;
        while (index >= 0){
            if (nums[index] < nums[index + 1]){
                break;
            }
            index--;
        }
        if (index < 0){
            reverse(nums, 0, nums.length - 1);
            return;
        }

        for (int i = nums.length - 1; i > index; i--){
            if (nums[i] > nums[index]){
                swap(nums, index, i);
                reverse(nums, index + 1, nums.length - 1);
                break;
            }
        }
    }
    private void swap(int[] nums, int left, int right){
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }
    private void reverse(int[] nums, int left, int right){
        while ( left < right){
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    // 33
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0){
            return -1;
        }
        int left = 0, right = nums.length - 1;
        int middle = left + (right - left) / 2;
        while (left <= right){
            if (nums[middle] == target){
                return middle;
            }
            if (nums[left] <= nums[middle]){
                if (nums[left] <= target && target <= nums[middle]){
                    right = middle - 1;
                }else {
                    left = middle + 1;
                }
            }else {
                if (nums[middle] <= target && target <= nums[right]){
                    left = middle + 1;
                }else {
                    right = middle - 1;
                }
            }
            middle = left + (right - left) / 2;
        }
        return -1;
    }

    // 34
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[2];
        result[0] = -1;
        result[1] = -1;

        int left = 0, right = nums.length - 1;
        int middle = left + (right - left) / 2;
        while (left <= right){
            if (nums[middle] == target){
                if (middle == 0 || nums[middle - 1] < nums[middle]){
                    result[0] = middle;
                    break;
                }else {
                    right = middle - 1;
                }
            }else if (nums[middle] > target){
                right = middle - 1;
            }else {
                left = middle + 1;
            }
            middle = left + (right - left) / 2;
        }

        left = 0;
        right = nums.length - 1;
        middle = left + (right - left) / 2;
        while (left <= right){
            if (nums[middle] == target){
                if (middle == nums.length - 1 || nums[middle + 1] > nums[middle]){
                    result[1] = middle;
                    break;
                }else {
                    left = middle + 1;
                }
            }else if (target < nums[middle]){
                right = middle - 1;
            }else {
                left = middle + 1;
            }
            middle = left + (right - left) / 2;
        }
        return result;
    }

    // 42
    public int trap(int[] height) {
        if (height == null || height.length <= 2){
            return 0;
        }
        int[] sums = getSums(height);
        int[] biggers = getFirstBigger(height);
        int maxWater = 0;

        for (int i= 0; i < height.length;){
            int rightRange = biggers[i];
            if (rightRange != -1)
                maxWater += (rightRange - i - 1) * height[i] - (sums[rightRange] - sums[i] - height[rightRange]);
            i = rightRange;
        }

        biggers = getFirstBiggerStartInRight(height);
        for (int i= height.length - 1; i >= 0;){
            int leftRange = biggers[i];
            if (leftRange != -1)
                maxWater += (i - leftRange - 1) * height[leftRange] - (sums[leftRange] - sums[i] - height[leftRange]);
            i = leftRange;
        }
        return maxWater;
    }
    private int[] getSums(int[] nums){
        int[] result = new int[nums.length];
        result[0] = nums[0];
        for (int i = 1; i < nums.length; i++){
            result[i] = result[i - 1] + nums[i];
        }
        return result;
    }
    private int[] getFirstBigger(int[] nums){
        LinkedList<Integer> list = new LinkedList<>();
        int[] result = new int[nums.length];

        for (int i = nums.length - 1; i >= 0; i--){
            while (!list.isEmpty() && nums[i] > list.peekFirst()){
                list.pollFirst();
            }
            if (list.isEmpty()){
                list.addFirst(i);
                result[i] = -1;
            }else if (nums[i] <= nums[list.peekFirst()]){
                result[i] = list.peekFirst();
                list.addFirst(i);
            }
        }
        return result;
    }
    private int[] getFirstBiggerStartInRight(int[] nums){
        LinkedList<Integer> list = new LinkedList<>();
        int[] result = new int[nums.length];

        for (int i = 0; i < nums.length; i++){
            while (!list.isEmpty() && nums[i] > list.peekLast()){
                list.pollLast();
            }
            if (list.isEmpty()){
                list.addLast(i);
                result[i] = -1;
            }else if (nums[i] <= nums[list.peekLast()]){
                result[i] = list.peekLast();
                list.addLast(i);
            }
        }
        return result;
    }
}
