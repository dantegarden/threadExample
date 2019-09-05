package com.example.threadLearning.threadSafe.deadLock.dynamic;

public class SafeTransfer implements ITransfer{

    private static final Object tieLock = new Object();

    @Override
    public void transfer(UserAccount from, UserAccount to, double amount) throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        //无视现在的hashCode，获得对象原始的hash
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);

        if(fromHash!=toHash){
            //先锁hash值小的那个
            UserAccount min = fromHash < toHash ? from: to;
            UserAccount max = fromHash < toHash ? to: from;
            synchronized (min){
                System.out.println(threadName+" get " + min.getName());
                synchronized (max){
                    System.out.println(threadName+" get " + max.getName());
                    from.deseaseMoney(amount);
                    to.addMoney(amount);
                }
            }
        }else{
            //抢第三把锁
            synchronized (tieLock){
                //这里就无所谓谁先谁后了
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


    }
}
