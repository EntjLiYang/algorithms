package thread;

import java.util.concurrent.Semaphore;

public class Semaph {
    Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) {
        // 获得了锁
        // 执行await，进入等待队列，释放锁
        // 其它线程single或singleall，当前线程尝试获取锁
        // 获取到了继续执行，没获取到继续再等待队列等待
    }

}
