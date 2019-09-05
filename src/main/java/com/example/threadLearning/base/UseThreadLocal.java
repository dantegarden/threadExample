package com.example.threadLearning.base;

public class UseThreadLocal {
    static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };

    public static class TestThread implements Runnable {
        int id;

        public TestThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": start");
            Integer s = threadLocal.get();
            s += id;
            threadLocal.set(s);
            System.out.println(Thread.currentThread().getName() + ": " + s);
        }
    }

    public static void main(String[] args) {
        TestThread testThread = new TestThread(5);
        Thread[] threads = new Thread[3];
        for(int i=0;i<3;i++){
            threads[i] = new Thread(testThread);
        }
        for(int i=0;i<3;i++){
            threads[i].start();
        }
    }
}
