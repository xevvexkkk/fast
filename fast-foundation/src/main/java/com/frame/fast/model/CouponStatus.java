package com.frame.fast.model;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum CouponStatus {

    INVALID(0,"无效"),
    VALID(1,"有效"),
    EXPIRED(2,"过期"),
    USED(3,"已使用"),
    ;

    @EnumValue
    private Integer value;

    private String name;

    CouponStatus(Integer value, String name){
        this.value = value;
        this.name = name;
    }

    public static CouponStatus forValue(int value) {
        for (CouponStatus sort : CouponStatus.values()) {
            if (sort.getValue() == value) {
                return sort;
            }
        }
        return null;
    }

    public static CouponStatus forName(String name) {
        for (CouponStatus sort : CouponStatus.values()) {
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
