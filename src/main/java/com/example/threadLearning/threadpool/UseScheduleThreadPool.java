package com.example.threadLearning.threadpool;

import com.example.threadLearning.SleepTools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UseScheduleThreadPool {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Random r = new Random();

    static class ScheduleWorker implements Runnable {
        private final static int NORMAL = 1;
        private final static int NORMAL_TIMEOUT = 2;
        private final static int EXCEPTION_HAPPENED = 3;
        private final static int CATCH_EXCEPTION = 4;

        private int type;

        public ScheduleWorker(int type) {
            this.type = type;
        }

        @Override
        public void run() {
            if(type==EXCEPTION_HAPPENED){
                System.out.println("ScheduleWorker[" + Thread.currentThread().getId() + "] : " + sdf.format(new Date()));
                throw new RuntimeException("exception happened ");
            }else if(type==CATCH_EXCEPTION){
                try {
                    System.out.println("ScheduleWorker[" + Thread.currentThread().getId() + "] : " + sdf.format(new Date()));
                    throw new RuntimeException("exception happened ");
                }catch (Exception e){
                    System.out.println("exception catched");
                }
            }else{
                if(type==NORMAL_TIMEOUT){
                    int duration = r.nextInt(6);
                    System.out.println("ScheduleWorker[" + Thread.currentThread().getId() + "] : " + sdf.format(new Date()) + " 将执行" + duration + "s");
                    SleepTools.sleep(duration);
                }else{
                    System.out.println("ScheduleWorker[" + Thread.currentThread().getId() + "] : " + sdf.format(new Date()));
                }
            }
        }
    }

    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(10);
        //普通的定时任务
        for (int i = 0; i < 1; i++) {
            //立即执行，每三秒执行一次
            pool.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.NORMAL), 0, 3, TimeUnit.SECONDS);
        }

        //普通的定时任务，任务耗时超过固定周期间隔，下一个任务会马上执行
        for (int i = 0; i < 1; i++) {
            //立即执行，每三秒执行一次
            pool.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.NORMAL_TIMEOUT), 0, 3, TimeUnit.SECONDS);
        }
        //捕捉异常，周期任务照常
        for (int i = 0; i < 1; i++) {
            //立即执行，每三秒执行一次
            pool.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.CATCH_EXCEPTION), 0, 3, TimeUnit.SECONDS);
        }
        //抛出异常，定时任务取消且不会抛出异常，所以run方法里必须用try/catch包住
        for (int i = 0; i < 1; i++) {
            //立即执行，每三秒执行一次
            pool.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.EXCEPTION_HAPPENED), 0, 3, TimeUnit.SECONDS);
        }
    }
}
