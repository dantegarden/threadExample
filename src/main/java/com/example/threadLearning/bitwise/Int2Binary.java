package com.example.threadLearning.bitwise;
/**演示位运算**/
public class Int2Binary {
    public static void main(String[] args) {
        int data = 4;
        System.out.println("4 is " + Integer.toBinaryString(4));
        //位与 1&1=1 1&0=0 0&0=0
        System.out.println(Integer.toBinaryString(4));
        System.out.println(Integer.toBinaryString(6));
        System.out.println("4&6 = " + Integer.toBinaryString(4 & 6));

        //位或 1|1=1 1|0=1 0|0=0
        System.out.println(Integer.toBinaryString(4));
        System.out.println(Integer.toBinaryString(6));
        System.out.println("4|6 = " + Integer.toBinaryString(4 | 6));

        //位非 ~1=0 ~0=1
        System.out.println(Integer.toBinaryString(4));
        System.out.println("~4 = " + Integer.toBinaryString(~4));

        //位异或 1^1=0 1^0=1 0^0=0
        System.out.println(Integer.toBinaryString(4));
        System.out.println(Integer.toBinaryString(6));
        System.out.println("4|6 = " + Integer.toBinaryString(4 ^ 6));

        // << 有符号左移 相当于*2 >>有符号右移 相当于/2
        //正数 第31位是0，负数 第31位是1，有符号只移动30位以前的
        System.out.println(Integer.toBinaryString(4));
        System.out.println("4<<1 = " + (4 << 1) + " => " + Integer.toBinaryString(4 << 1));
        System.out.println("4>>1 = " + (4 >> 1) + " => " + Integer.toBinaryString(4 >> 1));
        //无符号左移<<< 无符号右移>>>2
        System.out.println(Integer.toBinaryString(4));
        //System.out.println("4<<1 = " + (4 <<< 1) + " => " + Integer.toBinaryString(4 <<< 1));
        System.out.println("4>>>1 = " + (4 >>> 1) + " => " + Integer.toBinaryString(4 >>> 1));

        // x%2^n <==> x&(2^n-1) 即 %2^n ==> &(2^n-1)
        System.out.println("345%16 = " + (345%16));
        System.out.println("345&(16-1) = " + (345&(16-1)));
    }
}
