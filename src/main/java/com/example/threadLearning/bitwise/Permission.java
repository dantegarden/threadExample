package com.example.threadLearning.bitwise;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

/**利用位运算控制权限，只需要一个int字段*/
public class Permission {
    private static final int ALLOW_VIEW   = 1 << 0; //0001
    private static final int ALLOW_CREATE = 1 << 1; //0010
    private static final int ALLOW_UPDATE = 1 << 2; //0100
    private static final int ALLOW_DELETE = 1 << 3; //1000

    private int permission;

    public void setPerm(int perm) {
        this.permission = perm;
    }
    //赋予权限
    private void grant(int prem){
        permission = permission | prem;
    }
    //剥夺权限
    private void deprive(int prem){
        permission = permission & (~prem);
    }
    //判断是否有权限
    private boolean hasPermission(int prem){
        return (permission & prem) == prem;
    }
    //判断是否没有权限
    private boolean hasNoPermission(int prem){
        return (permission & prem) == 0;
    }

    public static void main(String[] args) {
        Permission permission = new Permission();
        permission.setPerm(15);
        permission.deprive(ALLOW_DELETE | ALLOW_UPDATE);
        System.out.println(permission.hasPermission(ALLOW_DELETE));
        System.out.println(permission.hasNoPermission(ALLOW_UPDATE));
        permission.grant(ALLOW_DELETE);
        System.out.println(permission.hasPermission(ALLOW_DELETE));
    }
}
