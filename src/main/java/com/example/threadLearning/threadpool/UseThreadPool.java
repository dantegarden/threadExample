package com.example.threadLearning.threadpool;

import com.example.threadLearning.SleepTools;

import java.util.Random;
import java.util.concurrent.*;

public class UseThreadPool {
    static class RunnableWorker implements Runnable{
        static Random r = new Random();
        private String taskName;

        public RunnableWorker(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }

        @Override
        public void run() {
            System.out.println("RunnableWorker[" + Thread.currentThread().getId()+"]: run task " + taskName);
            SleepTools.sleep(r.nextInt(10));
            System.out.println("RunnableWorker[" + Thread.currentThread().getId()+"]: end task " + taskName);
        }
    }

    static class CallableWorker implements Callable<String> {
        static Random r = new Random();
        private String taskName;

        public CallableWorker(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }

        @Override
        public String call() throws Exception {
            System.out.println("CallableWorker[" + Thread.currentThread().getId()+"]: run task " + taskName);
            SleepTools.sleep(r.nextInt(10));
            System.out.println("CallableWorker[" + Thread.currentThread().getId()+"]: end task " + taskName);
            return taskName;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = new ThreadPoolExecutor(5, 10, 3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        //ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 50; i++) {
            RunnableWorker worker = new RunnableWorker("rWorker_" + i);
            pool.execute(worker);
        }
        for (int i = 1; i <= 50; i++) {
            CallableWorker worker = new CallableWorker("cWorker_" + i);
            Future<String> future = pool.submit(worker);
            System.out.println(future.get());
        }
        pool.shutdown();
    }
}
