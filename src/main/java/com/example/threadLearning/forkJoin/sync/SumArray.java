package com.example.threadLearning.forkJoin.sync;

import com.example.threadLearning.SleepTools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumArray {

    public static class SumTask extends RecursiveTask<Integer>{

        private final static int THRESHOLD = MakeArray.ARRAY_LENGTH / 100;
        private int[] src;
        private int fromIndex; //开始统计的下标 用下标来选取计算的段落范围
        private int toIndex; //结束统计的下标

        public SumTask(int[] src, int fromIndex, int toIndex) {
            this.src = src;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Override
        protected Integer compute() {
            if(toIndex - fromIndex <= THRESHOLD){
                int count = 0;
                for (int i = fromIndex; i <= toIndex; i++) {
                    SleepTools.sleepMs(1);
                    count += src[i];
                }
                return count;
            }else{
                //fromIndex....toIndex 二分法，拆分成两个子任务
                int midIndex = (fromIndex + toIndex)/2;
                SumTask left = new SumTask(src, fromIndex, midIndex);
                SumTask right = new SumTask(src, midIndex+1, toIndex);
                invokeAll(left, right); //递交给线程池运行
                return left.join() + right.join();
            }
        }
    }

    public static void main(String[] args) {
        int[] src = MakeArray.makeArray();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SumTask rootTask = new SumTask(src, 0, src.length-1);
        long startTime = System.currentTimeMillis();
        forkJoinPool.invoke(rootTask);
        System.out.println("task is invoking");
        int count = rootTask.join();
        System.out.println("result: " + count + ", spend time: " + (System.currentTimeMillis() - startTime) + "ms");

        int count2 = 0;
        long startTime2 = System.currentTimeMillis();
        for (int i = 0; i < src.length; i++) {
            SleepTools.sleepMs(1);
            count2 += src[i];
        }
        System.out.println("result: " + count2 + ", spend time: " + (System.currentTimeMillis() - startTime2) + "ms");
    }
}
