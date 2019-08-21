package com.frame.fast.person;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.frame.fast.common.ResponseVo;
import com.frame.fast.common.WxSessionEntity;
import com.frame.fast.model.CustomProduct;
import com.frame.fast.model.PersonInfo;
import com.frame.fast.service.custom.ICustomProductService;
import com.frame.fast.service.person.PersonInfoService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class PersonController {

    private Gson gson = new Gson();

    private static final String appSecret = "1a41ec237afa26eb4738d45ef4b33a6e";

    private static final String appId = "wxbc1b80f39e9ef6a8";

    private RestTemplate restTemplate = new RestTemplate();

    private static final String wx_api = "https://api.weixin.qq.com/sns/jscode2session";

    @Resource
    private PersonInfoService personInfoService;

    @Resource
    private ICustomProductService customProductService;
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
        log.info("personInfo is {}",personInfo.toString());
        return gson.toJson(ResponseVo.successVo("success",personInfo));
    }

    @GetMapping("/openId")
    public String getOpenId(String appid,String secret,String js_code,String grant_type){
        Map<String,Object> param = new HashMap<>();
        param.put("appid",appId);
        param.put("secret",secret);
        param.put("js_code",js_code);
        param.put("grant_type",grant_type);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

// if you need to pass form parameters in request with headers.
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("appid", appId);
        map.add("secret", secret);
        map.add("js_code", js_code);
        map.add("grant_type", grant_type);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String result = restTemplate.postForEntity(wx_api, request, String.class).getBody();
        WxSessionEntity wxSessionEntity = gson.fromJson(result,WxSessionEntity.class);
        return gson.toJson(wxSessionEntity);
    }

    @GetMapping("/personal/order")
    public ResponseVo getUserValidOrders(@RequestParam String openId){
        PersonInfo personInfo = personInfoService.getByOpenId(openId);
        List<CustomProduct> products = customProductService.getByCustomId(personInfo.getId());
        return ResponseVo.successVo(products);
    }

}
