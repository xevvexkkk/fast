package com.frame.fast.model;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum OrderStatus {

    INIT(0,"init"),
    SUCCESS(1,"ok"),
    FAIL_CANCEL(2,"fail cancel"),
    FAIL(3,"fail"),
    ABNORMAL(4,"abnormal"),
    ;

    @EnumValue
    private Integer value;

    private String name;

    OrderStatus(Integer value, String name){
        this.value = value;
        this.name = name;
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
}
