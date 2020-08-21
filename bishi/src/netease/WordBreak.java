package netease;

import java.util.List;

public class WordBreak {
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] memo = new boolean[s.length() + 1];
        memo[0] = true;

        boolean res = false;
        for (int i = 1; i <= s.length(); i++){
            for (String word : wordDict){
                if (canPartition(s, i - 1, word)){
                    res = res || memo[i - word.length()];
                }
            }
            memo[i] = res;
        }
        return memo[s.length()];
    }
    private boolean func(String s, int end, List<String> wordDict){
        if (end < 0){
            return true;
        }

        boolean result = false;
        for (String word : wordDict){
            if (canPartition(s, end, word)){
                result = result || func(s, end - word.length(), wordDict);
            }
        }
        return result;
    }
    private boolean canPartition(String s, int end, String word){
        if (end + 1 < word.length())
            return false;

        int indexS = end;
        int indexWord = word.length() - 1;
        while (indexS >= 0 && indexWord >= 0){
            if (s.charAt(indexS) != word.charAt(indexWord)){
                return false;
            }
        }
        return true;
    }
}
