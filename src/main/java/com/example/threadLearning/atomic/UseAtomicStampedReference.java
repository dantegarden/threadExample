package com.example.threadLearning.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

public class UseAtomicStampedReference {
    static AtomicStampedReference<String> asr = new AtomicStampedReference<>("Mark", 0);

    public static void main(String[] args) throws InterruptedException {
        final int oldStamp = asr.getStamp();
        final String oldReference = asr.getReference();
        System.out.println(oldStamp + " " + oldReference);

        Thread rightStampedThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ": " + asr.getStamp() + " " + asr.getReference() + " 修改结果：" +
                        asr.compareAndSet(oldReference, oldReference+"Java", oldStamp, oldStamp+1));
            }
        });

        Thread errorStampedThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ": " + asr.getStamp() + " " + asr.getReference() + " 修改结果：" +
                        asr.compareAndSet(oldReference, oldReference+"C", oldStamp, oldStamp+1));
            }
        });

        rightStampedThread.start();
        rightStampedThread.join(); //保证right线程先跑完
        errorStampedThread.start();
        errorStampedThread.join();

        System.out.println(asr.getStamp() + " " + asr.getReference());
    }
}
