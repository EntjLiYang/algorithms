package others.thread;

import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author liyang
 * @Date 2020/7/7 10:09 上午
 * @Description 实现一个线程池
 **/
public class MyThreadPool {
    public MyThreadPool(int maxPoolSize, int corePoolSize, LinkedBlockingQueue<Runnable> taskQueue){
        this.maxPoolSize = maxPoolSize;
        this.corePoolSize = corePoolSize;
        this.taskQueue = taskQueue;
        this.workers = new HashSet<>();
    }

    private int maxPoolSize;
    private int corePoolSize;
    private LinkedBlockingQueue<Runnable> taskQueue;

    private HashSet<Worker> workers;
    private int curThreadNumbers;

    public boolean execute(Runnable task){
        if (curThreadNumbers < corePoolSize){
            addWorker(task);
            curThreadNumbers++;
            return true;
        }else if (taskQueue.offer(task)){
            return true;
        }
        else if (curThreadNumbers < maxPoolSize){
            addWorker(task);
            curThreadNumbers++;
            return true;
        }

        return false;
    }

    private void addWorker(Runnable task){
        Worker worker = new Worker(task);
        Thread thread = worker.thread;
        thread.start();

        workers.add(worker);
    }

    private final class Worker implements Runnable {

        public Thread thread;

        public Runnable firstTask;

        public Worker(Runnable task){
            this.firstTask = task;
            thread = Executors.defaultThreadFactory().newThread(this);
        }

        @Override
        public void run() {
            while (true){
                if (firstTask != null || (firstTask = getTask()) != null){
                    firstTask.run();
                }
            }
        }

        private Runnable getTask(){
            // 从taskQueue中取任务
            Runnable task = null;
            try {
                task = taskQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return task;
        }
    }
}
