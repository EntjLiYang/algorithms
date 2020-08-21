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
                if (dp[j] && set.contains(s.substring(j, i))) {
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
        while (q != null && q.next != null) {
            q = q.next;
            p = p.next;
        }
        ListNode head2 = reversePart(p);
        ListNode prv = new ListNode(0);
        ListNode newHead = prv;
        while (head2 != null && head != null) {
            prv.next = head;
            prv = prv.next;
            prv.next = head2;
            prv = prv.next;

            head = head.next;
            head2 = head2.next;
        }
        if (head2 != null) {
            prv.next = head2;
            prv = prv.next;
        }
        prv.next = null;
        newHead.next = null;
    }

    private ListNode reversePart(ListNode head) {
        ListNode pre = null, next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }


    // 4 寻找两个数组的中位数
    // 暴力的思路，将两个数组合并成一个数组，然后通过数组长度得到中位数的索引
    // 暴力优化空间复杂度，不需要将两个数组合并成一个更大的数组，只需要双指针遍历两个数组，找到中位数的索引，中位数索引index=(m+n)/2，统计两个指针扫过的数字数量，直到数量=index
    // 题目要求时间复杂度是O(log(m+n))，而且题目给出的两个数组是有序(升序)，因此考虑二分的方式，才能得到目标的时间复杂度
    // 具体怎么做呢？题目要求寻找中位数，而两个数组的总长度是已知的m+n，那么中位数要么是数组的第(m+n)/2 小数字（m+n为奇数时），要么是数组的第(m+n)/2 - 1 小和 (m+n)/2 小两个数字求平均
    // 因此题目就是求两个数组整体第k小的数字，k=(m+n)/2 数组A 数组B 长度分别为m n
    // 首先考虑一般的情况，比较数组A和数组B k/2 - 1索引处的两个数字的大小，如果A[k/2 - 1] < B[k/2 - 1] 则比A[k/2 - 1]小的数最多有k-2个
    // 因此A[k/2 - 1]处的数字不可能是第k小，最多是第k-1小，而且A[0,...,k/2 - 1]的k/2个数字都不可能是第k小的数字，删除数组A[0,...,k/2-1]的所有数字，更新A为A[k/2, ...]
    // 减少了一半的搜索空间，因此能够达到log级别
    // B[k/2 - 1] < A[k/2 - 1]的情况与上面类似
    // B[k/2 - 1] = A[k/2 - 1]的情况可以归为 < 的情况
    // 因为已经删掉了 k / 2个数组，那么k需要更新为k / 2

    // 特殊情况处理，假如有一个数组已经是空数组了，那么第k小的数字便是另外一个非空数组的第k个元素
    // 如果通过k/2 -1数组索引越界，那么选择该数组的最后一个元素进行比较
    // 如果k等于1，则只需要将两个数组当前元素取较小值返回

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int totalLength = m + n;
        int k = totalLength / 2;
        if (totalLength % 2 == 0) {
            // 偶数 k-1 k
            return getKthMinNum(nums1, nums2, k) + getKthMinNum(nums1, nums2, k - 1) * 0.5;
        } else {
            // 奇数 k
            return getKthMinNum(nums1, nums2, k);
        }
    }

    // 得到两个数组整体第k小的数
    private int getKthMinNum(int[] num1, int[] num2, int k) {
        int startNum1 = 0;
        int startNum2 = 0;
        int indexNum1 = 0;
        int indexNum2 = 0;
        int half = 0;
        while (true) {
            if (startNum1 == num1.length) {
                return num2[startNum2 + k - 1];
            }
            if (startNum2 == num2.length) {
                return num1[startNum1 + k - 1];
            }
            if (k == 1) {
                return Math.min(num1[startNum1], num2[startNum2]);
            }

            half = k / 2;
            indexNum1 = Math.min(num1.length, startNum1 + half) - 1;
            indexNum2 = Math.min(num2.length, startNum2 + half) - 1;
            if (num1[indexNum1] <= num2[indexNum2]) {
                startNum1 = indexNum1 + 1;
                k = k - (indexNum1 - startNum1 + 1);
            } else {
                startNum2 = indexNum2 + 1;
                k = k - (indexNum2 - startNum2 + 1);
            }
        }
    }

    // 剑指offer 39 投票法
    // 数组中一定存在众数，数量超过数组大小一半的数是众数
    public int majorityElement(int[] nums) {
        int zhongshu = nums[0];
        int votes = 1;
        for (int i = 1; i < nums.length; i++) {
            if (votes == 0) {
                zhongshu = nums[i];
                continue;
            }
            if (zhongshu == nums[i]) {
                votes++;
            } else {
                votes--;
            }
        }
        return zhongshu;
    }

    // 剑指offer 41
    // 目标是创建一个数据结构，能够快速得到中位数，并且支持向其中插入元素
    // 如果用数组维护一个有序序列，常数获得中位数，但是插入元素是线性级别的

    // 用两个堆来构造，假如该升序数组分为小的一半，和大的一半，那么用一个大顶堆保存小的一半，用一个小顶堆保存大的一半，并分别记录大顶堆数量m 小顶堆数量n
    // 维护的目标时，数据总量是偶数，则m==n，数据总量为奇数，m+1=n
    // 因此插入时，当前m==n，则将该数add到大顶堆，然后弹出大顶堆顶元素并插入小顶堆
    // m + 1 == n，则将该数add到小顶堆，然后弹出小顶堆元素并插入大顶堆

    // 获取中位数时，判断当前m==n与否，相等则各取两个堆的堆顶并求平均；不想等则取小顶堆堆顶元素

