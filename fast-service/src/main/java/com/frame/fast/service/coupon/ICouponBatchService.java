package com.frame.fast.service.coupon;

import com.baomidou.mybatisplus.extension.service.IService;
import com.frame.fast.model.CouponBatch;

/**
 * <p>
 * 优惠券 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-08-23
 */
public interface ICouponBatchService extends IService<CouponBatch> {

    CouponBatch getByCouponCode(String couponCode);

    void reset(CouponBatch couponBatch);

    CouponBatch getByCouponId(Long couponId);
}
