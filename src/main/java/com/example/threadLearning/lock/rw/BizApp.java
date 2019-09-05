package com.example.threadLearning.lock.rw;

import com.example.threadLearning.SleepTools;

import java.util.Random;

public class BizApp {

    private static int minWriteCount = 3; //最小线程数
    private static int readWriteRatio = 10; //读写线程的比例

    //读线程
    private static class ReadThread implements Runnable{
        private GoodsService goodsService;

        public ReadThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                goodsService.getNum();
            }
            System.out.println("ReadThread[" + Thread.currentThread().getId() +"] 读取商品数据耗时：" + (System.currentTimeMillis() - startTime) + "ms");
        }
    }

    //写线程
    private static class WriteThread implements Runnable{
        private GoodsService goodsService;

        public WriteThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                SleepTools.sleepMs(50);
                goodsService.setNum(random.nextInt(10));
            }
            System.out.println("WriteThread[" + Thread.currentThread().getId() +"] 写入商品数据耗时：" + (System.currentTimeMillis() - startTime) + "ms");
        }
    }

    public static void main(String[] args) {
        GoodsInfo goodsInfo = new GoodsInfo("Cup", 10000d, 10000);
        GoodsService goodsService = new UseRwLock(goodsInfo);//new UseSync(goodsInfo);
        for (int i = 0; i < minWriteCount; i++) {
            Thread writeT = new Thread(new WriteThread(goodsService));
            for (int j = 0; j < readWriteRatio; j++) {
                Thread readT = new Thread(new ReadThread(goodsService));
                readT.start();
            }
            SleepTools.sleepMs(100);
            writeT.start();
        }
    }
}
