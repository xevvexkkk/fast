package com.frame.fast.model;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum StaffPost {

    RECYCLE(0,"回收员"),
    ADMIN(1,"管理员"),
    OPERATE(2,"运维人员"),
    ;
    @EnumValue
    private Integer value;

    private String name;

    StaffPost(Integer value, String name){
        this.value = value;
        this.name = name;
    }

    public static StaffPost forValue(int value) {
        for (StaffPost sort : StaffPost.values()) {
            if (sort.getValue() == value) {
                return sort;
            }
        }
        return null;
    }

    public static StaffPost forName(String name) {
        for (StaffPost sort : StaffPost.values()) {
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
