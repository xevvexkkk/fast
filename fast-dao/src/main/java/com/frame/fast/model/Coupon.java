package com.frame.fast.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 优惠券
 * </p>
 *
 * @author jobob
 * @since 2019-08-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Coupon extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    private CouponStatus status;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠券类型
     */
    private CouponType type;

    /**
     * 天数/次数/金额
     */
    private Integer num;

    /**
     * 开始日期
     */
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    /**
     * 结束日期
     */
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    /**
     * 渠道
     */
    private String channel;

    private ProductSort productSort;

}
