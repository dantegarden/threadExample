package com.example.threadLearning.tools.semaphore;

import com.example.threadLearning.pool.MyConnectionImpl;
import sun.misc.Unsafe;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class DBPool {
    private static final Integer POOL_SIZE = 10;
    private static LinkedList<Connection> pool = new LinkedList<Connection>();
    private final Semaphore avaliable, unavaliable;
    static {
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.add(MyConnectionImpl.getInstance());
        }
    }

    public DBPool() {
        this.avaliable = new Semaphore(POOL_SIZE);
        this.unavaliable = new Semaphore(0);
    }

    public Connection fetchConnection() throws InterruptedException {
        avaliable.acquire(); //没有空位时会被阻塞，等到有空位再放行
        Connection connection = null;
        synchronized (pool) {
            connection = pool.removeFirst();
        }
        unavaliable.release();
        return connection;
    }

    public void releaseConnection(Connection connection) throws InterruptedException {
        if(connection!=null){
            System.out.println("当前有" + avaliable.getQueueLength() + "个线程等待数据库连接");
            System.out.println("可用连接数" + avaliable.availablePermits());
            unavaliable.acquire();
            synchronized (pool) {
                pool.addLast(connection);
            }

            avaliable.release();
        }
    }
}
