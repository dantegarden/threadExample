package com.example.threadLearning.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class UseAtomicInt {
    static AtomicInteger ai = new AtomicInteger(10);

    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement()); //return 10，10 --> 11  <=> i++
        System.out.println(ai.incrementAndGet()); //11 --> 12，return 12  <=> ++i
        System.out.println(ai.get()); //return 12

        System.out.println(incrementAndGet());
    }

    //揭示AtomicInteger自增的原理
    public static int incrementAndGet(){
        while(true){
            int i = ai.get();
            boolean success = ai.compareAndSet(i, ++i);
            if(success){
                return i;
            }
        }
    }
}
