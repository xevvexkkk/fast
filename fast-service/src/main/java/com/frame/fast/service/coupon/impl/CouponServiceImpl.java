package com.frame.fast.service.coupon.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.fast.model.Coupon;
import com.frame.fast.repository.CouponMapper;
import com.frame.fast.service.coupon.ICouponService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 优惠券 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-08-23
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements ICouponService {

    @Resource
    private CouponMapper couponMapper;




}
