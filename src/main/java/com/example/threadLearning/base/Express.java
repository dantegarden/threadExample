package com.example.threadLearning.base;

public class Express {
    public final static String DESTINATION = "ShangHai";
    private Integer km;
    private String site;

    public Express(Integer km, String site) {
        this.km = km;
        this.site = site;
    }

    public synchronized void changeKm(){
        this.km = 101;
        notifyAll();
    }

    public synchronized void changeSite(){
        this.site = DESTINATION;
        notifyAll();
    }

    public synchronized void waitKm() {
        while(km<=100){
            try {
                System.out.println("km thread[" + Thread.currentThread().getId()+"] is be wait");
                wait();
                System.out.println("km thread[" + Thread.currentThread().getId()+"] is be notified");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("km thread[" + Thread.currentThread().getId()+"] is end");
    }

    public synchronized void waitSite() {
        while(!DESTINATION.equals(site)){
            try {
                System.out.println("site thread[" + Thread.currentThread().getId()+"] is be waited");
                wait();
                System.out.println("site thread[" + Thread.currentThread().getId()+"] is be notified");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("site thread[" + Thread.currentThread().getId()+"] is end");
    }

}
