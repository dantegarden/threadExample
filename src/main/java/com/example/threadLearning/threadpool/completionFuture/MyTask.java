package com.example.threadLearning.threadpool.completionFuture;

import com.example.threadLearning.SleepTools;
import lombok.Data;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * @description:
 * @author: lij
 * @create: 2019-11-04 13:53
 */
@Data
public class MyTask implements Supplier<String> {
    private String name;
    private static Random random = new Random();

    public MyTask(String name) {
        this.name = name;
    }

    public String executeTask() {
        int spend = random.nextInt(5);
        SleepTools.sleep(spend);
        return String.format("任务%s[%s]执行完毕，用时 %d s",this.getName(), Thread.currentThread().getName(), spend);
    }

    @Override
    public String get() {
        return executeTask();
    }

    public static void main(String[] args) {
        CompletableFuture<String> taskA = CompletableFuture.supplyAsync(new MyTask("A"));
        CompletableFuture<String> taskB = CompletableFuture.supplyAsync(new MyTask("B"));
        CompletableFuture<String> taskC = CompletableFuture.supplyAsync(new MyTask("C"));

        long startTime = System.currentTimeMillis();
        //全部运行完成后继续
        CompletableFuture<Void> resultFuturn = CompletableFuture.allOf(taskA,taskB,taskC);
        try {
            resultFuturn.get();
            System.out.println("耗时："+ (System.currentTimeMillis()-startTime) +"毫秒");
            String resultA = taskA.get();
            System.out.println(resultA);
            String resultB = taskB.get();
            System.out.println(resultB);
            String resultC = taskC.get();
            System.out.println(resultC);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("耗时："+ (System.currentTimeMillis()-startTime) +"毫秒");
    }
}
