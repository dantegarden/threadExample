package com.example.threadLearning.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    private Lock lock = new ReentrantLock();
    private int count;

    public LockDemo(int count) {
        this.count = count;
    }

    public void increment(){
        lock.lock();
        try{
            count++;
        }finally { //保证锁一定能释放
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockDemo lockDemo = new LockDemo(1);
        System.out.println(lockDemo.count);
        lockDemo.increment();
        System.out.println(lockDemo.count);
    }
}
