package netease;

import java.util.*;

public class Main {
    // 2
    //    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int n = in.nextInt();
//        int m = in.nextInt();
//        int[] T = new int[m];
//        for (int i = 0; i < m; i++){
//            T[i] = in.nextInt();
//        }
//        if (n == m){
//            print(T);
//            return;
//        }
//
//        int count = n - m;
//        Set<Integer> set = new HashSet<>();
//        for (int t : T){
//            set.add(t);
//        }
//        int[] addNums = new int[count];
//        int index = 0;
//        for (int i = 1; i <= n && index < count; i++){
//            if (!set.contains(i)){
//                addNums[index++] = i;
//            }
//        }
//
//        int[] result = new int[n];
//        int indexResult = 0;
//        int indexT = 0;
//        int indexAddNums = 0;
//        while (indexT < m && indexAddNums < (n - m)){
//            if (T[indexT] < addNums[indexAddNums]){
//                result[indexResult++] = T[indexT++];
//            }else {
//                result[indexResult++] = addNums[indexAddNums++];
//            }
//        }
//        while (indexT < m){
//            result[indexResult++] = T[indexT++];
//        }
//        while (indexAddNums < (n - m)){
//            result[indexResult++] = addNums[indexAddNums++];
//        }
//        print(result);
//    }
//    private static void print(int[] nums){
//        for (int i = 0; i < nums.length; i++){
//            System.out.print(nums[i]);
//            if (i < nums.length - 1)    System.out.print(" ");
//        }
//    }
    // 3
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        while (T > 0){
            int n = in.nextInt();
            int[] nums = new int[n];
            for (int i = 0; i < nums.length; i++){
                nums[i] = in.nextInt();
            }
            int min = 0;
            for (int num : nums){
                min += num;
            }
            for (int i = 0; i < nums.length; i++){
                int counts = 0;
                int[] newNum = Arrays.copyOf(nums, n);
                for (int j = i; j < nums.length; j++){
                    newNum[j] = 0;
                    counts += nums[j];
                    boolean flag = canPartition(newNum);
                    if (flag){
                        min = Math.min(counts, min);
                    }
                }
            }
            System.out.println(min);
            T--;
        }
    }
    public static boolean canPartition(int[] nums){
        if (nums == null){
            return false;
        }
        int len = nums.length;
        if (len == 0){
            return false;
        }

        int sum = 0;
        for (int num : nums){
            sum += num;
        }
        if ((sum & 1) == 1){
            return false;
        }

        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        if (nums[0] <= target){
            dp[nums[0]] = true;
        }
        for (int i = 1; i < len; i++){
            for (int j = target; nums[i] <= j; j--){
                if (dp[target]){
                    return true;
                }
                dp[j] = dp[j] || dp[j - nums[i]];
            }
        }
        return dp[target];
    }


}
