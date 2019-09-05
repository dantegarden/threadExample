package com.example.threadLearning.threadSafe;

public class Singleton {

    private volatile static Singleton singleton; //volatile

    private Singleton() {
    }
    /**懒汉模式 双重检查*/
    public static Singleton getInstance() {
        if(singleton==null){
            synchronized (Singleton.class){
                if(singleton==null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

    /**饿汉模式 由虚拟机初始化来保证线程安全**/
    public static Singleton getInstance2(){
        return SingletonInstance.instance;
    }

    private static class SingletonInstance {
        private static Singleton instance = new Singleton();
    }
}
