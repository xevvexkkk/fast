package com.frame.fast.model;

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
public class CouponBatch extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 优惠券id
     */
    private Long couponId;

    /**
     * 批次id
     */
    private Integer batchId;

    /**
     * 电子码
     */
    private String code;

    /**
     * 状态
     */
    private CouponStatus status;

    /**
     * 发券日期
     */
    private LocalDateTime giveDate;

    /**
     * 使用日期
     */
    private LocalDateTime useDate;

    /**
     * openid
     */
    private String openId;

    /**
     * 用户id
     */
    private Long userId;


    public CouponBatch(){

    }

    public CouponBatch(Coupon coupon){
       this.couponId = coupon.getId();
       this.giveDate = LocalDateTime.now();
       if(coupon.getStartDate() == null || coupon.getEndDate() == null){
           throw new RuntimeException("coupon date cann`t by null");
       }
       if(LocalDateTime.now().isAfter(coupon.getStartDate()) && LocalDateTime.now().isBefore(coupon.getEndDate())){
           this.status = CouponStatus.VALID;
       }else {
           this.status = CouponStatus.EXPIRED;
       }
    }
}
