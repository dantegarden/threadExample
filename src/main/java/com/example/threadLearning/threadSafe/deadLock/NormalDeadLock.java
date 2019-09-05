package com.example.threadLearning.threadSafe.deadLock;

import com.example.threadLearning.SleepTools;

public class NormalDeadLock {
    private static Object objA = new Object();
    private static Object objB = new Object();

    private static void A2B(){
        String threadName = Thread.currentThread().getName();
        synchronized (objA){
            System.out.println(threadName + " get A");
            SleepTools.sleepMs(100);
            synchronized (objB){
                System.out.println(threadName + " get B");
            }
        }
    }

    private static void B2A(){
        String threadName = Thread.currentThread().getName();
        synchronized (objB){
            System.out.println(threadName + " get B");
            SleepTools.sleepMs(100);
            synchronized (objA){
                System.out.println(threadName + " get A");
            }
        }
    }

    private static class TestThread extends Thread {
        private String name;

        public TestThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);
            try {
                B2A();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("TestDeadLock");
        TestThread testThread = new TestThread("SubTestThread");
        testThread.start();
        try {
            A2B();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
