package com.frame.fast.model;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum CouponType {

    FREQUENCY(0,"单次券"),
    DAYS(1,"天数券"),
    AMOUNT(2,"金额券"),
    FUU_REDUCTION(3,"满减券"),
    ;

    @EnumValue
    private Integer value;

    private String name;

    CouponType(Integer value, String name){
        this.value = value;
        this.name = name;
    }

    public static CouponType forValue(int value) {
        for (CouponType sort : CouponType.values()) {
            if (sort.getValue() == value) {
                return sort;
            }
        }
        return null;
    }

    public static CouponType forName(String name) {
        for (CouponType sort : CouponType.values()) {
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
