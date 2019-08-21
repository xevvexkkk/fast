package com.frame.fast.service.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.fast.model.Order;
import com.frame.fast.repository.OrderMapper;
import com.frame.fast.service.order.IOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-08-12
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public Order getOrderByOrderId(Long orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id",orderId);
        return orderMapper.selectOne(wrapper);
    }


}
