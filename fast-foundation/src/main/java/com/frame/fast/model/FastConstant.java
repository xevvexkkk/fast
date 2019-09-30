package com.frame.fast.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FastConstant {

    public static final String MONTH_CARD ="MONTH_CARD";

    public static final String PRODUCT_CATEGORY_CALSSFY = "无忧系列";

    public static final String PRODUCT_CATEGORY_THROW = "舒适系列";

    public static final String ORDER_NOTIFY_TITLE = "用户下单通知";

    public static final String JOB_NOTIFY_TITLE = "分类作业通知";

    public static final Long[] WHITE_LIST = {18L,7L,9L};

    //下单成功通知
    public static final String[] ORDER_NOTIFY_MAIN_LIST = {"xerrex@163.com"};

    public static String getOrderNotifyContent(ProductSort productSort,String custName,String mobile,int community,String address){
        return "用户姓名: " +
                custName + "\r\n" +
                "手机号码: " +
                mobile + "\r\n" +
                "地址: " + Objects.requireNonNull(CommunityEnum.forValue(community)).getName() + address + "\r\n" +
                "购买产品: " + productSort.getName()
                ;
    }

    public static String getJobNotifyContent(List<Long> customId){
        StringBuilder msg = new StringBuilder();
        customId.forEach(n->{
            msg.append(n).append(",");
        });
        return "用户Id: " + msg.toString() +
                "未分配代扔员，请及时前往管理系统进行维护";
    }
}
