package com.example.threadLearning.queue;

import lombok.Data;

@Data
public class Order {
    private String orderNo; //订单编号
    private Double orderMoney; //订单金额

    public Order(String orderNo, Double orderMoney) {
        this.orderNo = orderNo;
        this.orderMoney = orderMoney;
    }
}
