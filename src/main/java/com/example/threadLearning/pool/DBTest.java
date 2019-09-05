package com.example.threadLearning.pool;

import javafx.concurrent.Worker;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DBTest {
    static DBPool dbPool = new DBPool(10);
    //栅栏，控制主线程等所有工作线程都跑完再继续
    static CountDownLatch end;

    //子线程
    static class Worker implements Runnable{
        int count;
        AtomicInteger got;
        AtomicInteger ngot;

        public Worker(int count, AtomicInteger got, AtomicInteger ngot) {
            this.count = count;
            this.got = got;
            this.ngot = ngot;
        }

        @Override
        public void run() {
            while(count > 0){
                try{
                    Connection connection = dbPool.fetchConnection(1000);
                    //拿到连接
                    if(connection!=null){
                        try{
                            connection.createStatement();
                            connection.commit();
                        }finally {
                            dbPool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    }else{ //超时
                        ngot.incrementAndGet();
                        System.out.println(Thread.currentThread().getName() + "等待超时");
                    }
                }catch (Exception ex){
                }finally {
                    count--;
                }
            }
            //20次全跑完后
            end.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 50; //50个线程
        end = new CountDownLatch(threadCount);
        int count = 20; //每个线程执行20次
        AtomicInteger got = new AtomicInteger(); //计数可以拿到连接的线程，引用传递
        AtomicInteger ngot = new AtomicInteger(); //计数没拿到连接的线程
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new Worker(count, got ,ngot), "worker_" + i);
            thread.start();
        }
        //主线程等待
        end.await();
        System.out.println("总共尝试了" + (threadCount*count) + "次");
        System.out.println("拿到连接的次数：" + got);
        System.out.println("没拿到连接的次数：" + ngot);
    }
}
