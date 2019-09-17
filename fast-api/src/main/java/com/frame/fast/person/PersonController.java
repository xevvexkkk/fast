package com.frame.fast.person;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.frame.fast.common.ResponseVo;
import com.frame.fast.common.WxSessionEntity;
import com.frame.fast.model.*;
import com.frame.fast.order.SimpleOrder;
import com.frame.fast.pay.constant.WxPayConfig;
import com.frame.fast.service.custom.IMonthProductService;
import com.frame.fast.service.order.IOrderService;
import com.frame.fast.service.person.PersonInfoService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class PersonController {

    private Gson gson = new Gson();

    private RestTemplate restTemplate = new RestTemplate();

    @Resource
    private PersonInfoService personInfoService;

    @Resource
    private IMonthProductService customProductService;
    @Resource
    private IOrderService orderService;
    @Resource
    private ValueOperations<String, String> valueOperations;
    @PostMapping("/saveInfo")
    public String saveInfo(@RequestBody PersonInfo personInfo){
        if(personInfo == null || StringUtils.isEmpty(personInfo.getOpenId())) {
            return gson.toJson(ResponseVo.failVo("personInfo cann`t be null"));
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("open_id",personInfo.getOpenId());
        PersonInfo one = personInfoService.getOne(wrapper);
        if(one != null){
            personInfo.setId(one.getId());
            personInfoService.updateById(personInfo);
        }else{
            personInfo.setNoviceFlag(true);
            personInfoService.save(personInfo);
        }
            System.out.print(personInfo.toString());
            return gson.toJson(ResponseVo.successVo("success",personInfo));
    }

    @GetMapping("/userInfo/{openId}")
    public String getUserInfo(@PathVariable String openId){
        if(StringUtils.isEmpty(openId)){
            return gson.toJson(ResponseVo.failVo("openId can`t be null"));
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("open_id",openId);
        PersonInfo personInfo = (PersonInfo) personInfoService.getOne(wrapper);
        if(personInfo == null){
            return gson.toJson("未查询到注册用户");
        }
        log.info("personInfo is {}",personInfo.toString());
        return gson.toJson(ResponseVo.successVo("success",personInfo));
    }

    @GetMapping("/openId")
    public String getOpenId(String appid,String secret,String js_code,String grant_type){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

// if you need to pass form parameters in request with headers.
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("appid", WxPayConfig.appid);
        map.add("secret", secret);
        map.add("js_code", js_code);
        map.add("grant_type", grant_type);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String result = restTemplate.postForEntity(WxPayConfig.js_session_url, request, String.class).getBody();

        WxSessionEntity wxSessionEntity = gson.fromJson(result,WxSessionEntity.class);
        return gson.toJson(wxSessionEntity);
    }

    @GetMapping("/personal/validMonthProduct")
    public ResponseVo getUserValidOrders(@RequestParam String openId){
        PersonInfo personInfo = personInfoService.getByOpenId(openId);
        if(personInfo == null){

            return ResponseVo.failVo("您的服务已到期");
        }

        String cachedMonthCardInfo = null;
        try {
            cachedMonthCardInfo = valueOperations.get(FastConstant.MONTH_CARD + personInfo.getId());
        } catch (Exception e) {
            log.error("error get cachedMonthCardInfo",e);
        }
        Map customProduct = gson.fromJson(cachedMonthCardInfo,Map.class);
        if(customProduct != null){
            return ResponseVo.successVo(customProduct);
        }
        List<MonthProduct> products = customProductService.getNotFinishedProducts(personInfo.getId());
        if(CollectionUtils.isEmpty(products)){
            return ResponseVo.failVo("您的服务已到期");
        }
        Map<String,String> endDateMap = new HashMap<>();
        LocalDateTime lastClassfyPayDate = null;
        LocalDateTime lastThrowPayDate = null;
        for (MonthProduct product : products) {
            if(product.getCategory().equals(CardCategory.CALSSFY)){
                lastClassfyPayDate = product.getEndEffectDate();
                endDateMap.put("classfyEndDate",lastClassfyPayDate.format(DateTimeFormatter.ISO_DATE));
            }
            if(product.getCategory().equals(CardCategory.THROW)){
                lastThrowPayDate = product.getEndEffectDate();
                endDateMap.put("throwEndDate",lastThrowPayDate.format(DateTimeFormatter.ISO_DATE));
            }
        }
        try {
            valueOperations.set(FastConstant.MONTH_CARD + personInfo.getId(),gson.toJson(endDateMap));
        } catch (Exception e) {
            log.error("error set monthCard info",e);
        }
        return ResponseVo.successVo(endDateMap);

    }

    @GetMapping("/person/orders")
    public ResponseVo getUserAllOrders(@RequestParam String openId){
        PersonInfo personInfo = personInfoService.getByOpenId(openId);
        if (personInfo == null){
            return ResponseVo.failVo("未查询到有效的地址，请在我的地址中预留信息后查询");
        }
        List<Order> orders = orderService.getListByOpenId(personInfo.getId(), LocalDateTime.now().minusDays(180), LocalDateTime.now());
        List<SimpleOrder> orderList = new ArrayList<>();
        orders.forEach(n->orderList.add(new SimpleOrder(n)));
        return ResponseVo.successVo(orderList);
    }

    @GetMapping("/person/card/rc/")
    public ResponseVo refreshCache(@RequestParam String openId){
        PersonInfo personInfo = personInfoService.getByOpenId(openId);
        if(personInfo != null){
            valueOperations.set(FastConstant.MONTH_CARD + personInfo.getId(),null);
        }
        return ResponseVo.successVo("请求成功");
    }
}
