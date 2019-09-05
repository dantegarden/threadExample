package com.example.threadLearning.lock.rw;

import lombok.Data;

@Data
public class GoodsInfo {
    private static final Double price = new Double(25); //单价
    private final String name;
    private Double totalMoney; //总销售额
    private Integer storeNumber; //库存

    public GoodsInfo(String name, Double totalMoney, Integer storeNumber) {
        this.name = name;
        this.totalMoney = totalMoney;
        this.storeNumber = storeNumber;
    }

    //结算
    public void caculate(Integer sellNumber){
        this.totalMoney += price * sellNumber;
        this.storeNumber -= sellNumber;
    }
}
