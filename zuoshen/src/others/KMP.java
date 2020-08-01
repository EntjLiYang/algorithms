package others;

public class KMP {
    /**
     * 判断字符串s是否含有子串等于m，如果有返回m在s中的起始位置
     *
     * @param s
     * @param m
     * @return
     */
    public int getIndexOf(String s, String m) {
        if (s == null || m == null || m.length() < 1 || s.length() < m.length()) {
            return -1;
        }

        char[] sCh = s.toCharArray();
        char[] mCh = m.toCharArray();
        int[] nextArray = getNextArray(mCh);

        int indexOfS = 0;
        int indexOfM = 0;
        while (indexOfS < sCh.length && indexOfM < mCh.length) {
            if (sCh[indexOfS] == mCh[indexOfM]) {
                indexOfS++;
                indexOfM++;
            } else if (nextArray[indexOfM] == -1) { // 说明此时indexOfM为0，只能indexOfS++继续匹配下面的字符
                indexOfS++;
            } else {
                indexOfM = nextArray[indexOfM];
            }
        }
        return indexOfM == mCh.length ? indexOfS - indexOfM : -1;
    }

    /**
     * 计算字符串所有位置上的前置字符串最长前缀后缀匹配字符个数
     *
     * @param chars
     * @return
     */
    private int[] getNextArray(char[] chars) {
        if (chars.length == 1) {
            return new int[]{-1};
        }
        int[] result = new int[chars.length];
        result[0] = -1;
        result[1] = 0;

        int jumper = 0; // 不断向前跳，直到找到一个能够匹配上chars[i - 1]得到一个更长的匹配长度
        int i = 2;
        while (i < chars.length){
            if (chars[i - 1] == chars[jumper]) {
                result[i] = result[i - 1] + 1;
                jumper++;
                i++;
            } else if (jumper > 0) {
                jumper = result[jumper];
            } else {
                result[i] = 0;
                i++;
            }
        }
        return result;
    }


    /**
     * 京东笔试题，给定一个字符串str，返回一个字符串其包含两个作为子串的str
     * 比如 abcabc  return->abcabcabc 只需要最后增加一个abc
     *
     * @param str
     * @return 利用KMP中求前缀后缀最长匹配字符个数，求出str.length()之前字符的最长前缀后缀匹配字符个数，这就是两个str重叠部分的长度
     */
    public String getDoubleSubStr(String str) {
        char[] strCh = new char[str.length() + 1];
        System.arraycopy(str.toCharArray(), 0, strCh, 0, str.length());
        int[] nextArray = getNextArray(strCh);
        return str + str.substring(nextArray[str.length()]);
    }
}
