package com.frame.fast.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.frame.fast.model.Order;
import com.frame.fast.model.ProductSort;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleOrder {

    private String orderStatusDesc;

    private ProductSort productSort;

    private String num;

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    private String category;

    private String productSortDesc;

    public SimpleOrder(){

    }

    public SimpleOrder(Order order){
        this.orderStatusDesc = order.getOrderStatus().getDesc();
        this.num = order.getProductSort().getNum() + (order.getProductSort().getType() == 1 ? " 天" : " 次");
        this.createAt = order.getCreateAt();
        this.productSort = order.getProductSort();
        this.category = order.getProductSort().getCategory();
        this.productSortDesc = order.getProductSort().getName();
    }
}
