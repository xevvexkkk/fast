package com.frame.fast.model;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum OrderStatus {

    INIT(0,"init","初始"),
    SUCCESS(1,"ok","已成交"),
    FAIL_CANCEL(2,"fail cancel","已取消"),
    FAIL(3,"fail","支付失败"),
    ABNORMAL(4,"abnormal","订单异常"),
    ;

    @EnumValue
    private Integer value;

    private String name;

    private String desc;
    OrderStatus(Integer value, String name,String desc){
        this.value = value;
        this.name = name;
        this.desc = desc;
    }

    public static OrderStatus forValue(int value) {
        for (OrderStatus sort : OrderStatus.values()) {
            if (sort.getValue() == value) {
                return sort;
            }
        }
        return null;
    }

    public static OrderStatus forName(String name) {
        for (OrderStatus sort : OrderStatus.values()) {
            if (sort.getName().equals(name)) {
                return sort;
            }
        }
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
