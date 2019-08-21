package com.frame.fast.service.order;

import com.baomidou.mybatisplus.extension.service.IService;
import com.frame.fast.model.Order;


/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-08-12
 */
public interface IOrderService extends IService<Order> {

    Order getOrderByOrderId(Long orderId);
}
