package com.example.threadLearning.tools.semaphore;

import com.example.threadLearning.SleepTools;
import com.example.threadLearning.pool.MyConnectionImpl;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.Random;

public class UseSemaphore {
    static DBPool pool = new DBPool();

    private static class BizThread implements Runnable{
        @Override
        public void run() {
            Random random = new Random();
            long startTime = System.currentTimeMillis();
            try {
                Connection connection = pool.fetchConnection();
                System.out.println("BizThread["+Thread.currentThread().getId()+"] ：获取数据库连接耗时 " + (System.currentTimeMillis() - startTime) + "ms");
                SleepTools.sleepMs(100 + random.nextInt(100));
                System.out.println("BizThread["+Thread.currentThread().getId()+"] ：查询结束,归还连接");
                pool.releaseConnection(connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new Thread(new BizThread()).start();
        }
    }
}
