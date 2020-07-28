import java.util.*;

public class Solution {
    // 1 Two sum
    public int[] twoSumByHash(int[] nums, int target) {
        HashMap<Integer, Integer> hash = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = target - nums[i];
            if (hash.containsKey(num)) {
                return new int[]{i, hash.get(num)};
            }
            hash.put(nums[i], i);
        }
        throw new IllegalArgumentException("不存在");
    }

    public int[] twoSum(int[] nums, int target) {
        int[] returnNums = new int[2];
        int[] newNums = Arrays.copyOfRange(nums, 0, nums.length);
        Arrays.sort(newNums);
        int[] result = new int[2];
        for (int i = 0; i < newNums.length; i++) {
            Integer index = binarySearch(newNums, i + 1, nums.length - 1, target);
            if (index != null) {
                result[0] = newNums[i];
                result[1] = newNums[index];
            }
            break;
        }
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (count == 2)
                break;
            if (nums[i] == result[0]) {
                returnNums[0] = i;
                count++;
            }
            if (nums[i] == result[1]) {
                returnNums[1] = i;
                count++;
            }
        }
        return returnNums;
    }

    private Integer binarySearch(int[] nums, int left, int right, int target) {
        if (right < left) {
            return null;
        }
        while (left <= right) {
            int middle = (right - left) / 2 + left;
            if (nums[middle] == target) {
                return middle;
            } else if (nums[middle] < target) {
                left = middle + 1;
            } else {
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
        while (indexRight < s.length()) {
            String cur = s.substring(indexRight, indexRight + 1);
            if (hashMap.containsKey(cur)) {
                int temp = hashMap.get(cur);
                for (int i = indexLeft; i <= temp; i++) {
                    hashMap.remove(s.substring(i, i + 1));
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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode prv = new ListNode(0);
        prv.next = head;

        ListNode left = prv;
        ListNode cur = head;
        int count = 1;
        while (cur != null) {
            if (count == n + 1) {
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
    private ListNode reverse(ListNode head) {
        ListNode pre = null, next = null;

        while (head != null) {
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

    private void generate(String cur, int leftNumbers, int rightNumbers) {
        if (leftNumbers == 0 && rightNumbers == 0) {
            result.add(cur + "");
        }

        if (leftNumbers > 0) {
            generate(cur + "(", leftNumbers - 1, rightNumbers);
        }
        // 剩下的括号中，右括号一定要比左括号多
        if (rightNumbers > 0 && rightNumbers - 1 >= leftNumbers) {
            generate(cur + ")", leftNumbers, rightNumbers - 1);
        }

    }

    // 25
    public ListNode reverseKGroup(ListNode head, int k) {
        // 反转前k个作为整个链表的头
        ListNode cur = head;
        int count = 1;
        while (count < k) {
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
        while (cur != null) {
            if (count == k) {
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
        while (index >= 0) {
            if (nums[index] < nums[index + 1]) {
                break;
            }
            index--;
        }
        if (index < 0) {
            reverse(nums, 0, nums.length - 1);
            return;
        }

        for (int i = nums.length - 1; i > index; i--) {
            if (nums[i] > nums[index]) {
                swap(nums, index, i);
                reverse(nums, index + 1, nums.length - 1);
                break;
            }
        }
    }

    private void swap(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    // 33
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0, right = nums.length - 1;
        int middle = left + (right - left) / 2;
        while (left <= right) {
            if (nums[middle] == target) {
                return middle;
            }
            if (nums[left] <= nums[middle]) {
                if (nums[left] <= target && target <= nums[middle]) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            } else {
                if (nums[middle] <= target && target <= nums[right]) {
                    left = middle + 1;
                } else {
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
        while (left <= right) {
            if (nums[middle] == target) {
                if (middle == 0 || nums[middle - 1] < nums[middle]) {
                    result[0] = middle;
                    break;
                } else {
                    right = middle - 1;
                }
            } else if (nums[middle] > target) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
            middle = left + (right - left) / 2;
        }

        left = 0;
        right = nums.length - 1;
        middle = left + (right - left) / 2;
        while (left <= right) {
            if (nums[middle] == target) {
                if (middle == nums.length - 1 || nums[middle + 1] > nums[middle]) {
                    result[1] = middle;
                    break;
                } else {
                    left = middle + 1;
                }
            } else if (target < nums[middle]) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
            middle = left + (right - left) / 2;
        }
        return result;
    }

    // 42
    public int trap(int[] height) {
        if (height == null || height.length <= 2) {
            return 0;
        }
        int[] sums = getSums(height);
        int[] biggers = getFirstBigger(height);
        int maxWater = 0;

        for (int i = 0; i < height.length; ) {
            int rightRange = biggers[i];
            if (rightRange != -1)
                maxWater += (rightRange - i - 1) * height[i] - (sums[rightRange] - sums[i] - height[rightRange]);
            i = rightRange;
        }

        biggers = getFirstBiggerStartInRight(height);
        for (int i = height.length - 1; i >= 0; ) {
            int leftRange = biggers[i];
            if (leftRange != -1)
                maxWater += (i - leftRange - 1) * height[leftRange] - (sums[leftRange] - sums[i] - height[leftRange]);
            i = leftRange;
        }
        return maxWater;
    }

    private int[] getSums(int[] nums) {
        int[] result = new int[nums.length];
        result[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            result[i] = result[i - 1] + nums[i];
        }
        return result;
    }

    private int[] getFirstBigger(int[] nums) {
        LinkedList<Integer> list = new LinkedList<>();
        int[] result = new int[nums.length];

        for (int i = nums.length - 1; i >= 0; i--) {
            while (!list.isEmpty() && nums[i] > list.peekFirst()) {
                list.pollFirst();
            }
            if (list.isEmpty()) {
                list.addFirst(i);
                result[i] = -1;
            } else if (nums[i] <= nums[list.peekFirst()]) {
                result[i] = list.peekFirst();
                list.addFirst(i);
            }
        }
        return result;
    }

    private int[] getFirstBiggerStartInRight(int[] nums) {
        LinkedList<Integer> list = new LinkedList<>();
        int[] result = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            while (!list.isEmpty() && nums[i] > list.peekLast()) {
                list.pollLast();
            }
            if (list.isEmpty()) {
                list.addLast(i);
                result[i] = -1;
            } else if (nums[i] <= nums[list.peekLast()]) {
                result[i] = list.peekLast();
                list.addLast(i);
            }
        }
        return result;
    }
    // 之前的想法是对当前的墙找到其左右各自第一个高于当前墙的两堵墙，然后求之间的水，这样需要求出每个值右边和左边第一个大于其值得墙
    // 这样是不对的
    // 暴力的方式：遍历每一个墙，对于当前的墙，找到其左边和右边高于当前墙的最高的墙，然后能够得到当前墙上面能存多少水，这样累加起来就是结果

    // 单调栈：只需要使用一个栈从左到右遍历即可，栈操作规则如下，栈中存储的是墙在数组中的索引位置
    // if 栈为空或当前墙小于等于栈顶墙，入栈
    // else 当前墙高度大于栈顶墙，则将栈顶墙出栈，并计算一部分水量，并重复判断当前墙和栈顶的关系，直到当前墙入栈
    // 怎么能把代码写的简单易读，优雅？？？主要是思路是否清晰，或者算法是否简单清晰
    public int trap1(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int sum = 0;
        for (int i = 0; i < height.length; i++) {
            while (!stack.empty() && height[i] > height[stack.peek()]) {
                int cur = stack.pop();
                sum += (i - stack.peek() - 1) * (Math.min(height[i], height[stack.peek()]) - height[cur]);
            }
            stack.push(i);
        }
        return sum;
    }


    //44
    public boolean isMatch(String s, String p) {
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        // init dp
        dp[0][0] = true;
        for (int i = 1; i <= p.length(); i++) {
            if (p.charAt(i) == '*')
                dp[0][i] = true;
            else {
                break;
            }
        }

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= p.length(); j++) {
                if (p.charAt(j) == '*') {
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else if (p.charAt(j) == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = s.charAt(i) == p.charAt(j) ? dp[i - 1][j - 1] : false;
                }
            }
        }
        return dp[s.length()][p.length()];
    }

    // 46
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        getAll(nums, new boolean[nums.length], new ArrayList<>(), 0, result);
        return result;
    }

    private void getAll(int[] nums, boolean[] used, List<Integer> curList, int curCount, List<List<Integer>> result) {
        if (curCount == nums.length) {
            result.add(curList);
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                used[i] = true;
                curList.add(nums[i]);
                getAll(nums, used, curList, curCount + 1, result);

                curList.remove(curCount - 1);
                used[i] = false;
            }
        }
    }

    // 51
    public List<List<String>> solveNQueens(int n) {
        // 遍历每一行
        // 对于当前行遍历每一列
        // 对于当前位置，判断能够放下一个皇后，如果能尝试放上，递归，回溯拿掉
        // 如何快速判断能否放下一个皇后，由于是从上到下，从左到右进行遍历，而且遍历本身可以保证一行只放一个，那么只需要判断当前位置的上面、左上、右上是否有皇后即可
        return null;
    }

    private boolean canPut(List<String> cur, int row, int col) {
        for (int r = 0; r < row; r++) {
            if (cur.get(r).charAt(col) == 'Q')
                return false;
        }
        for (int r = row - 1, c = col - 1; r >= 0 && c >= 0; r--, c--) {
            if (cur.get(r).charAt(c) == 'Q')
                return false;
        }
        for (int r = row - 1, c = col + 1; r >= 0 && c < cur.get(0).length(); r--, c++) {
            if (cur.get(r).charAt(c) == 'Q')
                return false;
        }
        return true;
    }

    // 56
    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0 || intervals.length == 1)
            return intervals;
        // sort
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        // merge
        List<int[]> res = new ArrayList<>();
        res.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            if (res.get(res.size() - 1)[1] >= intervals[i][0]) {
                int[] pre = res.get(res.size() - 1);
                pre[1] = Math.max(pre[1], intervals[i][1]);
            } else {
                res.add(intervals[i]);
            }
        }
        return res.toArray(new int[res.size()][2]);
    }

    // 78
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        getAll(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private void getAll(int[] nums, int count, List<Integer> curSet, List<List<Integer>> result) {
        if (count == nums.length) {
            result.add(curSet);
        }

        getAll(nums, count + 1, curSet, result);
        curSet.add(nums[count]);
        getAll(nums, count + 1, curSet, result);
        curSet.remove(curSet.size() - 1);
    }

    // 79
    public boolean exist(char[][] board, String word) {
        return true;
    }

    private boolean get(char[][] board, String word, int indexWord, int row, int col, boolean[][] used) {
        if (indexWord > word.length())
            return true;
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length)
            return false;

        if (board[row][col] == word.charAt(indexWord)) {
            used[row][col] = true;
            // 或运算有剪枝的作用
            if (get(board, word, indexWord + 1, row - 1, col, used) ||
                    get(board, word, indexWord + 1, row, col - 1, used) ||
                    get(board, word, indexWord + 1, row + 1, col, used) ||
                    get(board, word, indexWord + 1, row, col + 1, used))
                return true;

            used[row][col] = false;
        }
        return false;
    }

    // 103
    // 二叉树的层次遍历，通过队列实现
    // 这个题目的特殊之处在于需要交替遍历队列，从右到左和从左到右交替
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        boolean leftToRight = true;
        queue.addLast(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> curLevel = new LinkedList<>();
            if (leftToRight) {
                for (int i = 0; i < size; i++) {
                    TreeNode curNode = queue.pollFirst();
                    curLevel.add(curNode.val);
                    if (curNode.left != null) queue.addLast(curNode.left);
                    if (curNode.right != null) queue.addLast(curNode.right);
                }
                result.add(curLevel);
                leftToRight = false;
            } else {
                for (int i = 0; i < size; i++) {
                    TreeNode curNode = queue.pollLast();
                    curLevel.add(curNode.val);
                    if (curNode.right != null) queue.addFirst(curNode.right);
                    if (curNode.left != null) queue.addFirst(curNode.left);
                }
                result.add(curLevel);
                leftToRight = true;
            }
        }
        return result;
    }

    // 105 通过前序遍历和中序遍历结果构造二叉树
    // 通过递归构造
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return build(preorder, inorder, 0, 0, inorder.length - 1);
    }

    private TreeNode build(int[] preOrder, int[] inOrder, int index, int left, int right) {
        if (left > right)
            return null;

        TreeNode cur = new TreeNode(preOrder[index]);
        int tar = 0;
        for (int i = left; i <= right; i++) {
            if (preOrder[index] == inOrder[i]) {
                tar = i;
                break;
            }
        }
        cur.left = build(preOrder, inOrder, index + 1, left, tar - 1);
        cur.right = build(preOrder, inOrder, index + tar - left + 1, tar + 1, right);
        return cur;
    }
    // 121

    // 124
    // 这个题目的意思真不清楚！
    // 路径和是指从一个起点出发，可以向左或者向右得到路径的总和，但是要求同一个起点可以重复出发去找路径
    // 但是路径中的点不能重复使用
    public int maxPathSum(TreeNode root) {
        max(root);
        return maxPathSum;
    }

    private int maxPathSum = Integer.MIN_VALUE;
    private HashMap<TreeNode, Integer> hashMap = new HashMap<>();

    private int max(TreeNode root) { // 找到以root为根节点的单边最大路径和，不可以两边都加上，那么root节点就重复使用了，因为起点可以重复使用，路径中的其它节点不能重复使用
        if (root == null)
            return 0;

        if (!hashMap.containsKey(root.left)) {
            hashMap.put(root.left, max(root.left));
        }
        if (!hashMap.containsKey(root.right)) {
            hashMap.put(root.right, max(root.right));
        }
        int left = root.val + hashMap.get(root.left);
        int right = root.val + hashMap.get(root.right);

        maxPathSum = Math.max(maxPathSum, Math.max(left, right));
        maxPathSum = Math.max(maxPathSum, left + right - root.val);
        maxPathSum = Math.max(maxPathSum, root.val);

        return Math.max(root.val, Math.max(left, right));
    }

    // 139
//    public boolean wordBreak(String s, List<String> wordDict) {
//        HashSet<String> set = new HashSet<>();
//        for (String word : wordDict) {
//            set.add(word);
//        }
//        dp = new int[s.length()];
//        return canSplit(s,0,set);
//    }
//    private int[] dp; // dp[i] s[i, s.length - 1]能够拆分为字典中的词 -1未计算 0不能拆 1可以拆
//    // s[index, s.length - 1] 能否拆分为字典中的词
//    private boolean canSplit(String s, int index, HashSet<String> set) {
//        if (index == s.length())
//            return true;
//
//        for (int i = index; i < s.length(); i++) {
//            String curStr = s.substring(index, i + 1);
//            if (set.contains(curStr)){
//                boolean temp = false;
//                if (dp[i] == -1 && (temp = canSplit(s, i + 1, set))){
//                    dp[i] = temp ? 1 : 0;
//                    if (dp[i] == 1)
//                        return true;
//                }
//            }
//        }
//        return false;
//    }
    // 动态规划可以认为是记忆化搜索的循环写法，记忆化搜索是递归写法
    // 其本质是回溯法中有重叠子问题，通过记录解的方式避免后续重复计算
    // 而只要是回溯法就可以分析问题中是否有剪枝策略，因此动态规划也可以剪枝
    // 比如已经找到问题的答案，直接返回，后续计算停止；此题中当前substring的子串长度大于字典中字符串最大长度后，内循环即可停止
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        HashSet<String> set = new HashSet<>();
        int maxLength = 0;
        for (String word : wordDict) {
            set.add(word);
            maxLength = Math.max(maxLength, word.length());
        }
        for (int i = 1; i <= s.length(); i++) {
            // dp[i]
            for (int j = 0; j < i; j++) {
                if (i - j > maxLength)
                    break;
                if (dp[j] && set.contains(s.substring(j, i))){
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }


    // 143
    public void reorderList(ListNode head) {
        ListNode p = head, q = head;
        while (q != null && q.next != null){
            q = q.next;
            p = p.next;
        }
        ListNode head2 = reversePart(p);
        ListNode prv = new ListNode(0);
        ListNode newHead = prv;
        while (head2 != null && head != null){
            prv.next = head;
            prv = prv.next;
            prv.next = head2;
            prv = prv.next;

            head = head.next;
            head2 = head2.next;
        }
        if (head2 != null){
            prv.next = head2;
            prv = prv.next;
        }
        prv.next = null;
        newHead.next = null;
    }
    private ListNode reversePart(ListNode head) {
        ListNode pre = null, next = null;
        while (head != null){
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }
}
