package com.frame.fast.common;

import com.frame.fast.pay.constant.WxPayConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @RequestMapping("/health")
    public String health(){
        return "heart beating!";
    }

    @GetMapping("/fast/gas")
    public ResponseVo getAppSecret(){
        return ResponseVo.successVo(WxPayConfig.appSecret);
    }
}
