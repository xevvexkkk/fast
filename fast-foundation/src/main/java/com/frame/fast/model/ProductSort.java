package com.frame.fast.model;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum ProductSort {

    CLASSFY(1,"分类服务(单次)",0,1,800),
    CLASSFY_MONTH(2,"分类服务(包月)",1,30,12000),
    THROW(3,"代扔服务(单次)",0,1,800),
    THROW_MONTH(4,"代扔服务(包月)",1,30,800),
    THROW_MONTH_NEW(5,"代扔服务拉新(包月)",1,30,800),
    RECYCLE(6,"回收服务",0,1,800),
    ;
    @EnumValue
    private int code;

    private String name;

    private int type;

    private int num;

    private Integer fee;

    /**
     *
     * @param code
     * @param name
     * @param type 单次0 / 包月1
     * @param num 次数/天数
     */
    ProductSort(int code,String name,int type,int num,int fee){
        this.code = code;
        this.name = name;
        this.type = type;
        this.num = num;
        this.fee = fee;
    }

    public static ProductSort forCode(int code) {
        for (ProductSort sort : ProductSort.values()) {
            if (sort.getCode() == code) {
                return sort;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }
}
