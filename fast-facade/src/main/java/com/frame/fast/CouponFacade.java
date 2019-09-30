package com.frame.fast;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.frame.fast.model.*;
import com.frame.fast.service.coupon.ICouponBatchService;
import com.frame.fast.service.coupon.ICouponService;
import com.frame.fast.service.custom.IMonthProductService;
import com.frame.fast.service.custom.ISingleProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CouponFacade {

    @Resource
    private ICouponService couponService;

    @Resource
    private ICouponBatchService couponBatchService;

    @Resource
    private IMonthProductService customProductService;

    @Resource
    private PayFacade payFacade;

    @Resource
    private ISingleProductService singleProductService;

    @Resource
    private ValueOperations<String,String> valueOperations;
    /**
     * 生成优惠券电子码
     * @param genCount
     * @return
     */
    public List<String> generateCouponCode(Integer genCount){
        List<String> couponCodes = new ArrayList();
        for(int i=0 ; i<genCount ;i++){
            String random = RandomStringUtils.randomAlphabetic(15);
            couponCodes.add(random);
        }
        return couponCodes;
    }

    public void batchGenerateUnbindCoupons(Long couponId,Integer genCount){
        Coupon coupon = couponService.getById(couponId);

        List<String> strings = generateCouponCode(genCount);
        if(CollectionUtils.isEmpty(strings)){
            throw new RuntimeException("生成优惠券电子码异常");
        }
        List<CouponBatch> couponBatches = new ArrayList<>();
        strings.forEach(n->{
            CouponBatch couponBatch = new CouponBatch(coupon);
            couponBatch.setCode(n);
            couponBatches.add(couponBatch);
        });
        couponBatchService.saveBatch(couponBatches);
    }

    public CheckResult activeCoupon(String couponCode, PersonInfo personInfo){
        CheckResult checkResult = new CheckResult();
        CouponBatch couponBatch = couponBatchService.getByCouponCode(couponCode);
        if(couponBatch == null){
            checkResult.setResult(false);
            checkResult.setMsg("兑换码无效");
            return checkResult;
        }

        if(StringUtils.isNotEmpty(couponBatch.getOpenId())){
            checkResult.setResult(false);
            checkResult.setMsg("兑换码已使用");
            return checkResult;
        }
        Coupon coupon = couponService.getById(couponBatch.getCouponId());
        if(coupon == null){
            checkResult.setResult(false);
            checkResult.setMsg("未查询到对应的优惠信息");
            return checkResult;
        }
        if(LocalDateTime.now().isAfter(coupon.getStartDate()) && LocalDateTime.now().isBefore(coupon.getEndDate())){
            couponBatch.setStatus(CouponStatus.VALID);
        }else {
            couponBatch.setStatus(CouponStatus.EXPIRED);
            checkResult.setMsg("兑换码已过期");
            checkResult.setResult(false);
            return checkResult;
        }
        couponBatch.setOpenId(personInfo.getOpenId());
        couponBatch.setCustomId(personInfo.getId());
        couponBatch.setGiveDate(LocalDateTime.now());
        couponBatchService.updateById(couponBatch);
        //激活对应的产品
        ProductSort productSort = coupon.getProductSort();
        //月卡
        if(productSort.getType() == 1) {
            payFacade.validMonthCard(personInfo.getId(),personInfo.getOpenId(),productSort,coupon.getNum());
            valueOperations.set(FastConstant.MONTH_CARD + personInfo.getId(),null);
        }else {
            //单次
            SingleProduct single = singleProductService.getSingle(personInfo.getId(), productSort);
            single = single == null ? new SingleProduct() : single;
            single.setCustomId(personInfo.getId());
            single.setOpenId(personInfo.getOpenId());
            single.setProductSort(productSort);
            single.setRemainNum(coupon.getNum());
            singleProductService.saveOrUpdate(single);
        }
        checkResult.setResult(true);
        checkResult.setMsg("兑换成功");
        return checkResult;
    }
}
