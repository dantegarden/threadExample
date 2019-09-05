package com.example.threadLearning.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TrinityLock implements Lock {

    private static class Sync extends AbstractQueuedSynchronizer {
        public Sync(int count) {
            if(count <= 0){
                throw new IllegalArgumentException("count必须大于0");
            }
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int reduceArg) {
            while(true){
                int current = getState();
                int newCount = current - reduceArg;
                if(newCount < 0 || compareAndSetState(current, newCount)){
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int addCount) {
            while(true){
                int current = getState();
                int newCount = current + addCount;
                if(compareAndSetState(current, newCount)){
                    return true;
                }
            }
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() >= 0;
        }

        final Condition newCondition(){
            return new ConditionObject();
        }
    }

    private static final Sync sync = new Sync(3);

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1) >= 0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