<<<<<<< HEAD
    // 剑指offer 20
    // 数字有效字符是 0-9 e . +-
    // 并且存在一些规则 e只能有一个e的前面只能是数字
    // . 只能有一个，且其前置所有字符中不能有e，其前一个字符和后一个字符必须是数字
    // +- 在e的左右各自只能有一个
    public boolean isNumber(String s) {
        boolean preE = false;
        boolean preDot = false;
        boolean preFlag = false;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == 'e') {
                if (i == 0 || preE || s.charAt(i - 1) == '+' || s.charAt(i - 1) == '-' || s.charAt(i - 1) == '.') {
                    return false;
                }
                preE = true;
            } else if (ch == '.') {
                if (i == 0 || preDot || preE || s.charAt(i - 1) == '+' || s.charAt(i - 1) == '-') {
                    return false;
                }
                preDot = true;
            } else if (ch == '+' || ch == '-') {
                if (i >= 1 && s.charAt(i - 1) != 'e')
                    return false;
                preFlag = true;
            } else if (ch >= '0' && ch <= '9') {

            } else {
                return false;
            }
        }
        return true;
    }

    // 剑指offer 67
    public int strToInt(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int start = getFirstValiedIndex(str);
        if (start == str.length()) {
            return 0;
        }

        if (str.charAt(start) != '+' && str.charAt(start) != '-' && str.charAt(start) < '0' && str.charAt(start) > '9') {
            return 0;
        }
        int flag = 0; // 0 无符号 1 正好 -1 负号
        if (str.charAt(start) == '+' || str.charAt(start) == '-') {
            flag = str.charAt(start) == '+' ? 1 : -1;
            start++;
        }

        int end = getValiedNums(str, start);
        int num = getNum(str, start, end);
        return flag == 0 ? num : num * flag;
    }

    private int getNum(String string, int start, int end) {
        int result = 0;
        for (int i = start; i <= end; i++) {
            result = result * 10 + (string.charAt(i) - '0');
        }
        return result;
    }

    private int getValiedNums(String string, int start) {
        while (start < string.length()) {
            if (string.charAt(start) < '0' || string.charAt(start) > '9') {
                break;
            }
        }
        return start;
    }

    private int getFirstValiedIndex(String string) {
        int index = 0;
        while (index < string.length()) {
            if (string.charAt(index) != ' ') {
                break;
            }
        }
        return index;
    }

    // 剑指offer 14 - I
    public int cuttingRope(int n) {
        long[] dp = new long[n + 1];
        dp[2] = 1l;
        for (int i = 3; i <= n; i++) {
            // dp[i]
            long temp = 0l;
            for (int j = 1; j <= i - 2; j++) {
                temp = Math.max(temp, Math.max(j * (i - j), j * dp[i - j]));
            }
            dp[i] = temp;
        }
        return (int) (dp[n] % 1000000007);
    }






