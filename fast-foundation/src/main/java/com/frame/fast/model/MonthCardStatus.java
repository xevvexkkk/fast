package com.frame.fast.model;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum MonthCardStatus {

    VALID(1,"有效"),
    INVALID(0,"无效"),
    ;

    @EnumValue
    private Integer value;

    private String name;

    MonthCardStatus(Integer value, String name){
        this.value = value;
        this.name = name;
    }

    public static MonthCardStatus forValue(int value) {
        for (MonthCardStatus sort : MonthCardStatus.values()) {
            if (sort.getValue() == value) {
                return sort;
            }
        }
        return null;
    }

    public static MonthCardStatus forName(String name) {
        for (MonthCardStatus sort : MonthCardStatus.values()) {
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
