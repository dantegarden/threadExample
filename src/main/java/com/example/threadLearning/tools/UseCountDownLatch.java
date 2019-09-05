package com.example.threadLearning.tools;

import com.example.threadLearning.SleepTools;

import java.util.concurrent.CountDownLatch;
/**5个初始化线程 6个扣除点
 * 扣除完毕时，主线程和业务线程才能继续自己的工作**/
public class UseCountDownLatch {
    static CountDownLatch latch = new CountDownLatch(6);

    //初始化线程
    private static class InitThread implements Runnable{
        @Override
        public void run() {
            System.out.println("InitThread: " + Thread.currentThread().getName() + " do init work");
            latch.countDown();
            System.out.println("InitThread: " + Thread.currentThread().getName() + " do it's other work");
        }
    }

    //业务线程
    private static class BizThread implements Runnable{
        @Override
        public void run() {
            System.out.println("BizThread: " + Thread.currentThread().getName() + " wait init works ");
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("BizThread: " + Thread.currentThread().getName() + " do business work ");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //单独的初始化线程，里面有两个步骤，每完成一步扣减一次
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("SpecInitThread: " + Thread.currentThread().getName() + " init work step 1");
                SleepTools.sleepMs(1);
                latch.countDown();
                System.out.println("SpecInitThread: " + Thread.currentThread().getName() + " init work step 2");
                SleepTools.sleepMs(1);
                latch.countDown();
                System.out.println("SpecInitThread: " + Thread.currentThread().getName() + " do other work");
            }
        }).start();
        new Thread(new BizThread()).start();
        for (int i = 0; i < 4; i++) {
            new Thread(new InitThread()).start();
        }
        //阻塞住，等扣减点减完时放行
        latch.await();
        System.out.println("Main do business work");
    }
}
