package com.example.threadLearning.threadSafe.deadLock.dynamic;

public interface ITransfer {
    void transfer(UserAccount from, UserAccount to, double amount) throws InterruptedException;
}
