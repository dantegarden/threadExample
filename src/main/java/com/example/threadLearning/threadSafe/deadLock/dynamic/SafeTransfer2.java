package com.example.threadLearning.threadSafe.deadLock.dynamic;

import com.example.threadLearning.SleepTools;

import java.util.Random;
import java.util.concurrent.locks.Lock;

public class SafeTransfer2 implements ITransfer {
    static Random r = new Random();
    @Override
    public void transfer(UserAccount from, UserAccount to, double amount) throws InterruptedException {
        String threadName = Thread.currentThread().getName();

        Lock fromLock = from.getLock();
        Lock toLock = to.getLock();
        while(true){
            if(fromLock.tryLock()){
                System.out.println(threadName+" get " + from.getName());
                try {
                    if(toLock.tryLock()){
                        System.out.println(threadName+" get " + to.getName());
                        try{
                            //两把锁都拿到了
                            from.deseaseMoney(amount);
                            to.addMoney(amount);
                            break;
                        }finally {
                            toLock.unlock();
                        }
                    }
                } finally {
                    fromLock.unlock();
                }
            }
            SleepTools.sleepMs(r.nextInt(10)); //如果没有休眠，会形成活锁，休眠可以错开两个线程拿锁的时间
        }
    }
}
