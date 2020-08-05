package others.thread;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadPool3 {
    private LinkedList<Runnable> jobs = new LinkedList<>();
    private LinkedList<Runnable> workers = new LinkedList<>();
    private ReentrantLock jobLock = new ReentrantLock();
    private Condition jobCondition = jobLock.newCondition();
    private ReentrantLock workerLock = new ReentrantLock();
    private int DEFAULT_WORKERS = 10;
    private int MAX_WORKERS = 20;
    private AtomicInteger workerNum = new AtomicInteger();

    public static void main(String[] args) {
        MyThreadPool3 myThreadPool3 = new MyThreadPool3();
        myThreadPool3.addWorkers(20);
    }
    public MyThreadPool3(){
        addWorkers(DEFAULT_WORKERS);
    }
    public void addWorkers(int num){
        try {
            workerLock.lock();
            if (workerNum.get() + num > MAX_WORKERS)
                return;
            for (int i = 0; i < num; i++){
                Worker worker = new Worker();
                workers.addLast(worker);
                Thread thread = new Thread(worker, "Thread-Pool-" + workerNum.incrementAndGet());
                thread.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            workerLock.unlock();
        }
    }

    public void execute(Runnable job) {
        try {
            jobLock.lock();
            jobs.addLast(job);
            jobCondition.notify();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jobLock.unlock();
        }
    }

    private class Worker implements Runnable {
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Runnable job = null;
                try {
                    jobLock.lock();
                    if (jobs.isEmpty()) {
                        jobCondition.wait();
                    }
                    job = jobs.pollFirst();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    jobLock.unlock();
                }
                job.run();
            }
        }

        public void shutDown() {
            running = false;
        }
    }
}
