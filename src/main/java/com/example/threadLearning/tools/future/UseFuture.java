package com.example.threadLearning.tools.future;

import com.example.threadLearning.SleepTools;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class UseFuture {
    static class UseCallable implements Callable<Integer> {
        int count = 0;
        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName() + " is running");
            Thread.sleep(2000);
            for (int i = 0; i < 5000; i++) {
                count+=i;
            }
            System.out.println(Thread.currentThread().getName() + " is finished");
            return count;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        UseCallable useCallable = new UseCallable();
        FutureTask<Integer> futureTask = new FutureTask(useCallable);
        new Thread(futureTask).start();
        if(new Random().nextBoolean()){
            System.out.println("Get UseCallable result = " + futureTask.get());
        }else {
            System.out.println("终止计算");
            futureTask.cancel(true);
        }
    }
}
