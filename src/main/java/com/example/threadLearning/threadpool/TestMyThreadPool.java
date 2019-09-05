package com.example.threadLearning.threadpool;

import com.example.threadLearning.SleepTools;

import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

public class TestMyThreadPool {

    static MyThreadPool pool = new MyThreadPool();

    static class MyTask implements Runnable{
        private String name;
        private static Random r = new Random();

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("MyTask["+this.name+"]: start"  );
            SleepTools.sleepMs(r.nextInt(5000));
            System.out.println("MyTask["+this.name+"]: end"  );
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            pool.execute(new MyTask("test_" + i));
        }
        System.out.println(pool);
        SleepTools.sleep(100);
        System.out.println("interrupt all tasks");
        pool.destory();
        System.out.println(pool);
    }

}
