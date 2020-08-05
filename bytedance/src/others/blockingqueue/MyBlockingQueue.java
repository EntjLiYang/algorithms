package others.blockingqueue;


import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class MyBlockingQueue<T> {
    private LinkedList<T> queue = new LinkedList<>();
    private Semaphore empty;
    private Semaphore full;
    private Semaphore lock = new Semaphore(1);

    public MyBlockingQueue(int size){
        empty = new Semaphore(size);
        full = new Semaphore(size);
    }

    public void put(T num){
        try {
            empty.acquire();
            lock.acquire();

            queue.add(num);

            full.release();
            lock.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public T get(){
        T result = null;
        try {
            full.acquire();
            lock.acquire();

            result = queue.poll();

            empty.release();
            lock.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
