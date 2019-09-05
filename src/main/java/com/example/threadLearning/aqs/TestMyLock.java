package com.example.threadLearning.aqs;

import com.example.threadLearning.SleepTools;

import java.util.concurrent.locks.Lock;

public class TestMyLock {

    private static final Lock lock = new TrinityLock();//MyLock();

    private static class Worker implements Runnable{
        @Override
        public void run() {
            while(true){
                lock.lock();
                try{
                    SleepTools.sleep(1);
                    System.out.println(Thread.currentThread().getName()+ ": is working");
                    SleepTools.sleep(1);
                }finally {
                    System.out.println(Thread.currentThread().getName()+ ": is resting");
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread wT = new Thread(new Worker());
            wT.setDaemon(true);
            wT.start();
        }
        for (int i = 0; i < 10; i++) {
            SleepTools.sleep(1);
            System.out.println("Main: " + (i+1));
        }
    }
}
