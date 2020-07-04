package zuoShen.stackAndQueue;

public class HanoiTawo {
    /**
     * 将n个盘子从A经过B中转移动到C
     * @param n
     * @param A
     * @param B
     * @param C
     */
    private static int count = 0;
    public static void print(int n, String start, String middle, String end){
        if(n == 1){
            System.out.println("move 1 from " + start + " to " + end);
            count++;
            System.out.println(count + " 's move");
            return;
        }

        print(n-1, start, end, middle);
        System.out.println("move " + n + " from " + start + " to " + end);
        count++;
        System.out.println(count + " 's move");
        print(n-1, middle, start, end);
    }

    public static void main(String[] args) {
        print2(3, "A", "C");
    }


    // 对于只有1个盘子，分情况考虑，只要是需要B中转来移动，必须分两步，先移动到B再移动到目的地
    // 对于n个盘子，需要先将n-1个盘子移动到C，递归调用；然后将第n个盘子移动到B；再递归调用将n-1个盘子从C移动到A；将第n个盘子从B移动到C
    // 递归调用将n-1个盘子从A移动到C
    /**
     * 将n个盘子从start移动到end上
     * @param n
     * @param start
     * @param end
     */
    public static void print2(int n, String start, String end){
        if (n == 1){
            if (start.equals("B") || end.equals("B")){
                System.out.println("move " + n + " from " + start + " to " + end);
            }else {
                System.out.println("move " + n + " from " + start + " to " + "B");
                System.out.println("move " + n + " from " + "B" + " to " + end);
            }
            return;
        }

        if (start.equals("B") || end.equals("B")){
            String another = start == "A" || end == "A" ? "C" : "A";
            print2(n-1, start, another);
            System.out.println("move " + n + " form " + start + " to " + end);
            print2(n-1, another, end);
        }else {
            print2(n-1, start, end);
            System.out.println("move " + n + " from " + start + " to B");
            print2(n-1, end, start);
            System.out.println("move " + n + " from " + "B to " + end);
            print2(n-1, start, end);
        }
    }
}
