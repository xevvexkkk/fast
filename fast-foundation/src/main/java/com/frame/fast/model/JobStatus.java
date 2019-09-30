package com.frame.fast.model;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum JobStatus {

    INIT(0,"待回收"),
    NOT_READY(1,"垃圾未备"),
    SHUNT(2,"搁置"),
    FINISH(3,"已回收"),
    ;

    @EnumValue
    private Integer value;

    private String name;

    JobStatus(Integer value, String name){
        this.value = value;
        this.name = name;
    }

    public static JobStatus forValue(int value) {
        for (JobStatus sort : JobStatus.values()) {
            if (sort.getValue() == value) {
                return sort;
            }
        }
        return null;
    }

    public static JobStatus forName(String name) {
        for (JobStatus sort : JobStatus.values()) {
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
