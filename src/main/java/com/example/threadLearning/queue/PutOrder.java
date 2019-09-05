package com.example.threadLearning.queue;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class PutOrder implements Runnable {

    private DelayQueue<ItemVO<Order>> delayQueue;

    private Order order;

    public PutOrder(DelayQueue<ItemVO<Order>> delayQueue, Order order) {
        this.delayQueue = delayQueue;
        this.order = order;
    }

    @Override
    public void run() {
        int duration = new Random().nextInt(6000);
        ItemVO<Order> item = new ItemVO<>(duration, order);
        delayQueue.offer(item);
        System.out.println("生产者["+Thread.currentThread().getId()+"] ：订单" + order.getOrderNo()
                + " 将在" + TimeUnit.MILLISECONDS.toSeconds(duration) +"s后到期");
    }
}
