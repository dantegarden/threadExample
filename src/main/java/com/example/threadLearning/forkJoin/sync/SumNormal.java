package com.example.threadLearning.forkJoin.sync;

import com.example.threadLearning.SleepTools;

public class SumNormal {
    public static void main(String[] args) {
        int count = 0;
        int[] src = MakeArray.makeArray();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < src.length; i++) {
            SleepTools.sleepMs(1);
            count += src[i];
        }
        System.out.println("result: " + count + ", spend time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
