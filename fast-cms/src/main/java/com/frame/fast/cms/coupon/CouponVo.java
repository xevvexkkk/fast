package com.frame.fast.cms.coupon;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.frame.fast.model.Coupon;
import com.frame.fast.model.Order;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class CouponVo extends Coupon {

    private String statusDesc;

    private String typeDesc;

    private String productSortDesc;

    public static List<CouponVo> tranFromOrigin(List<Coupon> coupons){
        List<CouponVo> couponVos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(coupons)){
            coupons.forEach(n->{
                CouponVo vo = new CouponVo();
                BeanUtils.copyProperties(n,vo);
                vo.setProductSortDesc(n.getProductSort() == null ? "" :n.getProductSort().getName());
                vo.setStatusDesc(n.getStatus().getName());
                vo.setTypeDesc(n.getType().getName());
                couponVos.add(vo);
            });
        }
        return couponVos;
    }
}
