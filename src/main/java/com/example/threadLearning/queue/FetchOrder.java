package com.example.threadLearning.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**取出过期订单的线程**/
public class FetchOrder implements Runnable{

    private DelayQueue<ItemVO<Order>> delayQueue;

    public FetchOrder(DelayQueue<ItemVO<Order>> delayQueue) {
        this.delayQueue = delayQueue;
    }

    @Override
    public void run() {
        while(true){
            try {
                ItemVO<Order> item = delayQueue.take(); //一直阻塞着
                Order order = item.getData();
                System.out.println("消费者["+Thread.currentThread().getId()+"] ：订单" + order.getOrderNo()
                        + " 已到期");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
