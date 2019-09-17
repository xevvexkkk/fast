package com.frame.fast.model;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.ArrayList;
import java.util.List;

public enum ProductSort {

    CLASSFY(1,"无忧单次",0,1,800,CardCategory.CALSSFY.getName()),
    CLASSFY_MONTH(2,"无忧包月",1,30,12000,CardCategory.CALSSFY.getName()),
    THROW_MONTH(3,"舒适包月",1,30,60,CardCategory.THROW.getName()),
    CLASSFY_MONTH_NEW(4,"无忧包月新客专享(包月)",1,15,1,CardCategory.CALSSFY.getName()),
    HOME_APPLIANCES_RECYCLE(5,"家电回收",0,1,800,"家电回收"),
    RUBBISH_RECYCLE(6,"生活垃圾回收",0,1,800,"生活垃圾回收"),
    LARGE_FURNITURE_DISPOSAL(7,"大件家具处置",0,1,800,"大件家具处置"),
    MARGIN(8,"保证金",0,0,800,"保证金"),
    CLASSFY_MONTH_THREE(9,"无忧三月套餐",1,90,18800,CardCategory.CALSSFY.getName()),
    THROW_MONTH_THREE(10,"舒适三月套餐",1,90,13800,CardCategory.THROW.getName()),
    ;
    @EnumValue
    private int code;

    private String name;

    private int type;

    private int num;

    private Integer fee;

    private String category;
    /**
     *
     * @param code
     * @param name
     * @param type 单次0 / 包月1
     * @param num 次数/天数
     */
    ProductSort(int code,String name,int type,int num,int fee,String category){
        this.code = code;
        this.name = name;
        this.type = type;
        this.num = num;
        this.fee = fee;
        this.category = category;
    }

    public static ProductSort forCode(int code) {
        for (ProductSort sort : ProductSort.values()) {
            if (sort.getCode() == code) {
                return sort;
            }
        }
        return null;
    }

    public boolean isMonth(){
        if (this.type == 1) return true;
        else return false;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static List<ProductSort> getAllMonthProducts(){
        List<ProductSort> productSorts = new ArrayList<>();
        productSorts.add(ProductSort.CLASSFY_MONTH);
        productSorts.add(ProductSort.CLASSFY_MONTH_THREE);
        productSorts.add(ProductSort.THROW_MONTH);
        productSorts.add(ProductSort.THROW_MONTH_THREE);
        productSorts.add(ProductSort.CLASSFY_MONTH_NEW);
        return productSorts;
    }

    public static List<ProductSort> getAllMonthThrowProducts(){
        List<ProductSort> productSorts = new ArrayList<>();
        productSorts.add(ProductSort.THROW_MONTH);
        productSorts.add(ProductSort.THROW_MONTH_THREE);
        return productSorts;
    }

    public static List<ProductSort> getAllMonthClassfyProducts(){
        List<ProductSort> productSorts = new ArrayList<>();
        productSorts.add(ProductSort.CLASSFY_MONTH);
        productSorts.add(ProductSort.CLASSFY_MONTH_THREE);
        productSorts.add(ProductSort.CLASSFY_MONTH_NEW);
        return productSorts;
    }

    public static boolean classfy(ProductSort productSort){
        List<ProductSort> productSorts = new ArrayList<>();
        productSorts.add(ProductSort.CLASSFY_MONTH);
        productSorts.add(ProductSort.CLASSFY_MONTH_THREE);
        productSorts.add(ProductSort.CLASSFY_MONTH_NEW);
        return productSorts.contains(productSort);
    }

    /**
     * 无忧产品则获取舒适 ，舒适产品则获取无忧
     * @return
     */
    public static List<ProductSort> getOtherCategoryProducts(ProductSort productSort){
        List<ProductSort> productSorts = new ArrayList<>();

        if (productSort.getCategory().equals(CardCategory.CALSSFY.getName())){
            productSorts.add(ProductSort.THROW_MONTH);
            productSorts.add(ProductSort.THROW_MONTH_THREE);
        }
        if (productSort.getCategory().equals(CardCategory.THROW.getName())){
            productSorts.add(ProductSort.CLASSFY_MONTH);
            productSorts.add(ProductSort.CLASSFY_MONTH_THREE);
            productSorts.add(ProductSort.CLASSFY_MONTH_NEW);
        }
        return productSorts;
    }

}
