### 线程池

关于线程池的介绍有很多，从线程池结构设计的角度，从源码分析的角度。看了这些知识后，检验是否掌握，或者是否真正理解的最好办法就是自己实现一个线程池。

线程池的知识学习资料：
1. 美团技术团队文章  
    https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html
2. 《Java并发编程的艺术》中线程池部分

下面总结几个在自己实现线程池时暴露出的问题，这也是看书时没有深刻理解的点。
1. 都知道线程池中维护了一组线程，这些线程重复执行task。而Thread线程只能启动一次start方法，而且没有重复设置task的set方法，如何重复利用Thread来执行task呢？   

    答：Java中一个Thread实例对应底层的一个线程，因此Thread实例包含了线程的所需的底层资源。Thread只能通过start方法启动，一旦启动便会调用其内部持有的Runnable的run方法，来执行任务。   

    当Runnable的run方法执行结束，Thread的资源虽然没有被释放（当退出这个线程时，JVM会在合适的时间释放相关资源），但是Thread也无法继续执行其它Runnable任务，因为其内部的Runnable是在构造器中初始化的，也没有setter方法。   
    
   ThreadPoolExecutor中实现线程重复执行Runnable任务的方法是，实现了一个Worker类表示执行任务的工作线程。Worker实现该功能的核心逻辑是，本身实现Runnable接口，内部维护Thread实例和Runnable实例，构造时传入初始Runnable任务，并实例化内部的Thread且实例化时传入的参数为this（这就是Worker实现Runnable接口的原因）。启动Worker时获取内部Thread实例并调用start方法，这时Thread执行的run方法是该Worker实例中的，而不是直接执行内部维护的Runnable任务的run方法。Worker中的run方法会进入loop，先执行初始的Runnable任务，然后不断从task队列中取Runnable任务来执行。 
   
   简化版线程池如下：
   
   ```java
   package others;
   
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
           }else if (taskQueue.size() < maxPoolSize && taskQueue.offer(task)){
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

   ```