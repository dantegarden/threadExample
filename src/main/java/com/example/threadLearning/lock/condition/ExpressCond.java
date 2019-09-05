package com.example.threadLearning.lock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExpressCond {
    public final static String DESTINATION = "ShangHai";
    private Integer km;
    private String site;

    private final Lock lock = new ReentrantLock();
    private final Condition kmCond = lock.newCondition();
    private final Condition siteCond = lock.newCondition();

    public ExpressCond(Integer km, String site) {
        this.km = km;
        this.site = site;
    }

    public void changeKm(){
        lock.lock();
        try{
            this.km = 101;
            kmCond.signal(); //只唤醒一个
        }finally {
            lock.unlock();
        }

    }

    public void changeSite(){
        lock.lock();
        try{
            this.site = DESTINATION;
            siteCond.signalAll();
        }finally {
            lock.unlock();
        }

    }

    public void waitKm() {
        lock.lock();
        try{
            while(km<=100){
                try {
                    System.out.println("km thread[" + Thread.currentThread().getId()+"] is be waited");
                    kmCond.await(); //await会释放锁
                    System.out.println("km thread[" + Thread.currentThread().getId()+"] is be notified");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            lock.unlock();
            System.out.println("km thread[" + Thread.currentThread().getId()+"] is end");
        }
    }

    public void waitSite() {
        lock.lock();
        try{
            while(!DESTINATION.equals(site)){
                try {
                    System.out.println("site thread[" + Thread.currentThread().getId()+"] is be waited");
                    siteCond.await();
                    System.out.println("site thread[" + Thread.currentThread().getId()+"] is be notified");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            lock.unlock();
            System.out.println("site thread[" + Thread.currentThread().getId()+"] is end");
        }
    }
}
