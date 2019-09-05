package com.example.threadLearning.tools;

import com.example.threadLearning.SleepTools;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.Exchanger;

public class UseExchanger {

    static final Exchanger<Set<String>> exchanger = new Exchanger<>();

    private static class ThreadA implements Runnable{
        @Override
        public void run() {
            Set<String> setA = Sets.newHashSet();
            Arrays.asList("A","B","C","D").forEach(a -> {
                setA.add(a);
                SleepTools.sleepMs(1000);
            });

            try {
                System.out.println("ThreadA : wait to exchange");
                Set<String> setB = exchanger.exchange(setA);
                setB.forEach(b -> System.out.println("ThreadA: " + b));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ThreadB implements Runnable{
        @Override
        public void run() {
            Set<String> setB = Sets.newHashSet();
            Arrays.asList("1234567890".split("")).forEach(b -> {
                setB.add(b);
                SleepTools.sleepMs(1000);
            });

            try {
                System.out.println("ThreadB : wait to exchange");
                Set<String> setA = exchanger.exchange(setB);
                setA.forEach(a -> System.out.println("ThreadB: " + a));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new ThreadA()).start();
        new Thread(new ThreadB()).start();
    }
}
