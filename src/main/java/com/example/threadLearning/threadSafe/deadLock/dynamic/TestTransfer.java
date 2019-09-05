package com.example.threadLearning.threadSafe.deadLock.dynamic;

public class TestTransfer {
    private static class TransferThread extends Thread {
        private String name; //线程名
        private UserAccount from;
        private UserAccount to;
        private double amount;
        private ITransfer transfer;

        public TransferThread(String name, UserAccount from, UserAccount to, double amount, ITransfer transfer) {
            this.name = name;
            this.from = from;
            this.to = to;
            this.amount = amount;
            this.transfer = transfer;
        }

        @Override
        public void run() {
            try {
                Thread.currentThread().setName(name);
                transfer.transfer(from, to, amount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        UserAccount zhangsan = new UserAccount("zhangsan", 20000d);
        UserAccount lisi = new UserAccount("lisi", 10000d);
        ITransfer transfer = new SafeTransfer2();//new UnsafeTransfer();
        //你以为你获取锁的顺序是固定的，但因为调用传入参是动态，其实是不固定的
        TransferThread zhangsan2Lisi = new TransferThread("zhangsan2lisi", zhangsan, lisi, 2500, transfer);
        TransferThread lisi2Zhangsan = new TransferThread("lisi2zhangsan", lisi, zhangsan, 1000, transfer);
        zhangsan2Lisi.start();
        lisi2Zhangsan.start();
    }
}
