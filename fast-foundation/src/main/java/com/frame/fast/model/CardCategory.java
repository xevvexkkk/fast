package com.frame.fast.model;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum CardCategory {

    CALSSFY(0,"无忧系列"),
    THROW(1,"舒适系列"),
    ;
    @EnumValue
    private Integer value;

    private String name;

    CardCategory(Integer value, String name){
        this.value = value;
        this.name = name;
    }

    public static CardCategory forValue(int value) {
        for (CardCategory sort : CardCategory.values()) {
            if (sort.getValue() == value) {
                return sort;
            }
        }
        return null;
    }

    public static CardCategory forName(String name) {
        for (CardCategory sort : CardCategory.values()) {
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

    public static CardCategory getCategory (ProductSort productSort){
        return CardCategory.forName(productSort.getCategory());
    }
}
