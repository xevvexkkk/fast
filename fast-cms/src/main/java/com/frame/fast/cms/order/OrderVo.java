package com.frame.fast.cms.order;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.frame.fast.model.CommunityEnum;
import com.frame.fast.model.Order;
import com.frame.fast.model.PersonInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderVo extends Order {

    private String productSortDesc;

    private String orderStatusDesc;
    public static List<OrderVo> tranFromOrigin(List<Order> orders){
        List<OrderVo> orderVos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(orders)){
            orders.forEach(n->{
                OrderVo vo = new OrderVo();
                BeanUtils.copyProperties(n,vo);
                vo.setProductSortDesc(n.getProductSort() == null ? "" :n.getProductSort().getName());
                vo.setOrderStatusDesc(n.getOrderStatus() == null ? "" :n.getOrderStatus().getDesc());
                orderVos.add(vo);
            });
        }
        return orderVos;
    }
}
