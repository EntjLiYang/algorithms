package netease;

import java.util.Scanner;

public class Three {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        while (T > 0){
            T--;
            int n = in.nextInt();
            int[] values = new int[n];
            for (int i = 0; i < n; i++){
                values[i] = in.nextInt();
            }
            getMinLoseValue(values, n - 1, 0, 0, 0);
            System.out.println(minValues);
            minValues = Integer.MAX_VALUE;
        }
    }
    private static int minValues = Integer.MAX_VALUE;
    private static void getMinLoseValue(int[] values, int index, int package1, int package2, int rengDiao){
        if (index == -1){
            if (package1 == package2){
                minValues = Math.min(minValues, rengDiao);
            }
            return;
        }

        getMinLoseValue(values, index - 1, package1 + values[index], package2, rengDiao);
        getMinLoseValue(values, index - 1, package1, package2 + values[index], rengDiao);
        getMinLoseValue(values, index - 1, package1, package2, rengDiao + values[index]);
    }
}
