package com.frame.fast.service.coupon.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.fast.model.Coupon;
import com.frame.fast.model.CouponBatch;
import com.frame.fast.repository.CouponBatchMapper;
import com.frame.fast.service.coupon.ICouponBatchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 优惠券 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-08-23
 */
@Service
public class CouponBatchServiceImpl extends ServiceImpl<CouponBatchMapper, CouponBatch> implements ICouponBatchService {
    @Resource
    private CouponBatchMapper couponBatchMapper;

    @Override
    public CouponBatch getByCouponCode(String couponCode){
        QueryWrapper<CouponBatch> wrapper = new QueryWrapper<>();
        wrapper.eq("code",couponCode);
        return couponBatchMapper.selectOne(wrapper);
    }

    @Override
    public void reset(CouponBatch couponBatch){
        couponBatchMapper.reset(couponBatch.getId(),couponBatch.getStatus());
    }


    @Override
    public CouponBatch getByCouponId(Long couponId){
        QueryWrapper<CouponBatch> wrapper = new QueryWrapper<>();
        wrapper.eq("coupon_id",couponId);
        return couponBatchMapper.selectOne(wrapper);
    }
}
