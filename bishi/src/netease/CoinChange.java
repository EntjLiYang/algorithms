package netease;

import java.util.Arrays;

public class CoinChange {
    public int coinChange(int[] coins, int amount) {
        int n = coins.length;
        int[][] memo = new int[n][amount + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo, -1);
        }
        for (int i = 0; i <= amount; i++) {
            if (i % coins[0] == 0) {
                memo[0][i] = amount / coins[i];
            }
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= amount; j++) {
                // memo[i][j]
                int temp = memo[i - 1][j];
                if (j >= coins[i]){
                    temp = (temp == -1) ? (memo[i][j - coins[i]] == -1 ? -1 : memo[i][j -coins[i]] + 1) : (memo[i][j - coins[i]] == -1 ? temp : Math.min(temp, memo[i][j - coins[i]] + 1));
                }
                memo[i][j] = temp;
            }
        }
        return memo[n - 1][amount];
    }
}
