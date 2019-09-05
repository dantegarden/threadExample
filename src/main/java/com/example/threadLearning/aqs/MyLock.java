package com.example.threadLearning.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**手动实现ReentrantLock
 * 实现Lock接口，内部类Sync继承AQS
 * */
public class MyLock implements Lock {

    /**
     * 它是个独占锁，需要覆盖
     *  tryAcuqire
     *  tryRelease
     *  isHeldExclusively
     * */
    private static class Sync extends AbstractQueuedSynchronizer{
        /**state标志位表示是否获取到锁，
         * state==1锁被占用，state==0锁没被占用**/
        @Override
        protected boolean isHeldExclusively() {
            return getState()==1;
        }

        @Override
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0, 1)){ //很多线程抢，所以需要CAS保证拿锁的原子性
                setExclusiveOwnerThread(Thread.currentThread()); //记录哪个线程拿到锁
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if(getState()==0){
                throw new UnsupportedOperationException();
            }
            //独占锁，还锁没人抢，所以直接还
            setExclusiveOwnerThread(null);
            setState(0);
            return Boolean.TRUE;
        }

        Condition newCondition(){
            //condition的逻辑直接委托给ConditionObject
            return new ConditionObject();
        }
    }

    private final Sync sync = new Sync();

    @Override
    public void lock() {
        //arg会传递给tryAcquire(arg)，因为上面覆盖的方法没有用到，所以随便写
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        //arg会传递给tryRelease(arg)，因为上面覆盖的方法没有用到，所以随便写
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
