package com.example.threadLearning.threadSafe.deadLock.dynamic;
/**线程不安全的转账实现**/
public class UnsafeTransfer implements ITransfer {
    @Override
    public void transfer(UserAccount from, UserAccount to, double amount) throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        synchronized (from){
            System.out.println(threadName+" get " + from.getName());
            synchronized (to){
                System.out.println(threadName+" get " + to.getName());
                from.deseaseMoney(amount);
                to.addMoney(amount);
            }
        }
    }
}
