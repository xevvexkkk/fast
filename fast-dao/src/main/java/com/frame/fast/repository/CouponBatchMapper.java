package com.frame.fast.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frame.fast.model.CouponBatch;
import com.frame.fast.model.CouponStatus;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 优惠券 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-08-23
 */
public interface CouponBatchMapper extends BaseMapper<CouponBatch> {

    public void reset(@Param("id") Long id, @Param("status")CouponStatus status);
}
