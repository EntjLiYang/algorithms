package netease;

import java.util.Scanner;

public class PartitionEqualSubset {
    // 给定一个正整数数组，判断其是否可以分为和相等的两部分
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] nums = new int[n];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            nums[i] = in.nextInt();
            sum += nums[i];
        }
        if ((sum & 1) == 1) {
            System.out.println("false");
            return;
        }

        System.out.println(canPartition(nums, sum / 2));
    }

    private static boolean canPartition(int[] nums, int c) {
        boolean[][] memo = new boolean[nums.length][c + 1];
        if (nums[0] <= c) {
            memo[0][nums[0]] = true;
        }

        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j <= c; j++) {
                // memo[i][j]
                memo[i][j] = memo[i - 1][j];
                if (j >= nums[i]) {
                    memo[i][j] = memo[i][j] || memo[i - 1][j - nums[i]];
                }
            }
        }
        return memo[nums.length - 1][c];
    }
}
