package com.frame.fast;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.frame.fast.model.*;
import com.frame.fast.service.custom.IMonthProductService;
import com.frame.fast.service.custom.ISingleProductService;
import com.frame.fast.service.order.IOrderService;
import com.frame.fast.service.person.IPersonAddressPurchaseInfoService;
import com.frame.fast.service.person.PersonInfoService;
import com.frame.fast.util.DateUtils;
import com.frame.fast.util.MailService;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PayFacade {

    @Resource
    private IOrderService orderService;

    @Resource
    private PersonInfoService personInfoService;

    @Resource
    private IMonthProductService monthProductService;

    @Resource
    private IPersonAddressPurchaseInfoService purchaseInfoService;
    @Resource
    private ValueOperations<String, String> valueOperations;
    @Resource
    private ISingleProductService singleProductService;
    @Resource
    private MailService mailService;
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
            msg = "请您在我的-我的地址中预留信息后再购买";
            result.setMsg(msg);
            result.setResult(false);
            return result;
        }
        String address = personInfo.getAddress();
        if(StringUtils.isEmpty(address)){
            msg = "请您在我的-我的地址中预留信息后再购买";
            result.setMsg(msg);
            result.setResult(false);
            return result;
        }
        if(productSort.getType() == 0){
            msg = "验证通过";
            result.setMsg(msg);
            result.setResult(true);
            return result;
        }
        if(ProductSort.CLASSFY_MONTH_NEW.equals(productSort)){
            List<PersonAddressPurchaseInfo> purchaseInfos = purchaseInfoService.getByUserIdOrAddress(personInfo.getId(), productSort, personInfo.getCommunity().getValue(), personInfo.getArea(), personInfo.getAddress());
            if(CollectionUtils.isNotEmpty(purchaseInfos)){
                msg = "该产品无法重复购买";
                result.setMsg(msg);
                result.setResult(false);
                return result;
            }
        }
        //获取有效期>15天的产品 但是体验包不受此限制
        if(!ProductSort.CLASSFY_MONTH_NEW.equals(productSort)){
            List<MonthProduct> validProducts = monthProductService.getCanNotRepayMonthProducts(personInfo.getId());
            if(CollectionUtils.isNotEmpty(validProducts)){
                result.setMsg("有效期截前15天可以重新购买");
                result.setResult(false);
                return result;
            }
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
            if(ProductSort.CLASSFY_MONTH.equals(order.getProductSort()) || ProductSort.THROW_MONTH.equals(order.getProductSort())){
                personInfo.setNoviceFlag(false);
                personInfoService.updateById(personInfo);
            }
            //3.用户生效产品
            //包月产品 叠加生效日期
            if(order.getProductSort().getType() == 1){
                validMonthCard(personInfo.getId(),personInfo.getOpenId(),order.getProductSort(),null);
            }else if(order.getProductSort().getType() == 0){
                SingleProduct single = singleProductService.getSingle(personInfo.getId(), order.getProductSort());
                if(single == null){
                    single = new SingleProduct();
                    single.setProductSort(order.getProductSort());
                    single.setOpenId(order.getOpenId());
                    single.setCustomId(order.getCustomId());
                    single.setRemainNum(order.getProductSort().getNum());
                    singleProductService.save(single);
                }else {
                    single.setRemainNum(single.getRemainNum() + order.getProductSort().getNum());
                    singleProductService.updateById(single);
                }

            }
            //4.用户地址购买信息
            PersonAddressPurchaseInfo purchaseInfo = purchaseInfoService.getByUserIdAndAddress(order.getCustomId(), order.getProductSort(), personInfo.getCommunity().getValue(), personInfo.getArea(), personInfo.getAddress());
            if(purchaseInfo != null){
                purchaseInfo.setPurchaseNum(purchaseInfo.getPurchaseNum() + 1);
                purchaseInfoService.updateById(purchaseInfo);
            }else {
                purchaseInfo = new PersonAddressPurchaseInfo();
                purchaseInfo.setPurchaseNum(1);
                purchaseInfo.setAddress(personInfo.getAddress());
                purchaseInfo.setCommunity(personInfo.getCommunity().getValue());
                purchaseInfo.setArea(personInfo.getArea());
                purchaseInfo.setMobile(personInfo.getMobile());
                purchaseInfo.setOpenId(personInfo.getOpenId());
                purchaseInfo.setProductSort(order.getProductSort());
                purchaseInfo.setUserId(personInfo.getId());
                purchaseInfoService.save(purchaseInfo);
            }
            valueOperations.set(FastConstant.MONTH_CARD + personInfo.getId(),null);
            mailService.sendTextMail(FastConstant.ORDER_NOTIFY_MAIN_LIST[0],FastConstant.ORDER_NOTIFY_TITLE,FastConstant.getOrderNotifyContent(order.getProductSort(),personInfo.getName(),personInfo.getMobile(),personInfo.getCommunity().getValue(),personInfo.getAddress()));
        }
    }

    /**
     * 月卡激活
     * @param userId
     * @param openId
     * @param productSort
     * @param assignDays 如果传入该参数，则激活天数以此为准
     */
    public void validMonthCard(Long userId,String openId,ProductSort productSort,Integer assignDays){
        Integer days = assignDays == null ? productSort.getNum() : assignDays;
        CardCategory category = CardCategory.getCategory(productSort);
        MonthProduct monthProduct = monthProductService.getByCustomIdAndCategory(userId,productSort);
        monthProduct = monthProduct == null ? new MonthProduct() : monthProduct;
        monthProduct.setCustomId(userId);
        monthProduct.setCategory(CardCategory.getCategory(productSort));
        monthProduct.setOpenId(openId);
        List<MonthProduct> cardProducts = monthProductService.getByCustomId(userId);
        List<MonthProduct> notFinishedProducts = cardProducts.stream().filter(n->n.getRemainNum() >0).collect(Collectors.toList());
        //有正在生效的月卡
        if(CollectionUtils.isNotEmpty(notFinishedProducts)){
            //获取正在生效的月卡
            MonthProduct validProduct = cardProducts.stream().filter(n->n.getStatus().equals(MonthCardStatus.VALID)).collect(Collectors.toList()).get(0);
            //一般情况下 不应该存在待生效的另一种月卡 ，否则购买前校验不能通过，所以invalidProduct的remain_num = 0 但是优惠券直接激活的情况下需要支持对待生效月卡直接续费
            Optional<MonthProduct> invalidProduct= cardProducts.stream().filter(n->n.getStatus().equals(MonthCardStatus.INVALID)).findAny();

            //正在生效月卡和正在购买的月卡类型一致
            if(validProduct.getCategory().equals(category)){
                monthProduct.setStatus(MonthCardStatus.VALID);
                monthProduct.setEndEffectDate(validProduct.getEndEffectDate().plusDays(days));
                monthProduct.setRemainNum(validProduct.getRemainNum() + days);
                monthProduct.setId(validProduct.getId());
                //存在待生效的产品 则待生效产品生效期顺延
                if(invalidProduct.filter(product -> product.getRemainNum() > 0).isPresent()){
                    LocalDateTime endEffectDate = invalidProduct.get().getEndEffectDate().plusDays(days);

                    invalidProduct.get().setEndEffectDate(endEffectDate);
                    monthProductService.updateById(invalidProduct.get());
                }
                monthProductService.saveOrUpdate(monthProduct);

            }else {
                //正在生效月卡和正在购买月卡类型不一致
                //如果有待生效月卡 则叠加待生效月卡时间
                if(invalidProduct.filter(product -> product.getRemainNum() > 0).isPresent()){
                    LocalDateTime endEffectDate = invalidProduct.get().getEndEffectDate().plusDays(days);

                    invalidProduct.get().setEndEffectDate(endEffectDate);
                    monthProductService.updateById(invalidProduct.get());
                }else {
                    //如果没有待生效月卡 则该月卡直接在当前生效月卡失效后生效
                    monthProduct.setEndEffectDate(validProduct.getEndEffectDate().plusDays(days));
                    monthProduct.setRemainNum(invalidProduct.map(product -> product.getRemainNum() + days).orElse(days));
                    monthProduct.setStatus(MonthCardStatus.INVALID);
                    monthProduct.setId(invalidProduct.map(product -> product.getId()).orElse(null));
                    monthProductService.saveOrUpdate(monthProduct);
                }

            }
        }else {

            //无生效月卡 ，则直接生效该月卡
            monthProduct.setEndEffectDate(LocalDateTime.now().plusDays(days));
            int num = monthProduct.getRemainNum() == null ? 0 : monthProduct.getRemainNum();
            monthProduct.setRemainNum(num + days);
            monthProduct.setStatus(MonthCardStatus.VALID);
            monthProductService.saveOrUpdate(monthProduct);

        }
    }
}
