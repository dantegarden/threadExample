package com.example.threadLearning.pool;

import com.google.common.collect.Lists;

import java.sql.Connection;
import java.util.LinkedList;

public class DBPool {
    private static LinkedList<Connection> pool = Lists.newLinkedList();

    public DBPool(int initalSize){
        if(initalSize>0){
            for (int i = 0; i < initalSize; i++) {
                pool.addLast(MyConnectionImpl.getInstance());
            }
        }
    }
    /**在timeoutMils内还拿不到数据库连接，就返回null**/
    public Connection fetchConnection(long timeoutMils) throws InterruptedException {
        synchronized (pool){
            if(timeoutMils<0){ //永不超时
                while(pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst(); //被唤醒时取第一个
            }else{
                long overTime = System.currentTimeMillis() + timeoutMils;
                long remain = timeoutMils;
                while(pool.isEmpty() && remain>0){
                    pool.wait(remain);
                    remain = overTime - System.currentTimeMillis(); //剩余等待时间
                }
                //已经超时，或者连接池不为空
                Connection result = null;
                if(!pool.isEmpty()){
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }

    public void releaseConnection(Connection connection){
        if(connection!=null){
            synchronized (pool){
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }
}
