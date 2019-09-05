package com.example.threadLearning.threadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ReduceLock {

    private Map<String,String> matchMap = new HashMap<>();
    public synchronized boolean isMatch(String name, String regexp){
        String key = "user." + name;
        String job = matchMap.get(key); //其实就只有这个地方需要加锁
        if (job == null) {
            return false;
        }else{
            return Pattern.matches(regexp, job);
        }
    }

    //缩小锁的范围
    public boolean isMatch2(String name, String regexp){
        String key = "user." + name;
        String job;
        synchronized (this){
            job = matchMap.get(key);
        }
        String job2 = job; //时间太短了，不如合并（避免多余的缩减锁的范围，虽然虚拟机会做锁粗化）
        synchronized (this){
            job = matchMap.get(key);
        }
        if (job == null) {
            return false;
        }else{
            return Pattern.matches(regexp, job);
        }
    }
}
