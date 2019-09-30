package com.frame.fast.coupon;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.frame.fast.CouponFacade;
import com.frame.fast.model.ResponseVo;
import com.frame.fast.model.CheckResult;
import com.frame.fast.model.Coupon;
import com.frame.fast.model.PersonInfo;
import com.frame.fast.service.coupon.ICouponService;
import com.frame.fast.service.person.PersonInfoService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 优惠券 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-08-23
 */
@RestController
@RequestMapping("/coupon")
@Slf4j
public class CouponController {

    Gson gson = new Gson();

    @Resource
    private ICouponService couponService;

    @Resource
    private CouponFacade couponFacade;
    @Resource
    private PersonInfoService personInfoService;

    @GetMapping("/generate")
    public ResponseVo generateCoupons(Long couponId, Integer genCount){
        if(genCount == null || genCount <=0){
            return ResponseVo.failVo("生成数量不能为空");
        }
        if(genCount > 10000){
            return ResponseVo.failVo("生成数量不能超过10000");
        }
        couponFacade.batchGenerateUnbindCoupons(couponId,genCount);
        return ResponseVo.successVo(null);
    }

    @GetMapping("/create")
    public ResponseVo createCoupon(Coupon coupon){
        if(coupon == null){
            return ResponseVo.failVo("数据异常");
        }
        if(coupon.getStartDate() == null || coupon.getEndDate() == null){
            return ResponseVo.failVo("有效期不能为空");
        }
        couponService.save(coupon);
        return ResponseVo.successVo(null);
    }

    @GetMapping("/active")
    public ResponseVo activeCoupon(@RequestParam String couponCode,@RequestParam String openId){
        if(StringUtils.isEmpty(couponCode)){
            return ResponseVo.failVo("couponCode cann`t be null");
        }
        PersonInfo personInfo = personInfoService.getByOpenId(openId);
        CheckResult checkResult = couponFacade.activeCoupon(couponCode, personInfo);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setMessage(checkResult.getMsg());
        responseVo.setSuccess(checkResult.isResult());
        return responseVo;

    }

}
