package com.example.threadLearning.tools;

import com.example.threadLearning.SleepTools;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

public class UseCyclicBarrier {

    static Map<String, Integer> resultMap = new ConcurrentHashMap<>();

    static CyclicBarrier barrier = new CyclicBarrier(5, new Runnable(){
        @Override
        public void run() {
            //当屏障开放时执行
            for (Map.Entry<String,Integer> entry:
            resultMap.entrySet()) {
                System.out.println(entry.getKey() +" = " + entry.getValue());
            }
        }
    });

    private static class BizThread implements Runnable{
        @Override
        public void run() {
            System.out.println("BizThread : "+ Thread.currentThread().getName() + " is running ");
            Integer random = new Random().nextInt(10);
            resultMap.put(Thread.currentThread().getName(), random);
            SleepTools.sleep(random);
            System.out.println("BizThread : "+ Thread.currentThread().getName() + " arrived");

            try {
                //等待所有线程都到达屏障
                barrier.await();
                System.out.println("BizThread : "+ Thread.currentThread().getName() + " do other things");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new BizThread()).start();
        }
    }
}
