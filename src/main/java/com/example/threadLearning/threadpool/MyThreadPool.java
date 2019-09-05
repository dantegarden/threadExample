package com.example.threadLearning.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;

public class MyThreadPool {
    //线程池中默认的线程个数
    private static final int DEFAULT_WORK_NUM = Runtime.getRuntime().availableProcessors() + 1;
    //队列默认任务个数
    private static final int DEFAULT_TASK_COUNT = 100;

    //工作线程数组
    private WorkThread[] workThreads;
    //等待队列
    private final BlockingQueue<Runnable> taskQueue;
    //用户想要启动多少个线程
    private final int workerNum;

    public MyThreadPool() {
        this(DEFAULT_WORK_NUM, DEFAULT_TASK_COUNT);
    }

    public MyThreadPool(int workerNum, int taskCount) {
        if(workerNum <= 0) workerNum = DEFAULT_WORK_NUM;
        if(taskCount <= 0) taskCount = DEFAULT_TASK_COUNT;
        this.workerNum = workerNum;
        taskQueue = new ArrayBlockingQueue<>(taskCount);
        workThreads = new WorkThread[workerNum];
        for (int i = 0; i < workerNum; i++) {
            workThreads[i] = new WorkThread();
            workThreads[i].start();
        }
    }

    /**工作线程*/
    private class WorkThread extends Thread {
        @Override
        public void run() {
            Runnable r = null;
            try {
                while(!isInterrupted()){
                    r = taskQueue.take();
                    if(r != null){
                        System.out.println("WorkThead["+Thread.currentThread().getId()+"] : ready to exec " + r.toString());
                        r.run();
                    }
                    r = null; //help gc
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void stopWorker(){
            interrupt();
        }
    }

    public void execute(Runnable task){
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void destory(){
        for (int i = 0; i < workerNum; i++) {
            workThreads[i].stopWorker();
            workThreads[i] = null; //help gc
        }
        taskQueue.clear();
    }

    @Override
    public String toString() {
        return "MyThreadPool{" +
                "waitTaskNum=" + taskQueue.size() +
                ", workerNum=" + workerNum +
                '}';
    }
}
