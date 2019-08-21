package com.frame.fast.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 订单
 * </p>
 *
 * @author jobob
 * @since 2019-08-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("order_info")
public class Order extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    private Long orderId;

    /**
     * prepay_id
     */
    private String prepayId;

    /**
     * 用户Id
     */
    private Long customId;

    /**
     * openid
     */
    private String openId;

    /**
     * 产品分类
     */
    private ProductSort productSort;

    /**
     * 总价(分)
     */
    private Integer totalFee;

    /**
     * 优惠金额(分)
     */
    private Integer couponFee;

    /**
     * 实付(分)
     */
    private Integer realFee;

    /**
     * IP地址
     */
    private String spbillCreateIp;

    /**
     * 订单状态
     */
    private OrderStatus orderStatus;




}
