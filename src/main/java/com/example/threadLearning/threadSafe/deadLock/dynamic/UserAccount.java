package com.example.threadLearning.threadSafe.deadLock.dynamic;

import lombok.Data;

import java.util.concurrent.locks.ReentrantLock;

@Data
public class UserAccount {
    private final String name;
    private double money;

    private final ReentrantLock lock = new ReentrantLock();

    public UserAccount(String name, double money) {
        this.name = name;
        this.money = money;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public void addMoney(double m){
        this.money += m;
    }

    public void deseaseMoney(double m){
        this.money -= m;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
