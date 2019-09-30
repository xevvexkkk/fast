package com.frame.fast.cms.coupon;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.frame.fast.model.Coupon;
import com.frame.fast.model.CouponBatch;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class CouponBatchVo extends CouponBatch {

    private String statusDesc;

    public static List<CouponBatchVo> tranFromOrigin(List<CouponBatch> coupons){
        List<CouponBatchVo> couponVos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(coupons)){
            coupons.forEach(n->{
                CouponBatchVo vo = new CouponBatchVo();
                BeanUtils.copyProperties(n,vo);
                vo.setStatusDesc(n.getStatus().getName());
                couponVos.add(vo);
            });
        }
        return couponVos;
    }
}
