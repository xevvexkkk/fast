package com.frame.fast.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户产品状态
 * </p>
 *
 * @author jobob
 * @since 2019-08-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CustomProduct extends BaseEntity {

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
     * 剩余次/天数
     */
    private Integer remainNum;


}
