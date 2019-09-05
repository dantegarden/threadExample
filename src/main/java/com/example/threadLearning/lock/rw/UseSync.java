package com.example.threadLearning.lock.rw;

import com.example.threadLearning.SleepTools;

public class UseSync implements GoodsService{
    private GoodsInfo goodsInfo;

    public UseSync(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public synchronized GoodsInfo getNum() {
        SleepTools.sleepMs(5);
        return this.goodsInfo;
    }

    @Override
    public synchronized void setNum(Integer sellNumber) {
        SleepTools.sleepMs(5);
        goodsInfo.caculate(sellNumber);
    }
}
