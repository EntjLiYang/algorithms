package zuoShen.stackAndQueue;

import java.util.LinkedList;

public class GetMaxSubMatrix {
    public int getMax(int[][] matrix){
        int[] preHeight = null;
        int[] curHeight = null;
        int result = 0;
        for (int row = 0; row < matrix.length; row++){
            curHeight = getHeights(preHeight, matrix[row]);
            result = Math.max(result, getMaxSubMatrix(curHeight));
        }
        return result;
    }

    private int getMaxSubMatrix(int[] height){
        int result = 0;
        int[][] firstSmallerNum = getFirstSmallerNum(height);
        for (int i = 0; i < height.length; i++){
            int leftSmaller = firstSmallerNum[0][i];
            int rightSmaller = firstSmallerNum[1][i];
            if (leftSmaller == -1)
                leftSmaller = i;
            if (rightSmaller == -1)
                rightSmaller = i;
            result = Math.max(result, Math.max(height[i],(rightSmaller - leftSmaller + 1) * height[i]));
        }
        return result;
    }

    private int[][] getFirstSmallerNum(int[] height){
        int[][] result = new int[2][height.length];

        LinkedList<Integer> linkedList = new LinkedList<>();

        for (int i =0; i < height.length; i++){
            while (!linkedList.isEmpty() && height[linkedList.peekLast()] >= height[i]){
                linkedList.pollLast();
            }
            if (!linkedList.isEmpty()){
                result[0][i] = linkedList.peekLast();
            }else result[0][i] = -1;

            linkedList.addLast(i);
        }
        for (int i = height.length -1; i >= 0; i--){
            while (!linkedList.isEmpty() && height[linkedList.peekLast()] >= height[i]){
                linkedList.pollLast();
            }
            if (!linkedList.isEmpty()){
                result[1][i] = linkedList.peekLast();
            }else result[1][i] = -1;

            linkedList.addLast(i);
        }
        return result;
    }

    private int[] getHeights(int[] preHeight, int[] curRow){
        if (preHeight == null)
            return curRow;

        int[] curHeight = new int[curRow.length];
        for (int i = 0; i < preHeight.length; i++){
            curHeight[i] = preHeight[i] + curRow[i];
        }
        return curHeight;
    }
}
