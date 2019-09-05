package com.example.threadLearning.forkJoin.sync;

import java.util.Random;

public class MakeArray {
    public static final int ARRAY_LENGTH = 50000;
    public static int[] makeArray(){
        Random r = new Random();
        int[] result = new int[ARRAY_LENGTH];
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            result[i] = r.nextInt(ARRAY_LENGTH * 3);
        }
        return result;
    }
}
