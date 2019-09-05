package com.example.threadLearning.atomic;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class UseAtomicArray {
    static int[] value = new int[]{1,2};
    static AtomicIntegerArray iarr = new AtomicIntegerArray(value); //其实做了数组拷贝

    public static void main(String[] args) {
        iarr.getAndSet(1, 3); //数组下标，新值
        System.out.println(iarr.get(1));
        System.out.println(Arrays.toString(value)); //并不会改变原数组
    }
}
