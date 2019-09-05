package com.example.threadLearning.queue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;

public class TestDelayQueue {
    static DelayQueue<ItemVO<Order>> delayQueue = new DelayQueue<>();

    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("--------"+ i + "s--------");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        for (int i = 0; i < 30; i++) {
            Random random = new Random();
            Order order = new Order("NO" + random.nextInt(10000), random.nextDouble()*1000);
            new Thread(new PutOrder(delayQueue, order)).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(new FetchOrder(delayQueue)).start();
        }

    }
}
