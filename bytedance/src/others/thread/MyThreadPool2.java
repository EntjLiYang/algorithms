package others.thread;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadPool2 {
    private LinkedList<Runnable> jobs = new LinkedList<>();
    private List<Worker> workers = Collections.synchronizedList(new LinkedList<Worker>());
    private AtomicInteger workerNums = new AtomicInteger();
    private int DEFAULT_WORKERS = 10;
    private int MAX_WORKERS = 20;

    public MyThreadPool2() {
        addWorkers(DEFAULT_WORKERS);
    }


    public void execute(Runnable job) {
        synchronized (jobs) {
            jobs.add(job);
            jobs.notify();
        }
    }

    private void addWorkers(int num) {
        synchronized (jobs) {
            if (num + workerNums.get() > MAX_WORKERS)
                return;
            for (int i = 0; i < num; i++) {
                Worker worker = new Worker();
                workers.add(worker);
                Thread thread = new Thread(worker, "Thread-pool-" + workerNums.incrementAndGet());
                thread.start();
            }
            workerNums.getAndSet(workerNums.get()+num);
        }
    }

    public void shutDown() {
        synchronized (workers) {
            for (Worker worker : workers) {
                worker.shutDown();
            }
        }
    }

    private class Worker implements Runnable {
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Runnable job = null;
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    job = jobs.poll();
                }
                job.run();

            }
        }

        public void shutDown() {
            running = false;
        }
    }
}
