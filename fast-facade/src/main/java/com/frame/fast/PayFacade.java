package com.frame.fast;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.frame.fast.model.*;
import com.frame.fast.service.custom.ICustomProductService;
import com.frame.fast.service.order.IOrderService;
import com.frame.fast.service.person.IPersonAddressPurchaseInfoService;
import com.frame.fast.service.person.PersonInfoService;
import com.frame.fast.util.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
public class PayFacade {

    @Resource
    private IOrderService orderService;

    @Resource
    private PersonInfoService personInfoService;

    @Resource
    private ICustomProductService customProductService;

    @Resource
    private IPersonAddressPurchaseInfoService purchaseInfoService;

    public CheckResult checkBeforeOrder(String openId, String msg, ProductSort productSort){
        CheckResult result = new CheckResult();
        if(StringUtils.isEmpty(openId)){
            msg = "open id can`t be null";
            result.setMsg(msg);
            result.setResult(false);
            return result;
        }
        if(productSort == null){
            msg = "productSort can`t be null";
            result.setMsg(msg);
            result.setResult(false);
            return result;
        }
        PersonInfo personInfo = personInfoService.getByOpenId(openId);
        if(personInfo == null){
            msg = "该用户尚未进行注册";
            result.setMsg(msg);
            result.setResult(false);
            return result;
        }
        String address = personInfo.getAddress();
        if(StringUtils.isEmpty(address)){
            msg = "该用户地址尚未填写";
            result.setMsg(msg);
            result.setResult(false);
            return result;
        }
        if(ProductSort.THROW_MONTH_NEW.equals(productSort)){
            List<PersonAddressPurchaseInfo> purchaseInfos = purchaseInfoService.getByUserIdOrAddress(personInfo.getId(), productSort, personInfo.getCommunity(), personInfo.getArea(), personInfo.getAddress());
            if(CollectionUtils.isNotEmpty(purchaseInfos)){
                msg = "该产品无法重复购买";
                result.setMsg(msg);
                result.setResult(false);
                return result;
            }
        }
        List<CustomProduct> validMonthProduct = customProductService.getValidMonthProduct(personInfo.getId(), productSort);
        if(CollectionUtils.isNotEmpty(validMonthProduct)){
            msg = "已经订购了当前产品，无需重复续费";
            result.setMsg(msg);
            result.setResult(false);
            return result;
        }
        result.setResult(true);
        result.setMsg("验证通过");
        return result;
    }

    public Long generateOrderId(Long userId){
        String currentTime = DateUtils.getNowFormateShortYMDhmsDateString();
        String orderIdStr = currentTime + userId;
        return Long.valueOf(orderIdStr);
    }

    @Transactional
    public void syncOrderInfoAfterPayComplete(Order order){
        //1.订单状态
        orderService.updateById(order);
        //2.新用户标记
        if(OrderStatus.SUCCESS.equals(order.getOrderStatus())){
            PersonInfo personInfo = personInfoService.getByOpenId(order.getOpenId());
            if(ProductSort.THROW_MONTH_NEW.equals(order.getProductSort())){
                personInfo.setNoviceFlag(false);
                personInfoService.updateById(personInfo);
            }
            //3.用户生效产品
            CustomProduct customProduct = customProductService.getByCustomIdAndProductSort(personInfo.getId(),order.getProductSort());
            customProduct = customProduct == null ? new CustomProduct() : customProduct;
            customProduct.setCustomId(personInfo.getId());
            customProduct.setProductSort(order.getProductSort());
            customProduct.setOpenId(personInfo.getOpenId());
            customProduct.setOrderId(order.getOrderId());
            customProduct.setPrepayId(order.getPrepayId());
            int num = customProduct.getRemainNum() == null ? 0 : customProduct.getRemainNum();
            customProduct.setRemainNum(num + order.getProductSort().getNum());
            customProductService.saveOrUpdate(customProduct);
            //4.用户地址购买信息
            PersonAddressPurchaseInfo purchaseInfo = purchaseInfoService.getByUserIdAndAddress(order.getCustomId(), order.getProductSort(), personInfo.getCommunity(), personInfo.getArea(), personInfo.getAddress());
            if(purchaseInfo != null){
                purchaseInfo.setPurchaseNum(purchaseInfo.getPurchaseNum() + 1);
                purchaseInfoService.updateById(purchaseInfo);
            }else {
                purchaseInfo = new PersonAddressPurchaseInfo();
                purchaseInfo.setPurchaseNum(1);
                purchaseInfo.setAddress(personInfo.getAddress());
                purchaseInfo.setCommunity(personInfo.getCommunity());
                purchaseInfo.setArea(personInfo.getArea());
                purchaseInfo.setMobile(personInfo.getMobile());
                purchaseInfo.setOpenId(personInfo.getOpenId());
                purchaseInfo.setProductSort(order.getProductSort());
                purchaseInfo.setUserId(personInfo.getId());
                purchaseInfoService.save(purchaseInfo);
            }

        }
    }
}
