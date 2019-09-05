package com.example.threadLearning.lock.rw;

import com.example.threadLearning.SleepTools;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UseRwLock implements GoodsService{
    private GoodsInfo goodsInfo;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public UseRwLock(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public GoodsInfo getNum() {
        readLock.lock();
        try{
            SleepTools.sleepMs(5);
            return goodsInfo;
        }finally {
            readLock.unlock();
        }
    }

    @Override
    public void setNum(Integer sellNumber) {
        writeLock.lock();
        try{
            SleepTools.sleepMs(5);
            goodsInfo.caculate(sellNumber);
        }finally{
            writeLock.unlock();
        }
    }
}