=======

    // 剑指offer19
    // 如果不好考虑dp的转移方程，可以先自顶向下考虑递归解法，寻找重叠子问题，得到记忆化搜索解法，动态规范解法也就得到了
    public boolean isMatch2(String s, String p) {
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
        if (p.charAt(0) == '*') {
            dp[0][1] = true;
        }
        if (p.charAt(1) == '*') {
            dp[0][1] = true;
            dp[0][2] = true;
        }

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= p.length(); j++) {
                // pj .
                if (p.charAt(j) == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                // pj *
                else if (p.charAt(j) == '*') {
                    if (p.charAt(j - 1) == '.' || p.charAt(j - 1) == s.charAt(i)) {
                        dp[i][j] = dp[i - 1][j];
                    } else {
                        dp[i][j] = dp[i][j - 1];
                    }
                }
                // pj 普通字符
                else {
                    if (p.charAt(j) == s.charAt(i)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                }
            }
        }
        return dp[s.length()][p.length()];
    }

    // 剑指offer20
    public boolean isNumber(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }

        int pre = 0;// 1数字 2小数点 3正负号 4e

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9'){
                pre = 1;
            }else if (s.charAt(i) == '.'){
                if (pre != 1){
                    return false;
                }
                pre = 2;
            }else if (s.charAt(i) == '+' || s.charAt(i) == '-'){
                if (pre == 1 || pre == 2 || pre == 3){
                    return false;
                }
                pre = 3;
            }else if (s.charAt(i) == 'e'){
                //if (pre)
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String inputStr = in.nextLine();
        String[] inputs = inputStr.split(",");
        String str = inputs[0];
        int n = Integer.valueOf(inputs[1]);
        int lengthOfStr = str.length();

        int rows = n + ((lengthOfStr - 2 * n + 1) / (2 * n - 3) + 1) * (n - 1);
        String[][] map = new String[rows][n];
        for (int i = 0; i < rows; i++){
            Arrays.fill(map[i], "#");
        }

        int flag = 1; // 1 向右 -1 向左
        int distance = n - 1;
        int row = 0, col = 0;
        for (int i = 0; i < lengthOfStr;){
            String curLeft = str.substring(i, i+1);
            String curRight;
            if (i + 1 < lengthOfStr && (col != (n - 1) / 2)){
                curRight = str.substring(i+1, i+2);
                i += 2;
            }else {
                curRight = str.substring(i, i+1);
                i++;
            }

            map[row][col] = curLeft;
            map[row][col+distance] = curRight;
            if (flag == 1){
                if (col == (n - 1) / 2){
                    flag = flag * -1;
                    row++;
                    col--;
                }else {
                    row++;
                    col++;
                }
            }else {
                if (col == 0){
                    flag = flag * -1;
                    row++;
                    col++;
                }else {
                    row++;
                    col--;
                }
            }
            distance = distance - flag * 2;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++){
            for (int j = 0; j < map.length; j++){
                if (!map[j][i].equals("#")){
                    sb.append(map[j][i]);
                }
            }
        }
        System.out.println(sb.toString());
    }
>>>>>>> ca37e038649dbd8a21f914fb6bcc52eb2aad9fcc
}
