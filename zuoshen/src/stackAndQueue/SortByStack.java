package zuoShen.stackAndQueue;

import java.util.Scanner;
import java.util.Stack;

public class SortByStack {
    /**
     * 将栈中元素排序，只能使用额外的一个栈，可以申请变量但是不能使用额外的数据结构
     * @param numbers
     */
    public static void sortByOneStack(Stack<Integer> numbers){
        if (numbers == null || numbers.size() <= 1){
            return;
        }

        Stack<Integer> assist = new Stack<>();
        while (!numbers.empty()){
            assist.push(numbers.pop());
        }
        while (!assist.empty()){
            int curValue = assist.pop();
            int curCount = 0;
            while (!numbers.empty() && numbers.peek() > curValue){
                assist.push(numbers.pop());
                curCount++;
            }
            numbers.push(curValue);
            while (curCount > 0){
                numbers.push(assist.pop());
                curCount--;
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++){
            stack.push(in.nextInt());
        }
        sortByOneStack(stack);

    }
}
