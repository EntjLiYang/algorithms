import sun.text.normalizer.CharacterIteratorWrapper;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
        // 翻转前k个
        ListNode cur = head; // cur为遍历链表时当前的节点
        ListNode next = null; // next为得到一个k长度的子链表时切断翻转，要保持后面节点的引用
        int count = 1;
        while (count < k){
            cur = cur.next;
            count++;
        }
        next = cur.next;
        cur.next = null;
        count = 1;

        ListNode tail = head; // 当前遍历部分的前置链表尾部
        head = reverse(head);

        cur = next;
        ListNode midHead = next; // 当前遍历部分的链表头部
        ListNode midTail = null; // 当前遍历部分的链表尾部
        while (cur != null){
            if (count == k){ // 找到k个节点，切断链表，进行翻转
                next = cur.next;
                midTail = midHead;

                midHead = reverse(midHead);
                tail.next = midHead;
                tail = midTail;

                midHead = next;
                count = 1;
                cur = next;
            }else {
                count++;
                cur = cur.next;
            }
        }
        tail.next = midHead; // 可能最后不足k个

        return head;
    }

    // 31

}
