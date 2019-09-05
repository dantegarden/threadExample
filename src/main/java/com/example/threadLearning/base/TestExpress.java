package com.example.threadLearning.base;

public class TestExpress {

    private static Express express = new Express(0, "TianJin");

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
    }
}
