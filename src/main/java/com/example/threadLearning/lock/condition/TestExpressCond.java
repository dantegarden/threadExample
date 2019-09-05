package com.example.threadLearning.lock.condition;


import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class TestExpressCond {
    private static ExpressCond express = new ExpressCond(0, "TianJin");
    private static class CheckSite implements Runnable{
        @Override
        public void run() {
            express.waitSite();
        }
    }
    private static class CheckKm implements Runnable{
        @Override
        public void run() {
            express.waitKm();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            CheckKm checkKm = new CheckKm();
            new Thread(checkKm).start();
        }
        for (int i = 0; i < 3; i++) {
            CheckSite checkSite = new CheckSite();
            new Thread(checkSite).start();
        }
        Thread.sleep(5000);
        System.out.println("-----changeKm--------");
        express.changeKm();
        Thread.sleep(5000);
        System.out.println("-----changeSite--------");
        express.changeSite();
    }
}
