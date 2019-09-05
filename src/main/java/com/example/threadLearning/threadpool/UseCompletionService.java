package com.example.threadLearning.threadpool;

import com.example.threadLearning.SleepTools;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class UseCompletionService {
    private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int TASK_SIZE = Runtime.getRuntime().availableProcessors() * 10;

    private static class MyTask implements Callable<Integer> {
        static Random r = new Random();

        private String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println(this.name + " start to run");
            int sleepTime = r.nextInt(1000);
            SleepTools.sleepMs(sleepTime);
            System.out.println(this.name + " end and return " + sleepTime);
            return sleepTime;
        }
    }

    private void testByCompletion() throws InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();
        AtomicInteger ai = new AtomicInteger(0);
        //创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(CORE_SIZE);
        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<>(pool);
        //提交任务
        for (int i = 0; i < TASK_SIZE; i++) {
            completionService.submit(new MyTask("ExecTask" + i));
        }
        //收集线程池任务的执行结果
        for (int i = 0; i < TASK_SIZE; i++) {
            int slepTime = completionService.take().get();
            System.out.println("slept " + slepTime + " ms ...");
            ai.addAndGet(slepTime);
        }
        pool.shutdown();
        System.out.println("----------------------");
        System.out.println("tasks sleep time " + ai.get() + " ms ");
        System.out.println("spend time " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static void main(String[] args) {
        try {
            new UseCompletionService().testByCompletion();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
